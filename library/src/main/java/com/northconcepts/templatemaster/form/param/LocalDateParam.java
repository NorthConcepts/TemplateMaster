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
