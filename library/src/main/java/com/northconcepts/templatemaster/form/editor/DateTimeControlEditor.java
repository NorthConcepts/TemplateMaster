package com.northconcepts.templatemaster.form.editor;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class DateTimeControlEditor implements FieldValueEditor {

    public static final DateTimeControlEditor yyyyMMddHHmmss = new DateTimeControlEditor("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeControlEditor yyyyMMdd = new DateTimeControlEditor("yyyy-MM-dd");

    private final DateTimeFormatter formatter;

    public DateTimeControlEditor(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }
    
    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        String dateString;
        if (fieldValue instanceof TemporalAccessor) {
            TemporalAccessor temporalAccessor = (TemporalAccessor) fieldValue;
            dateString = formatter.format(temporalAccessor);
        } else if (fieldValue instanceof Date) {
            Date date = (Date) fieldValue;
            dateString = formatter.format(date.toInstant());
        } else {
            dateString = "";
        }
        
        Content content = new Content("/formdef/edit-field-date-time.html")
                .add("subUrl", resource.getSubUrl())
                .add("record", entity)
                .add("field", fieldDef)
                .add("dateString", dateString); 
        return content.toString();
        
        
    }

    @Override
    public boolean isEditValueRequiresHtmlEscaping() {
        return false;
    }
    
    @Override
    public boolean isEditControlIncluded() {
        return true;
    }

}