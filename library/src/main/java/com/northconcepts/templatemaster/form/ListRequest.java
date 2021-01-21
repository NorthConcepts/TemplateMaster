package com.northconcepts.templatemaster.form;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

public class ListRequest {

    @QueryParam("q")
    private String searchQuery;

    @QueryParam("s")
    private String sortField;

    @QueryParam("f")
    private String namedFilterCode;

    int pageNumber;

    @QueryParam("p")
    Integer pageSize;

    private HttpRequest request;

    public ListRequest(){}

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getNamedFilterCode() {
        return namedFilterCode;
    }

    public void setNamedFilterCode(String namedFilterCode) {
        this.namedFilterCode = namedFilterCode;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    @PathParam("pageNumber")
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public HttpRequest getRequest() {
        return request;
    }

    @Context
    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
