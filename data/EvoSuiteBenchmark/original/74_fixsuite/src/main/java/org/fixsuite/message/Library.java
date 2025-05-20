/**
 * Library.java 8:50:42 PM Apr 22, 2008
 * 
 * <PRE>
 * Copyright (c) 2008, Jan Amoyo
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS';
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * </PRE>
 */

package org.fixsuite.message;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.parsers.fpl.MainParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main storage for all available FIX version specifications.
 * 
 * @author jramoyo
 */
public class Library {

    private static final String FIELDS_FILE_NAME = "Fields.xml";

    private static final String ENUMS_FILE_NAME = "Enums.xml";

    private static final String COMPONENTS_FILE_NAME = "Components.xml";

    private static final String MSG_TYPE_FILE_NAME = "MsgType.xml";

    private static final String MSG_CONTENTS_FILE_NAME = "MsgContents.xml";

    private static final int FIELDS_FILE_KEY = 1;

    private static final int ENUMS_FILE_KEY = 2;

    private static final int COMPONENTS_FILE_KEY = 3;

    private static final int MSG_TYPE_FILE_KEY = 4;

    private static final int MSG_CONTENTS_FILE_KEY = 5;

    private static Logger logger = LoggerFactory.getLogger(Library.class);

    private Map<String, DictionaryInfo> dictionaries;

    public Library() {
        dictionaries = new TreeMap<String, DictionaryInfo>();
    }

    public boolean loadFromDirectory(String libraryPath) {
        boolean result = true;
        long startTime = System.currentTimeMillis();

        if (!dictionaries.isEmpty()) {
            logger.debug("Unloading previous dictionaries");
            dictionaries.clear();
        }

        // Cleanup JVM before loading
        System.gc();
        long startMemory = Runtime.getRuntime().freeMemory();
        File path = new File(libraryPath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        loadDirectory(arrangeFiles(files[i].listFiles()),
                                files[i].getName());
                    }
                }
                if (!dictionaries.isEmpty()) {
                    logger.debug("Loaded " + dictionaries.size()
                            + " FIX dictionaries.");
                } else {
                    logger.error("Unable to load any dictionaries");
                }
            } else {
                logger.error("Supplied directory is empty.");
                result = false;
            }
        } else {
            logger.error("Supplied path is not a directory.");
            result = false;
        }
        // Cleanup JVM after loading
        System.gc();
        logger.debug("Approximate memory allocation: "
                + new DecimalFormat().format(((double) (Runtime.getRuntime()
                        .freeMemory() - startMemory) / 1000000)) + " MB.");
        logger.info("Loading time: " + (System.currentTimeMillis() - startTime)
                + " milliseconds.");

        return result;
    }

    public List<DictionaryInfo> getDictionaries() {
        return new ArrayList<DictionaryInfo>(dictionaries.values());
    }

    public DictionaryInfo getDictionary(String version) {
        return dictionaries.get(version);
    }

    private void loadDirectory(List<File> files, String version) {
        DictionaryInfo dictionary = new DictionaryInfo(version);
        for (File file : files) {
            MainParser.parse(dictionary, file);
        }
        if (dictionary.isLoaded()) {
            dictionaries.put(dictionary.getVersion(), dictionary);
        } else {
            logger.debug("Unable to load dictionary from " + version + ".");
        }
    }

    /*
     * Files must be properly arranged for the FPL parsers to work
     */
    private List<File> arrangeFiles(File[] files) {
        Map<Integer, File> arrangedFiles = new TreeMap<Integer, File>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(FIELDS_FILE_NAME)) {
                arrangedFiles.put(FIELDS_FILE_KEY, files[i]);
            } else if (files[i].getName().equals(ENUMS_FILE_NAME)) {
                arrangedFiles.put(ENUMS_FILE_KEY, files[i]);
            } else if (files[i].getName().equals(COMPONENTS_FILE_NAME)) {
                arrangedFiles.put(COMPONENTS_FILE_KEY, files[i]);
            } else if (files[i].getName().equals(MSG_TYPE_FILE_NAME)) {
                arrangedFiles.put(MSG_TYPE_FILE_KEY, files[i]);
            } else if (files[i].getName().equals(MSG_CONTENTS_FILE_NAME)) {
                arrangedFiles.put(MSG_CONTENTS_FILE_KEY, files[i]);
            }
        }

        return new ArrayList<File>(arrangedFiles.values());
    }
}
