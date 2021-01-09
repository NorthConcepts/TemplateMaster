package com.northconcepts.templatemaster.form;

import java.util.ArrayList;
import java.util.List;

import com.northconcepts.templatemaster.service.Bean;

public class FormDef extends Bean implements PreparableViewer, PreparableEditor {
    
    private String id;
    private final CssStyleClass cssStyleClass = new CssStyleClass();
    private final List<FieldDef> fields = new ArrayList<FieldDef>();
    private boolean paginate = true;
    private int defaultPageSize = CrudResource.DEFAULT_PAGE_SIZE;
    private int minPageSize = CrudResource.MIN_PAGE_SIZE;
    private int maxPageSize = CrudResource.MAX_PAGE_SIZE;
    private boolean allowCreate = true;
    private boolean allowView = true;
    private boolean allowEdit = true;
    private boolean allowDelete = true;
    private boolean allowSearch = true;
    private boolean allowSort = true;
    private boolean allowClone;
    private boolean allowExport;
    private boolean allowImport;
    private String defaultSortField;
    private final List<CrudAction> singleRecordActions = new ArrayList<CrudAction>(); 
    private final List<CrudAction> bulkRecordActions = new ArrayList<CrudAction>(); 

    public FormDef() {
    }
    
    @Override
    public void prepareViewer() {
        prepareViewer(fields);
        prepareViewer(singleRecordActions);
        prepareViewer(bulkRecordActions);
    }
    
    @Override
    public void prepareEditor() {
        prepareEditor(fields);
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
    
    public boolean isPaginate() {
        return paginate;
    }
    
    public FormDef setPaginate(boolean paginate) {
        this.paginate = paginate;
        return this;
    }
    
    public int getDefaultPageSize() {
        return defaultPageSize;
    }
    
    public FormDef setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
        return this;
    }
    
    public int getMaxPageSize() {
        return maxPageSize;
    }
    
    public FormDef setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
        return this;
    }
    
    public int getMinPageSize() {
        return minPageSize;
    }
    
    public FormDef setMinPageSize(int minPageSize) {
        this.minPageSize = minPageSize;
        return this;
    }

    public boolean isAllowCreate() {
        return allowCreate;
    }

    public FormDef setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
        return this;
    }

    public boolean isAllowView() {
        return allowView;
    }
    
    public FormDef setAllowView(boolean allowView) {
        this.allowView = allowView;
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
    
    public boolean isAllowSearch() {
        return allowSearch;
    }

    public FormDef setAllowSearch(boolean allowSearch) {
        this.allowSearch = allowSearch;
        return this;
    }
    
    public boolean isAllowSort() {
        return allowSort;
    }
    
    public FormDef setAllowSort(boolean allowSort) {
        this.allowSort = allowSort;
        return this;
    }
    
    public boolean isAllowClone() {
        return allowClone;
    }

    public FormDef setAllowClone(boolean allowClone) {
        this.allowClone = allowClone;
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

//    public List<CrudAction> getSingleRecordActions(boolean includeBuiltInActions) {
//        if (includeBuiltInActions) {
//            List<CrudAction> actions = new ArrayList<CrudAction>();
//            
//            // TODO add view, edit, and clone actions
//            
//            actions.addAll(getSingleRecordActions());
//        }
//        return singleRecordActions;
//    }
    
    public boolean hasSingleOrBuiltInRecordActions() {
        return singleRecordActions.size() > 0 || allowView || allowEdit || allowClone;
    }
    
    public List<CrudAction> getSingleRecordActions() {
        return singleRecordActions;
    }
    
    public FormDef addSingleRecordAction(CrudAction action) {
        singleRecordActions.add(action);
        return this;
    }
    
    public List<CrudAction> getBulkRecordActions() {
        return bulkRecordActions;
    }
    
    public FormDef addBulkRecordAction(CrudAction action) {
        bulkRecordActions.add(action);
        return this;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public FormDef setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
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
