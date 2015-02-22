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
package com.github.tony19.timber.loggly;

import com.github.tony19.loggly.LogglyClient;
import timber.log.Timber;

/**
 * A <a href="https://github.com/JakeWharton/timber">Timber</a> tree that posts
 * log messages to <a href="http://loggly.com">Loggly</a>
 *
 * @author tony19@gmail.com
 */
public class LogglyTree extends Timber.HollowTree {

    private final LogglyClient loggly;
    private LogglyClient.Callback handler;

    /** Log severity level */
    private static enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    /**
     * Creates a  <a href="https://github.com/JakeWharton/timber">Timber</a>
     * tree for posting messages to <a href="http://loggly.com">Loggly</a>
     * @param token Loggly token from https://www.loggly.com/docs/customer-token-authentication-token/
     */
    public LogglyTree(String token) {
        loggly = new LogglyClient(token);

        // Setup an async callback
        // TODO: handle failed messages with N retries
        handler = new LogglyClient.Callback() {
            @Override
            public void success() {
                // XXX: Handle success
            }

            @Override
            public void failure(String error) {
                System.err.println("LogglyTree failed: " + error);
            }
        };
    }

    /**
     * Logs a message with {@code DEBUG} severity
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void d(String message, Object... args) {
        log(Level.DEBUG, message, args);
    }

    /**
     * Logs a message and an associated throwable with {@code DEBUG} severity
     * @param t throwable to be logged
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void d(Throwable t, String message, Object... args) {
        d(message, args);
    }

    /**
     * Logs a message with {@code INFO} severity
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void i(String message, Object... args) {
        log(Level.INFO, message, args);
    }

    /**
     * Logs a message and an associated throwable with {@code INFO} severity
     * @param t throwable to be logged
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void i(Throwable t, String message, Object... args) {
        i(message, args);
    }

    /**
     * Logs a message with {@code ERROR} severity
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void e(String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    /**
     * Logs a message and an associated throwable with {@code ERROR} severity
     * @param t throwable to be logged
     * @param message message to be logged
     * @param args message formatting arguments
     */
    @Override
    public void e(Throwable t, String message, Object... args) {
        e(message, args);
    }

    /**
     * Gets the JSON representation of a log event
     * @param level log severity level
     * @param message message to be logged
     * @param args message formatting arguments
     * @return JSON string
     */
    private String toJson(Level level, String message, Object... args) {
        return String.format("{\"level\": \"%1$s\", \"message\": \"%2$s\"}",
                            level,
                            String.format(message, args).replace("\"", "\\\""));
    }

    /**
     * Asynchronously sends a log event to Loggly
     * @param level log severity level
     * @param message message to be logged
     * @param args message formatting arguments
     */
    private void log(Level level, String message, Object... args) {
        loggly.log(toJson(level, message, args), handler);
    }
}
