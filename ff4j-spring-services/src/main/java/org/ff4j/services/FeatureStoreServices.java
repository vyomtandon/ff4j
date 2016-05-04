package org.ff4j.services;

/*
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2016 FF4J
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


import org.apache.commons.lang3.StringUtils;
import org.ff4j.FF4j;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.core.Feature;
import org.ff4j.services.domain.CacheApiBean;
import org.ff4j.services.domain.FeatureApiBean;
import org.ff4j.services.domain.FeatureStoreApiBean;
import org.ff4j.services.domain.GroupDescApiBean;
import org.ff4j.services.exceptions.FeatureStoreNotCached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class FeatureStoreServices {
    @Autowired
    private FF4j ff4j;

    public FeatureStoreApiBean getFeatureStore() {
        return new FeatureStoreApiBean(ff4j.getFeatureStore());
    }

    public Collection<FeatureApiBean> getAllFeatures() {
        List<FeatureApiBean> features;
        Map<String, Feature> featureMap = ff4j.getFeatureStore().readAll();
        if (CollectionUtils.isEmpty(featureMap)) {
            features = new ArrayList<>(0);
        } else {
            features = new ArrayList<>(featureMap.size());
            features.addAll(featureMap.values().stream().map(FeatureApiBean::new).collect(Collectors.toList()));
        }
        return features;
    }

    public Collection<GroupDescApiBean> getAllGroups() {
        Map<String, GroupDescApiBean> groups = new HashMap<>();
        Map<String, Feature> featureMap = ff4j.getFeatureStore().readAll();
        if (!CollectionUtils.isEmpty(featureMap)) {
            for (Feature feature : featureMap.values()) {
                initGroupMap(groups, feature.getUid(), feature.getGroup());
            }
        }
        return groups.values();
    }

    private void initGroupMap(Map<String, GroupDescApiBean> groups, String featureUID, String groupName) {
        if (StringUtils.isNotBlank(groupName)) {
            if (!groups.containsKey(groupName)) {
                groups.put(groupName, new GroupDescApiBean(groupName, new ArrayList<>()));
            }
            groups.get(groupName).getFeatures().add(featureUID);
        }
    }

    public void deleteAllFeatures() {
        ff4j.getFeatureStore().clear();
    }

    public CacheApiBean getFeaturesFromCache() {
        if (ff4j.getFeatureStore() instanceof FF4jCacheProxy) {
            return new CacheApiBean(ff4j.getFeatureStore());
        } else {
            throw new FeatureStoreNotCached();
        }
    }

    public void clearCachedFeatureStore() {
        if (ff4j.getFeatureStore() instanceof FF4jCacheProxy) {
            ((FF4jCacheProxy) ff4j.getFeatureStore()).getCacheManager().clearFeatures();
        } else {
            throw new FeatureStoreNotCached();
        }
    }
}
