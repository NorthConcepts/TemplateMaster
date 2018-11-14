package com.northconcepts.templatemaster.form;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.springframework.data.domain.Page;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.rest.BaseResource;

@Path("/")
@Produces(BaseResource.TEXT_HTML)
public abstract class CrudResource<ENTITY extends Serializable> extends BaseResource {

    protected static final int PAGE_SIZE = 20;
    
    protected final String title;
    protected final String subUrl;
    protected final String listBodyTemplate;
    protected final String viewBodyTemplate;
    protected final String newBodyTemplate;
    protected final String editBodyTemplate;
    protected final FormDef formDef;

    
    public CrudResource(String title, String subUrl, String templateFolder, FormDef formDef) {
        this.title = title;
        this.subUrl = subUrl;
        
        if (templateFolder == null) {
            templateFolder = "";
        }
        templateFolder = templateFolder.replace('\\', '/');
        templateFolder = Util.trimRight(templateFolder, '/');
        
        this.listBodyTemplate = templateFolder + "/list.html";
        this.viewBodyTemplate = templateFolder + "/view.html";
        this.newBodyTemplate = templateFolder + "/new.html";
        this.editBodyTemplate = templateFolder + "/edit.html";
        
        this.formDef = formDef;
    }

    protected abstract Page<ENTITY> getPage(String keyword, int pageNumber, int pageSize);
    
    protected abstract ENTITY getRecord(String id);
    
    protected FormDef getFormDef() {
        return formDef;
    }
    
    protected String getBaseUrl() {
        return subUrl;
    }

    @GET
    @Path("/")
    public Content getListHome(@QueryParam("q") String searchQuery) throws Throwable {
        return getList(searchQuery, 0);
    }
    
    @GET
    @Path("/{pageNumber}")
    public Content getList(@QueryParam("q") String searchQuery, @PathParam("pageNumber") int pageNumber) {
        String keyword =  Util.isNotEmpty(searchQuery)?searchQuery:null;

        Content page = newPage(title, listBodyTemplate);
        page.add("searchQuery", searchQuery);
        page.add("page", getPage(keyword, pageNumber, PAGE_SIZE));
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        
        return page;
    }

    @GET
    @Path("/view/{id}")
    public Response getViewRecord(@PathParam("id") String id) {
        ENTITY record = getRecord(id);
        if (record == null) {
            return notFound();
        }
        
        return ok(getViewRecordImpl(id, record));
    }

    protected Content getViewRecordImpl(String id, ENTITY record) {
        Content page = newPage(title, viewBodyTemplate);
        page.add("id", id);
        page.add("record", record);
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        return page;
    }

    @GET
    @Path("/new")
    public Response getNewRecord() {
        // TODO
        return notFound();
    }
    
//    @POST
//    @Path("/new")
//    public Response postNewRecord() {
//        // TODO
//        return gotoPath(getBaseUrl());
//    }
    
    @GET
    @Path("/edit/{id}")
    public Response getEditRecord(@PathParam("id") String id) {
        if (Util.isEmpty(editBodyTemplate)) {
            return notFound();
        }
        
        Content page = getEditRecordImpl(id);
        return ok(page);
    }

    protected Content getEditRecordImpl(String id) {
        Content page = newPage(title, editBodyTemplate);
        page.add("id", id);
        page.add("record", getRecord(id));
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", getFormDef());
        return page;
    } 

    @POST
    @Path("/edit/{id}")
    public Response postEditRecord(@PathParam("id") String id, @Form ENTITY form) throws Throwable {
        System.out.println("postEditRecord - form: " + form);
        return notFound();
    }
    

//    @POST
//    @Path("/edit/{id}")
//    public Response postEditRecord(@PathParam("id") String id) {
//        // TODO
//        return gotoPath(getBaseUrl());
//    }
    
}
