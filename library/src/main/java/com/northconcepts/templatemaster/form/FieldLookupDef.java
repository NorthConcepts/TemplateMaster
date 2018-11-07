package com.northconcepts.templatemaster.form;

public abstract class FieldLookupDef {
    
    public void refresh() {
    }
    
    public abstract int getValueCount();

    public abstract String getValue(int index);

    public String getDisplayName(int index) {
        return getValue(index);
    }
    
}
