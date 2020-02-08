package com.northconcepts.templatemaster.form.presenter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class DateTimePresenter implements FieldValuePresenter {

    public static final DateTimePresenter EEEMMMdyyyyhmmssaz = new DateTimePresenter("EEE MMM d, yyyy h:mm:ss a (z)");
    public static final DateTimePresenter EEEMMMdyyyyhmmaz = new DateTimePresenter("EEE MMM d, yyyy h:mm a z");
    public static final DateTimePresenter EEEMMMdyyyy = new DateTimePresenter("EEE MMM d, yyyy");

    private final DateTimeFormatter formatter;

    public DateTimePresenter(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    @Override
    public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue instanceof TemporalAccessor) {
            TemporalAccessor temporalAccessor = (TemporalAccessor) fieldValue;
            return formatter.format(temporalAccessor);
        } else if (fieldValue instanceof Date) {
            Date date = (Date) fieldValue;
            return formatter.format(date.toInstant());
        }
        return FieldValuePresenter.NULL.getDisplayValue(resource, fieldDef, entity, fieldValue);
    }

    @Override
    public boolean isDisplayValueRequiresHtmlEscaping() {
        return true;
    }

}