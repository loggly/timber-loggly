/**
 * Copyright (C) 2015 Anthony K. Trinh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tony19.timber.loggly.demo;

import android.app.Application;

import com.github.tony19.timber.loggly.LogglyTree;

import timber.log.Timber;

/**
 * Demonstrates an application that uses <a href="https://github.com/JakeWharton/timber">Timber</a>
 * and <a href="https://github.com/tony19/timber-loggly">LogglyTree</a> to post messages to
 * <a href="http://loggly.com">Loggly</a>, a cloud-based logging service
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Plant a LogglyTree for logging with Timber. Use tony19's demo
        // token for testing purposes only. Get your own token from
        // https://www.loggly.com/docs/customer-token-authentication-token/
        final String LOGGLY_TOKEN = "1e29e92a-b099-49c5-a260-4c56a71f7c89";
        Timber.plant(new LogglyTree(LOGGLY_TOKEN));
    }
}
