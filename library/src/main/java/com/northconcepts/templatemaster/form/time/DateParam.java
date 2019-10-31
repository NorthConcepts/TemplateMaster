package com.northconcepts.templatemaster.form.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.northconcepts.templatemaster.form.Param;

public class DateParam extends Param<Date> {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public DateParam(String valueAsString) {
        super(valueAsString);
    }

    public DateParam(Date value) {
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
