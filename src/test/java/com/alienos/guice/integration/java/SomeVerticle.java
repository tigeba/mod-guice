/***
 * Copyright 2013 Jonathan Wagner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alienos.guice.integration.java;

import com.alienos.guice.GuiceVerticle;
import com.alienos.guice.GuiceVertxBinding;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.vertx.testtools.VertxAssert;

@GuiceVertxBinding(modules = {SomeModule.class})
public class SomeVerticle extends GuiceVerticle {

    @Inject
    private SomeInjectedClass injected;
    @Inject
    @Named("foo")
    private String configParamFoo;
    @Inject
    private SomeProvidedClass providedClass;

    public void onStart() {
        VertxAssert.initialize(vertx);

        VertxAssert.assertEquals("foo", "foo");
        VertxAssert.assertNotNull(injected);
        VertxAssert.assertNotNull(configParamFoo);
        VertxAssert.assertEquals("bar", configParamFoo);
        VertxAssert.assertNotNull(providedClass);
        VertxAssert.assertEquals("bar", providedClass.getConfig());
        VertxAssert.testComplete();
    }
}
