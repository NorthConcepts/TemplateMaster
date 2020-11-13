package com.northconcepts.templatemaster.field.lookup;

import java.util.Date;

import com.northconcepts.templatemaster.form.FieldLookupDef;
import com.northconcepts.templatemaster.timezone.FormattedTimeZone;

public class TimeZoneFieldLookupDef extends FieldLookupDef {

    public static final TimeZoneFieldLookupDef INSTANCE = new TimeZoneFieldLookupDef();

    private FormattedTimeZone[] formattedTimeZones;

    private TimeZoneFieldLookupDef() {
    }

    @Override
    public void prepareEditor() {
        super.prepareEditor();
        this.formattedTimeZones = FormattedTimeZone.get(new Date());
    }
    
    @Override
    public int getValueCount() {
        return formattedTimeZones.length;
    }

    @Override
    public String getValue(int index) {
        return formattedTimeZones[index].getTimeZone().getID();
    }

    @Override
    public String getDisplayName(int index) {
        return formattedTimeZones[index].getDisplayString();
    }

}
