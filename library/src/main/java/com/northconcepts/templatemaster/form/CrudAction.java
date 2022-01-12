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
package com.northconcepts.templatemaster.form;

import com.northconcepts.templatemaster.service.Bean;

public class CrudAction extends Bean implements PreparableViewer {
    
    private final String displayName;
    private final String subPath;
    private final String target;
    private FormMethod method = FormMethod.GET;
    private final CssStyleClass cssStyleClass = new CssStyleClass();
    
    public CrudAction(String displayName, String subPath, String target) {
        this.displayName = displayName;
        this.subPath = subPath;
        this.target = target;
    }

    public CrudAction(String displayName, String subPath) {
        this.displayName = displayName;
        this.subPath = subPath;
        this.target = null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSubPath() {
        return subPath;
    }
    
    public String getTarget() {
        return target;
    }
    
    public FormMethod getMethod() {
        return method;
    }
    
    public CrudAction setMethod(FormMethod method) {
        this.method = method;
        return this;
    }
    
    public CssStyleClass getCssStyleClass() {
        return cssStyleClass;
    }
    
    public boolean isVisible(CrudResource<?, ?> resource, Object entity) {
        return true;
    }
    
}
