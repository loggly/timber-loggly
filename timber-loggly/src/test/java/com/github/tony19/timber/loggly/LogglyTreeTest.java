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

import android.util.Log;

import com.github.tony19.loggly.ILogglyClient;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by tony on 4/26/16.
 */
public class LogglyTreeTest {

    private ILogglyClient.Callback callback;
    private ILogglyClient client;
    private IFormatter formatter;
    private LogglyTree tree;

    @Before
    public void setup() {
        client = mock(ILogglyClient.class);
        callback = mock(ILogglyClient.Callback.class);
        formatter = mock(IFormatter.class);
        tree = new LogglyTree(client, callback, formatter);
    }

    @Test
    public void tagSetsLogglyTags() {
        final String tag = "foo";
        tree.tag(tag);
        verify(client).setTags(tag);
    }

    @Test
    public void logInvokesFormatter() {
        tree.d("hello %1$s", "world");
        verify(formatter).format(eq(Log.DEBUG), anyString(), eq("hello world"), isNull(Throwable.class));
    }

    @Test
    public void logInvokesClient() {
        tree.d("hello %1$s", "world");
        verify(client).log(anyString(), eq(callback));
    }
}
