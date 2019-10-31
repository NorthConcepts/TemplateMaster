package com.northconcepts.templatemaster.form.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class DateTimeParam extends Bean {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private final Date datetime;

    public DateTimeParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.datetime = null;
        } else {
            try {
                synchronized (format) {
                    this.datetime = format.parse(dateString);
                }
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public Date getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        if (datetime != null) {
            return datetime.toString();
        } else {
            return "";
        }
    }
}
