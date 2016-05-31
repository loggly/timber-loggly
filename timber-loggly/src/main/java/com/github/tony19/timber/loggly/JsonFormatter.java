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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Formats log messages in JSON
 */
class JsonFormatter implements IFormatter {

    private Map<Integer, String> LEVELS;

    /**
     * Constructs a formatter that creates a JSON object from log-event data
     */
    public JsonFormatter() {
        LEVELS = new HashMap<Integer, String>();
        LEVELS.put(Log.VERBOSE, "VERBOSE");
        LEVELS.put(Log.DEBUG, "DEBUG");
        LEVELS.put(Log.INFO, "INFO");
        LEVELS.put(Log.WARN, "WARN");
        LEVELS.put(Log.ERROR, "ERROR");
        LEVELS.put(Log.ASSERT, "ASSERT");
    }

    /**
     * Gets the JSON representation of a log event
     * @param priority log severity level
     * @param tag correlating string
     * @param message message to be logged
     * @param t throwable (or null)
     * @return JSON string
     */
    @Override
    public String format(int priority, String tag, String message, Throwable t) {
        StringBuilder formatted = new StringBuilder();
        formatted.append("{");
        formatted.append("\"level\": \"").append(toLevel(priority)).append("\"");
        appendMessage(formatted, message);
        appendThrowable(formatted, t);
        formatted.append("}");
        return formatted.toString();
    }

    /**
     * Appends a {@code Throwable} to a string buffer
     * http://stackoverflow.com/a/4812589/600838
     * @param buffer string buffer to modify
     * @param t throwable to stringify and append to buffer (null ignored)
     */
    private void appendThrowable(StringBuilder buffer, Throwable t) {
        if (t != null) {
            StringWriter errors = new StringWriter();
            t.printStackTrace(new PrintWriter(errors));
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append("\"exception\": \"")
                  .append(escape(errors.toString()))
                  .append("\"");
        }
    }

    /**
     * Appends a log message to a string buffer
     * @param buffer stirng buffer to modify
     * @param message log message to append to buffer (null ignored)
     */
    private void appendMessage(StringBuilder buffer, String message) {
        if (message != null && message.length() > 0) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append("\"message\": \"")
                  .append(escape(message))
                  .append("\"");
        }
    }

    /**
     * Escapes illegal JSON characters with a slash. Intended for JSON values.
     * @param input string to evaluate
     * @return the modified string
     */
    static private String escape(String input) {
        return input.replace("\r", "\\\\r")
                    .replace("\n", "\\\\n")
                    .replace("\t", "\\\\t")
                    .replace("\"", "\\\"");
    }

    /**
     * Gets a string representation of an integer priority
     * @param priority log priority level
     * @return the string
     */
    private String toLevel(int priority) {
        return LEVELS.containsKey(priority) ? LEVELS.get(priority) : LEVELS.get(Log.DEBUG);
    }
}
