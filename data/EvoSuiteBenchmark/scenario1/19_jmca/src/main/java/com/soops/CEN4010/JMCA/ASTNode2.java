package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode implements Comparable, java.io.Serializable {

    public DefaultMutableTreeNode createTree() {
        javax.swing.tree.DefaultMutableTreeNode node = new DefaultMutableTreeNode(toString());
        int arraySize = list.size();
        for (int i = 0; i < arraySize; ++i) {
            node.add(list.get(i).createTree());
        }
        return node;
    }
}
