package com.northconcepts.templatemaster.form.param;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class DateTimeParam extends Param<Date> {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static SimpleDateFormat format = new SimpleDateFormat(PATTERN);
    
    public DateTimeParam(String valueAsString) {
        super(valueAsString);
    }

    public DateTimeParam(Date value) {
        super(value);
    }

    @Override
    protected Date parse(String valueAsString) throws Throwable {
        synchronized (format) {
            return format.parse(valueAsString);
        }
    }

    @Override
    protected String format(Date value) throws Throwable {
        synchronized (format) {
            return format.format(value);
        }
    }

    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        synchronized (format) {
            return super.addExceptionProperties(exception).set("format", format).set("pattern", PATTERN);
        }
    }

}
