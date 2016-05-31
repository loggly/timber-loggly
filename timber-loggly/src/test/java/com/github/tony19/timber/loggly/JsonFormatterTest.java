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

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * Tests {@code JsonFormatter}
 */
public class JsonFormatterTest {
    private JsonFormatter formatter;

    @Before
    public void setup() {
        formatter = new JsonFormatter();
    }

    @Test
    public void formatIgnoresNullMessage() throws IOException {
        final String nullMsg = null;
        final JsonLog log = getLog(Log.INFO, "tag", nullMsg, new RuntimeException());
        assertThat(log.message, is(nullValue()));
    }

    @Test
    public void formatIgnoresEmptyMessage() throws IOException {
        final String emptyMsg = "";
        final JsonLog log = getLog(Log.INFO, "tag", emptyMsg, new RuntimeException());
        assertThat(log.message, is(nullValue()));
    }

    @Test
    public void formatIgnoresNullThrowable() throws IOException {
        final Throwable nullThrowable = null;
        final JsonLog log = getLog(Log.INFO, "tag", "message", nullThrowable);
        assertThat(log.exception, is(nullValue()));
    }

    @Test
    public void formatRendersThrowable() throws IOException {
        final String error = "my error message";
        final JsonLog log = getLog(Log.INFO, "tag", "message", new RuntimeException(error));

        assertThat(log.exception, containsString(error));
        assertThat(log.exception, containsString("RuntimeException"));
    }

    @Test
    public void formatEscapesCarriageReturnInMessage() throws IOException {
        final String message = "xxx \r xxx";
        final String expected = "xxx \\r xxx";
        final JsonLog log = getLog(Log.INFO, "tag", message, new RuntimeException());

        assertThat(log.message, is(equalTo(expected)));
    }

    @Test
    public void formatEscapesTabInMessage() throws IOException {
        final String message = "xxx \t xxx";
        final String expected = "xxx \\t xxx";
        final JsonLog log = getLog(Log.INFO, "tag", message, new RuntimeException());

        assertThat(log.message, is(equalTo(expected)));
    }

    @Test
    public void formatEscapesNewLineInMessage() throws IOException {
        final String message = "xxx \n xxx";
        final String expected = "xxx \\n xxx";
        final JsonLog log = getLog(Log.INFO, "tag", message, new RuntimeException());

        assertThat(log.message, is(equalTo(expected)));
    }

    @Test
    public void formatAllowsQuoteInMessage() throws IOException {
        final String message = "xxx \" xxx \"";
        final String expected = "xxx \" xxx \"";
        final JsonLog log = getLog(Log.INFO, "tag", message, new RuntimeException());

        assertThat(log.message, is(equalTo(expected)));
    }

    @Test
    public void formatRendersInfoLevel() throws IOException {
        final JsonLog log = getLog(Log.INFO, "tag", "message", new RuntimeException());
        assertThat(log.level, is(equalTo("INFO")));
    }

    @Test
    public void formatRendersDebugLevel() throws IOException {
        final JsonLog log = getLog(Log.DEBUG, "tag", "message", new RuntimeException());
        assertThat(log.level, is(equalTo("DEBUG")));
    }

    @Test
    public void formatRendersErrorLevel() throws IOException {
        final JsonLog log = getLog(Log.ERROR, "tag", "message", new RuntimeException());
        assertThat(log.level, is(equalTo("ERROR")));
    }

    @Test
    public void formatRendersWarningLevel() throws IOException {
        final JsonLog log = getLog(Log.WARN, "tag", "message", new RuntimeException());
        assertThat(log.level, is(equalTo("WARN")));
    }

    private JsonLog getLog(int priority, String tag, String message, Throwable t) throws IOException {
        final String formatted = formatter.format(priority, tag, message, t);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(formatted, JsonLog.class);
    }
}
