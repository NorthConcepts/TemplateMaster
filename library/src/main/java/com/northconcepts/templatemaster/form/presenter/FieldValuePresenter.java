package com.northconcepts.templatemaster.form.presenter;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;
import com.northconcepts.templatemaster.form.PreparableViewer;

public interface FieldValuePresenter extends PreparableViewer {
    
    public static final FieldValuePresenter NULL = new FieldValuePresenter() {
        
        @Override
        public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
            return fieldValue==null?fieldDef.getNullDisplayValue():fieldValue.toString();
        }
        
        @Override
        public boolean isDisplayValueRequiresHtmlEscaping() {
            return true;
        }
        
    };
    
    String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue);
    
    boolean isDisplayValueRequiresHtmlEscaping();

}
