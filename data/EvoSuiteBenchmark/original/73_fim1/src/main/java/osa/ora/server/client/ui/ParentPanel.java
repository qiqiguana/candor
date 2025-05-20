/*
 * ParentPanel.java
 *
 * Created on November 4, 2009, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.ui;

import java.io.File;

/**
 *
 * @author ooransa
 */
public abstract class ParentPanel extends javax.swing.JPanel {
    
    /** Creates a new instance of ParentPanel */
    public ParentPanel() {
    }
    public abstract void sendFileInit(File temp);
}
