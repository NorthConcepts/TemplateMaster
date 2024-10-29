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
package com.northconcepts.templatemaster.rest;

import com.northconcepts.templatemaster.content.Content;
import org.apache.commons.text.StringEscapeUtils;

public class FlashMessage {
    
    public static enum FlashMessageType {
        SUCCESS, INFO, WARNING, ERROR;
    }
    
    private final FlashMessageType type;
    private final String displayText;
    private final boolean displayTextRequiresHtmlEscaping;

    public FlashMessage(FlashMessageType type, String displayText, boolean displayTextRequiresHtmlEscaping) {
        this.type = type;
        this.displayText = displayText;
        this.displayTextRequiresHtmlEscaping = displayTextRequiresHtmlEscaping;
    }

    public FlashMessage(FlashMessageType type, String displayText) {
        this(type, displayText, false);
    }

    public FlashMessage(FlashMessageType type, Content displayContent) {
        this(type, displayContent.toString(), false);
    }
    
    public FlashMessageType getType() {
        return type;
    }

    public String getDisplayText() {
        if(isDisplayTextRequiresHtmlEscaping()) {
            return StringEscapeUtils.escapeHtml4(displayText);
        }
        return displayText;
    }


    public boolean isDisplayTextRequiresHtmlEscaping() {
        return displayTextRequiresHtmlEscaping;
    }
    
    @Override
    public String toString() {
        return getDisplayText();
    }
    
}
