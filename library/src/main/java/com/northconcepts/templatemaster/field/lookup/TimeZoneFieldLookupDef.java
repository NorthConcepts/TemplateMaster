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
package com.northconcepts.templatemaster.field.lookup;

import java.util.Date;

import com.northconcepts.templatemaster.form.FieldLookupDef;
import com.northconcepts.templatemaster.timezone.FormattedTimeZone;

public class TimeZoneFieldLookupDef extends FieldLookupDef {

    public static final TimeZoneFieldLookupDef INSTANCE = new TimeZoneFieldLookupDef();

    private FormattedTimeZone[] formattedTimeZones;

    private TimeZoneFieldLookupDef() {
    }

    @Override
    public void prepareEditor() {
        super.prepareEditor();
        this.formattedTimeZones = FormattedTimeZone.get(new Date());
    }
    
    @Override
    public int getValueCount() {
        return formattedTimeZones.length;
    }

    @Override
    public String getValue(int index) {
        return formattedTimeZones[index].getTimeZone().getID();
    }

    @Override
    public String getDisplayName(int index) {
        return formattedTimeZones[index].getDisplayString();
    }

}
