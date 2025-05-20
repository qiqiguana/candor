/**
 * FieldsParser.java 11:56:16 PM Apr 21, 2008
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
import org.jdom.Element;

/**
 * Parses the Fields.xml.
 * 
 * @author jramoyo
 */
public class FieldsParser extends AbstractParser {

    private static final String TAG_ELEMENT = "Tag";

    private static final String FIELD_NAME_ELEMENT = "FieldName";

    private static final String TYPE_ELEMENT = "Type";

    private static final String DESC_ELEMENT = "Desc";

    private static final String LEN_REFERS_ELEMENT = "LenRefers";

    private static final String ABBR_NAME_ELEMENT = "AbbrName";

    private static final String NOT_REQ_XML_ELEMENT = "NotReqXML";

    private static final String BASE_CATEGORY_ELEMENT = "BaseCatagory";

    private static final String BASE_CATEGORY_XML_NAME_ELEMENT = "BaseCatagoryXMLName";

    private static final String UNION_DATA_TYPE_ELEMENT = "UnionDataType";

    private static final String USES_ENUMS_FROM_TAG = "UsesEnumsFromTag";

    private static final String OVERRIDE_XML_NAME_ELEMENT = "OverrideXMLName";

    private static final String DEPRECATED_ATTRIBUE = "Deprecated";

    /**
     * @see org.fixsuite.message.parsers.fpl.AbstractParser#load(org.fixsuite.message.info.DictionaryInfo,
     *      org.jdom.Element)
     */
    @SuppressWarnings("unchecked")
    protected void load(DictionaryInfo dictionary, Element root) {
        List nodes = root.getChildren();
        Element element;
        int tagNumber;
        String name;
        String dataType;
        String description;
        int length;
        String abbreviation;
        boolean isNotRequiredXml;
        String baseCategory;
        String baseCategoryXmlName;
        String unionDataType;
        String usesEnumsFromTag;
        String overrideXmlName;
        String deprecatingVersion;
        FieldInfo field;
        Iterator i = nodes.iterator();
        while (i.hasNext()) {
            // Extract the values
            element = (Element) i.next();
            tagNumber = Integer.parseInt(element.getChildText(TAG_ELEMENT));
            name = element.getChildText(FIELD_NAME_ELEMENT);
            dataType = element.getChildText(TYPE_ELEMENT);
            description = element.getChildText(DESC_ELEMENT);
            if (element.getChildText(LEN_REFERS_ELEMENT) != null) {
                length = Integer.parseInt(element
                        .getChildText(LEN_REFERS_ELEMENT));
            } else {
                length = 0;
            }
            abbreviation = element.getChildText(ABBR_NAME_ELEMENT);
            // TODO Verify if 1 is "true"
            if (element.getChildText(NOT_REQ_XML_ELEMENT) != null
                    && element.getChildText(NOT_REQ_XML_ELEMENT).equals("1")) {
                isNotRequiredXml = true;
            } else {
                isNotRequiredXml = false;
            }
            baseCategory = element.getChildText(BASE_CATEGORY_ELEMENT);
            baseCategoryXmlName = element
                    .getChildText(BASE_CATEGORY_XML_NAME_ELEMENT);
            unionDataType = element.getChildText(UNION_DATA_TYPE_ELEMENT);
            usesEnumsFromTag = element.getChildText(USES_ENUMS_FROM_TAG);
            overrideXmlName = element.getChildText(OVERRIDE_XML_NAME_ELEMENT);
            deprecatingVersion = element.getAttributeValue(DEPRECATED_ATTRIBUE);
            // Insert the values
            field = new FieldInfo();
            field.setTagNumber(tagNumber);
            field.setName(name);
            field.setDataType(dataType);
            field.setDescription(description);
            field.setLength(length);
            field.setAbbreviation(abbreviation);
            field.setNotRequiredXml(isNotRequiredXml);
            field.setBaseCategory(baseCategory);
            field.setBaseCategoryXmlName(baseCategoryXmlName);
            field.setUnionDataType(unionDataType);
            field.setUsesEnumFromTag(usesEnumsFromTag);
            field.setOverrideXmlName(overrideXmlName);
            if (deprecatingVersion != null) {
                field.setDeprecatingVersion(deprecatingVersion);
            } else {
                field.setDeprecatingVersion("");
            }
            // Add to dictionary
            dictionary.addField(field);
        }
        dictionary.incrementLoadCount();
    }

}
