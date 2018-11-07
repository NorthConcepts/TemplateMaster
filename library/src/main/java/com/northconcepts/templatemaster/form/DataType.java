/*
 * Copyright (c) 2006-2018 North Concepts Inc.  All rights reserved.
 * Proprietary and Confidential.  Use is subject to license terms.
 *
 * http://northconcepts.com/data-pipeline/licensing/
 *
 */
package com.northconcepts.templatemaster.form;


public enum DataType {

    UNDEFINED(DataTypeGroup.UNDEFINED),
    
    STRING(DataTypeGroup.TEXTUAL),
//    CHAR(DataTypeGroup.TEXTUAL),
//    EXPRESSION(DataTypeGroup.TEXTUAL),
//    JSON(DataTypeGroup.TEXTUAL),
//    XML(DataTypeGroup.TEXTUAL),
    
    BYTE(DataTypeGroup.NUMERIC),
    SHORT(DataTypeGroup.NUMERIC),
    INT(DataTypeGroup.NUMERIC),
    LONG(DataTypeGroup.NUMERIC),
    BIG_INTEGER(DataTypeGroup.NUMERIC),
    
    FLOAT(DataTypeGroup.NUMERIC),
    DOUBLE(DataTypeGroup.NUMERIC),
    BIG_DECIMAL(DataTypeGroup.NUMERIC),
    
    DATETIME(DataTypeGroup.TEMPORAL),
    DATE(DataTypeGroup.TEMPORAL),
    TIME(DataTypeGroup.TEMPORAL),

    BOOLEAN(DataTypeGroup.LOGICAL),

    BLOB(DataTypeGroup.BINARY),
    ;
    
    
    private DataType(DataTypeGroup group) {
        this.group = group;
    }

    private final DataTypeGroup group;
    
    public DataTypeGroup getGroup() {
        return group;
    }
    
}