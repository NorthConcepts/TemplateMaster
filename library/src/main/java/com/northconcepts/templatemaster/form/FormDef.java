package com.northconcepts.templatemaster.form;

import java.util.ArrayList;
import java.util.List;

public class FormDef {
    
    private String id;
    private List<FieldDef> fields = new ArrayList<FieldDef>();
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
    
    public List<FieldDef> getFields() {
        return fields;
    }
    
    public FieldDef add(FieldDef field) {
        fields.add(field);
        return field;
    }

    public FieldDef add(String name, String displayName, boolean nullable, FieldLookupDef lookupValues) {
        return add(new FieldDef(name, displayName, nullable, lookupValues));
    }

    public FieldDef add(String name, String displayName) {
        return add(new FieldDef(name, displayName));
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
