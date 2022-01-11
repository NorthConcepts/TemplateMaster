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

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.rest.FlashMessage.FlashMessageType;

public final class RequestHolder {

    // ============================================================================================================
    //  ThreadLocal HttpServletRequest
    // ============================================================================================================

    private static final ThreadLocal<HttpServletRequest> threadLocalHttpServletRequest = new ThreadLocal<HttpServletRequest>();

    public static ThreadLocal<HttpServletRequest> getThreadLocalHttpServletRequest() {
        return threadLocalHttpServletRequest;
    }

    public static void setHttpServletRequest(HttpServletRequest request) {
        threadLocalHttpServletRequest.set(request);
    }

    public static void clearHttpServletRequest() {
        threadLocalHttpServletRequest.remove();
    }

    public static HttpServletRequest getHttpServletRequest(boolean exceptionOnFailure) {
        if (threadLocalHttpServletRequest == null) {
            if (!exceptionOnFailure) {
                return null;
            }
            throw new TemplateMasterException("threadLocalHttpServletRequest has not been initialized");
        }

        HttpServletRequest request = threadLocalHttpServletRequest.get();
        if (request == null) {
            if (!exceptionOnFailure) {
                return null;
            }
            throw new RuntimeException("not inside a servlet request; threadLocalHttpServletRequest.get() is null");
        }
        return request;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return getHttpServletRequest(true);
    }

    public static boolean hasHttpServletRequest() {
        return getHttpServletRequest(false) != null;
    }

    // ============================================================================================================
    //  Base URL, URL, Referrer
    // ============================================================================================================

    private static String cachedBaseUrl;

    public static String getBaseUrl() {
        HttpServletRequest request = getThreadLocalHttpServletRequest().get();
        if (request == null) {
            if (Util.isEmpty(cachedBaseUrl)) {
                throw new RuntimeException("not inside a servlet request and cachedBaseUrl is empty");
            }
            return cachedBaseUrl;
        }
        String url = request.getRequestURL().toString();
        String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        cachedBaseUrl = baseUrl;
        return baseUrl;
    }
    
    public static Url getUrl() {
        Url url = RequestHolder.getRequestAttribute(Url.class);
        if (url == null) {
            url = new Url(RequestHolder.getHttpServletRequest());
            RequestHolder.setRequestAttribute(Url.class, url);
        }
        return url;
    }

    public static String getReferrer() {
        return getHttpServletRequest().getHeader("referer");
    } 

    public static String getReferer() {
        return getReferrer();
    } 

    public static String getRemoteAddr(HttpServletRequest request) {
        return Util.coalesceCharSequences(request.getHeader("x-forwarded-for"), request.getRemoteAddr());
    }
    
    public static String getRemoteAddr() {
        return getRemoteAddr(RequestHolder.getHttpServletRequest());
    }
    
    public static String getRemoteHost(HttpServletRequest request) {
        return Util.coalesceCharSequences(request.getHeader("x-forwarded-host"), request.getRemoteHost());
    }
    
    public static String getRemoteHost() {
        return getRemoteHost(RequestHolder.getHttpServletRequest());
    }
    
    public static String getScheme(HttpServletRequest request) {
        return Util.coalesceCharSequences(request.getHeader("x-forwarded-proto"), request.getScheme());
    }
    
    public static String getScheme() {
        return getScheme(RequestHolder.getHttpServletRequest());
    }
    
    
    // ============================================================================================================
    //  Session Attribute
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
    public static <T> T getSessionAttribute(Class<T> type, String name) {
        HttpSession session = getHttpServletRequest().getSession(false);
        if (session == null) {
            return null;
        }
        return (T) session.getAttribute(getKey(type, name));
    }

    public static <T> T getSessionAttribute(Class<T> type) {
        return getSessionAttribute(type, null);
    }

    public static <T> void setSessionAttribute(Class<T> type, String name, T value) {
        HttpSession session = getHttpServletRequest().getSession(true);
        session.setAttribute(getKey(type, name), value);
    }

    public static <T> void setSessionAttribute(Class<T> type, T value) {
        setSessionAttribute(type, null, value);
    }

    public static <T> void removeSessionAttribute(Class<T> type, String name) {
        HttpSession session = getHttpServletRequest().getSession(false);
        if (session != null) {
            session.removeAttribute(getKey(type, name));
        }
    }

    public static <T> void removeSessionAttribute(Class<T> type) {
        removeSessionAttribute(type, null);
    }

    // ============================================================================================================
    //  Request Attribute
    // ============================================================================================================

    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(Class<T> type, String name) {
        return (T) getHttpServletRequest().getAttribute(getKey(type, name));
    }

    public static <T> T getRequestAttribute(Class<T> type) {
        return getRequestAttribute(type, null);
    }

    public static <T> void setRequestAttribute(Class<T> type, String name, T value) {
        getHttpServletRequest().setAttribute(getKey(type, name), value);
    }

    public static <T> void setRequestAttribute(Class<T> type, T value) {
        setRequestAttribute(type, null, value);
    }

    public static <T> void removeRequestAttribute(Class<T> type, String name) {
        getHttpServletRequest().removeAttribute(getKey(type, name));
    }

    public static <T> void removeRequestAttribute(Class<T> type) {
        removeRequestAttribute(type, null);
    }

    public static UUID getRequestUuid() {
        return getRequestAttribute(null, TemplateMasterBootstrap.UUID);
    }
    
    // ============================================================================================================
    //  Flash Message
    // ============================================================================================================

    public static FlashMessage getFlashMessage() {
        FlashMessage message = getRequestAttribute(FlashMessage.class);
        if (message != null) {
            return message;
        }
        
        message = getSessionAttribute(FlashMessage.class);
        if (message != null) {
            setRequestAttribute(FlashMessage.class, message);
            removeSessionAttribute(FlashMessage.class);
        }

        return message;
    }
    
    public static void setFlashMessage(FlashMessage message) {
        setSessionAttribute(FlashMessage.class, message);
    }

    public static void setSuccessFlashMessage(String message) {
        setFlashMessage(new FlashMessage(FlashMessageType.SUCCESS, message));
    }
    
    public static void setInfoFlashMessage(String message) {
        setFlashMessage(new FlashMessage(FlashMessageType.INFO, message));
    }
    
    public static void setWarningFlashMessage(String message) {
        setFlashMessage(new FlashMessage(FlashMessageType.WARNING, message));
    }
    
    public static void setErrorFlashMessage(String message) {
        setFlashMessage(new FlashMessage(FlashMessageType.ERROR, message));
    }

    // ============================================================================================================
    // Cookies
    // ============================================================================================================
    
    public static String getCookie(String name) {
        Cookie[] cookies = getHttpServletRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Util.matches(cookie.getName(), name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // ============================================================================================================
    
    private RequestHolder() {
    }

}
