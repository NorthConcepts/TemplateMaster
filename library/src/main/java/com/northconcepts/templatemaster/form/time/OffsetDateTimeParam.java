package com.northconcepts.templatemaster.form.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class OffsetDateTimeParam extends Bean {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final OffsetDateTime datetime;

    public OffsetDateTimeParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.datetime = null;
        } else {
            try {
                this.datetime = OffsetDateTime.parse(dateString, format);
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public OffsetDateTime getDatetime() {
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
