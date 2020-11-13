package com.northconcepts.templatemaster.form.editor;

import java.text.DecimalFormat;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;
import com.northconcepts.templatemaster.form.presenter.NumberPresenter;

public class NumberEditor implements FieldValueEditor {

    public static final NumberPresenter DOLLARS_NO_CENTS = new NumberPresenter("$#,##0");
    public static final NumberPresenter DOLLARS_CENTS = new NumberPresenter("$#,##0.00");
    public static final NumberPresenter WHOLE_NO_GROUPING = new NumberPresenter("0");
    public static final NumberPresenter WHOLE_GROUPING = new NumberPresenter("#,##0");
    public static final NumberPresenter DECIMAL_NO_GROUPING = new NumberPresenter("0.#");
    public static final NumberPresenter DECIMAL_GROUPING = new NumberPresenter("#,##0.#");

    private final DecimalFormat decimalFormat;

    public NumberEditor(String decimalFormat) {
        this.decimalFormat = new DecimalFormat(decimalFormat);
    }
    
    public NumberEditor(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue instanceof Number) {
            return decimalFormat.format(fieldValue);
        }
        return FieldValueEditor.NULL.getEditValue(resource, fieldDef, entity, fieldValue);
    }

    @Override
    public boolean isEditValueRequiresHtmlEscaping() {
        return true;
    }

    @Override
    public boolean isEditControlIncluded() {
        return false;
    }

}