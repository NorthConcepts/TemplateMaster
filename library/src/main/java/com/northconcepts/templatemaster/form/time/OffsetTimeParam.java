package com.northconcepts.templatemaster.form.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class OffsetTimeParam extends Bean {

    private final static DateTimeFormatter[] format = { 
            DateTimeFormatter.ofPattern("HH"), 
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("HH:mm:ss") };
    
    private final OffsetTime time;

    public OffsetTimeParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.time = null;
        } else {
            try {
                synchronized (format) {
                    int colons = Util.getCharacterCount(dateString, ':');
                    if (colons > format.length) {
                        throw new TemplateMasterException("unknown time format").set("dateString", dateString);
                    }
                    this.time = OffsetTime.parse(dateString, format[colons]);
                }
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public OffsetTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time != null ? time.toString() : "";
    }
}
