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

import java.net.URI;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;


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

    protected NewCookie createCookie(String name, String value, String path, int maxAgeInSeconds) {
        return new NewCookie(name, value, path, null, null, maxAgeInSeconds, false);
    }

}
