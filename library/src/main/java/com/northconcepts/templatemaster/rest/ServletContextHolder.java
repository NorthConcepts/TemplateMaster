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

import javax.servlet.ServletContext;

import com.northconcepts.templatemaster.content.Util;

public final class ServletContextHolder {

    private static ServletContext servletContext;
    
    public static ServletContext getServletContext() {
        return servletContext;
    }
    
    public static void setServletContext(ServletContext servletContext) {
        ServletContextHolder.servletContext = servletContext;
    }
    
    // ============================================================================================================
    //  Application Attribute
    // ============================================================================================================

    private static <T> String getKey(Class<T> type, String name) {
        if (Util.isNotEmpty(name)) {
            if (type != null) {
                return type.getName() + "::" + name;
            }
            return name;
        } else {
            return type.getName();
        }
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T getApplicationAttribute(Class<T> type, String name) {
        return (T) getServletContext().getAttribute(getKey(type, name));
    }

    public static <T> T getApplicationAttribute(Class<T> type) {
        return getApplicationAttribute(type, null);
    }

    public static <T> void setApplicationAttribute(Class<T> type, String name, T value) {
        getServletContext().setAttribute(getKey(type, name), value);
    }

    public static <T> void setApplicationAttribute(Class<T> type, T value) {
        setApplicationAttribute(type, null, value);
    }

    public static <T> void removeApplicationAttribute(Class<T> type, String name) {
        getServletContext().removeAttribute(getKey(type, name));
    }

    public static <T> void removeApplicationAttribute(Class<T> type) {
        removeApplicationAttribute(type, null);
    }

    // ============================================================================================================
    
    private ServletContextHolder() {
    }

}
