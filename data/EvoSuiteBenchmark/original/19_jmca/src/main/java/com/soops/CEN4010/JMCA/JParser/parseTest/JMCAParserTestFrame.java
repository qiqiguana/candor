package com.soops.CEN4010.JMCA.JParser.parseTest;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.Rectangle;

public class JMCAParserTestFrame extends JFrame {
    JPanel contentPane;
    DefaultMutableTreeNode topNode = null;
    JTree jTree1 = null;

    public JMCAParserTestFrame(DefaultMutableTreeNode node, String frameTitle) {
        super(frameTitle);
        topNode = node;
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        setSize(new Dimension(400, 300));
        jTree1 = new JTree(topNode);
        contentPane.add(new JScrollPane(jTree1));
    }
}
