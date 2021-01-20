package com.northconcepts.templatemaster.form;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.PathParam;

public class ListRequest {

    @QueryParam("q")
    String searchQuery;

    @QueryParam("s")
    String sortField;

    @QueryParam("f")
    String namedFilterCode;

    @PathParam("pageNumber")
    int pageNumber;

    @QueryParam("p")
    Integer pageSize;

    public ListRequest(String searchQuery, String sortField, String namedFilterCode, Integer pageSize) {
        this.searchQuery = searchQuery;
        this.sortField = sortField;
        this.namedFilterCode = namedFilterCode;
        this.pageNumber = 0;
        this.pageSize = pageSize;
    }

    public ListRequest(String searchQuery, String sortField, String namedFilterCode, int pageNumber, Integer pageSize) {
        this.searchQuery = searchQuery;
        this.sortField = sortField;
        this.namedFilterCode = namedFilterCode;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

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

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
