package org.ff4j.store.it;

/*
 * #%L
 * ff4j-store-redis
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


import org.ff4j.property.store.PropertyStore;
import org.ff4j.store.PropertyStoreRedis;
import org.ff4j.test.propertystore.PropertyStoreTestSupport;
import org.junit.Ignore;

/**
 * Implementatino of tests with REDIS
 * @author Cedrick Lunven (@clunven)</a>
 */
@Ignore
public class PropertyStoreRedisTestIT extends PropertyStoreTestSupport {

    /** {@inheritDoc} */
    protected PropertyStore initPropertyStore() {
       return new PropertyStoreRedis("test-ff4j-features.xml");
    }

}
