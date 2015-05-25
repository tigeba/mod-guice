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

import com.alienos.guice.GuiceVerticleFactory;
import com.google.inject.Module;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Guice integration tests
 */
@RunWith(VertxUnitRunner.class)
public class BasicIntegrationTest {

    TestSuite suite = TestSuite.create("the_test_suite");

    @Before
    public void beforeEach(TestContext context) {

        System.out.println("BEFORED!");
        VertxOptions options = new VertxOptions();

        Vertx vertx = Vertx.vertx();
        ArrayList<Module> mods = new ArrayList<>();
        SomeModule mod = new SomeModule();
        mod.setVertx(vertx);
        mods.add(mod);
        vertx.registerVerticleFactory(new GuiceVerticleFactory(mods));
        context.put("vertx", vertx);
        TestContextHelper.setContext(context);

    }

    @After
    public void afterEach(TestContext context) {
        System.out.println("AFTERED");
    }

    /**
     * Test the "standard" method where you extend the GuiceVerticle
     */
    @Test
    public void testDeployArbitraryVerticle(TestContext context) {
        Vertx vertx = context.get("vertx");

        JsonObject config = new JsonObject();
        config.put("foo", "bar");
        config.put("beer", "good");
        String verticleName = "guice:" + SomeVerticle.class.getName();
        DeploymentOptions options = new DeploymentOptions();
        options.setConfig(config);
        Async async = context.async();


        vertx.deployVerticle(verticleName, options, event -> {
            if (event.failed()) {
                event.cause().printStackTrace();
                context.fail(event.result());
            } else {
                async.complete();
            }
        });
    }

}
