package com.stackhunter.blog.example.articlesubmission.website;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

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
