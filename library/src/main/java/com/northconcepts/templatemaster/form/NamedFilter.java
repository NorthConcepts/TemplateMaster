package com.northconcepts.templatemaster.form;

public class NamedFilter {

    private String code;
    private String label;

    public NamedFilter() {
    }

    public NamedFilter(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public NamedFilter setCode(String code) {
        this.code = code;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public NamedFilter setLabel(String label) {
        this.label = label;
        return this;
    }

}
