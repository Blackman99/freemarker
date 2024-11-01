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

import org.apache.freemarker.core.model.TemplateBooleanModel;
import org.apache.freemarker.core.model.TemplateModel;
import org.apache.freemarker.core.model.impl.SimpleString;
import org.apache.freemarker.core.util._StringUtils;

/**
 * A holder for builtins that deal with null left-hand values.
 */
class BuiltInsForExistenceHandling {

    // Can't be instantiated
    private BuiltInsForExistenceHandling() { }

    private static abstract class ExistenceBuiltIn extends ASTExpBuiltIn {
    
        protected TemplateModel evalMaybeNonexistentTarget(Environment env) throws TemplateException {
            TemplateModel tm;
            if (target instanceof ASTExpParenthesis) {
                boolean lastFIRE = env.setFastInvalidReferenceExceptions(true);
                try {
                    tm = target.eval(env);
                } catch (InvalidReferenceException ire) {
                    tm = null;
                } finally {
                    env.setFastInvalidReferenceExceptions(lastFIRE);
                }
            } else {
                tm = target.eval(env);
            }
            return tm;
        }
        
    }
    
    static class has_contentBI extends BuiltInsForExistenceHandling.ExistenceBuiltIn {
        @Override
        TemplateModel _eval(Environment env) throws TemplateException {
            return ASTExpression.isEmpty(evalMaybeNonexistentTarget(env))
                    ? TemplateBooleanModel.FALSE
                    : TemplateBooleanModel.TRUE;
        }
    
        @Override
        boolean evalToBoolean(Environment env) throws TemplateException {
            return _eval(env) == TemplateBooleanModel.TRUE;
        }
    }

    static class blank_to_nullBI extends BuiltInsForExistenceHandling.ExistenceBuiltIn {
        @Override
        TemplateModel _eval(Environment env) throws TemplateException {
            TemplateModel model = evalMaybeNonexistentTarget(env);

            if (model == null) {
                return null;
            }

            String s = _EvalUtils.coerceModelToPlainTextOrUnsupportedMarkup(model, target, null, env);
            return isBlank(s) ? null : model;
        }

        private static boolean isBlank(String s) {
            if (s == null) {
                return true;
            }

            int len = s.length();
            if (len == 0) {
                return true;
            }

            for (int i = 0; i < len; i++) {
                if (!_StringUtils.isWhitespaceOrNonBreakingWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    static class trim_to_nullBI extends BuiltInsForExistenceHandling.ExistenceBuiltIn {
        @Override
        TemplateModel _eval(Environment env) throws TemplateException {
            TemplateModel model = evalMaybeNonexistentTarget(env);

            if (model == null) {
                return null;
            }

            String s = _EvalUtils.coerceModelToPlainTextOrUnsupportedMarkup(model, target, null, env);
            String trimmed = s.trim();
            return trimmed.isEmpty() ? null : new SimpleString(trimmed);
        }
    }

    static class empty_to_nullBI extends BuiltInsForExistenceHandling.ExistenceBuiltIn {
        @Override
        TemplateModel _eval(Environment env) throws TemplateException {
            TemplateModel model = evalMaybeNonexistentTarget(env);

            if (model == null) {
                return null;
            }

            String s = _EvalUtils.coerceModelToPlainTextOrUnsupportedMarkup(model, target, null, env);
            return s.isEmpty() ? null : model;
        }
    }

}
