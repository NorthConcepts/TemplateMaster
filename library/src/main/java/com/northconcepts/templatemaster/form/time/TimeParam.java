package com.northconcepts.templatemaster.form.time;

import java.sql.Time;
import java.text.SimpleDateFormat;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class TimeParam extends Bean {

    private final static SimpleDateFormat[] format = {new SimpleDateFormat("HH"), new SimpleDateFormat("HH:mm"), new SimpleDateFormat("HH:mm:ss")};
    
    private final Time time;

    public TimeParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.time = null;
        } else {
            try {
                synchronized (format) {
                    int colons = Util.getCharacterCount(dateString, ':');
                    if (colons > format.length) {
                        throw new TemplateMasterException("unknown time format").set("dateString", dateString);
                    }
                    this.time = new Time(format[colons].parse(dateString).getTime());
                }
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        if (time != null) {
            return time.toString();
        } else {
            return "";
        }
    }
}
