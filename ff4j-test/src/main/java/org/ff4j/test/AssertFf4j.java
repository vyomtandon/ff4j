package org.ff4j.test;

import java.util.Map;

/*
 * #%L
 * ff4j-core
 * %%
 * Copyright (C) 2013 Ff4J
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

import org.ff4j.FF4j;
import org.ff4j.property.Property;
import org.junit.Assert;

/**
 * Give utilities method for tests.
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
public class AssertFf4j {

	/** error message. */
    public static final String IS_MANDATORY = "' is mandatory";
    
	/** error message. */
    public static final String FEATURE = "Feature '";
    
    /** reference to ff4j context. */
    private final FF4j ff4j;

    /**
     * Initialisation with current ff4j context.
     * 
     * @param ff4j
     *            current ff4k context
     */
    public AssertFf4j(FF4j cff4j) {
        this.ff4j = cff4j;
    }

    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureExist(String featureName) {
        Assert.assertTrue(FEATURE + featureName + IS_MANDATORY, ff4j.exist(featureName));
        return this;
    }
    
    /**
     * Check existence of the traget property
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatPropertyExist(String propertyName) {
        Assert.assertTrue("Property '" + propertyName + IS_MANDATORY, ff4j.getPropertiesStore().existProperty(propertyName));
        return this;
    }

    /**
     * Check inexistence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureDoesNotExist(String featureName) {
        Assert.assertFalse(FEATURE + featureName + "' must not exist", ff4j.exist(featureName));
        return this;
    }
    

    /**
     * Check existence of the traget property
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatPropertyDoesNotExist(String propertyName) {
        Assert.assertFalse("Property '" + propertyName + IS_MANDATORY, ff4j.getPropertiesStore().existProperty(propertyName));
        return this;
    }

    /**
     * Check Feature Flipped
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatFeatureFlipped(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertTrue("'" + featureName + "' is not flipped where it should", ff4j.check(featureName));
        return this;
    }

    /**
     * Check Feature Flipped
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatFeatureNotFlipped(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertFalse("'" + featureName + "' is flipped where it shouldn't", ff4j.check(featureName));
        return this;
    }

    /**
     * Check Feature Allowed.
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatCurrentUserIsAllowedOnFeature(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertTrue(ff4j.isAllowed(ff4j.getFeature(featureName)));
        return this;
    }

    /**
     * Check Feature Allowed.
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatCurrentUserIsNotAllowedOnFeature(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertFalse(ff4j.isAllowed(ff4j.getFeature(featureName)));
        return this;
    }

    /**
     * Check Number of features
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatStoreHasSize(int expectedNumber) {
        Assert.assertEquals(expectedNumber, ff4j.getFeatureStore().readAll().size());
        return this;
    }
    
    /**
     * Check Number of features
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatPropertyStoreHasSize(int expectedNumber) {
        Assert.assertEquals(expectedNumber, ff4j.getPropertiesStore().listPropertyNames().size());
        return this;
    }

    /**
     * Check Number of features
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatStoreHasNumberOfGroups(int expectedNumber) {
        Assert.assertEquals(expectedNumber, ff4j.getFeatureStore().readAllGroups().size());
        return this;
    }

    /**
     * Check that feature exists and have expected role.
     * 
     * @param featureName
     *            target feature Name
     * @param roleName
     *            target role name
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasRole(String featureName, String roleName) {
        assertThatFeatureExist(featureName);
        Assert.assertTrue("'" + featureName + "' has no roles", !ff4j.getFeature(featureName).getPermissions().isEmpty());
        Assert.assertTrue("'" + featureName + "' has not role '" + roleName + "'", 
                ff4j.getFeature(featureName).getPermissions().contains(roleName));
        return this;
    }

    /**
     * Check that feature exists and does not have expected role.
     * 
     * @param featureName
     *            target feature Name
     * @param roleName
     *            target role name
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasNotRole(String featureName, String roleName) {
        assertThatFeatureExist(featureName);
        if (null != ff4j.getFeature(featureName).getPermissions()) {
            Assert.assertFalse("Feature must no contain role " + roleName, ff4j.getFeature(featureName).getPermissions().contains(roleName));
        }
        return this;
    }

    /**
     * Check that feature is in expected group.
     * 
     * @param featureName
     *            target feature Name
     * @param roleName
     *            target role name
     * @return current object
     */
    public final AssertFf4j assertThatFeatureIsInGroup(String featureName, String groupName) {
        assertThatFeatureExist(featureName);
        String group = ff4j.getFeature(featureName).getGroup();
        Assert.assertTrue("'" + featureName + "' must be in group '" + 
                groupName + "' but is in <" + group + ">", 
                group != null && groupName.equals(group));
        return this;
    }

    /**
     * Check that feature is in expected group.
     * 
     * @param featureName
     *            target feature Name
     * @param roleName
     *            target role name
     * @return current object
     */
    public final AssertFf4j assertThatFeatureNotInGroup(String featureName, String groupName) {
        assertThatFeatureExist(featureName);
        String group = ff4j.getFeature(featureName).getGroup();
        Assert.assertTrue(group == null || !groupName.equals(group));
        return this;
    }

    /**
     * Chack that feature is enabled in current store.
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatFeatureIsEnabled(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertTrue(ff4j.getFeatureStore().read(featureName).isEnable());
        return this;
    }

    /**
     * Chack that feature is disabled in current store.
     * 
     * @param featureName
     *            target featureName
     * @return current object
     */
    public final AssertFf4j assertThatFeatureIsDisabled(String featureName) {
        assertThatFeatureExist(featureName);
        Assert.assertFalse("'" + featureName + "' must be disabled", ff4j.getFeatureStore().read(featureName).isEnable());
        return this;
    }

    /**
     * Check Group Size
     * 
     * @param expected
     *            expected value for size
     * @param groupName
     *            target groupName
     * @return current object
     */
    public final AssertFf4j assertThatGroupExist(String groupName) {
        Assert.assertTrue("Group '" + groupName + " ' does no exist", ff4j.getFeatureStore().existGroup(groupName));
        return this;
    }

    /**
     * Check that group does not exist
     * 
     * @param expected
     *            expected value for size
     * @param groupName
     *            target groupName
     * @return current object
     */
    public AssertFf4j assertThatGroupDoesNotExist(String groupName) {
        Assert.assertFalse("Group '" + groupName + " ' does no exist", ff4j.getFeatureStore().existGroup(groupName));
        return this;
    }

    /**
     * Check Group Size
     * 
     * @param expected
     *            expected value for size
     * @param groupName
     *            target groupName
     * @return current object
     */
    public final AssertFf4j assertThatGroupHasSize(int expected, String groupName) {
        assertThatGroupExist(groupName);
        Assert.assertEquals(expected, ff4j.getFeatureStore().readGroup(groupName).size());
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasFlippingStrategy(String featureName) {
        Assert.assertNotNull(FEATURE + featureName + "' must have a FlippingStrategy but doesn't", ff4j.getFeature(featureName).getFlippingStrategy());
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureDoesNotHaveFlippingStrategy(String featureName) {
        Assert.assertNull(FEATURE + featureName + "' must not have a flipping strategy", ff4j.getFeature(featureName).getFlippingStrategy());
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasProperties(String featureName) {
        assertThatFeatureExist(featureName);
        Map < String, Property<?>> properties = ff4j.getFeature(featureName).getCustomProperties();
        Assert.assertTrue("Properties are required",  (properties != null) && (properties.size() > 0) );
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureDoesNotHaveProperties(String featureName) {
        assertThatFeatureExist(featureName);
        Map < String, Property<?>> properties = ff4j.getFeature(featureName).getCustomProperties();
        Assert.assertTrue("Properties are required",  (properties == null) || properties.isEmpty());
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasProperty(String featureName, String propertyName) {
        assertThatFeatureHasProperties(featureName);
        Map < String, Property<?>> properties = ff4j.getFeature(featureName).getCustomProperties();
        Assert.assertTrue("Feature must contain property " + propertyName, properties.containsKey(propertyName));
        return this;
    }
    
    /**
     * Check existence of the traget feature
     * 
     * @param featureName
     *            targte featurename
     * @return current object
     */
    public final AssertFf4j assertThatFeatureHasNotProperty(String featureName, String propertyName) {
        assertThatFeatureExist(featureName);
        Map < String, Property<?>> properties = ff4j.getFeature(featureName).getCustomProperties();
        Assert.assertTrue("Feature must contain property " + propertyName, (properties == null) || !properties.containsKey(propertyName));
        return this;
    }

}
