package com.northconcepts.templatemaster.form;

import java.util.LinkedHashSet;
import java.util.Set;

import com.northconcepts.templatemaster.content.Util;

public class CssStyleClass {

    private final Set<String> styleNames = new LinkedHashSet<String>();

    public CssStyleClass() {
    }
    
    public Set<String> getStyleNames() {
        return styleNames;
    }
    
    public CssStyleClass add(String styleName) {
        styleNames.add(styleName);
        return this;
    }

    public CssStyleClass remove(String styleName) {
        styleNames.remove(styleName);
        return this;
    }
    
    public boolean hasStyles() {
        return styleNames.size() > 0;
    }
    
    @Override
    public String toString() {
        return Util.collectionToString(styleNames, " ");
    }

}
