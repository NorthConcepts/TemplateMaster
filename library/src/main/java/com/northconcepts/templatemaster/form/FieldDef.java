package com.northconcepts.templatemaster.form;

import org.apache.commons.text.StringEscapeUtils;

import com.northconcepts.templatemaster.service.Bean;

public class FieldDef extends Bean {
    
    private String id;
    
    // FieldControlDef
    private String name;
    private String displayName;
    private String helpText;
    private ControlType controlType;
    private String placeholder;
    private String nullDisplayValue;
    private FieldValuePresenter fieldValuePresenter = FieldValuePresenter.NULL;
    private boolean visible = true;
    private final CssStyleClass cssStyleClass = new CssStyleClass();
    private Boolean autofocus;
    
    // FieldDataDef
    private DataType dataType;
    private boolean nullable = true;
    private boolean array;
    private String defaultValue;
    private String temporalPattern;
    private String numbericPattern;
    private String patternValidationMessage;
    private FieldLookupDef lookupValues;
    private boolean limitToLookupValues;
    private Integer minLength;
    private Integer maxLength;
    private Number minNumber;
    private Number maxNumber;

    // FieldPermissionDef
    private boolean allowCreate = true;
    private boolean allowEdit = true;
    private boolean allowSort = true;
    private boolean allowFilter = true;

    // Template-Related
    private boolean showInList = true;
    private boolean showInSelectList;
    private boolean showInView = true;
    
    public FieldDef() {
    }    
    
    public FieldDef(String name, String displayName, boolean nullable, FieldLookupDef lookupValues) {
        this.id = name;
        this.name = name;
        this.displayName = displayName;
        this.placeholder = displayName;
        this.nullable = nullable;
        this.lookupValues = lookupValues;
    }

    public FieldDef(String name, String displayName) {
        this(name, displayName, true, null);
    }

    public String getDisplayValue(Object value) {
        // TODO: handle pattern-based formatting
        return fieldValuePresenter.getDisplayValue(this, value);
    }

    public String getDisplayValueHtmlEscaped(Object value) {
        // TODO: handle pattern-based formatting
        String displayValue = fieldValuePresenter.getDisplayValue(this, value);
        
        displayValue = StringEscapeUtils.escapeHtml4(displayValue);
        
        if (isTextFormatted(displayValue)) {
            displayValue = "<pre>" + displayValue + "</pre>";
        }
        
        return displayValue;
    }

    public boolean isTextFormatted(String text) {
        if (text == null) {
            return false;
        }
        
        return text.contains("  ") ||
                text.contains("\n") ||
                text.contains("\r") ||
                text.contains("\t");
    }

    public boolean isReadonly() {
        return !isAllowCreate() && !isAllowEdit();
    }
    
    public FieldDef setReadonly(boolean readonly) {
        setAllowCreate(!readonly);
        setAllowEdit(!readonly);
        return this;
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
    
    public String getHelpText() {
        return helpText;
    }
    
    public FieldDef setHelpText(String helpText) {
        this.helpText = helpText;
        return this;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public FieldDef setControlType(ControlType controlType) {
        this.controlType = controlType;

        if(controlType == ControlType.PASSWORD) {
            setShowInView(false);
            setShowInList(false);
        }
        return this;
    }

    public boolean isTextArea() {
        return controlType == ControlType.TEXT_AREA;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public FieldDef setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }
    
    public String getNullDisplayValue() {
        return nullDisplayValue;
    }
    
    public FieldDef setNullDisplayValue(String nullDisplayValue) {
        this.nullDisplayValue = nullDisplayValue;
        return this;
    }

    public FieldValuePresenter getFieldValuePresenter() {
        return fieldValuePresenter;
    }
    
    public FieldDef setFieldValuePresenter(FieldValuePresenter fieldValuePresenter) {
        if (fieldValuePresenter == null) {
            fieldValuePresenter = FieldValuePresenter.NULL;
        }
        this.fieldValuePresenter = fieldValuePresenter;
        return this;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public FieldDef setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
    
    public CssStyleClass getCssStyleClass() {
        return cssStyleClass;
    }
    
    protected boolean isAutofocusSet() {
        return autofocus != null;
    }
    
    public boolean isAutofocus() {
        return autofocus != null && autofocus.booleanValue();
    }
    
    public FieldDef setAutofocus(boolean autofocus) {
        this.autofocus = autofocus;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public FieldDef setDataType(DataType dataType) {
        this.dataType = dataType;
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
    
    public boolean isArray() {
        return array;
    }
    
    public FieldDef setArray(boolean array) {
        this.array = array;
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

    public boolean isLookup() {
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
    
    public Integer getMinLength() {
        return minLength;
    }

    public FieldDef setMinLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public FieldDef setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Number getMinNumber() {
        return minNumber;
    }

    public FieldDef setMinNumber(Number minNumber) {
        this.minNumber = minNumber;
        return this;
    }

    public Number getMaxNumber() {
        return maxNumber;
    }

    public FieldDef setMaxNumber(Number maxNumber) {
        this.maxNumber = maxNumber;
        return this;
    }

    public boolean isAllowCreate() {
        return allowCreate;
    }

    public FieldDef setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
        return this;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public FieldDef setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
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

    public boolean isShowInList() {
        return showInList;
    }

    public FieldDef setShowInList(boolean showInList) {
        this.showInList = showInList;
        return this;
    }
    
    public boolean isShowInSelectList() {
        return showInSelectList;
    }
    
    public FieldDef setShowInSelectList(boolean showInSelectList) {
        this.showInSelectList = showInSelectList;
        return this;
    }

    public boolean isShowInView() {
        return showInView;
    }

    public FieldDef setShowInView(boolean showInView) {
        this.showInView = showInView;
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
