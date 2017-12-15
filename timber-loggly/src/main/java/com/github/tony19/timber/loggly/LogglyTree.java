/**
 * Copyright (C) 2015 Anthony K. Trinh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tony19.timber.loggly;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.github.tony19.loggly.ILogglyClient;
import com.github.tony19.loggly.LogglyClient;

import timber.log.Timber;

/**
 * A <a href="https://github.com/JakeWharton/timber">Timber</a> tree that posts
 * log messages to <a href="http://loggly.com">Loggly</a>
 *
 * @author tony19@gmail.com
 */
public class LogglyTree extends Timber.Tree {

    private ILogglyClient loggly;
    private ILogglyClient.Callback handler;
    private IFormatter formatter;

    private boolean isLimitWifi = false;

    private Context context;
    private ConnectivityManager connectivityManager;

    /**
     * Creates a <a href="https://github.com/JakeWharton/timber">Timber</a>
     * tree for posting messages to <a href="http://loggly.com">Loggly</a>
     * @param token Loggly token from https://www.loggly.com/docs/customer-token-authentication-token/
     */
    public LogglyTree(String token) {
        loggly = new LogglyClient(token);
        handler = new DummyCallback();
        formatter = new JsonFormatter();
    }

    /**
     * Creates a <a href="https://github.com/JakeWharton/timber">Timber</a>
     * tree for posting messages to <a href="http://loggly.com">Loggly</a>
     * @param client Loggly client
     * @param handler Loggly-post result handler
     * @param formatter log formatter
     */
    LogglyTree(ILogglyClient client, ILogglyClient.Callback handler, IFormatter formatter) {
        this.loggly = client;
        this.handler = handler;
        this.formatter = formatter;
    }

    /**
     * Limits the {@link LogglyTree} to only send logs while connected to WiFi or suppress them otherwise.
     * @param context Context
     * @return a {@link LogglyTree} that only logs over WiFi
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public LogglyTree limitWifi(@NonNull Context context) {
        isLimitWifi = true;

        this.context = context;
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return this;
    }

    /**
     * Determines if the device is connected to a WiFi network.
     * @return true if device is connected to WiFi or false otherwise
     */
    @SuppressLint("MissingPermission")
    private boolean isOnWifi() {
        if(context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            }
        }
        return false;
    }

    /**
     * Writes a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See {@link android.util.Log} for constants.
     * @param tag Explicit or inferred tag. May be {@code null}.
     * @param message Formatted log message. May be {@code null}, but then {@code t} will not be.
     * @param t Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
     */
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (!isLimitWifi || isOnWifi()) {
            loggly.log(formatter.format(priority, tag, message, t), handler);
        }
    }

    /**
     * Sets the Loggly tag for all logs going forward
     * @param tag desired tag or CSV of multiple tags; use empty string
     *            to clear tags
     */
    public void tag(String tag) {
        loggly.setTags(tag);
    }
}
