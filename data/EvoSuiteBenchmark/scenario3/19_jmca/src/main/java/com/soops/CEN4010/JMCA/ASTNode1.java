package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode implements Comparable, java.io.Serializable {

    /**
     *  reads the node in from a file
     *
     * @param filename String
     * @return ASTNode
     */
    public static ASTNode getTree(String filename);
}
