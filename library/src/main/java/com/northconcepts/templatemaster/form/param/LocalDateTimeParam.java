package com.northconcepts.templatemaster.form.param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class LocalDateTimeParam extends Param<LocalDateTime> {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN);
    
    public LocalDateTimeParam(String valueAsString) {
        super(valueAsString);
    }

    public LocalDateTimeParam(LocalDateTime value) {
        super(value);
    }

    @Override
    protected LocalDateTime parse(String valueAsString) throws Throwable {
        return LocalDateTime.parse(valueAsString, format);
    }

    @Override
    protected String format(LocalDateTime value) throws Throwable {
        return format.format(value);
    }

    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("format", format).set("pattern", PATTERN);
    }

}
