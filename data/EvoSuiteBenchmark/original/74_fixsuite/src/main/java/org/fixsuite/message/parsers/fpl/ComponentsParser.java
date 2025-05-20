/**
 * ComponentsParser.java 8:17:26 PM Apr 22, 2008
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

import org.fixsuite.message.info.ComponentInfo;
import org.fixsuite.message.info.DictionaryInfo;
import org.jdom.Element;

/**
 * Parses the Components.xml.
 * 
 * @author jramoyo
 */
public class ComponentsParser extends AbstractParser {

    private static final String COMPONENT_NAME_ELEMENT = "ComponentName";

    private static final String COMPONENT_TYPE_ELEMENT = "ComponentType";

    private static final String CATEGORY_ELEMENT = "Category";

    private static final String MSG_ID_ELEMENT = "MsgID";

    private static final String ABBR_NAME_ELEMENT = "AbbrName";

    private static final String NOT_REQ_XML_ELEMENT = "NotReqXML";

    /**
     * @see org.fixsuite.message.parsers.fpl.AbstractParser#load(org.fixsuite.message.info.DictionaryInfo,
     *      org.jdom.Element)
     */
    @SuppressWarnings("unchecked")
    protected void load(DictionaryInfo dictionary, Element root) {
        List nodes = root.getChildren();
        Element element;
        String name;
        String componentType;
        String category;
        int id;
        String abbreviation;
        boolean isNotRequiredXml;
        ComponentInfo component;
        Iterator i = nodes.iterator();
        while (i.hasNext()) {
            // Extract the values
            element = (Element) i.next();
            name = element.getChildText(COMPONENT_NAME_ELEMENT).trim();
            componentType = element.getChildText(COMPONENT_TYPE_ELEMENT);
            category = element.getChildText(CATEGORY_ELEMENT);
            id = Integer.parseInt(element.getChildText(MSG_ID_ELEMENT));
            abbreviation = element.getChildText(ABBR_NAME_ELEMENT);
            // TODO Verify if 1 is "true"
            if (element.getChildText(NOT_REQ_XML_ELEMENT) != null
                    && element.getChildText(NOT_REQ_XML_ELEMENT).equals("1")) {
                isNotRequiredXml = true;
            } else {
                isNotRequiredXml = false;
            }
            // Insert the values
            component = new ComponentInfo();
            component.setName(name);
            component.setComponentType(componentType);
            component.setCategory(category);
            component.setId(id);
            component.setAbbreviation(abbreviation);
            component.setNotRequiredXml(isNotRequiredXml);
            // Add to dictionary
            dictionary.addComponent(component);
        }
        dictionary.incrementLoadCount();
    }
}
