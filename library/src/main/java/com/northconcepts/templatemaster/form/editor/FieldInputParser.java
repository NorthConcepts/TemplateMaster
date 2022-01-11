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
package com.northconcepts.templatemaster.form.editor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public interface FieldInputParser<T> {
    
    //===================================
    // String
    //===================================
    public static final FieldInputParser<String> STRING_PARSER = new FieldInputParser<String>() {
        @Override
        public String parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:fieldInputValue.get(0);
        }
    };
    
    //===================================
    // Integer
    //===================================
    public static final FieldInputParser<Byte> BYTE_PARSER = new FieldInputParser<Byte>() {
        @Override
        public Byte parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Byte.parseByte(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<Short> SHORT_PARSER = new FieldInputParser<Short>() {
        @Override
        public Short parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Short.parseShort(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<Integer> INT_PARSER = new FieldInputParser<Integer>() {
        @Override
        public Integer parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Integer.parseInt(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<Long> LONG_PARSER = new FieldInputParser<Long>() {
        @Override
        public Long parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Long.parseLong(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<BigInteger> BIGINT_PARSER = new FieldInputParser<BigInteger>() {
        @Override
        public BigInteger parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:new BigInteger(fieldInputValue.get(0));
        }
    };
    
    //===================================
    // Floating Point
    //===================================
    public static final FieldInputParser<Float> FLOAT_PARSER = new FieldInputParser<Float>() {
        @Override
        public Float parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Float.parseFloat(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<Double> DOUBLE_PARSER = new FieldInputParser<Double>() {
        @Override
        public Double parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Double.parseDouble(fieldInputValue.get(0));
        }
    };
    
    public static final FieldInputParser<BigDecimal> BIGDECIMAL_PARSER = new FieldInputParser<BigDecimal>() {
        @Override
        public BigDecimal parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:new BigDecimal(fieldInputValue.get(0));
        }
    };
    
    //===================================
    // Boolean
    //===================================
    public static final FieldInputParser<Boolean> BOOLEAN_PARSER = new FieldInputParser<Boolean>() {
        @Override
        public Boolean parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable {
            return fieldInputValue == null || fieldInputValue.size() ==0?null:Boolean.parseBoolean(fieldInputValue.get(0));
        }
    };
    
    //===================================
    // Lookup
    //===================================
    
    @SuppressWarnings("unchecked")
    public static <S> FieldInputParser<S> lookup(Class<?> type) {
        if (type == String.class) {
            return (FieldInputParser<S>) STRING_PARSER;
        } else if (type == Integer.class) {
            return (FieldInputParser<S>) INT_PARSER;
        } else if (type == Long.class) {
            return (FieldInputParser<S>) LONG_PARSER;
        } else if (type == Byte.class) {
            return (FieldInputParser<S>) BYTE_PARSER;
        } else if (type == Short.class) {
            return (FieldInputParser<S>) SHORT_PARSER;
        } else if (type == BigInteger.class) {
            return (FieldInputParser<S>) BIGINT_PARSER;
        } else if (type == Double.class) {
            return (FieldInputParser<S>) DOUBLE_PARSER;
        } else if (type == Float.class) {
            return (FieldInputParser<S>) FLOAT_PARSER;
        } else if (type == BigDecimal.class) {
            return (FieldInputParser<S>) BIGDECIMAL_PARSER;
        } else if (type == Boolean.class) {
            return (FieldInputParser<S>) BOOLEAN_PARSER;
        }
        return (FieldInputParser<S>) STRING_PARSER;
    }
    
    T parse(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) throws Throwable;

}
