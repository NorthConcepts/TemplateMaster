package com.northconcepts.templatemaster.form;

import java.util.ArrayList;
import java.util.List;

import com.northconcepts.templatemaster.service.Bean;

public class FormDef extends Bean {
    
    private String id;
    private final CssStyleClass cssStyleClass = new CssStyleClass();
    private final List<FieldDef> fields = new ArrayList<FieldDef>();
    private boolean allowCreate = true;
    private boolean allowEdit = true;
    private boolean allowDelete = true;
    private boolean allowExport;
    private boolean allowImport;
    
    public FormDef() {
    }
    
    public String getId() {
        return id;
    }
    
    public FormDef setId(String id) {
        this.id = id;
        return this;
    }
    
    public CssStyleClass getCssStyleClass() {
        return cssStyleClass;
    }
    
    public List<FieldDef> getFields() {
        return fields;
    }
    
    public List<FieldDef> getListFields() {
        // TODO filter and apply list arrangement
        return fields;
    }
    
    public FieldDef add(FieldDef field) {
        fields.add(field);
        field.setFormDef(this);
        if (fields.size() == 1 && !field.isAutofocusSet()) {
            field.setAutofocus(true);
        }
        return field;
    }

    public FieldDef add(String name, String displayName, boolean nullable, FieldLookupDef lookupValues) {
        return add(new FieldDef(name, displayName, nullable, lookupValues));
    }

    public FieldDef add(String name, String displayName) {
        return add(new FieldDef(name, displayName));
    }

    public boolean isAllowCreate() {
        return allowCreate;
    }

    public FormDef setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
        return this;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public FormDef setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
        return this;
    }

    public boolean isAllowDelete() {
        return allowDelete;
    }

    public FormDef setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
        return this;
    }

    public boolean isAllowExport() {
        return allowExport;
    }

    public FormDef setAllowExport(boolean allowExport) {
        this.allowExport = allowExport;
        return this;
    }

    public boolean isAllowImport() {
        return allowImport;
    }

    public FormDef setAllowImport(boolean allowImport) {
        this.allowImport = allowImport;
        return this;
    }

    
/*    
    
      <div class="form-group">
        <label for="accountName" class="col-sm-3 control-label">Account Name</label> 
        <div class="col-sm-9"> 
          <input type="text" placeholder="Account Name" required="required" class="form-control" name="accountName" id="accountName" value="${(record.accountName)!?html}">
        </div>
      </div>
    
*/    
}
