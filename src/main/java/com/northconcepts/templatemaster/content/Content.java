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
package com.northconcepts.templatemaster.content;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.northconcepts.templatemaster.template.CodeTemplateSource;
import com.northconcepts.templatemaster.template.FileTemplateSource;
import com.northconcepts.templatemaster.template.ITemplateSource;

import freemarker.template.Template;
import freemarker.template.TemplateModelException;

public class Content implements IContent {
    
    public static final int BUFFER_SIZE = 1024 * 16;
    private Content parent;
    private final Map<String, ContentList> blocks = new HashMap<String, ContentList>();
    private ITemplateSource templateSource;
//    private final BlockTemplateModel templateModel = new BlockTemplateModel(this);
    
    public Content() {
    }
    
    public Content(String file) {
        setTemplateFile(file);
    }
    
    
    public Content getParent() {
        return parent;
    }
    
    protected Content setParent(Content parent) {
        this.parent = parent;
        return this;
    }

    public Map<String, ContentList> getBlocks() {
        return blocks;
    }
    
    public Content add(String name, IContent block) {
        get(name).add(block);
        return this;
    }

    public Content add(String name, Object value) {
        return add(name, new IdentityContent(value));
    }
    
    public ContentList get(String name) {
        return get(name, true);
    }
    
    public ContentList get(String name, boolean create) {
        ContentList result = blocks.get(name);
        if (result == null && create) {
            result = new ContentList(this);
            blocks.put(name, result);
        }
        return result;
    }
    
    public boolean isEmpty() throws TemplateModelException {
        return blocks.isEmpty() && (parent == null || parent.isEmpty());
    }
    
    public ITemplateSource getTemplateSource() {
        return templateSource;
    }
    
    public Content setTemplateSource(ITemplateSource templateSource) {
        this.templateSource = templateSource;
        return this;
    }
    
    public Content setTemplateFile(String file) {
        return setTemplateSource(new FileTemplateSource(file));
    }
    
    public Content setTemplateCode(String code) {
        return setTemplateSource(new CodeTemplateSource(code));
    }
    
    @Override
    public Object render() {
        try {
            StringWriter out = new StringWriter(BUFFER_SIZE);
            Template template = templateSource.getTemplate();
            template.process(this, out);
//            template.process(templateModel, out);
            return out.toString();
        } catch (Throwable e) {
            throw ContentException.wrap(e).set("templateSource", templateSource);
        }
    }
    
    @Override
    public String toString() {
        return render().toString();
    }

}
