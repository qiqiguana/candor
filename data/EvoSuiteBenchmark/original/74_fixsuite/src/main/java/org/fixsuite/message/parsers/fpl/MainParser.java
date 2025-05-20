/**
 * MainParser.java 8:48:30 PM Apr 22, 2008
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

package org.fixsuite.message.parsers.fpl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.parsers.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main FPL parser. MainParser maps an FPL XML specification and delegates the
 * parsing to the respective parser.
 * 
 * @author jramoyo
 */
public class MainParser {

    private static final String COMPONENTS_FILE_NAME = "Components.xml";

    private static final String ENUMS_FILE_NAME = "Enums.xml";

    private static final String FIELDS_FILE_NAME = "Fields.xml";

    private static final String MSG_CONTENTS_FILE_NAME = "MsgContents.xml";

    private static final String MSG_TYPE_FILE_NAME = "MsgType.xml";

    private static Logger logger = LoggerFactory.getLogger(MainParser.class);

    private static MainParser singleton;

    private Map<String, Parser> parsers;

    public MainParser() {
        parsers = new HashMap<String, Parser>();
        parsers.put(COMPONENTS_FILE_NAME, new ComponentsParser());
        parsers.put(ENUMS_FILE_NAME, new EnumsParser());
        parsers.put(FIELDS_FILE_NAME, new FieldsParser());
        parsers.put(MSG_CONTENTS_FILE_NAME, new MsgContentsParser());
        parsers.put(MSG_TYPE_FILE_NAME, new MsgTypeParser());
    }

    public static boolean parse(DictionaryInfo dictionary, File xmlFile) {
        boolean result = true;

        if (xmlFile.isFile()) {
            if (getInstance().getParser(xmlFile.getName()) != null) {
                getInstance().getParser(xmlFile.getName()).parse(dictionary,
                        xmlFile);
                logger.debug("Loaded XML file " + xmlFile.getPath() + ".");
            } else {
                logger.debug("Unknown XML file " + xmlFile.getName() + ".");
                result = false;
            }
        } else {
            logger.error("Path name does not point to a file.");
            result = false;
        }

        return result;
    }

    private static MainParser getInstance() {
        synchronized (MainParser.class) {
            if (singleton == null) {
                singleton = new MainParser();
            }
        }

        return singleton;
    }

    private Parser getParser(String fileName) {
        return parsers.get(fileName);
    }
}
