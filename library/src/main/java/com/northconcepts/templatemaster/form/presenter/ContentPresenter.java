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
package com.northconcepts.templatemaster.form.presenter;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class ContentPresenter implements FieldValuePresenter {
    
    public static ContentPresenter fromFile(String templateFile) {
        return new ContentPresenter(new Content(templateFile));
    }
    
    public static ContentPresenter fromTemplateCode(String code) {
        return new ContentPresenter(new Content().setTemplateCode(code));
    }
    
    private Content content;

    public ContentPresenter(Content content) {
        this.content = content;
    }
    
    public Content getContent() {
        return content;
    }
    
    public ContentPresenter setContent(Content content) {
        this.content = content;
        return this;
    }

    @Override
    public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        return content
                .clone()
                .add("resource", resource)
                .add("fieldDef", fieldDef)
                .add("formDef", fieldDef.getFormDef())
                .add("record", entity)
                .add("entity", entity)
                .add("fieldValue", fieldValue)
                .toString();
    }

    @Override
    public boolean isDisplayValueRequiresHtmlEscaping() {
        return false;
    }

}
