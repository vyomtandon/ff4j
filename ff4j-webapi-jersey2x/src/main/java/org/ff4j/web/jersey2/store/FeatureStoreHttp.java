package org.ff4j.web.jersey2.store;

/*
 * #%L
 * ff4j-web
 * %%
 * Copyright (C) 2013 - 2014 Ff4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.ff4j.utils.json.FeatureJsonParser.parseFeature;
import static org.ff4j.utils.json.FeatureJsonParser.parseFeatureArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ff4j.core.Feature;
import org.ff4j.exception.FeatureAccessException;
import org.ff4j.exception.FeatureAlreadyExistException;
import org.ff4j.exception.FeatureNotFoundException;
import org.ff4j.exception.GroupNotFoundException;
import org.ff4j.store.AbstractFeatureStore;
import org.ff4j.utils.Util;
import org.ff4j.web.api.FF4jJacksonMapper;
import org.ff4j.web.api.resources.domain.FeatureApiBean;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.internal.util.Base64;

import io.swagger.jaxrs.json.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.ff4j.web.FF4jWebConstants.*;

/**
 * Implementation of store using {@link HttpClient} connection.
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
public class FeatureStoreHttp extends AbstractFeatureStore {

    /** logger for this class. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Jersey Client. */
    protected Client client = null;

    /** Property to get url ROOT. */
    private String url = null;
    
    /** header parameter to add if secured mode enabled. */
    private String authorization = null;

    /** Target jersey resource. */
    private WebTarget storeWebRsc = null;

    /** Target jersey resource. */
    private WebTarget groupsWebRsc = null;

    /**
     * Default construtor
     */
    public FeatureStoreHttp() {}

    /**
     * Initialization from URL.
     *
     * @param rootApiUrl target root URL
     */
    public FeatureStoreHttp(String rootApiUrl) {
        this.url = rootApiUrl;
    }
    
    /**
     * Authentication through APIKEY.
     *
     * @param rootApiUrl target url
     * @param apiKey target api
     */
    public FeatureStoreHttp(String rootApiUrl, String apiKey) {
        this(rootApiUrl);
        this.authorization = buildAuthorization4ApiKey(apiKey);
    }
    
    /**
     * Authentication through login/password.
     *
     * @param rootApiUrl target url
     * @param username target username
     * @param password target password
     */
    public FeatureStoreHttp(String rootApiUrl, String username, String password) {
        this(rootApiUrl);
        this.authorization = buildAuthorization4UserName(username, password);
    }

    /**
     * Initializing jerseyClient.
     */
    private void initJerseyClient() {
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.register(JacksonJsonProvider.class);
            clientConfig.register(FF4jJacksonMapper.class);
            client = ClientBuilder.newClient(clientConfig);
        }
        if (url == null) {
            throw new IllegalArgumentException("Cannot initialialize Jersey Client : please provide store URL in 'url' attribute");
        }
    }

    /**
     * Get access to store web resource.
     * 
     * @return target web resource
     */
    private WebTarget getStore() {
        if (storeWebRsc == null) {
            initJerseyClient();
            storeWebRsc = client.target(url).path(RESOURCE_STORE).path(RESOURCE_FEATURES);
        }
        return storeWebRsc;
    }

    /**
     * Get access to groups web resource.
     * 
     * @return target web resource
     */
    private WebTarget getGroups() {
        if (groupsWebRsc == null) {
            initJerseyClient();
            groupsWebRsc = client.target(url).path(RESOURCE_STORE).path(RESOURCE_GROUPS);
        }
        return groupsWebRsc;
    }

    /** {@inheritDoc} */
    @Override
    public Feature read(String uid) {
        Util.assertHasLength(uid);
        Response cRes = getStore().path(uid).request(MediaType.APPLICATION_JSON_TYPE).get();
        log.info(String.valueOf(getStore().path(uid)));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        return parseFeature(cRes.readEntity(String.class));
    }

    /** {@inheritDoc} */
    @Override
    public void enable(String uid) {
        Util.assertHasLength(uid);
        Response cRes = post(getStore().path(uid).path(OPERATION_ENABLE));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void disable(String uid) {
        Util.assertHasLength(uid);
        Response cRes = post(getStore().path(uid).path(OPERATION_DISABLE));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean exist(String uid) {
        Util.assertHasLength(uid);
        Response cRes = getStore().path(uid).request(MediaType.APPLICATION_JSON_TYPE).get();
        if (Status.OK.getStatusCode() == cRes.getStatus()) {
            return true;
        }
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            return false;
        }
        throw new FeatureAccessException("Cannot check existence of feature, an HTTP error " + cRes.getStatus() + " occured : " + cRes.getEntity());
    }

    /** {@inheritDoc} */
    @Override
    public void create(Feature fp) {
        if (fp == null) {
            throw new IllegalArgumentException("Feature cannot be null nor empty");
        }
        if (exist(fp.getUid())) {
            throw new FeatureAlreadyExistException(fp.getUid());
        }
        // Now can process upsert through PUT HTTP method
        Response cRes = getStore().path(fp.getUid())//
                .request(MediaType.APPLICATION_JSON) //
                .put(Entity.entity(new FeatureApiBean(fp), MediaType.APPLICATION_JSON));
        // Check response code CREATED or raised error
        if (Status.CREATED.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot create feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Feature> readAll() {
        
        Response cRes = getStore().request(MediaType.APPLICATION_JSON_TYPE).get();
        if (Status.OK.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot read features, an HTTP error " + cRes.getStatus() + " occured.");
        }
       
        String resEntity = (String) cRes.readEntity(String.class);
        Feature[] fArray = parseFeatureArray(resEntity);
        Map<String, Feature> features = new HashMap<>();
        for (Feature feature : fArray) {
            features.put(feature.getUid(), feature);
        }
        return features;
    }

    /** {@inheritDoc} */
    @Override
    public void delete(String uid) {
        Util.assertHasLength(uid);
        Response cRes = getStore().path(uid).request().delete();
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot delete feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void update(Feature fp) {
        if (fp == null) {
            throw new IllegalArgumentException("Feature cannot be null nor empty");
        }
        if (!exist(fp.getUid())) {
            throw new FeatureNotFoundException(fp.getUid());
        }
        Response cRes = getStore().path(fp.getUid()) //
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(new FeatureApiBean(fp), MediaType.APPLICATION_JSON));
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot update feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void grantRoleOnFeature(String uid, String roleName) {
        Util.assertHasLength(uid, roleName);
        Response cRes = post(getStore().path(uid).path(OPERATION_GRANTROLE).path(roleName));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot grant role on feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void removeRoleFromFeature(String uid, String roleName) {
        Util.assertHasLength(uid, roleName);
        Response cRes = post(getStore().path(uid).path(OPERATION_REMOVEROLE).path(roleName));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot remove role on feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addToGroup(String uid, String groupName) {
        Util.assertHasLength(uid, groupName);
        
        Response cRes = post(getStore().path(uid).path(OPERATION_ADDGROUP).path(groupName));
       
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot add feature to group, an HTTP error " + cRes.getStatus() + " occured.");
        }
        
    }

    /** {@inheritDoc} */
    @Override
    public void removeFromGroup(String uid, String groupName) {
        Util.assertHasLength(uid, groupName);
        Response cRes = post(getStore().path(uid).path(OPERATION_REMOVEGROUP).path(groupName));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new FeatureNotFoundException(uid);
        }
        if (Status.BAD_REQUEST.getStatusCode() == cRes.getStatus()) {
            throw new GroupNotFoundException(groupName);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot remove feature from group, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void enableGroup(String groupName) {
        Util.assertHasLength(groupName);
        Response cRes = post(getGroups().path(groupName).path(OPERATION_ENABLE));
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new GroupNotFoundException(groupName);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot grant role on feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void disableGroup(String groupName) {
        Util.assertHasLength(groupName);
        Response cRes = post(getGroups().path(groupName).path(OPERATION_DISABLE));
        
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new GroupNotFoundException(groupName);
        }
        if (Status.NO_CONTENT.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot grant role on feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
    }
   

    /** {@inheritDoc} */
    public Map<String, Feature> readGroup(String groupName) {
        Util.assertHasLength(groupName);
        Response cRes = getGroups().path(groupName).request(MediaType.APPLICATION_JSON).get();
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            throw new GroupNotFoundException(groupName);
        }
        if (Status.OK.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot grant role on feature, an HTTP error " + cRes.getStatus() + " occured.");
        }
        String resEntity = cRes.readEntity(String.class);
        Feature[] fArray = parseFeatureArray(resEntity);
        Map<String, Feature> features = new HashMap<>();
        for (Feature feature : fArray) {
            features.put(feature.getUid(), feature);
        }
        return features;
    }

    /** {@inheritDoc} */
    @Override
    public boolean existGroup(String groupName) {
        Util.assertHasLength(groupName);
        Response cRes = getGroups().path(groupName).request(MediaType.APPLICATION_JSON).get();
        if (Status.OK.getStatusCode() == cRes.getStatus()) {
            return true;
        }
        if (Status.NOT_FOUND.getStatusCode() == cRes.getStatus()) {
            return false;
        }
        throw new FeatureAccessException("Cannot check existence of group , an HTTP error " + cRes.getStatus() + " occured.");
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public Set<String> readAllGroups() {
        Response cRes = getGroups().request(MediaType.APPLICATION_JSON).get();
        List < Map < String, String>> groupList = cRes.readEntity(List.class);
        if (Status.OK.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot read groups, an HTTP error " + cRes.getStatus() + " occured.");
        }
        Set < String > groupNames = new HashSet<>();
        for (Map <String, String > currentGroup : groupList) {
            groupNames.add(currentGroup.get("groupName"));
        }
        return groupNames;
    }
    
    /** {@inheritDoc} */
    @Override
    public void clear() {
        WebTarget wr = client.target(url).path(RESOURCE_STORE).path(STORE_CLEAR);
        Response cRes = post(wr);
        if (Status.OK.getStatusCode() != cRes.getStatus()) {
            throw new FeatureAccessException("Cannot clear feature store - " + cRes.getStatus());
        }
    }
    
    // ------- Static for authentication -------
    
    /**
     * Build Authorization header for final user.
     * @param username target username
     * @param password target password
     * @return target header
     */
    public static String buildAuthorization4UserName(String username, String password) {
        return " Basic " + new String(Base64.encodeAsString(username + ":" + password));
    }
    
    
    /**
     * Share header settings for invocations.
     *
     * @param webTarget target web
     * @return
     */
    private Response post(WebTarget webTarget) {
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        if (null != authorization) {
            invocationBuilder.header(HEADER_AUTHORIZATION, authorization);
        }
        return invocationBuilder.post(Entity.text(""));
    }
    
    /**
     * Build Authorization header for technical user.
     * @param apiKey target apiKey
     * @return target header
     */
    public static String buildAuthorization4ApiKey(String apiKey) {
        return PARAM_AUTHKEY + "=" + apiKey;
    }

    /**
     * Getter accessor for attribute 'url'.
     *
     * @return current value of 'url'
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter accessor for attribute 'url'.
     * @param url new value for 'url '
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
