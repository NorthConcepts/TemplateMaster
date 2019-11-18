package com.northconcepts.templatemaster.form.editor;

import java.math.BigDecimal;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;
import com.northconcepts.templatemaster.form.editor.FieldValueEditor;

public class BigDecimalEditor implements FieldValueEditor {

    public static BigDecimalEditor INSTANCE = new BigDecimalEditor();

    private BigDecimalEditor() {

    }

    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        return ((BigDecimal) fieldValue).toString();
    }

    @Override
    public boolean isEditValueRequiresHtmlEscaping() {
        return false;
    }

    @Override
    public boolean isControlIncluded() {
        return false;
    }

}