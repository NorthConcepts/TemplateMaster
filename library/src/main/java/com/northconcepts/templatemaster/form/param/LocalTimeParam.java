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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;

public class LocalTimeParam extends Param<LocalTime> {

    private final static DateTimeFormatter[] format = { 
            DateTimeFormatter.ofPattern("HH"), 
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("HH:mm:ss") };

    public LocalTimeParam(String valueAsString) {
        super(valueAsString);
    }

    public LocalTimeParam(LocalTime value) {
        super(value);
    }

    @Override
    protected LocalTime parse(String valueAsString) throws Throwable {
        int colons = Util.getCharacterCount(valueAsString, ':');
        if (colons > format.length) {
            throw new TemplateMasterException("unknown time format").set("valueAsString", valueAsString);
        }
        return LocalTime.parse(valueAsString, format[colons]);
    }

    @Override
    protected String format(LocalTime value) throws Throwable {
        return format[2].format(value);
    }
    
    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("format", Arrays.asList(format));
    }

}
