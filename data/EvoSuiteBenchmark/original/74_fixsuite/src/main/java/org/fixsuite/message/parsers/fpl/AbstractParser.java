/**
 * AbstractParser.java 11:42:21 PM Apr 21, 2008
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
import java.io.IOException;

import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.parsers.Parser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class to all FPL parsers.
 * 
 * @author jramoyo
 */
public abstract class AbstractParser implements Parser {

    private static Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * @see com.jramoyo.fix.library.parser.Parser#parse(com.jramoyo.fix.library.model.DictionaryInfo,
     *      java.io.File)
     */
    public boolean parse(DictionaryInfo dictionary, File xmlFile) {
        boolean result = true;
        try {
            Document document = new SAXBuilder().build(xmlFile);
            Element root = document.getRootElement();
            load(dictionary, root);
        } catch (IOException ex) {
            logger.error(
                    "Unable to access XML file " + xmlFile.getName() + ".", ex);
            result = false;
        } catch (JDOMException ex) {
            logger.error("Unable to parse XML file " + xmlFile + ".", ex);
            result = false;
        } catch (Exception ex) {
            logger.error("An exception occured while parsing." + xmlFile, ex);
            result = false;
        }

        return result;
    }

    protected abstract void load(DictionaryInfo dictionary, Element root)
            throws Exception;

}
