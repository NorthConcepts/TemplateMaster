package com.northconcepts.templatemaster.form;

import com.northconcepts.templatemaster.service.Bean;

public abstract class FieldLookupDef extends Bean {
    
    
    public abstract int getValueCount();

    public abstract String getValue(int index);

    public String getDisplayName(int index) {
        return getValue(index);
    }
    
}
