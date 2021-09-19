package com.northconcepts.templatemaster.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XForwardedRequestWrapper extends HttpServletRequestWrapper {

    private static final String HTTP_COLON = "http:";
    private static final String HTTPS_COLON = "https:";

    public XForwardedRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public HttpServletRequest getNestedRequest() {
        return (HttpServletRequest) getRequest();
    }

    public boolean isOverSsl() {
        return "https".equals(getScheme());
    }
    
    @Override
    public String getRemoteAddr() {
        return RequestHolder.getRemoteAddr(getNestedRequest());
    }

    @Override
    public String getRemoteHost() {
        return RequestHolder.getRemoteHost(getNestedRequest());
    }

    @Override
    public String getScheme() {
        return RequestHolder.getScheme(getNestedRequest());
    }
    
    @Override
    public StringBuffer getRequestURL() {
        StringBuffer requestURL = super.getRequestURL();
        if (requestURL != null && requestURL.toString().startsWith(HTTP_COLON) && isOverSsl()) {
            requestURL.replace(0, HTTP_COLON.length(), HTTPS_COLON);
        }
        
        return requestURL;
    }

}
