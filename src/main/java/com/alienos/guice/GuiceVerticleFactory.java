package com.alienos.guice;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.verticle.CompilingClassLoader;
import io.vertx.core.spi.VerticleFactory;

import java.util.List;

public class GuiceVerticleFactory implements VerticleFactory {
    private Vertx vertx;


    private Injector injector;
    private List<Module> modules;

    public GuiceVerticleFactory(List<Module> modules) {
        this.modules = modules;
    }


    @Override
    public String prefix() {
        return "guice";
    }

    @Override
    public void init(Vertx vertx) {
        this.vertx = vertx;
        modules.add(new GuiceVertxModule(vertx));
        injector = Guice.createInjector(modules);
        // injector.injectMembers(verticle);

        /*
        GuiceVertxBinding modAnnotation = verticle.getClass().getAnnotation(GuiceVertxBinding.class);

        if (null == modAnnotation) {
            String msg = "GuiceVerticle " + verticle.getClass().getName() + " did not declare a Module with @VertxModule()";
            throw new RuntimeException(msg);
            // container.logger().error(msg, new Exception(msg));
        }

        if (null == modAnnotation.modules()) {
            String msg = "@VertxModule() for " + verticle.getClass().getName() + "  did not declare a Module(s).";
            throw new RuntimeException(msg);
            // container.logger().error(msg, new Exception(msg));
        }

        try {
            List<Module> mods = new ArrayList<Module>();
            mods.add(new GuiceVertxModule(vertx, context));
            for (Class<?> modClass : modAnnotation.modules()) {
                VertxModule m = (VertxModule) modClass.newInstance();
                m.setContext(context);
                m.setVertx(vertx);
                mods.add(m);
            }

        */
    }

    @Override
    public Verticle createVerticle(String verticleName, ClassLoader classLoader) throws Exception {
        verticleName = VerticleFactory.removePrefix(verticleName);
        Class clazz;
        if (verticleName.endsWith(".java")) {
            CompilingClassLoader compilingLoader = new CompilingClassLoader(classLoader, verticleName);
            String className = compilingLoader.resolveMainClassName();
            clazz = compilingLoader.loadClass(className);
        } else {
            clazz = classLoader.loadClass(verticleName);
        }

        Verticle verticle = (Verticle) injector.getInstance(clazz);
        return verticle;
    }
}
