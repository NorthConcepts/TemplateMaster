package com.northconcepts.templatemaster.form.presenter;

import java.text.DecimalFormat;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class NumberPresenter implements FieldValuePresenter {

    public static final NumberPresenter DOLLARS_NO_CENTS = new NumberPresenter("$#,##0");
    public static final NumberPresenter DOLLARS_CENTS = new NumberPresenter("$#,##0.00");
    public static final NumberPresenter WHOLE_NO_GROUPING = new NumberPresenter("0");
    public static final NumberPresenter WHOLE_GROUPING = new NumberPresenter("#,##0");
    public static final NumberPresenter DECIMAL_NO_GROUPING = new NumberPresenter("0.#");
    public static final NumberPresenter DECIMAL_GROUPING = new NumberPresenter("#,##0.#");

    private DecimalFormat decimalFormat;

    public NumberPresenter(String decimalFormat) {
        this.decimalFormat = new DecimalFormat(decimalFormat);
    }

    @Override
    public String getDisplayValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        if (fieldValue instanceof Number) {
            return decimalFormat.format(fieldValue);
        }
        return FieldValuePresenter.NULL.getDisplayValue(resource, fieldDef, entity, fieldValue);
    }

    @Override
    public boolean isDisplayValueRequiresHtmlEscaping() {
        return true;
    }

}