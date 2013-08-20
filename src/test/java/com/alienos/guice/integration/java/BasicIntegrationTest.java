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

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;
import org.vertx.testtools.VertxAssert;

import static org.junit.Assert.assertTrue;

/**
 * Guice integration tests
 */
public class BasicIntegrationTest extends TestVerticle {

    /**
     * Test the "standard" method where you extend the GuiceVerticle
     */
    @Test
    public void testDeployArbitraryVerticle() {
        JsonObject config = new JsonObject();
        config.putString("foo", "bar");
        config.putString("beer", "good");
        container.deployVerticle(SomeVerticle.class.getName(), config);
    }

    /**
     * Oops we blew inheritance on something else.  No problem.. just use the helper method.
     */
    @Test
    public void testDeployRawVerticle() {
        JsonObject config = new JsonObject();
        config.putString("foo", "bar");
        config.putString("beer", "good");
        container.deployVerticle(RawVerticle.class.getName(), config);
    }

    /**
     * We need to know when the GuiceVerticle has finished deploying
     */
    @Test
    public void testDeployArbitraryVerticleWithFuture() {
        JsonObject config = new JsonObject();
        config.putString("foo", "bar");
        config.putString("beer", "good");
        container.deployVerticle(SomeFutureVerticle.class.getName(), config, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                assertTrue(event.succeeded());
                VertxAssert.testComplete();
            }
        });
    }
}
