/**
 * ListView.java 10:40:04 PM Apr 22, 2008
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

package org.fixsuite.message.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.fixsuite.message.Library;
import org.fixsuite.message.LibrarySuite;
import org.fixsuite.message.info.ComponentInfo;
import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.FixInfo;
import org.fixsuite.message.info.GroupInfo;
import org.fixsuite.message.info.MessageInfo;

/**
 * @author jramoyo
 */
public class ListView extends JPanel implements ItemListener,
        ListSelectionListener {

    private static final long serialVersionUID = 1L;

    private JComboBox dictionaryComboBox;

    private JList messageList;

    private JList componentList;

    private JList fieldList;

    private JList componentFieldList;

    private JScrollPane messageScrollPane;

    private JScrollPane componentScrollPane;

    private JScrollPane fieldScrollPane;

    private JScrollPane componentFieldScrollPane;

    private JRadioButton allFieldsRadioButton;

    private JRadioButton requiredFieldsRadioButton;

    private ButtonGroup requiredAllButtonGroup;

    private Library library;

    private boolean isRequiredOnly;

    public ListView(Library library) {
        super();
        this.library = library;
        isRequiredOnly = false;
        initGUI();
        // Load the fist dictionary
        ((MessageListModel) messageList.getModel())
                .load((DictionaryInfo) dictionaryComboBox.getItemAt(0));
    }

    private void initGUI() {
        // Set the layout
        setLayout(new BorderLayout());

        // Setup the components
        dictionaryComboBox = new JComboBox(new DictionaryComboBoxModel());
        dictionaryComboBox.setRenderer(new DictionaryComboBoxRenderer());
        ((DictionaryComboBoxModel) dictionaryComboBox.getModel()).load(library
                .getDictionaries());
        allFieldsRadioButton = new JRadioButton("Show All");
        allFieldsRadioButton.addItemListener(this);
        requiredFieldsRadioButton = new JRadioButton("Required Only");
        requiredFieldsRadioButton.addItemListener(this);
        requiredAllButtonGroup = new ButtonGroup();
        requiredAllButtonGroup.add(allFieldsRadioButton);
        requiredAllButtonGroup.add(requiredFieldsRadioButton);
        requiredAllButtonGroup.setSelected(allFieldsRadioButton.getModel(),
                true);
        messageList = new JList(new MessageListModel());
        messageList.setCellRenderer(new MessageListRenderer());
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        componentList = new JList(new ComponentListModel());
        componentList.setCellRenderer(new ComponentListRenderer());
        componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fieldList = new JList(new FieldListModel());
        fieldList.setCellRenderer(new FieldListRenderer());
        fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        componentFieldList = new JList(new FieldListModel());
        componentFieldList.setCellRenderer(new FieldListRenderer());
        componentFieldList
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageScrollPane = new JScrollPane();
        componentScrollPane = new JScrollPane();
        fieldScrollPane = new JScrollPane();
        componentFieldScrollPane = new JScrollPane();
        messageScrollPane.getViewport().add(messageList);
        messageScrollPane.setAlignmentX(0);
        componentScrollPane.getViewport().add(componentList);
        componentScrollPane.setAlignmentX(0);
        fieldScrollPane.getViewport().add(fieldList);
        fieldScrollPane.setAlignmentX(0);
        componentFieldScrollPane.getViewport().add(componentFieldList);
        componentFieldScrollPane.setAlignmentX(0);

        // Setup the listeners
        dictionaryComboBox.addItemListener(this);
        messageList.addListSelectionListener(this);
        fieldList.addListSelectionListener(this);
        componentList.addListSelectionListener(this);
        componentFieldList.addListSelectionListener(this);

        // Setup the containers
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        JPanel dictionaryPanel = new JPanel();
        dictionaryPanel.setLayout(new BoxLayout(dictionaryPanel,
                BoxLayout.X_AXIS));
        JSplitPane messageSplitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane fieldSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane componentSplitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT);
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        JPanel componentPanel = new JPanel();
        componentPanel
                .setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
        JPanel componentFieldPanel = new JPanel();
        componentFieldPanel.setLayout(new BoxLayout(componentFieldPanel,
                BoxLayout.Y_AXIS));

        // Setup the labels
        JLabel versionLabel = new JLabel("Available Versions: ");
        JLabel messageLabel = new JLabel("Messages", new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        messageLabel.setAlignmentX(0);
        JLabel fieldLabel = new JLabel("Fields", new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        fieldLabel.setAlignmentX(0);
        JLabel componentLabel = new JLabel("Components", new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        componentFieldPanel.setAlignmentX(0);
        JLabel componentFieldLabel = new JLabel("Fields", new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        componentFieldLabel.setAlignmentX(0);

        // Add the components
        dictionaryPanel.add(versionLabel);
        dictionaryPanel.add(dictionaryComboBox);
        toolbar.add(dictionaryPanel);
        toolbar.addSeparator();
        toolbar.add(allFieldsRadioButton);
        toolbar.add(requiredFieldsRadioButton);
        messagePanel.add(messageLabel);
        messagePanel.add(messageScrollPane);
        fieldPanel.add(fieldLabel);
        fieldPanel.add(fieldScrollPane);
        componentPanel.add(componentLabel);
        componentPanel.add(componentScrollPane);
        componentFieldPanel.add(componentFieldLabel);
        componentFieldPanel.add(componentFieldScrollPane);
        messageSplitPane.setLeftComponent(messagePanel);
        messageSplitPane.setRightComponent(fieldSplitPane);
        fieldSplitPane.setLeftComponent(fieldPanel);
        fieldSplitPane.setRightComponent(componentSplitPane);
        componentSplitPane.setTopComponent(componentPanel);
        componentSplitPane.setBottomComponent(componentFieldPanel);
        add(toolbar, BorderLayout.NORTH);
        add(messageSplitPane, BorderLayout.CENTER);

        // Setup the minimum divisions
        messageSplitPane.getLeftComponent().setMinimumSize(
                new Dimension(172, 768));
        messageSplitPane.getRightComponent().setMinimumSize(
                new Dimension(340, 768));
        fieldSplitPane.getLeftComponent().setMinimumSize(
                new Dimension(170, 768));
        fieldSplitPane.getRightComponent().setMinimumSize(
                new Dimension(170, 768));
    }

    public void itemStateChanged(ItemEvent event) {
        if (event.getSource().equals(dictionaryComboBox)
                && event.getStateChange() == ItemEvent.SELECTED) {
            // Clear list models
            ((FieldListModel) fieldList.getModel()).clear();
            ((ComponentListModel) componentList.getModel()).clear();
            ((FieldListModel) componentFieldList.getModel()).clear();
            // Load messages
            ((MessageListModel) messageList.getModel())
                    .load(((DictionaryInfo) dictionaryComboBox
                            .getSelectedItem()));
        } else if (event.getSource().equals(allFieldsRadioButton)) {
            isRequiredOnly = false;
            reloadWithFilter();
        } else if (event.getSource().equals(requiredFieldsRadioButton)) {
            isRequiredOnly = true;
            reloadWithFilter();
        }

    }

    public void valueChanged(ListSelectionEvent event) {
        if (event.getSource().equals(messageList)
                && !event.getValueIsAdjusting()
                && messageList.getSelectedValue() != null) {
            fieldList.clearSelection();
            componentList.clearSelection();
            componentFieldList.clearSelection();
            MessageInfo message = (MessageInfo) messageList.getSelectedValue();
            ((ComponentListModel) componentList.getModel()).load(message);
            ((FieldListModel) fieldList.getModel()).load(message);
            LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                    (FixInfo) messageList.getSelectedValue(),
                    (MessageInfo) messageList.getSelectedValue());
        } else if (event.getSource().equals(fieldList)
                && !event.getValueIsAdjusting()) {
            componentList.clearSelection();
            componentFieldList.clearSelection();
            LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                    (FixInfo) fieldList.getSelectedValue(),
                    (MessageInfo) messageList.getSelectedValue());
        } else if (event.getSource().equals(componentList)
                && !event.getValueIsAdjusting()) {
            fieldList.clearSelection();
            componentFieldList.clearSelection();
            if (componentList.getSelectedValue() != null) {
                ComponentInfo component = (ComponentInfo) componentList
                        .getSelectedValue();
                ((FieldListModel) componentFieldList.getModel())
                        .load(component);
                LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                        (FixInfo) componentList.getSelectedValue(),
                        (MessageInfo) messageList.getSelectedValue());
            } else {
                ((FieldListModel) componentFieldList.getModel()).clear();
            }
        } else if (event.getSource().equals(componentFieldList)
                && !event.getValueIsAdjusting()) {
            fieldList.clearSelection();
            LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                    (FixInfo) componentFieldList.getSelectedValue(),
                    (ComponentInfo) componentList.getSelectedValue());
        }
    }

    private void reloadWithFilter() {
        if (messageList != null) {
            MessageInfo message = (MessageInfo) messageList.getSelectedValue();
            if (message != null) {
                ((FieldListModel) fieldList.getModel()).load(message);
            }
        }
        if (componentFieldList != null) {
            ComponentInfo component = (ComponentInfo) componentList
                    .getSelectedValue();
            if (component != null) {
                ((FieldListModel) componentFieldList.getModel())
                        .load(component);
            }
        }
    }

    private class DictionaryComboBoxModel extends DefaultComboBoxModel {

        private static final long serialVersionUID = 1L;

        public void load(List<DictionaryInfo> dictionaries) {
            removeAllElements();
            for (DictionaryInfo dictionary : dictionaries) {
                addElement(dictionary);
            }
        }
    }

    private class MessageListModel extends DefaultListModel {

        private static final long serialVersionUID = 1L;

        public void load(DictionaryInfo dictionary) {
            removeAllElements();
            for (MessageInfo message : dictionary.getMessages()) {
                addElement(message);
            }
        }

    }

    private class ComponentListModel extends DefaultListModel {

        private static final long serialVersionUID = 1L;

        public void load(MessageInfo message) {
            removeAllElements();
            for (ComponentInfo component : message.getComponents()) {
                addElement(component);
            }
        }

    }

    private class FieldListModel extends DefaultListModel {

        private static final long serialVersionUID = 1L;

        public void load(ComponentInfo component) {
            removeAllElements();
            for (FieldInfo field : component.getFields()) {
                if (!isRequiredOnly) {
                    addElement(field);
                    if (field instanceof GroupInfo) {
                        load((GroupInfo) field, component);
                    }
                } else {
                    if (field.isRequiredInComponent(component)) {
                        addElement(field);
                        if (field instanceof GroupInfo) {
                            load((GroupInfo) field, component);
                        }
                    }
                }
            }
        }

        private void load(GroupInfo group, ComponentInfo component) {
            for (FieldInfo field : group.getFields()) {
                if (!isRequiredOnly) {
                    addElement(field);
                    if (field instanceof GroupInfo) {
                        load((GroupInfo) field, component);
                    }
                } else {
                    if (field.isRequiredInComponent(component)) {
                        addElement(field);
                        if (field instanceof GroupInfo) {
                            load((GroupInfo) field, component);
                        }
                    }
                }
            }
        }
    }

    private class DictionaryComboBoxRenderer extends BasicComboBoxRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            label.setText(((DictionaryInfo) value).getVersion());

            return label;
        }
    }

    private class MessageListRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            MessageInfo message = (MessageInfo) value;
            label.setText(message.getName() + " (" + message.getMessageType()
                    + ")");

            return label;
        }

    }

    private class ComponentListRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            ComponentInfo component = (ComponentInfo) value;
            label.setText(component.getName());

            return label;
        }

    }

    private class FieldListRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            FieldInfo field = (FieldInfo) value;
            label.setText(field.getName() + " (" + field.getTagNumber() + ")");

            return label;
        }
    }

}
