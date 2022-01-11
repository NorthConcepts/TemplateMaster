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

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;
import com.northconcepts.templatemaster.form.PreparableViewer;

public interface FieldValuePresenter extends PreparableViewer {
    
    public static final FieldValuePresenter NULL = new FieldValuePresenter() {
        
        @Override
        public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
            return fieldValue==null?fieldDef.getNullDisplayValue():fieldValue.toString();
        }
        
        @Override
        public boolean isDisplayValueRequiresHtmlEscaping() {
            return true;
        }
        
    };
    
    String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue);
    
    boolean isDisplayValueRequiresHtmlEscaping();

}
