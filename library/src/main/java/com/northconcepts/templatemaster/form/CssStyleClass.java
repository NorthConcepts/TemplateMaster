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

import java.util.LinkedHashSet;
import java.util.Set;

import com.northconcepts.templatemaster.content.Util;

public class CssStyleClass {

    private final Set<String> styleNames = new LinkedHashSet<String>();

    public CssStyleClass() {
    }
    
    public Set<String> getStyleNames() {
        return styleNames;
    }
    
    public CssStyleClass add(String styleName) {
        styleNames.add(styleName);
        return this;
    }

    public CssStyleClass remove(String styleName) {
        styleNames.remove(styleName);
        return this;
    }
    
    public boolean hasStyles() {
        return styleNames.size() > 0;
    }
    
    @Override
    public String toString() {
        return Util.collectionToString(styleNames, " ");
    }

}
