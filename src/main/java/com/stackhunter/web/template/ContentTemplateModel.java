/*
 * Copyright (c) 2014 North Concepts Inc.
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
package com.stackhunter.web.template;

import com.stackhunter.web.content.Content;
import com.stackhunter.web.content.ContentList;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ContentTemplateModel implements TemplateHashModel {

    private final Content block;

    public ContentTemplateModel(Content block) {
        this.block = block;
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        Content b = block;
        ContentList blocks = null;
        while (b != null && (blocks = b.get(key, false)) == null) {
            b = b.getParent();
        }
        if (blocks == null) {
//            throw new ContentException(ContentErrorCode.VALUE_NOT_FOUND).set("key", key);
            return null;
        }
        Object object = blocks.render();
//        Object object = (blocks == null)?null:blocks.render();
        return Templates.get().getConfiguration().getObjectWrapper().wrap(object);
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return block == null || block.isEmpty();
    }

}
