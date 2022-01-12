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
package com.northconcepts.templatemaster.form.editor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class DateTimeEditor implements FieldValueEditor {

    public static final DateTimeEditor yyyyMMddHHmmss = new DateTimeEditor("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeEditor yyyyMMdd = new DateTimeEditor("yyyy-MM-dd");

    private DateTimeFormatter formatter;

    public DateTimeEditor(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern);
    }
    
    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue == null) {
            return "";
        }

        if (fieldValue instanceof Instant) {
            Instant instant = (Instant) fieldValue;
            formatter = formatter.withZone(ZoneId.systemDefault());
            return formatter.format(instant);
        } else if (fieldValue instanceof TemporalAccessor) {
            TemporalAccessor temporalAccessor = (TemporalAccessor) fieldValue;
            return formatter.format(temporalAccessor);
        } else if (fieldValue instanceof Date) {
            Date date = (Date) fieldValue;
            return formatter.format(date.toInstant());
        }
        
        throw new TemplateMasterException("unsupported field value type " + fieldValue.getClass())
            .set("resource", resource)
            .set("fieldDef", fieldDef)
            .set("entity", entity)
            ;
    }

    @Override
    public boolean isEditValueRequiresHtmlEscaping() {
        return true;
    }
    
    @Override
    public boolean isEditControlIncluded() {
        return false;
    }

}
