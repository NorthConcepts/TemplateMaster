package com.northconcepts.templatemaster.form.param;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class OffsetDateTimeParam extends Param<OffsetDateTime> {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN);
    
    public OffsetDateTimeParam(String valueAsString) {
        super(valueAsString);
    }

    public OffsetDateTimeParam(OffsetDateTime value) {
        super(value);
    }

    @Override
    protected OffsetDateTime parse(String valueAsString) throws Throwable {
        return OffsetDateTime.parse(valueAsString, format);
    }

    @Override
    protected String format(OffsetDateTime value) throws Throwable {
        return format.format(value);
    }
  
    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("format", format).set("pattern", PATTERN)
;
    }

}
