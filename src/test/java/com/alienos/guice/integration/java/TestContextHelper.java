package com.alienos.guice.integration.java;

import io.vertx.ext.unit.TestContext;

/**
 * this is evil and bad. Not clear yet how to pass the TestContext to tested verticles
 */
public class TestContextHelper {

    public static TestContext getContext() {
        return context;
    }

    public static void setContext(TestContext context) {
        TestContextHelper.context = context;
    }

    private static TestContext context;


}
