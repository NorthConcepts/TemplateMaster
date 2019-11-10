package com.northconcepts.templatemaster.form.editor;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.northconcepts.templatemaster.content.Content;
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
        
        Content content = new Content()
                .setTemplateCode("<div class=\"input-group date\" id=\""+ fieldDef.getName() +"\"><input type=\"text\" id=\"${field.id!?html}\" value=\"${dateString}\" class=\"form-control\" name=\"${field.name!?html}\"  placeholder=\"${field.placeholder!}\" [#if field.required]required[/#if] [#if field.autofocus]autofocus[/#if]/><span class=\"input-group-addon\"><span class=\"glyphicon-calendar glyphicon\"></span></span></div>")
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
    public boolean isControlIncluded() {
        return true;
    }

}