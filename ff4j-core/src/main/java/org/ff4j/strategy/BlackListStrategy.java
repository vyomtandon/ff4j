package org.ff4j.strategy;

/*
 * #%L
 * ff4j-core
 * %%
 * Copyright (C) 2013 - 2015 Ff4J
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

import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;

/**
 * BLOCK acces for defined list of Clients.
 *
 * @author Cedrick Lunven (@clunven)
 */
public class BlackListStrategy extends ClientFilterStrategy {

    /**
     * Default Constructor.
     */
    public BlackListStrategy() {
        super();
    }

    /**
     * Parameterized constructor.
     * 
     * @param threshold
     *            threshold
     */
    public BlackListStrategy(String clientList) {
        super(clientList);
    }
    
   /**
    * {@inheritDoc}
    */
    @Override
    public boolean evaluate(String featureName, FeatureStore store, FlippingExecutionContext executionContext) {
        return !super.evaluate(featureName, store, executionContext);
    }
}
