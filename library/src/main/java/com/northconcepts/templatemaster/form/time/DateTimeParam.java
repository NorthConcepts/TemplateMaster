package com.northconcepts.templatemaster.form.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.northconcepts.templatemaster.form.Param;

public class DateTimeParam extends Param<Date> {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
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

}
