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
package com.northconcepts.templatemaster.form.param;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public abstract class Param<T> extends Bean {
    
    protected final T value;
    protected final String valueAsString;

    public Param(T value) {
        this.value = value;
        if (value == null) {
            this.valueAsString = null;
        } else {
            try {
                this.valueAsString = format(value);
            } catch (Throwable e) {
                throw addExceptionProperties(TemplateMasterException.wrap(e));
            }
        }
    }
    
    public Param(String valueAsString) {
        this.valueAsString = valueAsString;
        if (Util.isEmpty(valueAsString)) {
            this.value = null;
        } else {
            try {
                this.value = parse(valueAsString);
            } catch (Throwable e) {
                throw addExceptionProperties(TemplateMasterException.wrap(e));
            }
        }
    }
    
    protected abstract T parse(String valueAsString) throws Throwable;

    protected abstract String format(T value) throws Throwable;
    
    @Override
    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("valueAsString", valueAsString).set("value", value);
    }

    public T getValue() {
        return value;
    }
    
    public String getValueAsString() {
        return valueAsString;
    }
    
    @Override
    public String toString() {
        return value != null ? value.toString() : "";
    }
    
}
