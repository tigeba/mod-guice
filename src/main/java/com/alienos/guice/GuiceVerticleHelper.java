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
package com.alienos.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;

import java.util.ArrayList;
import java.util.List;

public class GuiceVerticleHelper {

    /**
     * Use the static inject method if your Verticle cannot inherit from GuiceVerticle.
     *
     * @param verticle  Your verticle
     * @param vertx     A Vertx instance
     * @param container A container instance
     */
    public static void inject(Verticle verticle, Vertx vertx, Container container) {
        GuiceVertxBinding modAnnotation = verticle.getClass().getAnnotation(GuiceVertxBinding.class);

        if (null == modAnnotation) {
            String msg = "GuiceVerticle " + verticle.getClass().getName() + " did not declare a Module with @VertxModule()";
            container.logger().error(msg, new Exception(msg));
        }

        if (null == modAnnotation.modules()) {
            String msg = "@VertxModule() for " + verticle.getClass().getName() + "  did not declare a Module(s).";
            container.logger().error(msg, new Exception(msg));
        }

        try {
            List<Module> mods = new ArrayList<Module>();
            mods.add(new GuiceVertxModule(vertx, container));
            for (Class<?> modClass : modAnnotation.modules()) {
                VertxModule m = (VertxModule) modClass.newInstance();
                m.setContainer(container);
                m.setVertx(vertx);
                mods.add(m);
            }

            Injector injector = Guice.createInjector(mods);
            injector.injectMembers(verticle);

        } catch (Exception ex) {
            container.logger().error("Error creating Injector", ex);
        }
    }
}
