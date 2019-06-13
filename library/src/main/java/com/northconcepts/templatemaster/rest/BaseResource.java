/*
 * Copyright (c) 2018 North Concepts Inc.
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
 * 
 */
package com.northconcepts.templatemaster.rest;

import java.io.Serializable;
import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;

@Produces(MediaType.TEXT_HTML)
public class BaseResource {
    
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

    protected NewCookie createCookie(String name, String value, String path, int maxAgeInSeconds) {
        return new NewCookie(name, value, path, null, null, maxAgeInSeconds, false);
    }

}
