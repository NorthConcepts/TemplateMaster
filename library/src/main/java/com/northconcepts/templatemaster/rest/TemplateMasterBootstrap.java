/*
 * Copyright (c) 2014-2018 North Concepts Inc.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.template.Templates;

import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;

@WebFilter(urlPatterns = "/*")
public class TemplateMasterBootstrap extends ResteasyBootstrap implements ServletContextListener, Filter {

    public static final int MAX_ENV_VALUE_LENGTH = 256;

    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    public final Logger LOG = LogManager.getLogger(getClass());

    private FilterConfig filterConfig;

    private ServletContext servletContext;

    public TemplateMasterBootstrap() {
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            LOG.debug("contextInitialized: " + event.getServletContext());
            ServletContextHolder.setServletContext(event.getServletContext());
            super.contextInitialized(event);
            servletContext = event.getServletContext();
            Configuration configuration = Templates.get().getConfiguration();
            // configuration.setTemplateLoader(new WebappTemplateLoader(servletContext, "WEB-INF/content"));
            configuration.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), "templatemaster"));
            configuration.setNumberFormat("0.####");

            BeansWrapper wrapper = (BeansWrapper) configuration.getObjectWrapper();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            configuration.setSharedVariable("RequestHolder", (TemplateHashModel) staticModels.get(RequestHolder.class.getName()));

            ResteasyProviderFactory providerFactory = getResteasyProviderFactory();
            providerFactory.registerProvider(ContentMessageBodyWriter.class);
            providerFactory.registerProvider(ContentExceptionMapper.class);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e);
        }
        debugEnvironment(getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOG.debug("contextDestroyed: " + event.getServletContext());
        super.contextDestroyed(event);
        servletContext = null;
        ServletContextHolder.setServletContext(null);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestHolder.setHttpServletRequest((HttpServletRequest) request);
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            TemplateMasterException e2 = TemplateMasterException.wrap(e);
            e2 = exception(e2, (HttpServletRequest) request);
            LOG.error(e2, e2);
            throw e2;
        } finally {
            RequestHolder.clearHttpServletRequest();
        }
    }

    public Registry getRegistry() {
        return (Registry) servletContext.getAttribute(Registry.class.getName());
    }

    public ResteasyProviderFactory getResteasyProviderFactory() {
        return ResteasyProviderFactory.getInstance();
    }

    public TemplateMasterException exception(TemplateMasterException e, HttpServletRequest request) {
        e.set("Request.AuthType", request.getAuthType());
        e.set("Request.CharacterEncoding", request.getCharacterEncoding());
        e.set("Request.ContentLength", request.getContentLength());
        e.set("Request.ContentType", request.getContentType());
        e.set("Request.LocalAddr", request.getLocalAddr());
        e.set("Request.LocalName", request.getLocalName());
        e.set("Request.LocalPort", request.getLocalPort());
        e.set("Request.Method", request.getMethod());
        e.set("Request.PathInfo", request.getPathInfo());
        e.set("Request.PathTranslated", request.getPathTranslated());
        e.set("Request.Protocol", request.getProtocol());
        e.set("Request.QueryString", request.getQueryString());
        e.set("Request.RemoteAddr", request.getRemoteAddr());
        e.set("Request.RemoteHost", request.getRemoteHost());
        e.set("Request.RemotePort", request.getRemotePort());
        e.set("Request.RemoteUser", request.getRemoteUser());
        e.set("Request.RequestedSessionId", request.getRequestedSessionId());
        e.set("Request.RequestedSessionIdFromCookie", request.isRequestedSessionIdFromCookie());
        // e.set("Request.RequestedSessionIdFromUrl", request.isRequestedSessionIdFromUrl());
        e.set("Request.RequestedSessionIdFromURL", request.isRequestedSessionIdFromURL());
        e.set("Request.RequestedSessionIdValid", request.isRequestedSessionIdValid());
        e.set("Request.RequestURI", request.getRequestURI());
        e.set("Request.RequestURL", request.getRequestURL());
        e.set("Request.Scheme", request.getScheme());
        e.set("Request.ServerName", request.getServerName());
        e.set("Request.ServerPort", request.getServerPort());
        e.set("Request.ServletPath", request.getServletPath());

        Enumeration<?> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = (String) headerNames.nextElement();
                e.set("Request.Header." + name, request.getHeader(name));
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                e.set("Request.Cookie." + cookies[i].getName(), cookies[i].getValue());
            }
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            ArrayList<String> parameterNames = new ArrayList<String>(parameterMap.keySet());
            Collections.sort(parameterNames);
            for (int i = 0; i < parameterNames.size(); i++) {
                String name = parameterNames.get(i);
                // String[] values = request.getParameterValues(name);
                String[] values = parameterMap.get(name);
                if (values == null) {
                    e.set("Request.Parameter." + name, "null");
                } else if (values.length == 1) {
                    e.set("Request.Parameter." + name, values[0]);
                } else {
                    for (int j = 0; j < values.length; j++) {
                        e.set("Request.Parameter." + name + "[" + j + "]", values[j]);
                    }
                }
            }
        }

        Enumeration<?> attributeNames = request.getAttributeNames();
        if (attributeNames != null) {
            while (attributeNames.hasMoreElements()) {
                String name = (String) attributeNames.nextElement();
                Object value = request.getAttribute(name);
                if (value == null) {
                    e.set("Request.Attribute." + name, "null");
                } else {
                    e.set("Request.Attribute." + name, value);
                }
            }
        }

        return e;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void debugEnvironment(ServletContext context) {
        StringBuilder s = new StringBuilder(1024 * 20);
        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("ServletContext:" + LINE_SEPARATOR);
        s.append("    ServletContextName=[").append(context.getServletContextName()).append("]" + LINE_SEPARATOR);
        s.append("    ServerInfo=[").append(context.getServerInfo()).append("]" + LINE_SEPARATOR);

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("JVM Properties:" + LINE_SEPARATOR);
        Properties properties = System.getProperties();
        if (properties != null) {
            Set<String> keys = new TreeSet(properties.keySet());
            for (String key : keys) {
                s.append("    ").append(key).append("=[").append(value(System.getProperty(key))).append("]" + LINE_SEPARATOR);
            }
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Environment Properties:" + LINE_SEPARATOR);
        Map<String, String> env = System.getenv();
        if (env != null) {
            Set<String> keys = new TreeSet(env.keySet());
            for (String key : keys) {
                s.append("    ").append(key).append("=[").append(value(env.get(key))).append("]" + LINE_SEPARATOR);
            }
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Servlet Context Init Parameters:" + LINE_SEPARATOR);
        Enumeration initParameterNames = context.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String key = (String) initParameterNames.nextElement();
            s.append("    ").append(key).append("=[").append(value(context.getInitParameter(key))).append("]" + LINE_SEPARATOR);
        }

        s.append("------------------------------------------" + LINE_SEPARATOR);
        s.append("Servlet Context Attribute:" + LINE_SEPARATOR);
        Enumeration attributeNames = context.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = (String) attributeNames.nextElement();
            s.append("    ").append(key).append("=[").append(value(context.getAttribute(key))).append("]" + LINE_SEPARATOR);
        }

        s.append("------------------------------------------");

        // log.info(s);
        System.out.println(s);
    }

    private static String value(Object value) {
        if (value == null) {
            return null;
        }
        String s = value.toString();
        if (s.length() > MAX_ENV_VALUE_LENGTH) {
            s = s.substring(0, MAX_ENV_VALUE_LENGTH) + "...";
        }
        return s;
    }

}
