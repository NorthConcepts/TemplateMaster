package com.northconcepts.templatemaster.form.editor;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;
import com.northconcepts.templatemaster.form.PreparableEditor;

public interface FieldValueEditor  extends PreparableEditor {
    
    public static final FieldValueEditor NULL = new FieldValueEditor() {
        
        @Override
        public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
            return new Content("/formdef/edit-field-default.html")
                    .add("resource", resource)
                    .add("record", entity)
                    .add("formDef", fieldDef.getFormDef())
                    .add("field", fieldDef)
                    .add("fieldValue", fieldValue)
                    .toString();
        }
        
        @Override
        public boolean isEditValueRequiresHtmlEscaping() {
            return false;
        }
        
        @Override
        public boolean isEditControlIncluded() {
            return true;
        }
        
//        @Override
//        public Object parseEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) {
//            Object value = fieldInputValue != null && fieldInputValue.size() > 0?fieldInputValue.get(0):null;
//            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldDef.getName(), entity.getClass());
//            propertyDescriptor.g
//            propertyDescriptor.getWriteMethod().invoke(entity, value);
//            
//            return value;
////            FieldInputParser
//        }
        
    };
    
    String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue);
    
    boolean isEditValueRequiresHtmlEscaping();

    boolean isEditControlIncluded();

//    Object parseEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Class<?> propertyType, List<String> fieldInputValue) throws Throwable;
//    
//    default void setEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, List<String> fieldInputValue) {
//        Object value = null;
//        PropertyDescriptor propertyDescriptor = null;
//        try {
//            propertyDescriptor = new PropertyDescriptor(fieldDef.getName(), entity.getClass());
//            value = parseEditValue(resource, fieldDef, entity, propertyDescriptor.getPropertyType(), fieldInputValue);
//            propertyDescriptor.getWriteMethod().invoke(entity, value);
//        } catch (Throwable e) {
//            throw TemplateMasterException.wrap(e)
//                .set("resource", resource)
//                .set("fieldDef", fieldDef)
//                .set("entity", entity)
//                .set("fieldInputValue", fieldInputValue)
//                .set("value", value)
//                .set("propertyDescriptor", propertyDescriptor)
//                ;
//        }
//    }
    
}
