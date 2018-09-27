/*
 * Copyright (c) 2014 North Concepts Inc.
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
 * 
 */
package com.stackhunter.web.content;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static ContentException wrap(Throwable exception, IErrorCode errorCode) {
        if (exception instanceof ContentException) {
            ContentException se = (ContentException)exception;
        	if (errorCode != null && errorCode != se.getErrorCode()) {
                return new ContentException(exception.getMessage(), exception, errorCode);
			}
			return se;
        } else {
            return new ContentException(exception.getMessage(), exception, errorCode);
        }
    }
    
    public static ContentException wrap(Throwable exception) {
    	return wrap(exception, null);
    }
    
    private IErrorCode errorCode;
    private final Map<String,Object> properties = new LinkedHashMap<String,Object>(64);
//    private final Map<String,Object> properties = new TreeMap<String,Object>();
    
    public ContentException(IErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ContentException(String message, IErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ContentException(Throwable cause, IErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ContentException(String message, Throwable cause, IErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public IErrorCode getErrorCode() {
        return errorCode;
    }
	
	public ContentException setErrorCode(IErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
    @SuppressWarnings("unchecked")
	public <T> T get(String name) {
        return (T)properties.get(name);
    }
	
    public ContentException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }
    
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) { 
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (errorCode != null) {
	        	s.println("\t" + errorCode + ":" + errorCode.getClass().getName()); 
			}
            for (String key : properties.keySet()) {
            	s.println("\t" + key + "=[" + properties.get(key) + "]"); 
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (int i=0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }
    
}
