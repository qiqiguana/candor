package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode implements Comparable, java.io.Serializable {

    /**
     * add this node and recursively all its children to a swing tree structure <br>
     * used to construct a JTree with the ASTree
     *
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode createTree();
}
