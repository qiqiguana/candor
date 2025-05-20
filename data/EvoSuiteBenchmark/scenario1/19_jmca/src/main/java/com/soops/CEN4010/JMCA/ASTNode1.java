package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode implements Comparable, java.io.Serializable {

    public static ASTNode getTree(String filename) {
        ObjectInputStream rdr = null;
        ASTNode rootNode = null;
        try {
            FileInputStream flstrm = new FileInputStream(new File(filename));
            rdr = new ObjectInputStream(flstrm);
            rootNode = (ASTNode) rdr.readObject();
            rdr.close();
        } catch (java.io.IOException ie) {
        } finally {
            return rootNode;
        }
    }
}
