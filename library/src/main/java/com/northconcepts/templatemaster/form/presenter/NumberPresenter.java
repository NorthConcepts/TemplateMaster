/*
 * Copyright (c) 2014-2022 North Concepts Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    private final DecimalFormat decimalFormat;

    public NumberPresenter(String decimalFormat) {
        this.decimalFormat = new DecimalFormat(decimalFormat);
    }
    
    public NumberPresenter(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
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
