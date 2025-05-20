/*
 * Created on 06-Feb-2006
 * Copyright (C) 2006 by Andrea Vacondio.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.pdfsam.plugin.coverfooter.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;
import org.pdfsam.guiclient.business.listeners.EnterDoClickListener;
import org.pdfsam.guiclient.commons.business.actions.SetOutputPathSelectionTableAction;
import org.pdfsam.guiclient.commons.business.listeners.CompressCheckBoxItemListener;
import org.pdfsam.guiclient.commons.components.CommonComponentsFactory;
import org.pdfsam.guiclient.commons.components.JPdfVersionCombo;
import org.pdfsam.guiclient.commons.components.sharedchooser.SharedJFileChooser;
import org.pdfsam.guiclient.commons.components.sharedchooser.SharedJFileChooserType;
import org.pdfsam.guiclient.commons.models.SimplePdfSelectionTableModel;
import org.pdfsam.guiclient.commons.panels.JPdfSelectionPanel;
import org.pdfsam.guiclient.configuration.Configuration;
import org.pdfsam.guiclient.dto.PdfSelectionTableItem;
import org.pdfsam.guiclient.dto.StringItem;
import org.pdfsam.guiclient.exceptions.LoadJobException;
import org.pdfsam.guiclient.exceptions.SaveJobException;
import org.pdfsam.guiclient.gui.components.JHelpLabel;
import org.pdfsam.guiclient.plugins.interfaces.AbstractPlugablePanel;
import org.pdfsam.i18n.GettextResource;
import org.pdfsam.plugin.coverfooter.listeners.RunButtonActionListener;

/**
 * Plugable JPanel provides a GUI for merge functions.
 * 
 * @author Andrea Vacondio
 * @see javax.swing.JPanel
 */
public class CoverFooterMainGUI extends AbstractPlugablePanel {

    private static final long serialVersionUID = -992513717368544272L;

    private static final Logger log = Logger.getLogger(CoverFooterMainGUI.class.getPackage().getName());

    private JTextField destinationTextField = CommonComponentsFactory.getInstance().createTextField(
            CommonComponentsFactory.DESTINATION_TEXT_FIELD_TYPE);;
    private SpringLayout layoutDestinationPanel;
    private JPanel destinationPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private SpringLayout layoutOptionPanel;
    private SpringLayout centralPanelLayout;
    private JPanel optionPanel = new JPanel();
    private JHelpLabel mergeTypeHelpLabel;
    private JHelpLabel destinationHelpLabel;
    private Configuration config;
    private JCheckBox mergeTypeCheck = new JCheckBox();
    private JPdfVersionCombo versionCombo = new JPdfVersionCombo();
    private JPdfSelectionPanel selectionPanel = new JPdfSelectionPanel(
            JPdfSelectionPanel.UNLIMTED_SELECTABLE_FILE_NUMBER, SimplePdfSelectionTableModel.MAX_COLUMNS_NUMBER, true,
            false);
    private JPdfSelectionPanel coverSelectionPanel = new JPdfSelectionPanel(JPdfSelectionPanel.SINGLE_SELECTABLE_FILE,
            SimplePdfSelectionTableModel.MAX_COLUMNS_NUMBER);
    private JPdfSelectionPanel footerSelectionPanel = new JPdfSelectionPanel(JPdfSelectionPanel.SINGLE_SELECTABLE_FILE,
            SimplePdfSelectionTableModel.MAX_COLUMNS_NUMBER);

    // button
    private final JButton browseDestButton = CommonComponentsFactory.getInstance().createButton(
            CommonComponentsFactory.BROWSE_BUTTON_TYPE);
    private final JButton runButton = CommonComponentsFactory.getInstance().createButton(
            CommonComponentsFactory.RUN_BUTTON_TYPE);

    // checks
    private final JCheckBox overwriteCheckbox = CommonComponentsFactory.getInstance().createCheckBox(
            CommonComponentsFactory.OVERWRITE_CHECKBOX_TYPE);
    private final JCheckBox outputCompressedCheck = CommonComponentsFactory.getInstance().createCheckBox(
            CommonComponentsFactory.COMPRESS_CHECKBOX_TYPE);

    // keylisteners
    private final EnterDoClickListener runEnterKeyListener = new EnterDoClickListener(runButton);
    private final EnterDoClickListener browseEnterKeyListener = new EnterDoClickListener(browseDestButton);

    // focus policy
    private final CoverFooterFocusPolicy coverFooterFocusPolicy = new CoverFooterFocusPolicy();
    private final JLabel outputVersionLabel = CommonComponentsFactory.getInstance().createLabel(
            CommonComponentsFactory.PDF_VERSION_LABEL);

    private static final String PLUGIN_AUTHOR = "Andrea Vacondio";
    private static final String PLUGIN_VERSION = "0.3.4e";
    public static final String ALL_STRING = "All";

    /**
     * Constructor
     */
    public CoverFooterMainGUI() {
        super();
        initialize();

    }

    /**
     * Panel initialization
     */
    private void initialize() {
        config = Configuration.getInstance();
        setPanelIcon("/images/cover_footer.png");
        setPreferredSize(new Dimension(500, 760));

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 5;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints topConst = new GridBagConstraints();
        topConst.fill = GridBagConstraints.HORIZONTAL;
        topConst.ipady = 5;
        topConst.weightx = 1.0;
        topConst.weighty = 0.0;
        topConst.gridwidth = 3;
        topConst.gridheight = 1;
        topConst.gridx = 0;
        topConst.gridy = 0;

        coverSelectionPanel.setBorder(BorderFactory.createTitledBorder(GettextResource.gettext(config
                .getI18nResourceBundle(), "Frontpage pdf file")));
        coverSelectionPanel.addPopupMenuAction(new SetOutputPathSelectionTableAction(selectionPanel,
                destinationTextField, null));

        topPanel.add(coverSelectionPanel, topConst);

        footerSelectionPanel.setBorder(BorderFactory.createTitledBorder(GettextResource.gettext(config
                .getI18nResourceBundle(), "Addendum pdf file")));
        footerSelectionPanel.addPopupMenuAction(new SetOutputPathSelectionTableAction(selectionPanel,
                destinationTextField, null));
        topConst.gridy = 1;
        topPanel.add(footerSelectionPanel, topConst);

        topConst.fill = GridBagConstraints.BOTH;
        topConst.weightx = 1.0;
        topConst.weighty = 1.0;
        topConst.gridwidth = 3;
        topConst.gridheight = 1;
        topConst.gridy = 2;
        topConst.insets = new Insets(10, 0, 5, 0);
        topPanel.add(selectionPanel, topConst);
        add(topPanel, c);

        selectionPanel.addPopupMenuAction(new SetOutputPathSelectionTableAction(selectionPanel, destinationTextField,
                null));

        centralPanelLayout = new SpringLayout();
        centralPanel.setLayout(centralPanelLayout);
        centralPanel.setMinimumSize(new Dimension(100, 220));
        // OPTION_PANEL
        layoutOptionPanel = new SpringLayout();
        optionPanel.setLayout(layoutOptionPanel);
        optionPanel.setBorder(BorderFactory.createTitledBorder(GettextResource.gettext(config.getI18nResourceBundle(),
                "Merge options")));
        // END_OPTION_PANEL
        mergeTypeCheck.setText(GettextResource.gettext(config.getI18nResourceBundle(), "PDF documents contain forms"));
        mergeTypeCheck.setSelected(false);
        optionPanel.add(mergeTypeCheck);

        String helpText = "<html><body><b>"
                + GettextResource.gettext(config.getI18nResourceBundle(), "Merge type")
                + "</b><ul>"
                + "<li><b>"
                + GettextResource.gettext(config.getI18nResourceBundle(), "Unchecked")
                + ":</b> "
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Use this merge type for standard pdf documents")
                + ".</li>"
                + "<li><b>"
                + GettextResource.gettext(config.getI18nResourceBundle(), "Checked")
                + ":</b> "
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Use this merge type for pdf documents containing forms")
                + "."
                + "<br><b>"
                + GettextResource.gettext(config.getI18nResourceBundle(), "Note")
                + ":</b> "
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Setting this option the documents will be completely loaded in memory") + ".</li>"
                + "</ul></body></html>";
        mergeTypeHelpLabel = new JHelpLabel(helpText, true);
        optionPanel.add(mergeTypeHelpLabel);
        // DESTINATION_PANEL
        layoutDestinationPanel = new SpringLayout();
        destinationPanel.setLayout(layoutDestinationPanel);
        destinationPanel.setBorder(BorderFactory.createTitledBorder(GettextResource.gettext(config
                .getI18nResourceBundle(), "Destination folder")));
        centralPanel.add(optionPanel);
        centralPanel.add(destinationPanel);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 5;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add(centralPanel, c);
        // END_DESTINATION_PANEL
        destinationPanel.add(destinationTextField);

        // BROWSE_BUTTON
        browseDestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = SharedJFileChooser.getInstance(SharedJFileChooserType.NO_FILTER,
                        JFileChooser.DIRECTORIES_ONLY, destinationTextField.getText());
                if (fileChooser.showOpenDialog(browseDestButton.getParent()) == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = fileChooser.getSelectedFile();
                    if (chosenFile != null) {
                        destinationTextField.setText(chosenFile.getAbsolutePath());
                    }
                }
            }
        });
        destinationPanel.add(browseDestButton);
        // END_BROWSE_BUTTON
        // CHECK_BOX
        overwriteCheckbox.setSelected(true);
        destinationPanel.add(overwriteCheckbox);

        outputCompressedCheck.addItemListener(new CompressCheckBoxItemListener(versionCombo));
        outputCompressedCheck.setSelected(true);
        destinationPanel.add(outputCompressedCheck);

        destinationPanel.add(versionCombo);
        destinationPanel.add(outputVersionLabel);

        // END_CHECK_BOX
        // HELP_LABEL_DESTINATION
        String helpTextDest = "<html><body><b>"
                + GettextResource.gettext(config.getI18nResourceBundle(), "Destination output file")
                + "</b>"
                + "<p>"
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Browse or enter the full path to the destination output file.")
                + "</p>"
                + "<p>"
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Check the box if you want to overwrite the output file if it already exists.")
                + "</p>"
                + "<p>"
                + GettextResource.gettext(config.getI18nResourceBundle(),
                        "Check the box if you want compressed output files.") + " "
                + GettextResource.gettext(config.getI18nResourceBundle(), "PDF version 1.5 or above.") + "</p>"
                + "</body></html>";
        destinationHelpLabel = new JHelpLabel(helpTextDest, true);
        destinationPanel.add(destinationHelpLabel);
        // END_HELP_LABEL_DESTINATION
        // RUN_BUTTON
        runButton.addActionListener(new RunButtonActionListener(this));
        runButton.setToolTipText(GettextResource.gettext(config.getI18nResourceBundle(), "Execute pdf merge"));
        runButton.setSize(new Dimension(88, 25));

        c.fill = GridBagConstraints.NONE;
        c.ipadx = 5;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        add(runButton, c);
        // END_RUN_BUTTON
        // KEY_LISTENER
        runButton.addKeyListener(runEnterKeyListener);
        browseDestButton.addKeyListener(browseEnterKeyListener);

        destinationTextField.addKeyListener(runEnterKeyListener);

        // LAYOUT
        setLayout();

        // END_LAYOUT
    }

    /**
     * Set plugin layout for each component
     * 
     */
    private void setLayout() {
        centralPanelLayout.putConstraint(SpringLayout.SOUTH, optionPanel, 60, SpringLayout.NORTH, optionPanel);
        centralPanelLayout.putConstraint(SpringLayout.EAST, optionPanel, 0, SpringLayout.EAST, centralPanel);
        centralPanelLayout.putConstraint(SpringLayout.NORTH, optionPanel, 5, SpringLayout.NORTH, centralPanel);
        centralPanelLayout.putConstraint(SpringLayout.WEST, optionPanel, 0, SpringLayout.WEST, centralPanel);

        centralPanelLayout.putConstraint(SpringLayout.SOUTH, destinationPanel, 155, SpringLayout.NORTH,
                destinationPanel);
        centralPanelLayout.putConstraint(SpringLayout.EAST, destinationPanel, 0, SpringLayout.EAST, optionPanel);
        centralPanelLayout.putConstraint(SpringLayout.NORTH, destinationPanel, 5, SpringLayout.SOUTH, optionPanel);
        centralPanelLayout.putConstraint(SpringLayout.WEST, destinationPanel, 0, SpringLayout.WEST, optionPanel);

        layoutOptionPanel.putConstraint(SpringLayout.SOUTH, mergeTypeCheck, 30, SpringLayout.NORTH, optionPanel);
        layoutOptionPanel.putConstraint(SpringLayout.EAST, mergeTypeCheck, -40, SpringLayout.EAST, optionPanel);
        layoutOptionPanel.putConstraint(SpringLayout.NORTH, mergeTypeCheck, 5, SpringLayout.NORTH, optionPanel);
        layoutOptionPanel.putConstraint(SpringLayout.WEST, mergeTypeCheck, 5, SpringLayout.WEST, optionPanel);

        layoutOptionPanel.putConstraint(SpringLayout.SOUTH, mergeTypeHelpLabel, -1, SpringLayout.SOUTH, optionPanel);
        layoutOptionPanel.putConstraint(SpringLayout.EAST, mergeTypeHelpLabel, -1, SpringLayout.EAST, optionPanel);

        layoutDestinationPanel.putConstraint(SpringLayout.EAST, destinationTextField, -105, SpringLayout.EAST,
                destinationPanel);
        layoutDestinationPanel.putConstraint(SpringLayout.NORTH, destinationTextField, 10, SpringLayout.NORTH,
                destinationPanel);
        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, destinationTextField, 30, SpringLayout.NORTH,
                destinationPanel);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, destinationTextField, 5, SpringLayout.WEST,
                destinationPanel);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, overwriteCheckbox, 17, SpringLayout.NORTH,
                overwriteCheckbox);
        layoutDestinationPanel.putConstraint(SpringLayout.NORTH, overwriteCheckbox, 5, SpringLayout.SOUTH,
                destinationTextField);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, overwriteCheckbox, 0, SpringLayout.WEST,
                destinationTextField);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, outputCompressedCheck, 17, SpringLayout.NORTH,
                outputCompressedCheck);
        layoutDestinationPanel.putConstraint(SpringLayout.NORTH, outputCompressedCheck, 5, SpringLayout.SOUTH,
                overwriteCheckbox);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, outputCompressedCheck, 0, SpringLayout.WEST,
                destinationTextField);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, outputVersionLabel, 17, SpringLayout.NORTH,
                outputVersionLabel);
        layoutDestinationPanel.putConstraint(SpringLayout.NORTH, outputVersionLabel, 8, SpringLayout.SOUTH,
                outputCompressedCheck);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, outputVersionLabel, 0, SpringLayout.WEST,
                destinationTextField);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, versionCombo, 0, SpringLayout.SOUTH,
                outputVersionLabel);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, versionCombo, 2, SpringLayout.EAST, outputVersionLabel);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, browseDestButton, 25, SpringLayout.NORTH,
                browseDestButton);
        layoutDestinationPanel.putConstraint(SpringLayout.EAST, browseDestButton, -5, SpringLayout.EAST,
                destinationPanel);
        layoutDestinationPanel.putConstraint(SpringLayout.NORTH, browseDestButton, 0, SpringLayout.NORTH,
                destinationTextField);
        layoutDestinationPanel.putConstraint(SpringLayout.WEST, browseDestButton, -93, SpringLayout.EAST,
                destinationPanel);

        layoutDestinationPanel.putConstraint(SpringLayout.SOUTH, destinationHelpLabel, -1, SpringLayout.SOUTH,
                destinationPanel);
        layoutDestinationPanel.putConstraint(SpringLayout.EAST, destinationHelpLabel, -1, SpringLayout.EAST,
                destinationPanel);

    }

    /**
     * @return the Plugin author
     */
    public String getPluginAuthor() {
        return PLUGIN_AUTHOR;
    }

    /**
     * @return the Plugin name
     */
    public String getPluginName() {
        return GettextResource.gettext(config.getI18nResourceBundle(), "Frontpage and Addendum");
    }

    /**
     * @return the Plugin version
     */
    public String getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * @return the FocusTraversalPolicy associated with the plugin
     */
    public FocusTraversalPolicy getFocusPolicy() {
        return (FocusTraversalPolicy) coverFooterFocusPolicy;

    }

    public Node getJobNode(Node arg0, boolean savePasswords) throws SaveJobException {
        try {
            if (arg0 != null) {
                Element coverSource = ((Element) arg0).addElement("cover");
                PdfSelectionTableItem[] coverItems = coverSelectionPanel.getTableRows();
                if (coverItems != null && coverItems.length > 0) {
                    coverSource.addAttribute("value", coverItems[0].getInputFile().getAbsolutePath());
                    coverSource.addAttribute("pageselection",
                            (coverItems[0].getPageSelection() != null) ? coverItems[0].getPageSelection() : ALL_STRING);
                    if (savePasswords) {
                        coverSource.addAttribute("password", coverItems[0].getPassword());
                    }
                }

                Element footerSource = ((Element) arg0).addElement("footer");
                PdfSelectionTableItem[] footerItems = coverSelectionPanel.getTableRows();
                if (coverItems != null && coverItems.length > 0) {
                    footerSource.addAttribute("value", footerItems[0].getInputFile().getAbsolutePath());
                    footerSource.addAttribute("pageselection",
                            (footerItems[0].getPageSelection() != null) ? footerItems[0].getPageSelection()
                                    : ALL_STRING);
                    if (savePasswords) {
                        footerSource.addAttribute("password", footerItems[0].getPassword());
                    }
                }

                Element filelist = ((Element) arg0).addElement("filelist");
                PdfSelectionTableItem[] items = selectionPanel.getTableRows();
                for (int i = 0; i < items.length; i++) {
                    Element fileNode = ((Element) filelist).addElement("file");
                    fileNode.addAttribute("name", items[i].getInputFile().getAbsolutePath());
                    fileNode.addAttribute("pageselection", (items[i].getPageSelection() != null) ? items[i]
                            .getPageSelection() : ALL_STRING);
                    if (savePasswords) {
                        fileNode.addAttribute("password", items[i].getPassword());
                    }
                }

                Element fileDestination = ((Element) arg0).addElement("destination");
                fileDestination.addAttribute("value", destinationTextField.getText());

                Element fileOverwrite = ((Element) arg0).addElement("overwrite");
                fileOverwrite.addAttribute("value", overwriteCheckbox.isSelected() ? TRUE : FALSE);

                Element mergeType = ((Element) arg0).addElement("merge_type");
                mergeType.addAttribute("value", mergeTypeCheck.isSelected() ? TRUE : FALSE);

                Element fileCompress = ((Element) arg0).addElement("compressed");
                fileCompress.addAttribute("value", outputCompressedCheck.isSelected() ? TRUE : FALSE);

                Element pdfVersion = ((Element) arg0).addElement("pdfversion");
                pdfVersion.addAttribute("value", ((StringItem) versionCombo.getSelectedItem()).getId());
            }
            return arg0;
        } catch (Exception ex) {
            throw new SaveJobException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadJobNode(Node arg0) throws LoadJobException {
        try {
            Node coverSource = (Node) arg0.selectSingleNode("cover/@value");
            if (coverSource != null && coverSource.getText().length() > 0) {
                Node coverPageSelection = (Node) arg0.selectSingleNode("cover/@pageselection");
                Node coverFilePwd = (Node) arg0.selectSingleNode("cover/@password");
                coverSelectionPanel.getLoader().addFile(new File(coverSource.getText()),
                        (coverFilePwd != null) ? coverFilePwd.getText() : null,
                        (coverPageSelection != null) ? coverPageSelection.getText() : null);
            }

            footerSelectionPanel.getClearButton().doClick();
            Node footerSource = (Node) arg0.selectSingleNode("cover/@value");
            if (footerSource != null && footerSource.getText().length() > 0) {
                Node footerPageSelection = (Node) arg0.selectSingleNode("cover/@pageselection");
                Node footerFilePwd = (Node) arg0.selectSingleNode("cover/@password");
                footerSelectionPanel.getLoader().addFile(new File(footerSource.getText()),
                        (footerFilePwd != null) ? footerFilePwd.getText() : null,
                        (footerPageSelection != null) ? footerPageSelection.getText() : null);
            }

            Node fileDestination = (Node) arg0.selectSingleNode("destination/@value");
            if (fileDestination != null) {
                destinationTextField.setText(fileDestination.getText());
            }

            Node fileOverwrite = (Node) arg0.selectSingleNode("overwrite/@value");
            if (fileOverwrite != null) {
                overwriteCheckbox.setSelected(TRUE.equals(fileOverwrite.getText()));
            }

            Node mergeType = (Node) arg0.selectSingleNode("merge_type/@value");
            if (mergeType != null) {
                mergeTypeCheck.setSelected(TRUE.equals(mergeType.getText()));
            }

            selectionPanel.getClearButton().doClick();
            List fileList = arg0.selectNodes("filelist/file");
            for (int i = 0; fileList != null && i < fileList.size(); i++) {
                Node fileNode = (Node) fileList.get(i);
                if (fileNode != null) {
                    Node fileName = (Node) fileNode.selectSingleNode("@name");
                    if (fileName != null && fileName.getText().length() > 0) {
                        Node pageSelection = (Node) fileNode.selectSingleNode("@pageselection");
                        Node filePwd = (Node) fileNode.selectSingleNode("@password");
                        selectionPanel.getLoader().addFile(new File(fileName.getText()),
                                (filePwd != null) ? filePwd.getText() : null,
                                (pageSelection != null) ? pageSelection.getText() : null);
                    }
                }
            }

            Node fileCompressed = (Node) arg0.selectSingleNode("compressed/@value");
            if (fileCompressed != null && TRUE.equals(fileCompressed.getText())) {
                outputCompressedCheck.doClick();
            }

            Node pdfVersion = (Node) arg0.selectSingleNode("pdfversion/@value");
            if (pdfVersion != null) {
                for (int i = 0; i < versionCombo.getItemCount(); i++) {
                    if (((StringItem) versionCombo.getItemAt(i)).getId().equals(pdfVersion.getText())) {
                        versionCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
            log.info(GettextResource.gettext(config.getI18nResourceBundle(), "Cover And Footer section loaded."));
        } catch (Exception ex) {
            log.error(GettextResource.gettext(config.getI18nResourceBundle(), "Error: "), ex);
        }
    }

    /**
     * 
     * @author Andrea Vacondio Focus policy for merge panel
     * 
     */
    public class CoverFooterFocusPolicy extends FocusTraversalPolicy {
        public CoverFooterFocusPolicy() {
            super();
        }

        public Component getComponentAfter(Container CycleRootComp, Component aComponent) {
            if (aComponent.equals(coverSelectionPanel.getAddFileButton())) {
                return coverSelectionPanel.getClearButton();
            } else if (aComponent.equals(coverSelectionPanel.getClearButton())) {
                return footerSelectionPanel.getAddFileButton();
            } else if (aComponent.equals(footerSelectionPanel.getAddFileButton())) {
                return footerSelectionPanel.getClearButton();
            } else if (aComponent.equals(footerSelectionPanel.getClearButton())) {
                return selectionPanel.getAddFileButton();
            } else if (aComponent.equals(selectionPanel.getAddFileButton())) {
                return selectionPanel.getRemoveFileButton();
            } else if (aComponent.equals(selectionPanel.getRemoveFileButton())) {
                return selectionPanel.getClearButton();
            } else if (aComponent.equals(selectionPanel.getClearButton())) {
                return mergeTypeCheck;
            } else if (aComponent.equals(mergeTypeCheck)) {
                return destinationTextField;
            } else if (aComponent.equals(destinationTextField)) {
                return browseDestButton;
            } else if (aComponent.equals(browseDestButton)) {
                return overwriteCheckbox;
            } else if (aComponent.equals(overwriteCheckbox)) {
                return outputCompressedCheck;
            } else if (aComponent.equals(outputCompressedCheck)) {
                return versionCombo;
            } else if (aComponent.equals(versionCombo)) {
                return runButton;
            } else if (aComponent.equals(runButton)) {
                return coverSelectionPanel.getAddFileButton();
            }
            return coverSelectionPanel.getAddFileButton();
        }

        public Component getComponentBefore(Container CycleRootComp, Component aComponent) {

            if (aComponent.equals(runButton)) {
                return versionCombo;
            } else if (aComponent.equals(versionCombo)) {
                return outputCompressedCheck;
            } else if (aComponent.equals(outputCompressedCheck)) {
                return overwriteCheckbox;
            } else if (aComponent.equals(overwriteCheckbox)) {
                return browseDestButton;
            } else if (aComponent.equals(browseDestButton)) {
                return destinationTextField;
            } else if (aComponent.equals(destinationTextField)) {
                return mergeTypeCheck;
            } else if (aComponent.equals(mergeTypeCheck)) {
                return selectionPanel.getClearButton();
            } else if (aComponent.equals(selectionPanel.getClearButton())) {
                return selectionPanel.getRemoveFileButton();
            } else if (aComponent.equals(selectionPanel.getRemoveFileButton())) {
                return selectionPanel.getAddFileButton();
            } else if (aComponent.equals(selectionPanel.getAddFileButton())) {
                return footerSelectionPanel.getClearButton();
            } else if (aComponent.equals(footerSelectionPanel.getClearButton())) {
                return footerSelectionPanel.getAddFileButton();
            } else if (aComponent.equals(footerSelectionPanel.getAddFileButton())) {
                return coverSelectionPanel.getClearButton();
            } else if (aComponent.equals(coverSelectionPanel.getClearButton())) {
                return coverSelectionPanel.getAddFileButton();
            } else if (aComponent.equals(coverSelectionPanel.getAddFileButton())) {
                return runButton;
            }
            return coverSelectionPanel.getAddFileButton();
        }

        public Component getDefaultComponent(Container CycleRootComp) {
            return coverSelectionPanel.getAddFileButton();
        }

        public Component getLastComponent(Container CycleRootComp) {
            return runButton;
        }

        public Component getFirstComponent(Container CycleRootComp) {
            return coverSelectionPanel.getAddFileButton();
        }
    }

    public void resetPanel() {
        selectionPanel.clearSelectionTable();
        coverSelectionPanel.clearSelectionTable();
        footerSelectionPanel.clearSelectionTable();
        destinationTextField.setText("");
        versionCombo.resetComponent();
        outputCompressedCheck.setSelected(false);
        overwriteCheckbox.setSelected(false);
        mergeTypeCheck.setSelected(false);
    }

    /**
     * @return the destinationTextField
     */
    public JTextField getDestinationTextField() {
        return destinationTextField;
    }

    /**
     * @return the mergeTypeCheck
     */
    public JCheckBox getMergeTypeCheck() {
        return mergeTypeCheck;
    }

    /**
     * @return the versionCombo
     */
    public JPdfVersionCombo getVersionCombo() {
        return versionCombo;
    }

    /**
     * @return the selectionPanel
     */
    public JPdfSelectionPanel getSelectionPanel() {
        return selectionPanel;
    }

    /**
     * @return the coverSelectionPanel
     */
    public JPdfSelectionPanel getCoverSelectionPanel() {
        return coverSelectionPanel;
    }

    /**
     * @return the footerSelectionPanel
     */
    public JPdfSelectionPanel getFooterSelectionPanel() {
        return footerSelectionPanel;
    }

    /**
     * @return the overwriteCheckbox
     */
    public JCheckBox getOverwriteCheckbox() {
        return overwriteCheckbox;
    }

    /**
     * @return the outputCompressedCheck
     */
    public JCheckBox getOutputCompressedCheck() {
        return outputCompressedCheck;
    }

}
