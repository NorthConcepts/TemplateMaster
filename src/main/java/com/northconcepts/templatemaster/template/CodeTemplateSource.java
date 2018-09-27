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

import java.io.StringReader;

import com.northconcepts.templatemaster.content.ContentException;

import freemarker.template.Template;

public class CodeTemplateSource implements ITemplateSource {
    
    private final String code;
    
    public CodeTemplateSource(String code) {
        this.code = code;
    }

    @Override
    public Template getTemplate() {
        try {
            return new Template(getClass().getName() + "-" + hashCode(), new StringReader(code), Templates.get().getConfiguration());
        } catch (Throwable e) {
            throw ContentException.wrap(e).set("code", code);
        }
    }
    
    @Override
    public String toString() {
        return getClass().getName() + ": " + code;
    }

}
