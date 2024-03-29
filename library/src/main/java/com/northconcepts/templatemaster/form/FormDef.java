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
package com.northconcepts.templatemaster.form;

import java.util.ArrayList;
import java.util.List;

import com.northconcepts.templatemaster.service.Bean;

public class FormDef extends Bean implements PreparableViewer, PreparableEditor {
    
    private String id;
    private String singularTitle;
    private String pluralTitle;
    private final CssStyleClass cssStyleClass = new CssStyleClass();
    private final List<FieldDef> fields = new ArrayList<FieldDef>();
    private String idFieldName = "id";
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
    private final List<NamedFilter> namedFilters = new ArrayList<>();

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
    
    public String getSingularTitle() {
        return singularTitle;
    }
    
    public FormDef setSingularTitle(String singularTitle) {
        this.singularTitle = singularTitle;
        return this;
    }
    
    public String getPluralTitle() {
        return pluralTitle;
    }
    
    public FormDef setPluralTitle(String pluralTitle) {
        this.pluralTitle = pluralTitle;
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
    
    public String getIdFieldName() {
        return idFieldName;
    }
    
    public FormDef setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
        return this;
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

    public List<NamedFilter> getNamedFilters() {
        return namedFilters;
    }

    public FormDef addNamedFilter(NamedFilter filter) {
        namedFilters.add(filter);
        return this;
    }
    
    public FormDef addNamedFilter(String code, String label) {
        return addNamedFilter(new NamedFilter(code, label));
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
