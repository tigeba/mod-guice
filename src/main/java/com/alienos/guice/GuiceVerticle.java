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

import org.vertx.java.platform.Verticle;

/**
 * Base class for Guice/Vert.x integration.  Simply extend this class to create a GuiceVerticle
 */
public abstract class GuiceVerticle extends Verticle {

    @Override
    public final void start() {

    }

    @Override
    public final void start(org.vertx.java.core.Future<Void> startedResult) {
        GuiceVerticleHelper.inject(this, vertx, container);
        onStart(startedResult);
    }

    /**
     * Implement onStart in lieu of the standard Verticle.start();
     */
    public void onStart() {

    }

    /**
     * Implement onStart(future) in lieu of the standard Verticle.start(future)
     *
     * @param startedResult
     */
    public void onStart(org.vertx.java.core.Future<Void> startedResult) {
        onStart();
        startedResult.setResult(null);
    }

}
