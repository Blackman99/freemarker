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

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import freemarker.test.TemplateTest;

public class StringBuiltInTest extends TemplateTest {


    @Override
    protected Configuration createConfiguration() throws Exception {
        Configuration cfg = super.createConfiguration();
        cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
        return cfg;
    }


    @Test
    public void testTruncate() throws IOException, TemplateException {
        assertOutput("${\"\"?is_blank?c}", "true");
        assertOutput("${\"    \"?is_blank?c}", "true");
        assertOutput("${\"    a\"?is_blank?c}", "false");
        assertOutput("${\"a\"?is_blank?c}", "false");
        assertOutput("${\"a \"?is_blank?c}", "false");
    }
   
}