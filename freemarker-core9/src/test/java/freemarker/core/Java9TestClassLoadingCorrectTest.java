/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package freemarker.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class Java9TestClassLoadingCorrectTest {
    @Test
    public void java9Supported() {
        // This can be a problem if the test is not ran from the jar, and the impl class from the core takes precedence
        assertTrue(
                "Multi-Release JAR selection of the proper " + _Java9Impl.class.getName() + " variant didn't happen in the Java 9 test environment",
                _Java9.INSTANCE.isSupported());
    }
}
