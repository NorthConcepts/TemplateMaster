package com.northconcepts.templatemaster.form;

public class FieldDef {
    
    private String id;
    
    // FieldControlDef
    private String name;
    private String displayName;
    private ControlType controlType;
    private String placeholder;
    private boolean visible = true;
    
    
    // FieldDataDef
    private DataType dataType;
    private boolean nullable = true;
    private String defaultValue;
    private String temporalPattern;
    private String numbericPattern;
    private String patternValidationMessage;

    // FieldPermissionDef
    private boolean allowCreate = true;
    private boolean allowUpdate = true;
    private boolean allowSort = true;
    private boolean allowFilter = true;
    
    
    private FieldLookupDef lookupValues;
    private boolean limitToLookupValues;
    
    
    public FieldDef(String name, String displayName, boolean nullable, FieldLookupDef lookupValues) {
        this.name = name;
        this.displayName = displayName;
        this.nullable = nullable;
        this.lookupValues = lookupValues;
    }

    public FieldDef(String name, String displayName) {
        this(name, displayName, true, null);
    }

    public String getDisplayValue(Object value) {
        return value==null?null:value.toString();
    }

    public String getId() {
        return id;
    }
    
    public FieldDef setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }

    public FieldDef setName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FieldDef setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
    
    public ControlType getControlType() {
        return controlType;
    }
    
    public FieldDef setControlType(ControlType controlType) {
        this.controlType = controlType;
        return this;
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
    
    public FieldDef setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }
    
    public DataType getDataType() {
        return dataType;
    }
    
    public FieldDef setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public FieldDef setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    public String getTemporalPattern() {
        return temporalPattern;
    }

    public FieldDef setTemporalPattern(String temporalPattern) {
        this.temporalPattern = temporalPattern;
        return this;
    }

    public String getNumbericPattern() {
        return numbericPattern;
    }

    public FieldDef setNumbericPattern(String numbericPattern) {
        this.numbericPattern = numbericPattern;
        return this;
    }

    public String getPatternValidationMessage() {
        return patternValidationMessage;
    }

    public FieldDef setPatternValidationMessage(String patternValidationMessage) {
        this.patternValidationMessage = patternValidationMessage;
        return this;
    }

    public boolean isRequired() {
        return !isNullable();
    }
    
    public boolean isNullable() {
        return nullable;
    }
    
    public FieldDef setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }
    
    public boolean isAllowCreate() {
        return allowCreate;
    }

    public FieldDef setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
        return this;
    }

    public boolean isAllowUpdate() {
        return allowUpdate;
    }

    public FieldDef setAllowUpdate(boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public FieldDef setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isAllowSort() {
        return allowSort;
    }

    public FieldDef setAllowSort(boolean allowSort) {
        this.allowSort = allowSort;
        return this;
    }

    public boolean isAllowFilter() {
        return allowFilter;
    }

    public FieldDef setAllowFilter(boolean allowFilter) {
        this.allowFilter = allowFilter;
        return this;
    }

    public boolean isLookup() {
        // TODO: find better way/place to do this (LookupFactory.createLookup()?)
        if (lookupValues != null) {
            lookupValues.refresh();
        }
        return lookupValues != null;
    }
    
    public FieldLookupDef getLookupValues() {
        return lookupValues;
    }
    
    public FieldDef setLookupValues(FieldLookupDef lookupValues) {
        this.lookupValues = lookupValues;
        return this;
    }
    
    public boolean isLimitToLookupValues() {
        return limitToLookupValues;
    }
    
    public FieldDef setLimitToLookupValues(boolean limitToLookupValues) {
        this.limitToLookupValues = limitToLookupValues;
        return this;
    }
    
    public boolean isReadonly() {
        return isVisible() && !isAllowCreate() && !isAllowUpdate();
    }
    
    public boolean isTextArea() {
        return controlType == ControlType.TEXT_AREA;
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
