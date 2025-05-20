package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * <p>Title: Group 2 Project </p>
 *
 * <p>Class: CEN4010 Fall 2005</p>
 *
 * <p>Description: Data Structure for ASTree Node</p>
 *
 * <p>Instructor Dr. Stoeklin</p>
 *
 * @author Group 2
 */




public class ASTNode implements Comparable, java.io.Serializable {
    /**
     * data of the node
     */
    String type = null;
    String identity = null;

    /**
     * implements Comparable
     * @param node Object
     * @return int values 0 for same and 1 for different
     */
    public int compareTo(Object node) {
        if (!type.equals(((ASTNode) node).getType()) ||
            !identity.equals(((ASTNode) node).getIdentity())) {
            return 1;
        }
        for (int i = 0; i < list.size(); ++i) {
            try {
                if (list.get(i).compareTo(((ASTNode) node).list.get(i)) == 1) {
                    return 1;
                }
            } catch (IndexOutOfBoundsException ne) {
                return 1;
            } catch (NullPointerException np) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * children of the node
     */
    //IC - Added the <Object> tags to get rid of warnings...
    //http://java.sun.com/developer/JDCTechTips/2005/tt0315.html
    // SS - changed <Object> to <ASTNode> and removed the casts

    java.util.ArrayList<ASTNode> list = new java.util.ArrayList<ASTNode>();
    /**
     * parent of the node
     */
    ASTNode parent = null;

    /**
     * add child to node
     * @param node ASTNode
     */
    public void addChild(ASTNode node) {
        list.add(node);
    }

    /**
     * default constructor
     */
    public ASTNode() {
    }

    /**
     * construct a  node and set its parent
     * @param p ASTNode
     */
    public ASTNode(ASTNode p) {
        parent = p;
    }

    /**
     * construct a node and set data types and parent
     * @param type String
     * @param identity String
     * @param p ASTNode
     */
    public ASTNode(String type, String identity, ASTNode p) {
        parent = p;
        setType(type);
        setIdentity(identity);
    }

    /**
     * get the parent node
     * @return ASTNode
     */
    public ASTNode getParent() {
        return parent;
    }

    /**
     * set the data element type
     * @param t String
     */
    public void setType(String t) {
        type = t;
    }

    /**
     * set the data type identity
     * @param id String
     */
    public void setIdentity(String id) {
        identity = id;
    }

    /**
     * get the data element type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * get the data element identity
     * @return String
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * concat type and identity
     * @return String
     */
    public String toString() {
        String returnString = type + " :: " + identity;
        return returnString;
    }

    /**
     * display this node and all its children recursively
     * @param wtr Writer
     */
    public void display(Writer wtr) {
        try {
            wtr.write(toString());
        } catch (IOException ie) {
            System.err.println(ie.getMessage());
        }
        int arraySize = list.size();
        for (int i = 0; i < arraySize; ++i) {
            try {
                wtr.write("\n");
            } catch (IOException ie) {
                System.err.println(ie.getMessage());
            }
             list.get(i).display(wtr);
        }
    }

    /**
     *  reads the node in from a file
     * @param filename String
     * @return ASTNode
     */
    public static ASTNode getTree(String filename) {
        ObjectInputStream rdr = null;
        ASTNode rootNode = null;
        try {
            FileInputStream flstrm = new FileInputStream(new File(filename));
            rdr = new ObjectInputStream(flstrm);
            rootNode = (ASTNode) rdr.readObject();
            rdr.close();
        } catch (java.io.IOException ie) {} finally {
            return rootNode;
        }
    }

    /**
     * save the node and its children to disk
     * @param filename String
     */
    public void dump(String filename) {
        ObjectOutputStream wtr = null;
        try {
            FileOutputStream flstrm = new FileOutputStream(new File(filename));
            wtr = new ObjectOutputStream(flstrm);
            wtr.writeObject(this);
            wtr.close();
        } catch (java.io.IOException ie) {}
    }

    /**
     * add this node and recursively all its children to a swing tree structure <br>
     * used to construct a JTree with the ASTree
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode createTree() {

        javax.swing.tree.DefaultMutableTreeNode node = new
                DefaultMutableTreeNode(toString());

        int arraySize = list.size();
        for (int i = 0; i < arraySize; ++i) {
            node.add( list.get(i).createTree());
        }
        return node;
    }
}
