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

/**
 * Classes for two-way FreeMarker-JSP integration. It contains both a JSP custom tag that allows embedding of FreeMarker
 * templates inside JSP pages, as well as the infrastructure that allows JSP custom tags to be used inside FreeMarker
 * templates.
 *
 * Note that this is for the legacy "javax" Servlet/JSP API; for Jakarta (that is, in modern Servlet containers),
 * use the <code>freemarker.ext.jakarta.jsp</code> package instead (since 2.3.33).
 */
package freemarker.ext.jsp;