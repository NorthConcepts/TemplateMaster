package com.northconcepts.templatemaster.form.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class DateParam extends Bean {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    private final Date date;

    public DateParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.date = null;
        } else {
            try {
                synchronized (format) {
                    this.date = format.parse(dateString);
                }
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        if (date != null) {
            return date.toString();
        } else {
            return "";
        }
    }
}
