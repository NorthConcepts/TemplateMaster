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
