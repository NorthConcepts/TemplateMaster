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
