package com.northconcepts.templatemaster.form;

import com.northconcepts.templatemaster.service.Bean;

public class CrudAction extends Bean {
    
    private final String displayName;
    private final String subPath;
    private final String target;
    
    public CrudAction(String displayName, String subPath, String target) {
        this.displayName = displayName;
        this.subPath = subPath;
        this.target = target;
    }

    public CrudAction(String displayName, String subPath) {
        this.displayName = displayName;
        this.subPath = subPath;
        this.target = null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSubPath() {
        return subPath;
    }
    
    public String getTarget() {
        return target;
    }
    
}
