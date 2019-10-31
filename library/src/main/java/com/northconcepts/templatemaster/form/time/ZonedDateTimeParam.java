package com.northconcepts.templatemaster.form.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.form.Param;

public class ZonedDateTimeParam extends Param<ZonedDateTime> {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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

}
