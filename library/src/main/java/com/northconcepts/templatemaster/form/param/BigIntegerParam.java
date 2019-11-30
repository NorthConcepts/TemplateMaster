package com.northconcepts.templatemaster.form.param;

import java.math.BigInteger;

import com.northconcepts.templatemaster.content.TemplateMasterException;

public class BigIntegerParam extends Param<BigInteger> {

    public BigIntegerParam(String valueAsString) {
        super(valueAsString);
    }

    public BigIntegerParam(BigInteger value) {
        super(value);
    }

    @Override
    protected BigInteger parse(String valueAsString) throws Throwable {
        if (valueAsString == null) {
            return null;
        }
        try {
            return new BigInteger(valueAsString);
        } catch (Throwable e) {
            throw new TemplateMasterException("Invalid value: " + valueAsString, e);
        }
    }

    @Override
    protected String format(BigInteger value) throws Throwable {
        return value.toString();
    }

}
