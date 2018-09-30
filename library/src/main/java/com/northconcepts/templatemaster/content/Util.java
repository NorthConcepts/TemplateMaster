/*
 * Copyright (c) 2018 North Concepts Inc.
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
package com.northconcepts.templatemaster.content;

public class Util {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String trimLeft(String string, char padChar) {
        if (string == null) {
            return string;
        }
        int length = string.length();
        int i = 0;
        while (i < length && string.charAt(i) == padChar) {
            i++;
        }
        return (i > 0) ? string.substring(i) : string;
    }

    public static String trimRight(String string, char padChar) {
        if (string == null) {
            return string;
        }
        int length = string.length();
        while (length > 0 && string.charAt(length - 1) == padChar) {
            length--;
        }
        return (length < string.length()) ? string.substring(0, length) : string;
    }

    public static boolean matches(String s1, String s2, boolean caseSensitive, boolean allowEmpty) {
        if (!allowEmpty && (isEmpty(s1) || isEmpty(s2))) {
            return false;
        }
        return compare(s1, s2, caseSensitive) == 0;
    }

    public static boolean matches(String s1, String s2, boolean caseSensitive) {
        return compare(s1, s2, caseSensitive) == 0;
    }

    public static boolean matches(String s1, String s2) {
        return matches(s1, s2, true);
    }

    public static int compare(String s1, String s2, boolean caseSensitive) {
        if (s1 == s2) {
            return 0;
        } else if (s1 == null) {
            return -1;
        } else if (s2 == null) {
            return 1;
        } else {
            return caseSensitive ? s1.compareTo(s2) : s1.compareToIgnoreCase(s2);
        }
    }

}
