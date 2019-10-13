package com.northconcepts.templatemaster.form;

public interface FieldValuePresenter {
    
    public static final FieldValuePresenter NULL = new FieldValuePresenter() {
        
        @Override
        public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
            return fieldValue==null?fieldDef.getNullDisplayValue():fieldValue.toString();
        }
        
        @Override
        public boolean requiresHtmlEscaping() {
            return true;
        }
        
    };
    
    String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue);
    
    boolean requiresHtmlEscaping();

}
