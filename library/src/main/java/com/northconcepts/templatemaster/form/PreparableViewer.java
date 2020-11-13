package com.northconcepts.templatemaster.form;

import java.util.Collection;

public interface PreparableViewer {
    
    /**
     * Implementors should make sure to synchronize their methods since it can be called by multiple threads concurrently.  
     */
    default void prepareViewer() {
    }
    
    default void prepareViewer(Collection<? extends PreparableViewer> collection) {
        if (collection != null) {
            for (PreparableViewer preparable : collection) {
                preparable.prepareViewer();
            }
        }
    }

}
