/*
 * Copyright (c) 2014-2022 North Concepts Inc.
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
 */
package com.northconcepts.templatemaster.rest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;

public final class Url {

    private static final String ENCODING = "UTF-8";
    private final URI uri;

    public Url(URI uri) {
        this.uri = uri;
    }

    public Url(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", uri);
        }
    }

    public Url(HttpServletRequest request) {
        String s = request.getRequestURL().toString();
        if (Util.isNotEmpty(request.getQueryString())) {
            s += "?" + request.getQueryString();
        }

        try {
            this.uri = new URI(s);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", s);
        }
    }

    public URI getUri() {
        return uri;
    }

    private Map<String, List<String>> parseQueryParams() {
        try {
            Map<String, List<String>> queryParams = new LinkedHashMap<String, List<String>>();

            String query = uri.getQuery();
            if (Util.isEmpty(query)) {
                return queryParams;
            }

            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                String key = URLDecoder.decode(pair[0], ENCODING);
                String value = null;
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], ENCODING);
                }
                List<String> values = queryParams.get(key);
                if (values == null) {
                    values = new ArrayList<String>();
                    queryParams.put(key, values);
                }
                if (value != null) {
                    values.add(value);
                }
            }

            return queryParams;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", uri);
        }
    }

    private String queryParamsToString(Map<String, List<String>> queryParams) throws UnsupportedEncodingException {
        String s = "";
        for (String key : queryParams.keySet()) {
            if (!Util.isEmpty(s)) {
                s += "&";
            }
            List<String> values = queryParams.get(key);
            if (values == null || values.size() == 0) {
                s += URLEncoder.encode(key, ENCODING);
            } else {
                for (String value : values) {
                    s += URLEncoder.encode(key, ENCODING) + "=" + URLEncoder.encode(value, ENCODING);
                }
            }
        }

        return s;
    }

    private static void setQuery(URI uri, String query) {
        try {
            if (Util.isEmpty(query)) {
                query = null;
            }

            Field field = URI.class.getDeclaredField("query");
            field.setAccessible(true);
            field.set(uri, query);

            field = URI.class.getDeclaredField("string");
            field.setAccessible(true);
            field.set(uri, null);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", uri).set("query", query);
        }

    }

    public Url setQueryParam(String key, Object newValue) {
        Map<String, List<String>> queryParams = parseQueryParams();

        List<String> values = queryParams.get(key);
        if (values == null) {
            values = new ArrayList<String>();
            queryParams.put(key, values);
        } else {
            values.clear();
        }

        if (newValue != null) {
            values.add(newValue.toString());
        }

        try {
            URI uri = new URI(this.uri.getScheme(), this.uri.getUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getPath(), null, this.uri.getFragment());
            setQuery(uri, queryParamsToString(queryParams));
            return new Url(uri);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", this.uri).set("key", key).set("newValue", newValue);
        }
    }

    public Url removeQueryParam(String... keys) {
        Map<String, List<String>> queryParams = parseQueryParams();
        if (keys != null) {
            for (String key : keys) {
                queryParams.remove(key);
            }
        }
        try {
            URI uri = new URI(this.uri.getScheme(), this.uri.getUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getPath(), null, this.uri.getFragment());
            setQuery(uri, queryParamsToString(queryParams));
            return new Url(uri);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", this.uri).set("key", keys);
        }
    }

    public Url retainQueryParam(String... keys) {
        Map<String, List<String>> queryParams = parseQueryParams();
        if (keys != null) {
            Set<String> keySet = new HashSet<String>(Arrays.asList(keys));
            for (String key : new HashSet<String>(queryParams.keySet())) {
                if (!keySet.contains(key)) {
                    queryParams.remove(key);
                }
            }
        }
        try {
            URI uri = new URI(this.uri.getScheme(), this.uri.getUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getPath(), null, this.uri.getFragment());
            setQuery(uri, queryParamsToString(queryParams));
            return new Url(uri);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", this.uri).set("key", keys);
        }
    }

    public Url setPath(String newPath) {
        try {
            URI baseHref = new URI(RequestHolder.getBaseUrl());

            String baseFolder;
            if (baseHref.getPath() != null) {
                baseFolder = baseHref.getPath();
            } else {
                baseFolder = "/";
            }

            newPath = Util.trimRight(baseFolder, '/') + "/" + Util.trimLeft(newPath, '/');

            URI uri = new URI(this.uri.getScheme(), this.uri.getUserInfo(), this.uri.getHost(), this.uri.getPort(), newPath, this.uri.getQuery(),
                    this.uri.getFragment());
            return new Url(uri);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("uri", this.uri).set("newPath", newPath);
        }
    }

    @Override
    public String toString() {
        return uri.toString();
    }

}
