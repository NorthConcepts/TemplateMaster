package com.northconcepts.templatemaster.form;

import javax.ws.rs.QueryParam;

public class ListRequest {

    @QueryParam("q")
    private String searchQuery;

    @QueryParam("s")
    private String sortField;

    @QueryParam("f")
    private String namedFilterCode;

    @QueryParam("p")
    private Integer pageSize;

    @QueryParam("cb")
    private String callbackUrl;

    //int pageNumber;

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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /*    public int getPageNumber() {
        return pageNumber;
    }

    @PathParam("pageNumber")
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }*/
}
