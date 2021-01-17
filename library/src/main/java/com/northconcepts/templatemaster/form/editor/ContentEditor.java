package com.northconcepts.templatemaster.form.editor;

import com.northconcepts.templatemaster.content.Content;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FieldDef;

public class ContentEditor implements FieldValueEditor {

    public static ContentEditor fromFile(String templateFile) {
        return new ContentEditor(new Content(templateFile));
    }
    
    public static ContentEditor fromTemplateCode(String code) {
        return new ContentEditor(new Content().setTemplateCode(code));
    }
    
    private Content content;
    private boolean editControlIncluded = true;

    public ContentEditor(Content content) {
        this.content = content;
    }

    public Content getContent() {
        return content;
    }
    
    public ContentEditor setContent(Content content) {
        this.content = content;
        return this;
    }

    @Override
    public String getEditValue(CrudResource<?, ?> resource, FieldDef fieldDef, Object entity, Object fieldValue) {
        return content
                .clone()
                .add("resource", resource)
                .add("fieldDef", fieldDef)
                .add("formDef", fieldDef.getFormDef())
                .add("record", entity)
                .add("entity", entity)
                .add("fieldValue", fieldValue)
                .toString();
    }

    @Override
    public boolean isEditValueRequiresHtmlEscaping() {
        return false;
    }

    @Override
    public boolean isEditControlIncluded() {
        return editControlIncluded;
    }
    
    public ContentEditor setEditControlIncluded(boolean editControlIncluded) {
        this.editControlIncluded = editControlIncluded;
        return this;
    }

}
