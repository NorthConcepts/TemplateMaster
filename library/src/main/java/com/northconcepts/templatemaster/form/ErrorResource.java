package com.northconcepts.templatemaster.form;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.rest.BaseResource;

public class ErrorResource extends BaseResource {
    
    public String getBaseUrl() {
        return "/error";
    }

    @GET
    @POST
    @Path("/")
    public Response getError() throws Throwable {
        return getError500();
    }

    @GET
    @POST
    @Path("/500")
    public Response getError500() throws Throwable {
        Content content = getTemplate();
        content.add("title", "Website Error");
        content.add("body", new Content("/pages/error/error-500.html"));        
        content.add("includePageHeader", false);
        content.add("includePageTitle", false);
        return Response.status(500).entity(content).build();
    }

//    @GET
//    @POST
//    @Path("/500")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response getError500AsPlainText() throws Throwable {
//        Content content = getPlainTextTemplate();
//        content.add("title", "Website Error");
//        content.add("body", new Content("pages/error/error-500.txt"));        
//        content.add("includePageHeader", false);
//        content.add("includePageTitle", false);
//        return Response.status(500).entity(content).build();
//    }

    @GET
    @POST
    @Path("/404")
    @Produces(MediaType.TEXT_HTML)
    public Response getError404(@Context HttpServletRequest request) throws Throwable {
        Object uri = request.getAttribute("javax.servlet.error.request_uri");
        if (uri == null) {
            uri = "";
        }
        
        Content content = getTemplate();
        content.add("title", "Page Not Found");
        content.add("body", new Content("/pages/error/error-404.html"));        
        content.add("request_uri", uri.toString());
        content.add("includePageHeader", false);
        content.add("includePageTitle", false);

        return Response.status(404).entity(content).build();
    }
    
    @GET
    @POST
    @Path("/400")
    @Produces(MediaType.TEXT_HTML)
    public Response getError400(@Context HttpServletRequest request) throws Throwable {
        Object uri = request.getAttribute("javax.servlet.error.request_uri");
        if (uri == null) {
            uri = "";
        }
        
        Content content = getTemplate();
        content.add("title", "Bad Request");
        content.add("body", new Content("/pages/error/error-400.html"));        
        content.add("request_uri", uri.toString());
        content.add("includePageHeader", false);
        content.add("includePageTitle", false);

        return Response.status(400).entity(content).build();
    }

//    @GET
//    @POST
//    @Path("/404")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response getError404AsPlainText(@Context HttpServletRequest request) throws Throwable {
//        Object uri = request.getAttribute("javax.servlet.error.request_uri");
//        if (uri == null) {
//            uri = "";
//        }
//        
//        Content content = getPlainTextTemplate();
//        content.add("title", "Page Not Found");
//        content.add("body", new Content("pages/error/error-404.txt"));        
//        content.add("request_uri", uri.toString());
//        content.add("includePageHeader", false);
//        content.add("includePageTitle", false);
//
//        return Response.status(404).entity(content).build();
//    }

}
