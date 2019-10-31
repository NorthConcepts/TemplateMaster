package com.northconcepts.templatemaster.form.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.form.Param;

public class LocalDateTimeParam extends Param<LocalDateTime> {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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

}
