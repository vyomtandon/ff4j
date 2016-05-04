package org.ff4j.propertystore;

/*
 * #%L
 * ff4j-store-jcache
 * %%
 * Copyright (C) 2013 - 2015 FF4J
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
import org.ff4j.store.PropertyStoreJCache;
import org.ff4j.test.propertystore.PropertyStoreTestSupport;
import org.jsr107.ri.spi.RICachingProvider;

/**
 * Test to work with Redis as a store.
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
public class PropertyStoreJCacheTestRICache extends PropertyStoreTestSupport {
   
    /** {@inheritDoc} */
    @Override
    protected PropertyStore initPropertyStore() {
        PropertyStoreJCache riCacheStore = new PropertyStoreJCache(RICachingProvider.class.getName());
        riCacheStore.importPropertiesFromXmlFile("ff4j.xml");
        return riCacheStore;
    }  

}
