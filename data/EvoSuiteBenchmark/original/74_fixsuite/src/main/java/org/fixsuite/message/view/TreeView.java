/**
 * TreeView.java 10:39:35 PM Apr 22, 2008
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.fixsuite.message.Library;
import org.fixsuite.message.LibrarySuite;
import org.fixsuite.message.info.ComponentInfo;
import org.fixsuite.message.info.CompositeFixInfo;
import org.fixsuite.message.info.DictionaryInfo;
import org.fixsuite.message.info.FieldInfo;
import org.fixsuite.message.info.FixInfo;
import org.fixsuite.message.info.MessageInfo;

/**
 * @author jramoyo
 */
public class TreeView extends JPanel implements ActionListener, KeyListener,
        TreeSelectionListener {

    private static final long serialVersionUID = 1L;

    private JScrollPane mainScrollPane;

    private JTree fixTree;

    private JLabel searchLabel;

    private JTextField searchTextField;

    private JButton searchButton;

    private JButton clearButton;

    private JButton expandButton;

    private JButton collapseButton;

    private Library library;

    /**
     * Creates a new TreeView
     */
    public TreeView(Library library) {
        super();
        this.library = library;
        initGUI();
    }

    private void initGUI() {
        // Set the layout
        setLayout(new BorderLayout());

        // Setup the main scroll pane
        mainScrollPane = new JScrollPane();
        mainScrollPane.setPreferredSize(new Dimension(300, 400));
        add(mainScrollPane, BorderLayout.CENTER);

        // FIX Tree
        fixTree = new JTree(loadFIXTree());
        fixTree.setCellRenderer(new FixInfoCellRenderer());
        fixTree.addTreeSelectionListener(this);
        mainScrollPane.getViewport().add(fixTree);

        // Setup the top panel
        searchLabel = new JLabel("Search ");
        searchTextField = new JTextField(20);
        searchTextField.setToolTipText("Search for Item (regex)");
        searchTextField.addKeyListener(this);
        searchButton = new JButton("Go");
        searchButton.addActionListener(this);
        searchButton.setToolTipText("Start Search");
        clearButton = new JButton(new ImageIcon(".\\resources\\clear.gif"));
        clearButton.addActionListener(this);
        clearButton.setToolTipText("Clear the Search Results");
        expandButton = new JButton(new ImageIcon(".\\resources\\expand.gif"));
        expandButton.addActionListener(this);
        expandButton.setToolTipText("Expand the Tree");
        collapseButton = new JButton(
                new ImageIcon(".\\resources\\collapse.gif"));
        collapseButton.addActionListener(this);
        collapseButton.setToolTipText("Collapse the Tree");
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(clearButton);
        toolbar.addSeparator();
        toolbar.add(searchLabel);
        toolbar.add(searchTextField);
        toolbar.add(searchButton);
        toolbar.addSeparator();
        toolbar.add(expandButton);
        toolbar.add(collapseButton);
        add(toolbar, BorderLayout.NORTH);
    }

    private FixInfoTreeNode loadFIXTree() {
        FixInfoTreeNode root = new FixInfoTreeNode("Dictionaries");
        FixInfoTreeNode node;
        for (DictionaryInfo dictionary : library.getDictionaries()) {
            node = new FixInfoTreeNode(dictionary.getVersion());
            loadMessages(node, dictionary);
            root.add(node);
        }

        return root;
    }

    private void loadMessages(FixInfoTreeNode versionNode,
            DictionaryInfo dictionary) {
        FixInfoTreeNode node;
        for (MessageInfo message : dictionary.getMessages()) {
            node = new FixInfoTreeNode(message);
            loadItems(node, message, 0);
            versionNode.add(node);
        }
    }

    private void loadItems(FixInfoTreeNode branchNode,
            CompositeFixInfo leafNode, int level) {
        level++;
        FixInfoTreeNode node;
        for (FixInfo info : leafNode.getItems()) {
            node = new FixInfoTreeNode(info);
            if (info instanceof CompositeFixInfo) {
                if (level < 5) {
                    loadItems(node, (CompositeFixInfo) info, level);
                }
            }
            branchNode.add(node);
        }
    }

    private void expandAll() {
        synchronized (fixTree) {
            int row = 0;
            while (row < fixTree.getRowCount()) {
                fixTree.expandRow(row);
                row++;
            }
        }
    }

    private void collapseAll() {
        synchronized (fixTree) {
            int row = fixTree.getRowCount() - 1;
            while (row >= 0) {
                fixTree.collapseRow(row);
                row--;
            }
        }
    }

    private void search(FixInfoTreeNode node, String searchString) {
        int count = 0;

        if (node.getUserObject() instanceof FixInfo) {
            FixInfo info = (FixInfo) node.getUserObject();
            if (info instanceof MessageInfo) {
                if (((MessageInfo) info).getName().matches(searchString)
                        || ((MessageInfo) info).getMessageType().matches(
                                searchString)) {
                    node.setHighlighted(true);
                    fixTree.expandPath(new TreePath(node.getPath()));
                }
            } else if (info instanceof ComponentInfo) {
                if (((ComponentInfo) info).getName().matches(searchString)) {
                    node.setHighlighted(true);
                    fixTree.expandPath(new TreePath(node.getPath()));
                }
            } else if (info instanceof FieldInfo) {
                String tagNumber = "" + ((FieldInfo) info).getTagNumber();
                if (((FieldInfo) info).getName().matches(searchString)
                        || tagNumber.matches(searchString)) {
                    node.setHighlighted(true);
                    if (node.getChildCount() > 0) {
                        fixTree.expandPath(new TreePath(node.getPath()));
                    } else {
                        fixTree.expandPath(new TreePath(((FixInfoTreeNode) node
                                .getParent()).getPath()));
                    }
                }
            }
        }
        while (count < node.getChildCount()) {
            FixInfoTreeNode childNode = (FixInfoTreeNode) node
                    .getChildAt(count);
            search(childNode, searchString);
            count++;
        }
    }

    private void clear(FixInfoTreeNode node) {
        int count = 0;
        node.setHighlighted(false);
        while (count < node.getChildCount()) {
            FixInfoTreeNode childNode = (FixInfoTreeNode) node
                    .getChildAt(count);
            clear(childNode);
            count++;
        }
    }

    public void valueChanged(TreeSelectionEvent event) {
        FixInfoTreeNode node = (FixInfoTreeNode) fixTree
                .getLastSelectedPathComponent();
        if (node != null) {
            if (node.getUserObject() instanceof FieldInfo) {
                if (((FixInfoTreeNode) node.getParent()).getUserObject() instanceof ComponentInfo) {
                    LibrarySuite.getInstance().getDetailsPanel()
                            .displayDetails(
                                    (FieldInfo) node.getUserObject(),
                                    (ComponentInfo) ((FixInfoTreeNode) node
                                            .getParent()).getUserObject());
                } else {
                    LibrarySuite.getInstance().getDetailsPanel()
                            .displayDetails(
                                    (FieldInfo) node.getUserObject(),
                                    (ComponentInfo) ((FixInfoTreeNode) node
                                            .getPath()[2]).getUserObject());
                }
            } else if (node.getUserObject() instanceof MessageInfo) {
                LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                        (MessageInfo) node.getUserObject(), null);
            } else if (node.getUserObject() instanceof ComponentInfo) {
                LibrarySuite.getInstance().getDetailsPanel().displayDetails(
                        (ComponentInfo) node.getUserObject(), null);
            } else {
                LibrarySuite.getInstance().getDetailsPanel().clearDisplay();
            }
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(collapseButton)) {
            collapseAll();
        } else if (event.getSource().equals(expandButton)) {
            expandAll();
        } else if (event.getSource().equals(searchButton)) {
            clear((FixInfoTreeNode) fixTree.getModel().getRoot());
            collapseAll();
            search((FixInfoTreeNode) fixTree.getModel().getRoot(),
                    searchTextField.getText().trim());
        } else if (event.getSource().equals(clearButton)) {
            clear((FixInfoTreeNode) fixTree.getModel().getRoot());
            collapseAll();
        }

    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            searchButton.doClick();
        }
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }

    private class FixInfoTreeNode extends DefaultMutableTreeNode {

        private static final long serialVersionUID = 1L;

        private boolean isHighlighted;

        private FixInfoTreeNode(Object userObject) {
            super(userObject);
            isHighlighted = false;
        }

        private boolean isHighlighted() {
            return isHighlighted;
        }

        private void setHighlighted(boolean isHighlighted) {
            this.isHighlighted = isHighlighted;
        }

    }

    private class FixInfoCellRenderer extends DefaultTreeCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree,
                    value, sel, expanded, leaf, row, hasFocus);
            if (((FixInfoTreeNode) value).getUserObject() instanceof FieldInfo) {
                FieldInfo field = (FieldInfo) ((FixInfoTreeNode) value)
                        .getUserObject();
                label.setText(field.getName() + " (" + field.getTagNumber()
                        + ")");
            } else if (((FixInfoTreeNode) value).getUserObject() instanceof MessageInfo) {
                MessageInfo message = (MessageInfo) ((FixInfoTreeNode) value)
                        .getUserObject();
                label.setText(message.getName() + " ("
                        + message.getMessageType() + ")");
            } else if (((FixInfoTreeNode) value).getUserObject() instanceof ComponentInfo) {
                ComponentInfo component = (ComponentInfo) ((FixInfoTreeNode) value)
                        .getUserObject();
                label.setText("[" + component.getName() + "]");
            }
            if (((FixInfoTreeNode) value).isHighlighted()) {
                label.setForeground(Color.BLUE);
            }

            return label;
        }

    }

}
