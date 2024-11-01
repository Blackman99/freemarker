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

package org.apache.freemarker.core;

import org.apache.freemarker.core.outputformat.impl.HTMLOutputFormat;
import org.apache.freemarker.test.TemplateTest;
import org.junit.Test;

import java.io.IOException;

public class StringBuiltInTest extends TemplateTest {
    @Override
    protected void setupConfigurationBuilder(Configuration.ExtendableBuilder<?> cb) {
        cb.setOutputFormat(HTMLOutputFormat.INSTANCE);
        cb.setNumberFormat(",##0.###");
    }

    @Test
    public void testBlankToNull() throws IOException, TemplateException {
        assertOutput("${nonExisting?blankToNull!'-'}", "-");
        assertOutput("${nonExisting!?blankToNull!'-'}", "-");
        assertOutput("${''?blankToNull!'-'}", "-");
        assertOutput("${' '?blankToNull!'-'}", "-");
        assertOutput("${'  a  '?blankToNull!'-'}", "  a  ");
        assertOutput("${'a '?blankToNull!'-'}", "a ");
        assertOutput("${' a'?blankToNull!'-'}", " a");
        assertOutput("${'a'?blankToNull!'-'}", "a");
        assertOutput("${'a b'?blankToNull!'-'}", "a b");

        assertOutput("${(nonExisting + '.')?blankToNull!'-'}", "-");

        assertOutput("${1234?blankToNull!'-'}", "1,234");

        // No consistent with ?trim (and String.trim()), as all UNICODE whitespace count as whitespace:
        assertOutput("${' \u2003  '?blankToNull!'-'}", "-");
        assertOutput("${' \u00A0  '?blankToNull!'-'}", "-"); // Even if it's non-breaking whitespace
    }

    @Test
    public void blankToNullTypeError() {
        assertErrorContains("${[]?blankToNull!'-'}",
                "For \"?blankToNull\" left-hand operand: Expected a string", "sequence");
        assertErrorContains("<#assign html></#assign>${html?blankToNull!'-'}",
                "For \"?blankToNull\" left-hand operand: Expected a string", "TemplateHTMLOutputModel");
    }

    @Test
    public void testTrimToNull() throws IOException, TemplateException {
        assertOutput("${nonExisting?trimToNull!'-'}", "-");
        assertOutput("${nonExisting!?trimToNull!'-'}", "-");
        assertOutput("${''?trimToNull!'-'}", "-");
        assertOutput("${' '?trimToNull!'-'}", "-");
        assertOutput("${'    '?trimToNull!'-'}", "-");
        assertOutput("${'  a  '?trimToNull!'-'}", "a");
        assertOutput("${'a '?trimToNull!'-'}", "a");
        assertOutput("${' a'?trimToNull!'-'}", "a");
        assertOutput("${'a'?trimToNull!'-'}", "a");
        assertOutput("${'a b'?trimToNull!'-'}", "a b");

        assertOutput("${(nonExisting + '.')?trimToNull!'-'}", "-");

        assertOutput("${1234?trimToNull!'-'}", "1,234");

        // To be consistent with ?trim (and String.trim()), only char <= 32 is whitespace, not all UNICODE whitespace:
        assertOutput("${'  \u2003  '?trimToNull!'-'}", "\u2003");
    }

    @Test
    public void trimToNullTypeError() {
        assertErrorContains("${[]?trimToNull!'-'}",
                "For \"?trimToNull\" left-hand operand: Expected a string", "sequence");
        assertErrorContains("<#assign html></#assign>${html?trimToNull!'-'}",
                "For \"?trimToNull\" left-hand operand: Expected a string", "TemplateHTMLOutputModel");
    }

    @Test
    public void emptyToNull() throws IOException, TemplateException {
        assertOutput("${nonExisting?emptyToNull!'-'}", "-");
        assertOutput("${nonExisting!?emptyToNull!'-'}", "-");
        assertOutput("${''?emptyToNull!'-'}", "-");
        assertOutput("${' '?emptyToNull!'-'}", " ");
        assertOutput("${'    '?emptyToNull!'-'}", "    ");
        assertOutput("${'  a  '?emptyToNull!'-'}", "  a  ");
        assertOutput("${'a '?emptyToNull!'-'}", "a ");
        assertOutput("${' a'?emptyToNull!'-'}", " a");
        assertOutput("${'a'?emptyToNull!'-'}", "a");
        assertOutput("${'a b'?emptyToNull!'-'}", "a b");

        assertOutput("${(nonExisting + '.')?emptyToNull!'-'}", "-");

        assertOutput("${1234?emptyToNull!'-'}", "1,234");
    }

    @Test
    public void emptyToNullTypeError() {
        assertErrorContains("${[]?emptyToNull!'-'}",
                "For \"?emptyToNull\" left-hand operand: Expected a string", "sequence");
        assertErrorContains("<#assign html></#assign>${html?emptyToNull!'-'}",
                "For \"?emptyToNull\" left-hand operand: Expected a string", "TemplateHTMLOutputModel");
    }

}
