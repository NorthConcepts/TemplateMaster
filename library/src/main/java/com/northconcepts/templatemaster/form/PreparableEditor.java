package com.northconcepts.templatemaster.form;

import java.util.Collection;

public interface PreparableEditor {
    
    /**
     * Implementors should make sure to synchronize their methods since it can be called by multiple threads concurrently.  
     */
    default void prepareEditor() {
    }
    
    default void prepareEditor(Collection<? extends PreparableEditor> collection) {
        if (collection != null) {
            for (PreparableEditor preparable : collection) {
                preparable.prepareEditor();
            }
        }
    }

}
