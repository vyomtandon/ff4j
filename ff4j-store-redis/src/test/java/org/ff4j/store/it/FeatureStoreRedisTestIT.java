package org.ff4j.store.it;

/*
 * #%L
 * ff4j-store-redis
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

import java.util.Map;

import org.ff4j.core.Feature;
import org.ff4j.core.FeatureStore;
import org.ff4j.store.FeatureStoreRedis;
import org.ff4j.test.store.FeatureStoreTestSupport;
import org.junit.After;
import org.junit.Ignore;

/**
 * Test to work with Redis as a store.
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
@Ignore
public class FeatureStoreRedisTestIT extends FeatureStoreTestSupport {
   
    /** {@inheritDoc} */
    @Override
    protected FeatureStore initStore() {
        FeatureStoreRedis redisStore = new FeatureStoreRedis();
        redisStore.importFeaturesFromXmlFile("ff4j.xml");
        
        /*redisStore.create(new Feature("AwesomeFeature", true, "some desc"));
        redisStore.create(new Feature("first", true, "description", null, Arrays.asList("USER")));
        redisStore.create(new Feature("second", false, "description", "GRP0", Arrays.asList("USER")));
        redisStore.create(new Feature("third", false, "ThirdJDBC", "GRP1", Arrays.asList("ADMINISTRATOR", "BETA-TESTER")));
        FlippingStrategy strategy = new org.ff4j.strategy.el.ExpressionFlipStrategy();
        strategy.init("forth", ParameterUtils.toMap("expression=third|second"));
        redisStore.create(new Feature("forth", true, "ForthJDBC", "GRP1", Arrays.asList("ADMINISTRATOR", "BETA-TESTER"),strategy));*/
        return redisStore;
    }
    
    /**
     * Clean store after each test (avoid duplication)
     */
    @After
    public void cleanStore() {
        Map < String, Feature > f = testedStore.readAll();
        for (String key : f.keySet()) {
            testedStore.delete(key);
        }
    }

}
