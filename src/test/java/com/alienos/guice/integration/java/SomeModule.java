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

import com.alienos.guice.VertxModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import io.vertx.core.Vertx;

public class SomeModule implements VertxModule {

    private Vertx vertx;

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(SomeInjectedClass.class);
        binder.bindConstant().annotatedWith(Names.named("foo")).to("bar");
    }


    @Provides
    public SomeProvidedClass provideClass(@Named("foo") String config) {
        return new SomeProvidedClass(config);
    }

}
