package com.stackhunter.blog.example.articlesubmission.website;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import com.northconcepts.templatemaster.content.Util;

@WebFilter(urlPatterns = "/")
public class RootRedirectFilter implements Filter {

    private String locaton;

    public RootRedirectFilter() {
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String contextPath = filterConfig.getServletContext().getContextPath();
        if (Util.isNotEmpty(contextPath)) {
            locaton = contextPath + "/articles";
        } else {
            locaton = "/articles";
        }
    }
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse)response).sendRedirect(locaton);
    }

}
