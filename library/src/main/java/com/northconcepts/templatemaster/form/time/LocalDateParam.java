package com.northconcepts.templatemaster.form.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public class LocalDateParam extends Bean {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private final LocalDate date;

    public LocalDateParam(String dateString) {
        if (Util.isEmpty(dateString)) {
            this.date = null;
        } else {
            try {
                this.date = LocalDate.parse(dateString, format);
            } catch (Throwable e) {
                throw TemplateMasterException.wrap(e).set("dateString", dateString);
            }
        }
    }
    
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date != null ? date.toString() : "";
    }
}
