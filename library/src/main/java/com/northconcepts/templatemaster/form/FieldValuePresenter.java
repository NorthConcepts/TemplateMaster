package com.northconcepts.templatemaster.form;

public interface FieldValuePresenter {
    
    public static final FieldValuePresenter NULL = new FieldValuePresenter() {
        
        @Override
        public String getDisplayValue(FieldDef fieldDef, Object value) {
            return value==null?fieldDef.getNullDisplayValue():value.toString();
        }
    };
    
    String getDisplayValue(FieldDef fieldDef, Object value);

}
