/**
 * DisplayDictionaryTest.java 8:55:12 PM Apr 22, 2008
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

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.fixsuite.message.info.ComponentInfo;
import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.GroupInfo;
import org.fixsuite.message.info.MessageInfo;
import org.fixsuite.message.info.ValueInfo;

/**
 * @author jramoyo
 */
public class DisplayDictionaryTest extends TestCase {

    public void setUp() {
        BasicConfigurator.configure();
    }

    public void testDisplay40Dictionary() {
        try {
            displayDictionary(".\\resources\\library40\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay41Dictionary() {
        try {
            displayDictionary(".\\resources\\library41\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay42Dictionary() {
        try {
            displayDictionary(".\\resources\\library42\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay43Dictionary() {
        try {
            displayDictionary(".\\resources\\library43\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay44Dictionary() {
        try {
            displayDictionary(".\\resources\\library44\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay50Dictionary() {
        try {
            displayDictionary(".\\resources\\library50\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplay50SP1Dictionary() {
        try {
            displayDictionary(".\\resources\\library50SP1\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    public void testDisplayAllDictionary() {
        try {
            displayDictionary(".\\resources\\libraryFpl\\");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    private void displayDictionary(String dictionaryPath) {
        Library library = new Library();
        library.loadFromDirectory(dictionaryPath);
        System.out.println();
        System.out.println();
        for (DictionaryInfo dictionary : library.getDictionaries()) {
            System.out.println("+ " + dictionary.getVersion());
            displayMessages(dictionary.getMessages());
        }
    }

    private void displayMessages(List<MessageInfo> messages) {
        for (MessageInfo message : messages) {
            System.out.println("\t- " + message.getName() + " ("
                    + message.getMessageType() + ")");
            System.out
                    .println("\t- Abbreviation: " + message.getAbbreviation());
            System.out.println("\t- Override Abbreviation: "
                    + message.getOverrideAbbreviation());
            System.out.println("\t- Type: " + message.getComponentType());
            System.out.println("\t- Category: " + message.getCategory());
            System.out.println("\t- Section: " + message.getSection());
            System.out.println("\t- Volume: " + message.getVolume());
            System.out.println("\t- XML Not Required: "
                    + message.isNotRequiredXml());
            displayComponents(message);
            displayFields(message);
        }
    }

    private void displayComponents(MessageInfo message) {
        for (ComponentInfo component : message.getComponents()) {
            System.out.println("\t\t > [" + component.getName() + "]");
            System.out.println("\t\t > Abbreviation: "
                    + component.getAbbreviation());
            System.out.println("\t\t > Type: " + component.getComponentType());
            System.out.println("\t\t > Category: " + component.getCategory());
            System.out.println("\t\t > XML Not Required: "
                    + component.isNotRequiredXml());
            displayComponentFields(component, message);
        }
    }

    private void displayFields(MessageInfo message) {
        for (FieldInfo field : message.getFields()) {
            System.out.println("\t\t> " + field.getName() + " ("
                    + field.getTagNumber() + ")");
            System.out.println("\t\t\t * Required: "
                    + field.isRequiredInComponent(message));
            System.out.println("\t\t\t * XML Not Required: "
                    + field.isNotRequiredXml());
            System.out.println("\t\t\t * Abbreviation: "
                    + field.getAbbreviation());
            if (field.getValidValues() != null) {
                System.out.print("\t\t\t * Values: ");
                for (ValueInfo value : field.getValidValues()) {
                    System.out.print(value.getValue() + " ");
                }
                System.out.println();
            }
            if (field instanceof GroupInfo) {
                displayGroup((GroupInfo) field, message);
            }
        }
    }

    private void displayGroup(GroupInfo group, MessageInfo message) {
        if (group.getFields() != null) {
            for (FieldInfo field : group.getFields()) {
                System.out.println("\t\t\t> " + field.getName() + " ("
                        + field.getTagNumber() + ")");
                System.out.println("\t\t\t\t * Required: "
                        + field.isRequiredInComponent(message));
                System.out.println("\t\t\t\t * XML Not Required: "
                        + field.isNotRequiredXml());
                System.out.println("\t\t\t\t * Abbreviation: "
                        + field.getAbbreviation());
                if (field.getValidValues() != null) {
                    System.out.print("\t\t\t\t * Values: ");
                    for (ValueInfo value : field.getValidValues()) {
                        System.out.print(value.getValue() + " ");
                    }
                    System.out.println();
                }
                if (field instanceof GroupInfo) {
                    displayComponentGroup((GroupInfo) field, message);
                }
            }
        }
    }

    private void displayComponentFields(ComponentInfo component,
            MessageInfo message) {
        if (component.getFields() != null) {
            for (FieldInfo field : component.getFields()) {
                System.out.println("\t\t\t -> " + field.getName() + " ("
                        + field.getTagNumber() + ")");
                System.out.println("\t\t\t\t * Required: "
                        + field.isRequiredInComponent(message));
                System.out.println("\t\t\t\t * XML Not Required: "
                        + field.isNotRequiredXml());
                System.out.println("\t\t\t\t * Abbreviation: "
                        + field.getAbbreviation());
                if (field.getValidValues() != null) {
                    System.out.print("\t\t\t\t * Values: ");
                    for (ValueInfo value : field.getValidValues()) {
                        System.out.print(value.getValue() + " ");
                    }
                    System.out.println();
                }
            }
        } else {
            System.out.println("\t\t > No available fields *****");
        }
    }

    private void displayComponentGroup(GroupInfo group, MessageInfo message) {
        if (group.getFields() != null) {
            for (FieldInfo field : group.getFields()) {
                System.out.println("\t\t\t\t> " + field.getName() + " ("
                        + field.getTagNumber() + ")");
                System.out.println("\t\t\t\t\t * Required: "
                        + field.isRequiredInComponent(message));
                System.out.println("\t\t\t\t\t * XML Not Required: "
                        + field.isNotRequiredXml());
                System.out.println("\t\t\t\t\t * Abbreviation: "
                        + field.getAbbreviation());
                if (field.getValidValues() != null) {
                    System.out.print("\t\t\t\t\t * Values: ");
                    for (ValueInfo value : field.getValidValues()) {
                        System.out.print(value.getValue() + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

}
