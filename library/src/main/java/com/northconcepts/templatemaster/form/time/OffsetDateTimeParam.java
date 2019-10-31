package com.northconcepts.templatemaster.form.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.form.Param;

public class OffsetDateTimeParam extends Param<OffsetDateTime> {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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
  
}
