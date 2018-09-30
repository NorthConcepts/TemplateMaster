/*
 * Copyright (c) 2014-2018 North Concepts Inc.
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
 * 
 */
package com.northconcepts.templatemaster.content;

import java.util.ArrayList;
import java.util.List;

public class ContentList implements IContent {
    
    private final Content parent;
    private final List<IContent> elements = new ArrayList<IContent>();

    // XXX: not a publicly creatable class 
    ContentList(Content parent) {
        this.parent = parent;
    }
    
    public List<IContent> getElements() {
        return elements;
    }
    
    public ContentList add(IContent block) {
        elements.add(block);
        if (block instanceof Content) {
            ((Content)block).setParent(parent);
        }
        return this;
    }
    
    public ContentList add(Object literalValue) {
        elements.add(new IdentityContent(literalValue));
        return this;
    }
    
    @Override
    public Object render() {
        if (elements.size() == 0) {
            return "";
        } else if (elements.size() == 1) {
            return elements.get(0).render();
        } else {
            StringBuilder s = new StringBuilder(Content.BUFFER_SIZE);
            for (IContent block : elements) {
                s.append(block.render());
            }
            return s.toString();
        }
    }

    @Override
    public String toString() {
        return render().toString();
    }

}
