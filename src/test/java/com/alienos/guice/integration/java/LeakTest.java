package com.alienos.guice.integration.java;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.vertx.testtools.VertxAssert.testComplete;


public class LeakTest extends TestVerticle {

    int counter = 0;

    @Test
    public void checkForLeaks() {


        deployAndUndeploy(new Handler<Void>() {
            @Override
            public void handle(Void aVoid) {
                testComplete();

            }
        });

    }


    private void deployAndUndeploy(final Handler<Void> done) {
        JsonObject config = new JsonObject();
        config.putString("foo", "bar");
        config.putNumber("counter", counter);
        container.deployVerticle(LeakCheckVerticle.class.getName(), config, new AsyncResultHandler<String>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                String deployId = stringAsyncResult.result();
                assertNotNull(deployId);
                container.undeployVerticle(deployId, new Handler<AsyncResult<Void>>() {
                    @Override
                    public void handle(AsyncResult<Void> voidAsyncResult) {
                        assertEquals(true, voidAsyncResult.succeeded());
                        counter++;

                        if (counter >= 1000000) {
                            done.handle(null);
                        } else {
                            deployAndUndeploy(done);
                        }

                    }
                });


            }
        });
    }

}
