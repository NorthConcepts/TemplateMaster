package com.northconcepts.templatemaster.form.param;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.service.Bean;

public abstract class Param<T> extends Bean {
    
    protected final T value;
    protected final String valueAsString;

    public Param(T value) {
        this.value = value;
        if (value == null) {
            this.valueAsString = null;
        } else {
            try {
                this.valueAsString = format(value);
            } catch (Throwable e) {
                throw addExceptionProperties(TemplateMasterException.wrap(e));
            }
        }
    }
    
    public Param(String valueAsString) {
        this.valueAsString = valueAsString;
        if (Util.isEmpty(valueAsString)) {
            this.value = null;
        } else {
            try {
                this.value = parse(valueAsString);
            } catch (Throwable e) {
                throw addExceptionProperties(TemplateMasterException.wrap(e));
            }
        }
    }
    
    protected abstract T parse(String valueAsString) throws Throwable;

    protected abstract String format(T value) throws Throwable;
    
    @Override
    public TemplateMasterException addExceptionProperties(TemplateMasterException exception) {
        return super.addExceptionProperties(exception).set("valueAsString", valueAsString).set("value", value);
    }

    public T getValue() {
        return value;
    }
    
    public String getValueAsString() {
        return valueAsString;
    }
    
    @Override
    public String toString() {
        return value != null ? value.toString() : "";
    }
    
}
