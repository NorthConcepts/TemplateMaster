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
package com.northconcepts.templatemaster.template;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.WrappingTemplateModel;

public class StaticBeanTemplateModel extends WrappingTemplateModel implements TemplateHashModel, AdapterTemplateModel  {

    private final TemplateHashModel staticModel;
    private Class<?> beanClass;
    private final Map<String, Method> staticGetters = new HashMap<String, Method>();
    
    public StaticBeanTemplateModel(BeansWrapper wrapper, Class<?> beanClass) {
        super(wrapper);
        this.beanClass = beanClass;
        
        try {
            TemplateHashModel staticModels = wrapper.getStaticModels();
            this.staticModel = (TemplateHashModel) staticModels.get(beanClass.getName());
            cacheStaticGettersByProperty();
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void cacheStaticGettersByProperty() {
        Method[] methods = beanClass.getDeclaredMethods();
        METHOD_LOOP:
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                String name = method.getName();
                if (name.startsWith("get")) {
                    name = name.substring(3,4).toLowerCase() + name.substring(4);
                } else if (name.startsWith("is")) {
                    name = name.substring(2,3).toLowerCase() + name.substring(3);
                } else {
                    continue METHOD_LOOP;
                }
                staticGetters.put(name, method);
            }
        }
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        Method method = staticGetters.get(key);
        if (method != null) {
            try {
                return wrap(method.invoke(null));
            } catch (TemplateModelException e) {
                throw e;
            } catch (Throwable e) {
                throw new TemplateModelException(e.getMessage(), e);
            }
        }
        return staticModel.get(key);
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return false;
    }
    
    @Override
    public TemplateHashModel getAdaptedObject(Class<?> hint) {
        return staticModel;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
    
    public Map<String, Method> getStaticGetters() {
        return staticGetters;
    }
    
}
