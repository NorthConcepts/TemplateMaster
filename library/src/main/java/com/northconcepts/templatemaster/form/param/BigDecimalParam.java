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
