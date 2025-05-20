/*
 * FileTransferHandler.java
 *
 * Created on November 4, 2009, 10:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package osa.ora.server.client.ui.utils;

import java.awt.datatransfer.*;
import java.io.*;
import javax.swing.*;
import osa.ora.server.client.ui.ParentPanel;

/**
 *
 * @author ooransa
 */
public class FileTransferHandler extends TransferHandler {

    ParentPanel parentPanel;

    /** Creates a new instance of FileTransferHandler */
    public FileTransferHandler(ParentPanel chatPanel) {
        super();
        this.parentPanel = chatPanel;
    }
    public static final DataFlavor[] flavors = {DataFlavor.javaFileListFlavor};

    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY;
    }

    public boolean canImport(JComponent comp, DataFlavor flavor[]) {
        for (int i = 0, n = flavor.length; i < n; i++) {
            for (int j = 0, m = flavors.length; j < m; j++) {
                if (flavor[i].equals(flavors[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean importData(JComponent comp, Transferable t) {
        if (t.isDataFlavorSupported(flavors[0])) {
            try {
                java.util.List list = (java.util.List) t.getTransferData(flavors[0]);
                for (int i = 0; i < list.size(); i++) {
                    if (!((File) list.get(i)).isFile()) {
                        // not a file, do something
                        return false;
                    }
                    // is a file, check for file suffix and see if it's one that you want to support
                    //System.out.println(((File) list.get(i)).toURL());
                    File file = (((File) list.get(i)));
                    parentPanel.sendFileInit(file);
                    //stream the file...
                    return true;
                }
            } catch (UnsupportedFlavorException ignored) {
            } catch (java.io.IOException ignored) {
            }
        }
        return false;
    }
}

