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

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.codec.binary.Hex;

public final class Util {

    public static final int MIN_PAGE_SIZE = 1;
    public static final int MAX_PAGE_SIZE = 10000;

    public static final String ALPHANUMERIC_CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ023456789"; // removed l1I (lowercase
    // L, uppercase i, and
    // number 1)

    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    private Util() {

    }

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

    public static String collectionToString(Collection<?> collection, String separator) {
        if (collection == null || collection.size() == 0) {
            return "";
        }

        StringBuilder s = new StringBuilder(32 * collection.size());
        
        Iterator<?> i = collection.iterator();
        s.append(i.next());
        
        while (i.hasNext()) {
            s.append(separator).append(i.next());
        }

        return s.toString();
    }
    
    public static String collectionToString(Collection<?> collection, String elementFormat, String separator) {
        if (collection == null || collection.size() == 0) {
            return "";
        }

        StringBuilder s = new StringBuilder((32+elementFormat.length()+separator.length()) * collection.size());
        
        Iterator<?> i = collection.iterator();
        s.append(String.format(elementFormat, i.next()));
        
        while (i.hasNext()) {
            s.append(separator).append(String.format(elementFormat, i.next()));
        }

        return s.toString();
    }
    
    /**
     * Returns the number of <code>searchChar</code>s contained in <code>string</code> or zero (0).
     * 
     * @param string the string to search
     * @param searchChar the character to count
     * 
     * @return the number of matching characters found
     */
    public static int getCharacterCount(String string, char searchChar) {
        int index = 0;
        int count = 0;

        while ((index = string.indexOf(searchChar, index)) >= 0) {
            count++;
            index++;
        }
        
        return count;
    }

    public static String emptyToNull(String value) {
        if (isEmpty(value)) {
            return null;
        }
        return value;
    }

    public static String nullToEmpty(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    public static boolean equals(Object o1, Object o2) {
        return Objects.deepEquals(o1, o2);
    }

    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    public static String capitalize(String string) {
        if (isEmpty(string)) {
            return null;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String getServerAddress() {
        String serverAddress;
        try {
            serverAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            serverAddress = null;
        }
        return serverAddress;
    }

    public static String digest(String salt, String password) {
        try {
            if (isEmpty(salt)) {
                throw new TemplateMasterException("salt is empty");
            }

            if (isEmpty(password)) {
                throw new TemplateMasterException("password is empty");
            }

            salt = salt.trim();
            password = password.trim();

            password = salt + password;
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            String hashString = Hex.encodeHexString(hash);
            return hashString;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e);
        }
    }

    public static String getRandomString(int charCount) {
        return getRandomString(charCount, ALPHANUMERIC_CHARS);
    }

    public static String getRandomString(int charCount, String alphabet) {
        try {
            if (charCount < 1) {
                throw new TemplateMasterException("charCount must be > 0");
            }
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            StringBuilder s = new StringBuilder(charCount);
            for (int i = 0; i < charCount; i++) {
                int index = Math.abs(random.nextInt() % alphabet.length());
                s.append(alphabet.charAt(index));
            }
            return s.toString();
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("charCount", charCount);
        }
    }

    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw TemplateMasterException.wrap(e).set("delay", delay);
        }
    }

    public static File ensureWritableFolder(File parentFolder, String folderName) {
        try {
            return ensureWritableFolder(new File(parentFolder, folderName));
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("parentFolder", parentFolder).set("folderName", folderName);
        }
    }

    public static File ensureWritableFolder(File folder) {
        try {
            // log.debug("initializing folder [" + folder + "]");

            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    throw new TemplateMasterException("unable to create folder, may not have permission").set("folder", folder);
                }
            }

            // XXX: don't check if path is a directory in case it's symlink???

            if (!folder.canWrite()) {
                if (!folder.setWritable(true)) {
                    throw new TemplateMasterException("unable to make folder writable, may not have permission").set("folder", folder);
                }
            }

            return folder;
        } catch (Throwable e) {
            throw TemplateMasterException.wrap(e).set("folder", folder);
        }
    }

    public static <T> Future<T> run(final Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask<T>(callable);
        futureTask.run();
        return futureTask;
    }
}
