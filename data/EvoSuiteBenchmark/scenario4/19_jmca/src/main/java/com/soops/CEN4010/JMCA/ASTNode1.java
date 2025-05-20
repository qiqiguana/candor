package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

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
    public int compareTo(Object node);

    java.util.ArrayList<ASTNode> list = new java.util.ArrayList<ASTNode>();

    /**
     * parent of the node
     */
    ASTNode parent = null;

    /**
     * add child to node
     * @param node ASTNode
     */
    public void addChild(ASTNode node);

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
    }

    /**
     * construct a node and set data types and parent
     * @param type String
     * @param identity String
     * @param p ASTNode
     */
    public ASTNode(String type, String identity, ASTNode p) {
    }

    /**
     * get the parent node
     * @return ASTNode
     */
    public ASTNode getParent();

    /**
     * set the data element type
     * @param t String
     */
    public void setType(String t);

    /**
     * set the data type identity
     * @param id String
     */
    public void setIdentity(String id);

    /**
     * get the data element type
     * @return String
     */
    public String getType();

    /**
     * get the data element identity
     * @return String
     */
    public String getIdentity();

    /**
     * concat type and identity
     * @return String
     */
    public String toString();

    /**
     * display this node and all its children recursively
     * @param wtr Writer
     */
    public void display(Writer wtr);

    /**
     *  reads the node in from a file
     * @param filename String
     * @return ASTNode
     */
    public static ASTNode getTree(String filename);

    /**
     * save the node and its children to disk
     * @param filename String
     */
    public void dump(String filename);

    /**
     * add this node and recursively all its children to a swing tree structure <br>
     * used to construct a JTree with the ASTree
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode createTree();
}
