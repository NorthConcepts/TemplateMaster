package com.northconcepts.templatemaster.form.editor;

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

    private final DateTimeFormatter formatter;

    public DateTimeEditor(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }
    
    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue == null) {
            return "";
        }
        
        if (fieldValue instanceof TemporalAccessor) {
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
    public boolean isControlIncluded() {
        return false;
    }

}