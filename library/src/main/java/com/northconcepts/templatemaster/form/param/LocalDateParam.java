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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class LocalDateParam extends Param<LocalDate> {

    private static final String PATTERN = "yyyy-MM-dd";
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN);
    
    public LocalDateParam(String valueAsString) {
        super(valueAsString);
    }

    public LocalDateParam(LocalDate value) {
        super(value);
    }

    @Override
    protected LocalDate parse(String valueAsString) throws Throwable {
        return LocalDate.parse(valueAsString, format);
    }

    @Override
    protected String format(LocalDate value) throws Throwable {
        return format.format(value);
    }

    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("format", format).set("pattern", PATTERN);
    }

}
