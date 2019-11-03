package com.northconcepts.templatemaster.form;

import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.rest.BaseResource;
import com.northconcepts.templatemaster.rest.RequestHolder;
import com.northconcepts.templatemaster.rest.Url;

@Path("/")
@Produces(BaseResource.TEXT_HTML)
public abstract class CrudResource<ID extends Serializable, ENTITY extends Serializable> extends BaseResource {

    protected static final int PAGE_SIZE = 20;
    
    protected final String singularTitle;
    protected final String pluralTitle;
    protected final String subUrl;
    protected final FormDef formDef;
    private String listBodyTemplate = "/formdef/list.html";
    private String selectListBodyTemplate = "/formdef/select-list.html";
    private String listPagerTemplate = "/formdef/list-pager.html";    
    private String listJavascriptTemplate = "/formdef/list.js";    
    private String viewBodyTemplate = "/formdef/view.html";
    private String newBodyTemplate = "/formdef/new.html";
    private String editBodyTemplate = "/formdef/edit.html";

    public CrudResource(String singularTitle, String pluralTitle, String subUrl, FormDef formDef) {
        this.singularTitle = singularTitle;
        this.pluralTitle = pluralTitle;
        this.subUrl = subUrl;
        this.formDef = formDef;
    }

    protected abstract Page<ENTITY> getPage(String keyword, String sortField, int pageNumber, int pageSize);
    
    protected abstract ENTITY getRecord(ID id);
    
    protected ENTITY createRecord(ENTITY newRecord) {
        return newRecord;
    }
    
    protected ENTITY editRecord(ID id, ENTITY editedRecord) {
        return editedRecord;
    }
    
    protected long deleteRecords(ID[] ids) {
        return 0L;
    }
    
    public String getSingularTitle() {
        return singularTitle;
    }
    
    public String getPluralTitle() {
        return pluralTitle;
    }
    
    protected String getBaseUrl() {
        return subUrl;
    }
    
    public String getSubUrl() {
        return subUrl;
    }
    
    protected FormDef getFormDef() {
        return formDef;
    }
    
    public String getListBodyTemplate() {
        return listBodyTemplate;
    }

    public CrudResource<ID, ENTITY> setListBodyTemplate(String listBodyTemplate) {
        this.listBodyTemplate = listBodyTemplate;
        return this;
    }
    
    public String getSelectListBodyTemplate() {
        return selectListBodyTemplate;
    }
    
    public CrudResource<ID, ENTITY> setSelectListBodyTemplate(String selectListBodyTemplate) {
        this.selectListBodyTemplate = selectListBodyTemplate;
        return this;
    }

    public String getListPagerTemplate() {
        return listPagerTemplate;
    }

    public CrudResource<ID, ENTITY> setListPagerTemplate(String listPagerTemplate) {
        this.listPagerTemplate = listPagerTemplate;
        return this;
    }

    public String getListJavascriptTemplate() {
        return listJavascriptTemplate;
    }

    public CrudResource<ID, ENTITY> setListJavascriptTemplate(String listJavascriptTemplate) {
        this.listJavascriptTemplate = listJavascriptTemplate;
        return this;
    }

    public String getViewBodyTemplate() {
        return viewBodyTemplate;
    }

    public CrudResource<ID, ENTITY> setViewBodyTemplate(String viewBodyTemplate) {
        this.viewBodyTemplate = viewBodyTemplate;
        return this;
    }

    public String getNewBodyTemplate() {
        return newBodyTemplate;
    }

    public CrudResource<ID, ENTITY> setNewBodyTemplate(String newBodyTemplate) {
        this.newBodyTemplate = newBodyTemplate;
        return this;
    }

    public String getEditBodyTemplate() {
        return editBodyTemplate;
    }

    public CrudResource<ID, ENTITY> setEditBodyTemplate(String editBodyTemplate) {
        this.editBodyTemplate = editBodyTemplate;
        return this;
    }

    //==========================================================================================
    // List
    //==========================================================================================
    
    @GET
    @Path("/")
    public Content getListRecords(@QueryParam("q") String searchQuery, @QueryParam("s")String sortField) throws Throwable {
        return getListRecords(searchQuery, sortField, 0);
    }
    
    @GET
    @Path("/{pageNumber}")
    public Content getListRecords(@QueryParam("q") String searchQuery, @QueryParam("s")String sortField, @PathParam("pageNumber") int pageNumber) {
        searchQuery =  Util.isNotEmpty(searchQuery)?searchQuery:null;
        sortField = Util.isNotEmpty(sortField)?sortField:null;

        formDef.prepareViewer();
        
        Content page = newPage(pluralTitle, listBodyTemplate);
        page.add("searchQuery", searchQuery);
        page.add("page", getPage(searchQuery, sortField, pageNumber, PAGE_SIZE));
        page.add("resource", this);
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", formDef);
        page.add("listPager", new Content(listPagerTemplate));
        page.add("script", new Content(listJavascriptTemplate));
        page.add("sortField", sortField);

        return page;
    }

    protected Sort getSortBy(String column) {
        if(Util.isEmpty(column)) {
            return null;
        }
        return column.startsWith("-") ? Sort.by(Sort.Direction.DESC, column.replaceFirst("-", "")) :
                Sort.by(Sort.Direction.ASC, column);
    }

    protected Pageable getPageable(String sortField, int pageNumber, int pageSize) {
        Sort sort = getSortBy(sortField);
        return sort == null ? PageRequest.of(pageNumber, pageSize) : PageRequest.of(pageNumber, pageSize, sort);
    }
    //==========================================================================================
    // Select List
    //==========================================================================================
    
    @GET
    @Path("/select/")
    public Content getSelectListRecords(@QueryParam("cb") String callbackUrl, @QueryParam("q") String searchQuery, @QueryParam("s")String sortField) throws Throwable {
        return getSelectListRecords(callbackUrl, searchQuery, sortField, 0);
    }
    
    @GET
    @Path("/select/{pageNumber}")
    public Content getSelectListRecords(@QueryParam("cb") String callbackUrl, @QueryParam("q") String searchQuery, @QueryParam("s")String sortField, @PathParam("pageNumber") int pageNumber) {
        searchQuery =  Util.isNotEmpty(searchQuery)?searchQuery:null;
        sortField = Util.isNotEmpty(sortField)?sortField:null;

        formDef.prepareViewer();
        
        Content page = newPage("Select " + singularTitle, selectListBodyTemplate);
        page.add("searchQuery", searchQuery);
        page.add("page", getPage(searchQuery, sortField, pageNumber, PAGE_SIZE));
        page.add("resource", this);
        page.add("subUrl", subUrl + "/select");
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", formDef);
        page.add("listPager", new Content(listPagerTemplate));
        page.add("sortField", sortField);

        if (Util.isEmpty(callbackUrl)) {
            setErrorFlashMessage("No callback URL specified");
        } else {
            page.add("callbackUrl", new Url(callbackUrl));
        }
//        page.add("script", new Content(listJavascriptTemplate));
        
        
        return page;
    }
    
    //==========================================================================================
    // Delete
    //==========================================================================================
    
    @POST
    @Path("/delete")
    public Response postDeleteRecords(@FormParam("id") ID[] ids) throws Throwable {
        if (!formDef.isAllowDelete()) {
            setErrorFlashMessage("Delete is not allowed");
            return badRequest();
        }
        
        return postDeleteRecordsImpl(ids);
    }
    
    protected Response postDeleteRecordsImpl(ID[] ids) throws Throwable {
        long deleteRecords = deleteRecords(ids);
        setSuccessFlashMessage(deleteRecords + " " + (deleteRecords==1?singularTitle:pluralTitle).toLowerCase() + " deleted");
        return gotoUri(RequestHolder.getReferrer());
    }

    //==========================================================================================
    // View
    //==========================================================================================
    
    @GET
    @Path("/view/{id}")
    public Response getViewRecord(@PathParam("id") ID id) {
        ENTITY record = getRecord(id);
        if (record == null) {
            return notFound();
        }
        
        return ok(getViewRecordImpl(id, record));
    }

    protected Content getViewRecordImpl(ID id, ENTITY record) {
        formDef.prepareViewer();
        
        Content page = newPage(singularTitle, viewBodyTemplate);
        page.add("id", id);
        page.add("record", record);
        page.add("resource", this);
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", formDef);
        return page;
    }

    //==========================================================================================
    // Edit
    //==========================================================================================
    
    @GET
    @Path("/edit/{id}")
    public Response getEditRecord(@PathParam("id") ID id) {
        if (!formDef.isAllowEdit()) {
            setErrorFlashMessage("Update not allowed");
            return badRequest();
        }
        
        ENTITY record = getRecord(id);
        if (record == null) {
            return notFound();
        }
        
        Content page = getEditRecordImpl(id, record);
        return ok(page);
    }

    protected Content getEditRecordImpl(ID id, ENTITY record) {
        formDef.prepareEditor();
        
        Content page = newPage("Edit " + singularTitle, editBodyTemplate);
        page.add("id", id);
        page.add("record", record);
        page.add("resource", this);
        page.add("subUrl", subUrl);
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", getFormDef());
        page.add("formMode", FormMode.EDIT);        
        return page;
    } 

    @POST
    @Path("/edit/{id}")
    public Response postEditRecord(@PathParam("id") ID id, @Context UriInfo uriInfo, @Form ENTITY form) throws Throwable {
        if (!formDef.isAllowEdit()) {
            setErrorFlashMessage("Update not allowed");
            return badRequest();
        }
        
        return postEditRecordImpl(id, uriInfo, form);
    }
    
    protected Response postEditRecordImpl(ID id, UriInfo uriInfo, ENTITY form) throws Throwable {
        editRecord(id, form);
        setSuccessFlashMessage(singularTitle + " updated");
        return gotoUri(RequestHolder.getReferrer());
    }
    

    //==========================================================================================
    // New
    //==========================================================================================
    
    @GET
    @Path("/new")
    public Response getNewRecord() {
        if (!formDef.isAllowCreate()) {
            setErrorFlashMessage("Create not allowed");
            return badRequest();
        }
        
        Content page = getNewRecordImpl();
        return ok(page);
    }

    protected Content getNewRecordImpl() {
        formDef.prepareEditor();
        
        Content page = newPage("New " + singularTitle, newBodyTemplate);
//        page.add("record", getRecord(id));
        page.add("subUrl", subUrl);
        page.add("resource", this);
        page.add("baseUrl", getBaseUrl());
        page.add("formDef", getFormDef());
        page.add("formMode", FormMode.NEW);        
        return page;
    } 

    @POST
    @Path("/new")
    public Response postNewRecord(@Context UriInfo uriInfo, @Form ENTITY form) throws Throwable {
        if (!formDef.isAllowCreate()) {
            setErrorFlashMessage("Create not allowed");
            return badRequest();
        }
        
        try {
            return postNewRecordImpl(uriInfo, form);
        } catch (Throwable e) {
            setErrorFlashMessage("Error creating " + singularTitle + ": " + e.getMessage());
            return gotoPath(subUrl + "/new");
        }
    }
    
    protected Response postNewRecordImpl(UriInfo uriInfo, ENTITY form) throws Throwable {
        createRecord(form);
        setSuccessFlashMessage(singularTitle + " created");
        return gotoPath(subUrl);
    }
    
}
