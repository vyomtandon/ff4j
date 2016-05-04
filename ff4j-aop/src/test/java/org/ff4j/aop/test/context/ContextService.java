package org.ff4j.aop.test.context;

import org.ff4j.aop.ContextLocation;
import org.ff4j.aop.Flip;

/*
 * #%L
 * ff4j-aop
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


import org.ff4j.core.FlippingExecutionContext;

public interface ContextService {
    
    @Flip(name = "context-french", alterBean = "context.french",
            strategy = ContextStrategy.class, contextLocation = ContextLocation.FF4J)
    String sayHelloWithThreadLocal(String name);

    @Flip(name = "context-french", alterBean = "context.french",
            strategy = ContextStrategy.class, contextLocation = ContextLocation.PARAMETER)
    String sayHelloWithParameter(String name, FlippingExecutionContext context);
}
