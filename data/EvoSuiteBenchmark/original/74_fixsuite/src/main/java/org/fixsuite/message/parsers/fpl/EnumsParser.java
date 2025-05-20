/**
 * EnumsParser.java 11:50:20 PM Apr 21, 2008
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

import java.util.Iterator;
import java.util.List;

import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.ValueInfo;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses the Enums.xml. FieldsParser must execute before this parser.
 * 
 * @author jramoyo
 */
public class EnumsParser extends AbstractParser {

    private static final String TAG_ELEMENT = "Tag";

    private static final String ENUM_ELEMENT = "Enum";

    private static final String DESC_ELEMENT = "Description";

    private static final String GROUP_ELEMENT = "Group";

    private static final String DEPRECATED_ATTRIBUE = "Deprecated";

    private static Logger logger = LoggerFactory.getLogger(EnumsParser.class);

    /**
     * @see org.fixsuite.message.parsers.fpl.AbstractParser#load(org.fixsuite.message.info.DictionaryInfo,
     *      org.jdom.Element)
     */
    @SuppressWarnings("unchecked")
    protected void load(DictionaryInfo dictionary, Element root) {
        List nodes = root.getChildren();
        Element element;
        int tagNumber;
        String value;
        String description;
        String group;
        String deprecatingVersion;
        FieldInfo field;
        ValueInfo validValue;
        Iterator i = nodes.iterator();
        while (i.hasNext()) {
            // Extract the values
            element = (Element) i.next();
            tagNumber = Integer.parseInt(element.getChildText(TAG_ELEMENT));
            value = element.getChildText(ENUM_ELEMENT);
            description = element.getChildText(DESC_ELEMENT);
            group = element.getChildText(GROUP_ELEMENT);
            deprecatingVersion = element.getAttributeValue(DEPRECATED_ATTRIBUE);
            // Insert the values
            validValue = new ValueInfo();
            validValue.setDescription(description);
            validValue.setValue(value);
            validValue.setGroup(group);
            validValue.setDeprecatingVersion(deprecatingVersion);
            field = dictionary.getField(tagNumber);
            // Add to dictionary
            if (field != null) {
                field.addValidValue(validValue);
            } else {
                logger.error("Field " + tagNumber + " not found in dictionary "
                        + dictionary.getVersion() + ".");
            }
        }
        dictionary.incrementLoadCount();
    }
}
