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

import com.fasterxml.jackson.databind.JsonNode;
import com.northconcepts.templatemaster.content.Content;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ContentObjectWrapper extends DefaultObjectWrapper {
    
    public static final ContentObjectWrapper INSTANCE = new ContentObjectWrapper();
    
    public ContentObjectWrapper() {
        super(Configuration.VERSION_2_3_28);
    }
    
    @Override
    public TemplateModel wrap(Object object) throws TemplateModelException {
        if (object instanceof Content) {
            return new ContentTemplateModel((Content)object);
        }
                                        
        if (object instanceof JsonNode) {
            return JsonTemplateModel.toTemplateModel((JsonNode)object);
        }
                                        
        TemplateModel templateModel = super.wrap(object);
        
        return templateModel;
    }

}
