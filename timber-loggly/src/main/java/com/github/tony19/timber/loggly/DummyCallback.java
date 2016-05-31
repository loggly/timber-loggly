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

/**
 * Dummy callback for LogglyClient
 */
class DummyCallback implements ILogglyClient.Callback {
    /**
     * Function to be called when the log request was successfully sent to Loggly
     */
    @Override
    public void success() {
        // ignore
    }

    /**
     * Function to be called when the log request failed
     *
     * @param error message details about the failure
     */
    @Override
    public void failure(String error) {
        System.err.println("LogglyTree failed: " + error);
    }
}
