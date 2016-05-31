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

/**
 * Formats log events as a string
 */
public interface IFormatter {
    /**
     * Formats log events as a string
     * @param priority log priority level
     * @param tag correlating string
     * @param message message to be logged
     * @param t throwable (or null)
     * @return the string
     */
    String format(int priority, String tag, String message, Throwable t);
}
