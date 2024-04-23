/*
 * Copyright (c) 2014-2022 North Concepts Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northconcepts.templatemaster.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class XForwardedRequestWrapper extends HttpServletRequestWrapper {

    private static final String HTTP_COLON = "http:";
    private static final String HTTPS_COLON = "https:";

    public XForwardedRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public HttpServletRequest getNestedRequest() {
        return (HttpServletRequest) getRequest();
    }

    public boolean isOverSsl() {
        return "https".equals(getScheme());
    }
    
    @Override
    public String getRemoteAddr() {
        return RequestHolder.getRemoteAddr(getNestedRequest());
    }

    @Override
    public String getRemoteHost() {
        return RequestHolder.getRemoteHost(getNestedRequest());
    }

    @Override
    public String getScheme() {
        return RequestHolder.getScheme(getNestedRequest());
    }
    
    @Override
    public StringBuffer getRequestURL() {
        StringBuffer requestURL = super.getRequestURL();
        if (requestURL != null && requestURL.toString().startsWith(HTTP_COLON) && isOverSsl()) {
            requestURL.replace(0, HTTP_COLON.length(), HTTPS_COLON);
        }
        
        return requestURL;
    }

}
