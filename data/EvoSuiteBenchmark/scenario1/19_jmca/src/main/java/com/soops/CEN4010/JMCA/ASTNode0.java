package com.soops.CEN4010.JMCA;

import java.util.ArrayList;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode implements Comparable, java.io.Serializable {

    public int compareTo(Object node) {
        if (!type.equals(((ASTNode) node).getType()) || !identity.equals(((ASTNode) node).getIdentity())) {
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
}
