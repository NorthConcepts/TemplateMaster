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
package com.northconcepts.templatemaster.form.param;

import java.math.BigDecimal;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class BigDecimalParam extends Param<BigDecimal> {

    public BigDecimalParam(String valueAsString) {
        super(valueAsString);
    }

    public BigDecimalParam(BigDecimal value) {
        super(value);
    }

    @Override
    protected BigDecimal parse(String valueAsString) throws Throwable {
        if (valueAsString == null) {
            return null;
        }
        try {
            return new BigDecimal(valueAsString);
        } catch (Throwable e) {
            throw new TemplateMasterException("Invalid value: " + valueAsString, e);
        }
    }

    @Override
    protected String format(BigDecimal value) throws Throwable {
        return value.toString();
    }

}
