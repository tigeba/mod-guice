package com.alienos.guice.integration.java;

import com.alienos.guice.GuiceVertxBinding;
import io.vertx.core.AbstractVerticle;


@GuiceVertxBinding(modules = {SomeModule.class})
public class LeakCheckVerticle extends AbstractVerticle {


    private int counter = 0;

    public void start() {
        // counter = container.config().getInteger("counter");
        // container.logger().error("Started Verticle -> " + counter);
    }


    public void stop() {
        //  container.logger().error("Stopped Verticle -> " + counter);
    }

}