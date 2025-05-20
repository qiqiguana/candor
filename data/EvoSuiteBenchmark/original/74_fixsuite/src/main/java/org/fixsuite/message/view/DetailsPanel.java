/**
 * DetailsPanel.java 8:55:51 PM Apr 24, 2008
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.fixsuite.message.info.ComponentInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.FixInfo;
import org.fixsuite.message.info.MessageInfo;
import org.fixsuite.message.info.ValueInfo;

/**
 * @author jramoyo
 */
public class DetailsPanel extends JPanel implements ActionListener,
        MouseListener {

    private static final long serialVersionUID = 1L;

    private JDesktopPane desktopPane;

    private JInternalFrame detailsInternalFrame;

    private JInternalFrame validValuesInternalFrame;

    private JInternalFrame descriptionInternalFrame;

    private JInternalFrame commentsInternalFrame;

    private JList validValuesList;

    private JTextPane descriptionTextPane;

    private JTextPane commentsTextPane;

    private JScrollPane validValuesScrollPane;

    private JScrollPane descriptionScrollPane;

    private JScrollPane commentsScrollPane;

    private JPopupMenu popupMenu;

    private JMenuItem arrangeMenuItem;

    private JMenuItem packMenuItem;

    // Field Specific

    private InfoPanel fieldTagNumberInfo;

    private InfoPanel fieldNameInfo;

    private InfoPanel fieldIsRequiredInfo;

    private InfoPanel fieldDataTypeInfo;

    private InfoPanel fieldAbbreviationInfo;

    private InfoPanel fieldOverrideXmlNameInfo;

    private InfoPanel fieldBaseCategoryInfo;

    private InfoPanel fieldBaseCategoryXmlNameInfo;

    private InfoPanel fieldUnionDataTypeInfo;

    private InfoPanel fieldUsesEnumFromTagInfo;

    private InfoPanel fieldLengthInfo;

    private InfoPanel fieldIsNotRequiredXmlInfo;

    private InfoPanel fieldDeprecatingVersionInfo;

    // Component Specific

    private InfoPanel componentNameInfo;

    private InfoPanel componentComponentTypeInfo;

    private InfoPanel componentCategoryInfo;

    private InfoPanel componentAbbreviationInfo;

    private InfoPanel componentIsNotRequiredXmlInfo;

    private InfoPanel componentIsRequiredInfo;

    // Message Specific

    private InfoPanel messageNameInfo;

    private InfoPanel messageComponentTypeInfo;

    private InfoPanel messageCategoryInfo;

    private InfoPanel messageAbbreviationInfo;

    private InfoPanel messageIsNotRequiredXmlInfo;

    private InfoPanel messageMessageTypeInfo;

    private InfoPanel messageOverrideAbbreviationInfo;

    private InfoPanel messageSectionInfo;

    private InfoPanel messageVolumeInfo;

    // Inner Frame Panels

    private JPanel fieldInfoPanel;

    private JPanel componentInfoPanel;

    private JPanel messageInfoPanel;

    public DetailsPanel() {
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        popupMenu = new JPopupMenu();
        arrangeMenuItem = new JMenuItem("Arrange ");
        arrangeMenuItem.addActionListener(this);
        packMenuItem = new JMenuItem("Repack");
        packMenuItem.addActionListener(this);
        popupMenu.add(arrangeMenuItem);
        popupMenu.add(packMenuItem);

        fieldTagNumberInfo = new InfoPanel("Tag #", "");
        fieldNameInfo = new InfoPanel("Name", "");
        fieldIsRequiredInfo = new InfoPanel("Is Required", "");
        fieldDataTypeInfo = new InfoPanel("Data Type", "");
        fieldUnionDataTypeInfo = new InfoPanel("Union Data Type", "");
        fieldAbbreviationInfo = new InfoPanel("Abbreviation", "");
        fieldOverrideXmlNameInfo = new InfoPanel("Override XML Name", "");
        fieldBaseCategoryInfo = new InfoPanel("Base Category", "");
        fieldBaseCategoryXmlNameInfo = new InfoPanel("Base Category XML Name",
                "");
        fieldUsesEnumFromTagInfo = new InfoPanel("Uses Values From Tag", "");
        fieldLengthInfo = new InfoPanel("Field Length", "");
        fieldIsNotRequiredXmlInfo = new InfoPanel("Does not Require XML", "");
        fieldDeprecatingVersionInfo = new InfoPanel("Deprecated in Version", "");
        componentNameInfo = new InfoPanel("Component Name", "");
        componentComponentTypeInfo = new InfoPanel("Component Type", "");
        componentCategoryInfo = new InfoPanel("Category", "");
        componentAbbreviationInfo = new InfoPanel("Abbreviation", "");
        componentIsNotRequiredXmlInfo = new InfoPanel("Does not Require XML",
                "");
        componentIsRequiredInfo = new InfoPanel("Is Required", "");
        messageNameInfo = new InfoPanel("Message Name", "");
        messageComponentTypeInfo = new InfoPanel("Component Type", "");
        messageCategoryInfo = new InfoPanel("Category", "");
        messageAbbreviationInfo = new InfoPanel("Abbreviation", "");
        messageIsNotRequiredXmlInfo = new InfoPanel("Does not Require XML", "");
        messageMessageTypeInfo = new InfoPanel("Message Type", "");
        messageOverrideAbbreviationInfo = new InfoPanel(
                "Override Abbreviation", "");
        messageSectionInfo = new InfoPanel("Section", "");
        messageVolumeInfo = new InfoPanel("Volume", "");
        fieldTagNumberInfo.setAlignmentX(0);
        fieldNameInfo.setAlignmentX(0);
        fieldIsRequiredInfo.setAlignmentX(0);
        fieldDataTypeInfo.setAlignmentX(0);
        fieldUnionDataTypeInfo.setAlignmentX(0);
        fieldAbbreviationInfo.setAlignmentX(0);
        fieldOverrideXmlNameInfo.setAlignmentX(0);
        fieldBaseCategoryInfo.setAlignmentX(0);
        fieldBaseCategoryXmlNameInfo.setAlignmentX(0);
        fieldUsesEnumFromTagInfo.setAlignmentX(0);
        fieldLengthInfo.setAlignmentX(0);
        fieldIsNotRequiredXmlInfo.setAlignmentX(0);
        fieldDeprecatingVersionInfo.setAlignmentX(0);
        componentNameInfo.setAlignmentX(0);
        componentComponentTypeInfo.setAlignmentX(0);
        componentCategoryInfo.setAlignmentX(0);
        componentAbbreviationInfo.setAlignmentX(0);
        componentIsNotRequiredXmlInfo.setAlignmentX(0);
        componentIsRequiredInfo.setAlignmentX(0);
        messageNameInfo.setAlignmentX(0);
        messageComponentTypeInfo.setAlignmentX(0);
        messageCategoryInfo.setAlignmentX(0);
        messageAbbreviationInfo.setAlignmentX(0);
        messageIsNotRequiredXmlInfo.setAlignmentX(0);
        messageMessageTypeInfo.setAlignmentX(0);
        messageOverrideAbbreviationInfo.setAlignmentX(0);
        messageSectionInfo.setAlignmentX(0);
        messageVolumeInfo.setAlignmentX(0);

        validValuesList = new JList(new DefaultListModel());
        validValuesList.setCellRenderer(new ValidValuesListRender());
        descriptionTextPane = new JTextPane();
        descriptionTextPane.setEditable(false);
        descriptionTextPane.setFont(new Font(descriptionTextPane.getFont()
                .getName(), Font.BOLD, 12));
        commentsTextPane = new JTextPane();
        commentsTextPane.setEditable(false);
        commentsTextPane.setFont(new Font(commentsTextPane.getFont().getName(),
                Font.BOLD, 12));
        validValuesScrollPane = new JScrollPane();
        validValuesScrollPane.getViewport().add(validValuesList);
        descriptionScrollPane = new JScrollPane();
        descriptionScrollPane.getViewport().add(descriptionTextPane);
        descriptionScrollPane.setPreferredSize(new Dimension(300, 150));
        commentsScrollPane = new JScrollPane();
        commentsScrollPane.getViewport().add(commentsTextPane);
        commentsScrollPane.setPreferredSize(new Dimension(300, 150));

        fieldInfoPanel = new JPanel();
        fieldInfoPanel
                .setLayout(new BoxLayout(fieldInfoPanel, BoxLayout.Y_AXIS));
        fieldInfoPanel.setBorder(BorderFactory
                .createEmptyBorder(10, 10, 10, 10));
        componentInfoPanel = new JPanel();
        componentInfoPanel.setLayout(new BoxLayout(componentInfoPanel,
                BoxLayout.Y_AXIS));
        componentInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10,
                10, 10));
        messageInfoPanel = new JPanel();
        messageInfoPanel.setLayout(new BoxLayout(messageInfoPanel,
                BoxLayout.Y_AXIS));
        messageInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                10));
        fieldInfoPanel.add(fieldTagNumberInfo);
        fieldInfoPanel.add(fieldNameInfo);
        fieldInfoPanel.add(fieldIsRequiredInfo);
        fieldInfoPanel.add(fieldDataTypeInfo);
        fieldInfoPanel.add(fieldUnionDataTypeInfo);
        fieldInfoPanel.add(fieldAbbreviationInfo);
        fieldInfoPanel.add(fieldOverrideXmlNameInfo);
        fieldInfoPanel.add(fieldBaseCategoryInfo);
        fieldInfoPanel.add(fieldBaseCategoryXmlNameInfo);
        fieldInfoPanel.add(fieldUsesEnumFromTagInfo);
        fieldInfoPanel.add(fieldLengthInfo);
        fieldInfoPanel.add(fieldIsNotRequiredXmlInfo);
        fieldInfoPanel.add(fieldDeprecatingVersionInfo);
        messageInfoPanel.add(messageMessageTypeInfo);
        messageInfoPanel.add(messageNameInfo);
        messageInfoPanel.add(messageAbbreviationInfo);
        messageInfoPanel.add(messageOverrideAbbreviationInfo);
        messageInfoPanel.add(messageComponentTypeInfo);
        messageInfoPanel.add(messageCategoryInfo);
        messageInfoPanel.add(messageSectionInfo);
        messageInfoPanel.add(messageVolumeInfo);
        messageInfoPanel.add(messageIsNotRequiredXmlInfo);
        componentInfoPanel.add(componentNameInfo);
        componentInfoPanel.add(componentIsRequiredInfo);
        componentInfoPanel.add(componentAbbreviationInfo);
        componentInfoPanel.add(componentComponentTypeInfo);
        componentInfoPanel.add(componentCategoryInfo);
        componentInfoPanel.add(componentIsNotRequiredXmlInfo);

        detailsInternalFrame = new JInternalFrame("", false);
        detailsInternalFrame.setFrameIcon(new ImageIcon(
                ".\\resources\\gray.gif"));
        descriptionInternalFrame = new JInternalFrame("Field Description", true);
        descriptionInternalFrame.setFrameIcon(new ImageIcon(
                ".\\resources\\gray.gif"));
        commentsInternalFrame = new JInternalFrame("Field Comments", true);
        commentsInternalFrame.setFrameIcon(new ImageIcon(
                ".\\resources\\gray.gif"));
        validValuesInternalFrame = new JInternalFrame("Valid Values", true);
        validValuesInternalFrame.setFrameIcon(new ImageIcon(
                ".\\resources\\gray.gif"));
        detailsInternalFrame.setLocation(0, 0);
        descriptionInternalFrame.setLocation(0, 200);
        commentsInternalFrame.setLocation(0, 400);
        validValuesInternalFrame.setLocation(0, 600);

        JScrollPane mainScrollPane = new JScrollPane();
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.GRAY);
        desktopPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        desktopPane.setPreferredSize(Toolkit.getDefaultToolkit()
                .getScreenSize());
        desktopPane.addMouseListener(this);
        mainScrollPane.getViewport().add(desktopPane);
        add(mainScrollPane, BorderLayout.CENTER);
    }

    private void displayMessageInfo(MessageInfo message) {
        messageNameInfo.setValue(message.getName());
        messageComponentTypeInfo.setValue(message.getComponentType());
        messageCategoryInfo.setValue(message.getCategory());
        messageAbbreviationInfo.setValue(message.getAbbreviation());
        messageIsNotRequiredXmlInfo.setValue("" + message.isNotRequiredXml());
        messageMessageTypeInfo.setValue(message.getMessageType());
        messageSectionInfo.setValue(message.getSection());
        messageOverrideAbbreviationInfo.setValue(message
                .getOverrideAbbreviation());
        messageVolumeInfo.setValue(message.getVolume());

        messageInfoPanel.validate();
        desktopPane.add(detailsInternalFrame);
        detailsInternalFrame.setTitle("Message Details");
        detailsInternalFrame.getContentPane().add(messageInfoPanel,
                BorderLayout.CENTER);
        detailsInternalFrame.setVisible(true);
        detailsInternalFrame.revalidate();
        detailsInternalFrame.pack();
    }

    private void displayComponentInfo(ComponentInfo component) {
        componentNameInfo.setValue(component.getName());
        componentIsRequiredInfo.setValue("" + component.isRequired());
        componentComponentTypeInfo.setValue(component.getComponentType());
        componentCategoryInfo.setValue(component.getCategory());
        componentAbbreviationInfo.setValue(component.getAbbreviation());
        componentIsNotRequiredXmlInfo.setValue(""
                + component.isNotRequiredXml());

        componentInfoPanel.validate();
        desktopPane.add(detailsInternalFrame);
        detailsInternalFrame.setTitle("Component Details");
        detailsInternalFrame.getContentPane().add(componentInfoPanel,
                BorderLayout.CENTER);
        detailsInternalFrame.setVisible(true);
        detailsInternalFrame.revalidate();
        detailsInternalFrame.pack();
    }

    private void displayFieldInfo(FieldInfo field, ComponentInfo component) {
        fieldTagNumberInfo.setValue("" + field.getTagNumber());
        fieldNameInfo.setValue(field.getName());
        fieldIsRequiredInfo.setValue(""
                + field.isRequiredInComponent(component));
        fieldDataTypeInfo.setValue(field.getDataType());
        fieldUnionDataTypeInfo.setValue(field.getUnionDataType());
        fieldAbbreviationInfo.setValue(field.getAbbreviation());
        fieldOverrideXmlNameInfo.setValue(field.getOverrideXmlName());
        fieldBaseCategoryInfo.setValue(field.getBaseCategory());
        fieldBaseCategoryXmlNameInfo.setValue(field.getBaseCategoryXmlName());
        fieldUsesEnumFromTagInfo.setValue(field.getUsesEnumFromTag());
        if (field.getLength() > 0) {
            fieldLengthInfo.setValue("" + field.getLength());
        } else {
            fieldLengthInfo.setValue("");
        }
        fieldIsNotRequiredXmlInfo.setValue("" + field.isNotRequiredXml());
        fieldDeprecatingVersionInfo
                .setValue("" + field.getDeprecatingVersion());

        fieldInfoPanel.validate();
        desktopPane.add(detailsInternalFrame);
        detailsInternalFrame.setTitle("Field Details");
        detailsInternalFrame.getContentPane().add(fieldInfoPanel,
                BorderLayout.CENTER);
        detailsInternalFrame.setVisible(true);
        detailsInternalFrame.revalidate();
        detailsInternalFrame.pack();

        if (field.getValidValues() != null) {
            ((DefaultListModel) validValuesList.getModel()).removeAllElements();
            for (ValueInfo value : field.getValidValues()) {
                ((DefaultListModel) validValuesList.getModel())
                        .addElement(value);
            }
            if (field.getValidValues().size() * 30 > 400) {
                validValuesScrollPane.setPreferredSize(new Dimension(200, 400));
            } else {
                validValuesScrollPane.setPreferredSize(new Dimension(200, field
                        .getValidValues().size() * 30));
            }
            desktopPane.add(validValuesInternalFrame);
            validValuesInternalFrame.add(validValuesScrollPane);
            validValuesInternalFrame.setVisible(true);
            validValuesInternalFrame.pack();
        }

        if (field.getDescription() != null
                && !field.getDescription().equals("")) {
            descriptionTextPane.setText(field.getDescription());
            desktopPane.add(descriptionInternalFrame);
            descriptionInternalFrame.getContentPane()
                    .add(descriptionScrollPane);
            descriptionInternalFrame.setVisible(true);
            descriptionInternalFrame.pack();
        }

        if (field.getComments() != null && !field.getComments().equals("")
                && !field.getComments().equals("\n")) {
            commentsTextPane.setText(field.getComments());
            desktopPane.add(commentsInternalFrame);
            commentsInternalFrame.getContentPane().add(commentsScrollPane);
            commentsInternalFrame.setVisible(true);
            commentsInternalFrame.pack();
        }
    }

    public void displayDetails(FixInfo info, ComponentInfo component) {
        clearDisplay();
        if (info instanceof FieldInfo) {
            displayFieldInfo((FieldInfo) info, component);
        } else if (info instanceof MessageInfo) {
            displayMessageInfo((MessageInfo) info);
        } else if (info instanceof ComponentInfo) {
            displayComponentInfo((ComponentInfo) info);
        }
    }

    public void clearDisplay() {
        desktopPane.getDesktopManager().closeFrame(detailsInternalFrame);
        desktopPane.getDesktopManager().closeFrame(validValuesInternalFrame);
        desktopPane.getDesktopManager().closeFrame(descriptionInternalFrame);
        desktopPane.getDesktopManager().closeFrame(commentsInternalFrame);
        detailsInternalFrame.getContentPane().removeAll();
    }

    public void arrangeFrames() {
        detailsInternalFrame.setLocation(0, 0);
        descriptionInternalFrame.setLocation(0, detailsInternalFrame
                .getHeight());
        if (descriptionInternalFrame.isShowing()) {
            commentsInternalFrame.setLocation(0, detailsInternalFrame
                    .getHeight()
                    + descriptionInternalFrame.getHeight());
            if (commentsInternalFrame.isShowing()) {
                validValuesInternalFrame.setLocation(0, detailsInternalFrame
                        .getHeight()
                        + descriptionInternalFrame.getHeight()
                        + commentsInternalFrame.getHeight());
            } else {
                validValuesInternalFrame.setLocation(0, detailsInternalFrame
                        .getHeight()
                        + descriptionInternalFrame.getHeight());
            }
        } else {
            commentsInternalFrame.setLocation(0, detailsInternalFrame
                    .getHeight());
            if (commentsInternalFrame.isShowing()) {
                validValuesInternalFrame.setLocation(0, detailsInternalFrame
                        .getHeight()
                        + commentsInternalFrame.getHeight());
            } else {
                validValuesInternalFrame.setLocation(0, detailsInternalFrame
                        .getHeight());
            }
        }
    }

    public void packFrames() {
        detailsInternalFrame.pack();
        descriptionInternalFrame.pack();
        commentsInternalFrame.pack();
        validValuesInternalFrame.pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(arrangeMenuItem)) {
            arrangeFrames();
        } else if (e.getSource().equals(packMenuItem)) {
            packFrames();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (desktopPane.getAllFrames().length > 0) {
                arrangeMenuItem.setEnabled(true);
                packMenuItem.setEnabled(true);
            } else {
                arrangeMenuItem.setEnabled(false);
                packMenuItem.setEnabled(false);
            }
            popupMenu.show(desktopPane, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    private class ValidValuesListRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            ValueInfo validValue = (ValueInfo) value;
            label.setText("<html><b><font size=\"5\">" + validValue.getValue()
                    + "</font></b> - <i>" + validValue.getDescription()
                    + "</i></html>");

            return label;
        }

    }

}
