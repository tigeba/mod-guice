# mod-guice README

mod-guice is a [Google Guice](https://code.google.com/p/google-guice/) module for [Vert.x](http://vertx.io).  It includes everything you need to be Guicing your Vert.x like a pro in five minutes.


### The easy tutorial

First, create a Guice Module that implements VertxModule

```
public class MyModule implements VertxModule {

    private Container container;
    private Vertx vertx;

    @Override
    public void configure(Binder binder) {
		///Do binding stuff here!
		binder.bind(MyService.class).to(MyServiceImpl.class);
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }
}
```

Next create your Verticle, extending GuiceVerticle.  Using the @GuiceVertxBinding annotation, supply a list of all the Modules the verticle needs at runtime.  Use the @Inject annotation to inject any dependencies defined in the modules.  You should @Override the onStart() method to kick off any of your normal verticle activity.

```
@GuiceVertxBinding(modules = {MyModule.class})
public class MyVerticle extends GuiceVerticle {

    @Inject
    MyService myService;

    @Override
    public void onStart() {
       	myService.doStuff();
    }
}
```

Make sure that you include the name of the module in your mod.xml for any module that needs to use mod-guice

```
com.alienos.vertx~mod-guice~1.0.0-beta1
```


### But wait, my Verticles already inherit from some other class!

Not a problem, here is an example for that case.


```
@GuiceVertxBinding(modules = {MyModule.class})
public class RawVerticle extends Verticle {

    @Inject
    MyService myService;

    @Override
    public void start() {
    	//Just call this firt from the start method
        GuiceVerticleHelper.inject(this, vertx, container);

		//Proceed as normal
		myService.doStuff();
    }
}
```










