/**
 * MsgContentsParser.java 8:27:31 PM Apr 22, 2008
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
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.GroupInfo;
import org.jdom.Element;

/**
 * Parses the MsgContents.xml. MsgContentsParser is the core of the FPL parsers,
 * it consolidates all data collected from other FPL parsers. This should be the
 * last parser to execute.
 * 
 * @author jramoyo
 */
public class MsgContentsParser extends AbstractParser {

    private static final String INDENT_ELEMENT = "Indent";

    private static final String TAG_TEXT_ELEMENT = "TagText";

    private static final String REQD_ELEMENT = "Reqd";

    private static final String MSG_ID_ELEMENT = "MsgID";

    private static final String DESCRIPTION_ELEMENT = "Description";

    private static final String POSITION_ELEMENT = "Position";

    private LinkedList<GroupInfo> indentStack;

    /**
     * Creates a new MsgContentsParser
     */
    public MsgContentsParser() {
        indentStack = new LinkedList<GroupInfo>();
    }

    /**
     * @see org.fixsuite.message.parsers.fpl.AbstractParser#load(org.fixsuite.message.info.DictionaryInfo,
     *      org.jdom.Element)
     */
    @SuppressWarnings("unchecked")
    protected void load(DictionaryInfo dictionary, Element root) {
        List nodes = root.getChildren();
        TreeSet<MsgContentModel> msgContentSet = new TreeSet<MsgContentModel>();
        Element element;
        int currentIndent = 0;
        int tagNumber;
        FieldInfo previousField = null;
        int previousId = 0;
        Iterator i = nodes.iterator();
        MsgContentModel msgContentModel;
        // Collect and extract the values
        while (i.hasNext()) {
            element = (Element) i.next();
            msgContentModel = new MsgContentModel();
            msgContentModel.setIndent(Integer.parseInt(element
                    .getChildText(INDENT_ELEMENT)));
            msgContentModel.setTagText(element.getChildText(TAG_TEXT_ELEMENT));
            msgContentModel.setDescription(element
                    .getChildText(DESCRIPTION_ELEMENT));
            if (element.getChildText(REQD_ELEMENT).equals("1")) {
                msgContentModel.setRequired(true);
            } else {
                msgContentModel.setRequired(false);
            }
            msgContentModel.setId(Integer.parseInt(element
                    .getChildText(MSG_ID_ELEMENT)));
            msgContentModel.setPosition(Double.parseDouble(element
                    .getChildText(POSITION_ELEMENT)));
            // Store the model in a sorted set
            msgContentSet.add(msgContentModel);
        }
        for (MsgContentModel model : msgContentSet) {
            // Process values
            try {
                // Item is a tag
                tagNumber = Integer.parseInt(model.getTagText());
                dictionary.getField(tagNumber).setComments(
                        model.getDescription());

                if (model.isRequired()) {
                    if (model.getId() >= 1000) {
                        dictionary.getField(tagNumber).addRequiringComponent(
                                dictionary.getComponent(model.getId()));
                    } else {
                        dictionary.getField(tagNumber).addRequiringComponent(
                                dictionary.getMessage(model.getId()));
                    }
                }

                if (model.getIndent() > currentIndent) {
                    currentIndent = model.getIndent();
                    GroupInfo group = new GroupInfo(previousField);
                    group.addField(dictionary.getField(tagNumber), model
                            .getPosition());
                    dictionary.replaceAsGroup(previousField, group);

                    if (indentStack.isEmpty()) {
                        if (previousId >= 1000) {
                            if (dictionary.getComponent(previousId) != null) {
                                dictionary.getComponent(previousId)
                                        .replaceAsGroup(previousField, group);
                            }
                        } else {
                            dictionary.getMessage(previousId).replaceAsGroup(
                                    previousField, group);
                        }
                    } else {
                        indentStack.getLast().replaceAsGroup(previousField,
                                group);
                    }
                    indentStack.addLast(group);
                } else {
                    if (model.getIndent() < currentIndent) {
                        currentIndent = model.getIndent();
                        if (model.getIndent() != 0) {
                            indentStack.removeLast();
                        } else {
                            indentStack.clear();
                        }
                    }

                    if (indentStack.isEmpty()) {
                        if (model.getId() >= 1000) {
                            if (dictionary.getComponent(model.getId()) != null) {
                                dictionary.getComponent(model.getId())
                                        .addField(
                                                dictionary.getField(tagNumber),
                                                model.getPosition());
                            }
                        } else {
                            dictionary.getMessage(model.getId()).addField(
                                    dictionary.getField(tagNumber),
                                    model.getPosition());
                        }
                    } else {
                        indentStack.getLast().addField(
                                dictionary.getField(tagNumber),
                                model.getPosition());
                    }
                }
                previousField = dictionary.getField(tagNumber);
                previousId = model.getId();
            } catch (NumberFormatException ex) {
                // Item is a component
                if (model.isRequired()) {
                    dictionary.getComponent(model.getTagText()).setRequired(
                            true);
                }

                if (model.getIndent() < currentIndent) {
                    currentIndent = model.getIndent();
                    if (model.getIndent() != 0) {
                        indentStack.removeLast();
                    } else {
                        indentStack.clear();
                    }
                }
                if (indentStack.isEmpty()) {
                    if (model.getId() >= 1000) {
                        if (dictionary.getComponent(model.getId()) != null) {
                            dictionary.getComponent(model.getId())
                                    .addComponent(
                                            dictionary.getComponent(model
                                                    .getTagText()),
                                            model.getPosition());
                        }
                    } else {
                        if (dictionary.getMessage(model.getId()) != null) {
                            dictionary.getMessage(model.getId())
                                    .addComponent(
                                            dictionary.getComponent(model
                                                    .getTagText()),
                                            model.getPosition());
                        }
                    }
                } else {
                    indentStack.getLast().addComponent(
                            dictionary.getComponent(model.getTagText()),
                            model.getPosition());
                }
            }
        }
        indentStack.clear();
        dictionary.incrementLoadCount();
    }
}
