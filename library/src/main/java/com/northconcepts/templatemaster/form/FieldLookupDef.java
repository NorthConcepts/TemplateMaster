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

public abstract class FieldLookupDef extends Bean implements PreparableEditor {
    
    public abstract int getValueCount();

    public abstract String getValue(int index);

    public String getDisplayName(int index) {
        return getValue(index);
    }
    
}
