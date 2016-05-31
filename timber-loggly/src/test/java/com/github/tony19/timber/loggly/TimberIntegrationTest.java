/**
 * Copyright (C) 2016 Anthony K. Trinh
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
package com.github.tony19.timber.loggly;

import com.github.tony19.loggly.ILogglyClient;

import org.junit.Before;
import org.junit.Test;

import timber.log.Timber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests handling of percent literals
 * https://github.com/tony19/timber-loggly/issues/8
 */
public class TimberIntegrationTest {

    private ILogglyClient client;
    private LogglyTree tree;

    @Before
    public void setup() {
        client = mock(ILogglyClient.class);
        tree = new LogglyTree(client, new DummyCallback(), new JsonFormatter());
        Timber.plant(tree);
    }

    @Test
    public void acceptsPercentInFormat() {
        final String input = "hello %1w$rld";
        Timber.d(input);
        verify(client).log(contains(input), any(DummyCallback.class));
    }

    @Test
    public void acceptsPercentInArgument() {
        Timber.d("hello %1$s", "%world");
        verify(client).log(contains("hello %world"), any(DummyCallback.class));
    }
}
