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
