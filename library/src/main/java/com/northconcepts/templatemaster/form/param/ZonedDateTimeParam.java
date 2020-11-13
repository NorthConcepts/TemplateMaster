package com.northconcepts.templatemaster.form.param;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class ZonedDateTimeParam extends Param<ZonedDateTime> {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN);
    
    public ZonedDateTimeParam(String valueAsString) {
        super(valueAsString);
    }

    public ZonedDateTimeParam(ZonedDateTime value) {
        super(value);
    }

    @Override
    protected ZonedDateTime parse(String valueAsString) throws Throwable {
        return ZonedDateTime.parse(valueAsString, format);
    }

    @Override
    protected String format(ZonedDateTime value) throws Throwable {
        return format.format(value);
    }

    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("format", format).set("pattern", PATTERN);
    }

}
