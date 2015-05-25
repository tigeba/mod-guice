/**
 * Copyright 2013 Jonathan Wagner
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alienos.guice.integration.java;

import com.alienos.guice.GuiceVertxBinding;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;

@GuiceVertxBinding(modules = {SomeModule.class})
public class SomeVerticle extends AbstractVerticle {

    @Inject
    private SomeInjectedClass injected;
    @Inject
    @Named("foo")
    private String configParamFoo;
    @Inject
    private SomeProvidedClass providedClass;

    @Override
    public void start() {

        TestContext context = TestContextHelper.getContext();

        context.assertEquals("foo", "foo");
        context.assertNotNull(injected);
        context.assertNotNull(configParamFoo);
        context.assertEquals("bar", configParamFoo);
        context.assertNotNull(providedClass);
        context.assertEquals("bar", providedClass.getConfig());
    }
}
