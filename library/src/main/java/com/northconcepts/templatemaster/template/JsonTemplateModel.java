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
package com.northconcepts.templatemaster.template;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.JsonNode;
import com.northconcepts.templatemaster.content.TemplateMasterException;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class JsonTemplateModel implements TemplateHashModel {

    private final JsonNode node;

    private JsonTemplateModel(JsonNode node) {
        this.node = node;
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        // TODO: handle arrays
        JsonNode value = node.get(key);
        if (value == null) {
            throw new TemplateMasterException("Value not found").set("key", key).set("parentJsonObject", node);
        }
        return Templates.get().getConfiguration().getObjectWrapper().wrap(value);
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return node == null || node.size() == 0;
    }
    
    public static TemplateModel toTemplateModel(JsonNode node) {
        try {
            if (node.isObject() || node.isArray()) {
                return new JsonTemplateModel(node);
            } 
            
            Object value = null;
            if (node.isTextual()) {
                value = node.asText();
            } else if (node.isInt()) {
                value = node.asInt();
            } else if (node.isLong()) {
                value = node.asLong();
            } else if (node.isBoolean()) {
                value = node.asBoolean();
            } else if (node.isDouble()) {
                value = node.asDouble();
            } else if (node.isFloatingPointNumber() | node.isBigDecimal()) {
                value = new BigDecimal(node.asText());
            } else if (node.isIntegralNumber() || node.isBigInteger()) {
                value = new BigInteger(node.asText());
            } else if (node.isBinary()) {
                value = node.asText(); // TODO: decode Base64 
            }
            return Templates.get().getConfiguration().getObjectWrapper().wrap(value);
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("jsonNode", node);
        }
    }

}
