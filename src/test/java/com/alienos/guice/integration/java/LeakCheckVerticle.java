package com.alienos.guice.integration.java;

import com.alienos.guice.GuiceVerticle;
import com.alienos.guice.GuiceVertxBinding;

/**
 * Created by tigeba on 5/5/14.
 */
@GuiceVertxBinding(modules = {SomeModule.class})
public class LeakCheckVerticle extends GuiceVerticle {


    private int counter = 0;

    public void onStart() {
        counter = container.config().getInteger("counter");
        container.logger().error("Started Verticle -> " + counter);
    }

    public void stop() {
        container.logger().error("Stopped Verticle -> " + counter);
    }
}