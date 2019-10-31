package com.northconcepts.templatemaster.form.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.form.Param;

public class LocalDateParam extends Param<LocalDate> {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
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

}
