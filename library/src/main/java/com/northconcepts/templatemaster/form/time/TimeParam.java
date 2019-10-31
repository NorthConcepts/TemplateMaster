package com.northconcepts.templatemaster.form.time;

import java.sql.Time;
import java.text.SimpleDateFormat;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.form.Param;

public class TimeParam extends Param<Time> {

    private final static SimpleDateFormat[] format = {
            new SimpleDateFormat("HH"), 
            new SimpleDateFormat("HH:mm"), 
            new SimpleDateFormat("HH:mm:ss")};
    
    public TimeParam(String valueAsString) {
        super(valueAsString);
    }

    public TimeParam(Time value) {
        super(value);
    }

    @Override
    protected Time parse(String valueAsString) throws Throwable {
        synchronized (format) {
            int colons = Util.getCharacterCount(valueAsString, ':');
            if (colons > format.length) {
                throw new TemplateMasterException("unknown time format").set("valueAsString", valueAsString);
            }
            return new Time(format[colons].parse(valueAsString).getTime());
        }
    }

    @Override
    protected String format(Time value) throws Throwable {
        synchronized (format) {
            return format[2].format(value);
        }
    }
    
}
