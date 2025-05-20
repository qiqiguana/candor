package fr.pingtimeout.jtail.gui.model;

import fr.pingtimeout.jtail.io.LineReader;
import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.io.File;
import java.util.Observable;

public class OpenFileModel extends Observable {
    private File file;
    private FileIndex.Type indexType;

    public OpenFileModel() {
        JTailLogger.debug("Creating OpenFileModel");
    }

    public void setFile(File file) {
        this.file = file;

        this.setChanged();
        this.notifyObservers(createModelEvent(OpenFileModelEvent.Type.FILE_CHANGED));
    }

    public void setIndexType(FileIndex.Type indexType) {
        this.indexType = indexType;
        
        this.setChanged();
        this.notifyObservers(createModelEvent(OpenFileModelEvent.Type.INDEX_TYPE_CHANGED));
    }

    public File getFile() {
        return file;
    }

    public FileIndex.Type getIndexType() {
        return indexType;
    }

    private OpenFileModelEvent createModelEvent(OpenFileModelEvent.Type type) {
        return new OpenFileModelEvent(this.file, this.indexType, type);
    }
}
