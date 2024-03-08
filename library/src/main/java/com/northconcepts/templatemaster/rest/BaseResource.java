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

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;

@Produces(MediaType.TEXT_HTML)
public class BaseResource {
    
    public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    public static final String TEXT_HTML = "text/html; charset=utf-8";

    protected final Logger log = LogManager.getLogger(getClass());
    
    protected Response ok() {
        return Response.ok().build();
    }
    
    protected Response ok(Object entity) {
        return Response.ok(entity).build();
    }
    
    protected Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    protected Response serverError(Object entity) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(entity).build();
    }
    
    protected Response badRequest() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    protected Response badRequest(Object entity) {
        return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
    }
    
    protected Response gotoPath(String path) {
        Url currentUrl = null;
        Url newUrl = null;
        try {
            currentUrl = new Url(RequestHolder.getHttpServletRequest());
            newUrl = currentUrl.setPath(path);
            return Response.seeOther(newUrl.getUri()).build();
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("path", path).set("currentUrl", currentUrl).set("newUrl", newUrl);
        }
    }
   
    protected Response gotoUri(String uri) {
        try {
            return Response.seeOther(new URI(uri)).build();
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", uri);
        }
    }
    
    protected Response movedPermanently(String uri) {
        try {
            return Response.status(Status.MOVED_PERMANENTLY).location(new URI(uri)).build();
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", uri);
        }
    }

    protected Content getTemplate() {
        return new Content("templates/site.html");
    }
    
    protected Content newPage(String title, String bodyTemplatePath) {
        return newPage(title, title, bodyTemplatePath);
    }

    protected Content newPage(String title, String description, String bodyTemplatePath) {
        Content content = getTemplate();
        
        if (Util.isNotEmpty(title)) {
            content.add("title", title);
        }
        
        if (Util.isNotEmpty(description)) {
            content.add("description", description);
        }

        if (Util.isNotEmpty(bodyTemplatePath)) {
            content.add("body", new Content(bodyTemplatePath));
        }
        
        return content;
    }

    protected Registry getRegistry() {
        return (Registry) RequestHolder.getHttpServletRequest().getServletContext().getAttribute("org.jboss.resteasy.spi.Registry");
    }

    protected ResteasyProviderFactory getResteasyProviderFactory() {
        return (ResteasyProviderFactory) RequestHolder.getHttpServletRequest().getServletContext().getAttribute("org.jboss.resteasy.spi.ResteasyProviderFactory");
    }
    
    protected HttpResponse getHttpResponse() {
        return ResteasyProviderFactory.getContextData(HttpResponse.class);
    }

    protected HttpRequest getHttpRequest() {
        return ResteasyProviderFactory.getContextData(HttpRequest.class);
    }


//    protected <A> A getSessionAttribute(String name) {
//        return getSessionAttribute(null, name);
//    }

//    protected <A> void setSessionAttribute(String name, A value) {
//        setSessionAttribute(null, name, value);
//    }
    protected <A extends Serializable> A getSessionAttribute(Class<A> type, String name) {
        return RequestHolder.getSessionAttribute(type, name);
    }

    protected <A extends Serializable> A getSessionAttribute(Class<A> type) {
        return RequestHolder.getSessionAttribute(type);
    }

    protected <A extends Serializable> void setSessionAttribute(Class<A> type, String name, A value) {
        RequestHolder.setSessionAttribute(type, name, value);
    }

    protected <A extends Serializable> void setSessionAttribute(Class<A> type, A value) {
        RequestHolder.setSessionAttribute(type, value);
    }

    protected <A extends Serializable> void removeSessionAttribute(Class<A> type, String name) {
        RequestHolder.removeSessionAttribute(type, name);
    }

    protected <A extends Serializable> void removeSessionAttribute(Class<A> type) {
        RequestHolder.removeSessionAttribute(type);
    }

//    protected <T> void removeSessionAttribute(String name) {
//        removeSessionAttribute(null, name);
//    }

    protected FlashMessage getFlashMessage() {
        return RequestHolder.getFlashMessage();
    }
    
    protected void setFlashMessage(FlashMessage message) {
        RequestHolder.setFlashMessage(message);
    }

    protected void setSuccessFlashMessage(String message) {
        RequestHolder.setSuccessFlashMessage(message);
    }
    
    protected void setInfoFlashMessage(String message) {
        RequestHolder.setInfoFlashMessage(message);
    }
    
    protected void setWarningFlashMessage(String message) {
        RequestHolder.setWarningFlashMessage(message);
    }
    
    protected void setErrorFlashMessage(String message) {
        RequestHolder.setErrorFlashMessage(message);
    }
    
    protected String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Util.matches(cookie.getName(), name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    protected Part getPart(HttpServletRequest request, String name) throws IOException, ServletException {
        Collection<Part> parts = request.getParts();
        if (parts != null) {
            for (Part part : parts) {
                if (Util.matches(part.getName(), name)) {
                    return part;
                }
            }
        }

        return null;
    }

    protected NewCookie createCookie(String name, String value, String path, int maxAgeInSeconds) {
        return new NewCookie(name, value, path, null, null, maxAgeInSeconds, false);
    }

    protected void logRequest(HttpServletRequest request) {
        log.debug(getRequestAsString(request));
    }
    
    @SuppressWarnings("deprecation")
    public static String getRequestAsString(HttpServletRequest request) {
        StringBuilder s = new StringBuilder(1024 * 20);
        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Request:" + LINE_SEPARATOR);
        s.append("    AuthType=[").append(request.getAuthType()).append("]" + LINE_SEPARATOR);
        s.append("    CharacterEncoding=[").append(request.getCharacterEncoding()).append("]" + LINE_SEPARATOR);
        s.append("    ContentLength=[").append(request.getContentLength()).append("]" + LINE_SEPARATOR);
        s.append("    ContentType=[").append(request.getContentType()).append("]" + LINE_SEPARATOR);
        s.append("    LocalAddr=[").append(request.getLocalAddr()).append("]" + LINE_SEPARATOR);
        s.append("    LocalName=[").append(request.getLocalName()).append("]" + LINE_SEPARATOR);
        s.append("    LocalPort=[").append(request.getLocalPort()).append("]" + LINE_SEPARATOR);
        s.append("    Method=[").append(request.getMethod()).append("]" + LINE_SEPARATOR);
        s.append("    PathInfo=[").append(request.getPathInfo()).append("]" + LINE_SEPARATOR);
        s.append("    PathTranslated=[").append(request.getPathTranslated()).append("]" + LINE_SEPARATOR);
        s.append("    Protocol=[").append(request.getProtocol()).append("]" + LINE_SEPARATOR);
        s.append("    QueryString=[").append(request.getQueryString()).append("]" + LINE_SEPARATOR);
        s.append("    RemoteAddr=[").append(request.getRemoteAddr()).append("]" + LINE_SEPARATOR);
        s.append("    RemoteHost=[").append(request.getRemoteHost()).append("]" + LINE_SEPARATOR);
        s.append("    RemotePort=[").append(request.getRemotePort()).append("]" + LINE_SEPARATOR);
        s.append("    RemoteUser=[").append(request.getRemoteUser()).append("]" + LINE_SEPARATOR);
        s.append("    RequestedSessionId=[").append(request.getRequestedSessionId()).append("]" + LINE_SEPARATOR);
        s.append("    RequestedSessionIdFromCookie =[").append(request.isRequestedSessionIdFromCookie()).append("]" + LINE_SEPARATOR);
        s.append("    RequestedSessionIdFromUrl =[").append(request.isRequestedSessionIdFromUrl()).append("]" + LINE_SEPARATOR);
        s.append("    RequestedSessionIdValid =[").append(request.isRequestedSessionIdValid()).append("]" + LINE_SEPARATOR);
        s.append("    RequestURI=[").append(request.getRequestURI()).append("]" + LINE_SEPARATOR);
        s.append("    RequestURL=[").append(request.getRequestURL()).append("]" + LINE_SEPARATOR);
        s.append("    Scheme=[").append(request.getScheme()).append("]" + LINE_SEPARATOR);
        s.append("    ServerName=[").append(request.getServerName()).append("]" + LINE_SEPARATOR);
        s.append("    ServerPort=[").append(request.getServerPort()).append("]" + LINE_SEPARATOR);
        s.append("    ServletPath=[").append(request.getServletPath()).append("]" + LINE_SEPARATOR);

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Header:" + LINE_SEPARATOR);
        Enumeration<?> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = (String) headerNames.nextElement();
                s.append("    ").append(name).append("=[").append(request.getHeader(name)).append("]" + LINE_SEPARATOR);
            }
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Cookies:" + LINE_SEPARATOR);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                s.append("    ").append(cookies[i].getName()).append("=[").append(cookies[i].getValue()).append("]" + LINE_SEPARATOR);
            }
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Parameters:" + LINE_SEPARATOR);
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            ArrayList<String> parameterNames = new ArrayList<String>(parameterMap.keySet());
            Collections.sort(parameterNames);
            for (int i = 0; i < parameterNames.size(); i++) {
                String name = parameterNames.get(i);
                // String[] values = request.getParameterValues(name);
                String[] values = parameterMap.get(name);
                if (values == null) {
                    s.append("    ").append(name).append("=[null]" + LINE_SEPARATOR);
                } else if (values.length == 1) {
                    s.append("    ").append(name).append("=[").append(values[0]).append("]" + LINE_SEPARATOR);
                } else {
                    for (int j = 0; j < values.length; j++) {
                        s.append("    ").append(name).append("[").append(j).append("]=[").append(values[j]).append("]" + LINE_SEPARATOR);
                    }
                }
            }
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Attributes:" + LINE_SEPARATOR);
        Enumeration<?> attributeNames = request.getAttributeNames();
        if (attributeNames != null) {
            while (attributeNames.hasMoreElements()) {
                String name = (String) attributeNames.nextElement();
                Object value = request.getAttribute(name);
                if (value == null) {
                    s.append("    ").append(name).append("=[null]" + LINE_SEPARATOR);
                } else {
                    s.append("    ").append(name).append("=[").append(value).append("]" + LINE_SEPARATOR);
                }
            }
        }


        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Session Attributes:" + LINE_SEPARATOR);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Enumeration<?> sessionNames = session.getAttributeNames();
            if (sessionNames != null) {
                while (sessionNames.hasMoreElements()) {
                    String name = (String) sessionNames.nextElement();
                    Object value = session.getAttribute(name);
                    if (value == null) {
                        s.append("    ").append(name).append("=[null]" + LINE_SEPARATOR);
                    } else {
                        s.append("    ").append(name).append("=[").append(value).append("]" + LINE_SEPARATOR);
                    }
                }
            }
        }
        s.append("------------------------------------------");

        return s.toString();
    }

    protected Sort getSortBy(String column) {
        if(Util.isEmpty(column)) {
            return null;
        }
        return column.startsWith("-") ? Sort.by(Sort.Direction.DESC, column.replaceFirst("-", "")) :
                Sort.by(Sort.Direction.ASC, column);
    }

    protected Pageable getPageable(int pageNumber, int pageSize, String sortField) {
        Sort sort = getSortBy(sortField);
        return sort == null ? PageRequest.of(pageNumber, pageSize) : PageRequest.of(pageNumber, pageSize, sort);
    }
    
}
