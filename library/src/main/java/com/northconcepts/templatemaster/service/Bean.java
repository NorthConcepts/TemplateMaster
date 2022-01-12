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
package com.northconcepts.templatemaster.service;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.northconcepts.templatemaster.content.TemplateMasterException;

public class Bean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final ObjectMapper json = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .setSerializationInclusion(Include.NON_NULL);
    
    static {
        json.getSerializerProvider().setNullKeySerializer(new JsonSerializer<Object>() {

            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeFieldName("");
            }
        });
    }

    public static <T extends Bean> T fromJsonString(String jsonString, Class<T> type) {
        try {
            T bean = json.readValue(jsonString, type);
            return bean;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("jsonString", jsonString).set("type", type);
        }
    }

    public static <T extends Bean> T fromJsonString(String jsonString, T bean) {
        try {
            json.readerForUpdating(bean).readValue(jsonString);
            return bean;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("jsonString", jsonString).set("bean", bean);
        }
    }

    public static <T extends Bean> T fromJsonString(JsonNode jsonNode, Class<T> type) {
        try {
            T bean = json.treeToValue(jsonNode, type);
            return bean;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("jsonNode", jsonNode).set("type", type);
        }
    }

    public static <T extends Bean> T fromJsonString(JsonNode jsonNode, T bean) {
        try {
            json.readerForUpdating(bean).readValue(jsonNode);
            return bean;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("jsonNode", jsonNode).set("bean", bean);
        }
    }

    public Bean() {
    }

    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        exception.set("_class", getClass());
        return exception;
    }

    public String toJsonString() {
        try {
            synchronized (json) {
                return json.writeValueAsString(this);
            }
        } catch (Throwable e) {
            throw addExceptionProperties(TemplateMasterException.wrap(e));
        }
    }

    @Override
    public String toString() {
        return toJsonString();
    }

}
