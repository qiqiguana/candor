package fr.pingtimeout.jtail.gui.controller;

import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class SelectIndexTypeListener implements ActionListener{
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    private final OpenFileModel openFileModel;

    public SelectIndexTypeListener(OpenFileModel openFileModel) {
        this.openFileModel = openFileModel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComboBox comboBox = ((JComboBox)actionEvent.getSource());

        final String indexType = comboBox.getSelectedItem().toString();

        JTailLogger.debug("The user selected '{}' as index type", indexType);

        final String memoryBasedIndex = bundle.getString(FileIndex.Type.MEMORY_BASED.getLabelKey());
        final String fileBasedIndex = bundle.getString(FileIndex.Type.FILE_BASED.getLabelKey());

        if(memoryBasedIndex.equals(indexType)) {
            JTailLogger.debug("New index type : {}", FileIndex.Type.MEMORY_BASED);
            this.openFileModel.setIndexType(FileIndex.Type.MEMORY_BASED);
        } else if (fileBasedIndex.equals(indexType)) {
            JTailLogger.debug("New index type : {}", FileIndex.Type.FILE_BASED);
            this.openFileModel.setIndexType(FileIndex.Type.FILE_BASED);
        } else {
            JTailLogger.warn("Unknown index type : " + indexType);
            JTailLogger.warn("=> Using {} instead", FileIndex.Type.MEMORY_BASED);
            this.openFileModel.setIndexType(FileIndex.Type.MEMORY_BASED);
        }
    }
}
