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

import com.alienos.guice.GuiceVertxBinding;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.alienos.guice.GuiceVerticleHelper;
import org.vertx.java.platform.Verticle;
import org.vertx.testtools.VertxAssert;

/**
 * A raw verticle we inject semi-manually if we have blown inheritance for some reason..
 */
@GuiceVertxBinding(modules = {SomeModule.class})
public class RawVerticle extends Verticle {

    @Inject
    private SomeInjectedClass injected;

    @Inject
    @Named("foo")
    private String configParamFoo;


    @Inject
    private SomeProvidedClass providedClass;


    @Override
    public void start() {
        GuiceVerticleHelper.inject(this, vertx, container);
        //Same tests as the extended verticle..

        VertxAssert.initialize(vertx);
        // You can also assert from other verticles!!
        VertxAssert.assertEquals("foo", "foo");
        VertxAssert.assertNotNull(injected);
        VertxAssert.assertNotNull(configParamFoo);
        VertxAssert.assertEquals("bar", configParamFoo);

        VertxAssert.assertNotNull(providedClass);
        VertxAssert.assertEquals("bar", providedClass.getConfig());

        VertxAssert.testComplete();


    }

}
