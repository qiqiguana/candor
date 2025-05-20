package fr.pingtimeout.jtail.gui.model;

import fr.pingtimeout.jtail.io.index.FileIndex;

import java.io.File;

public class OpenFileModelEvent {
    public final File file;
    public final FileIndex.Type indexType;
    public final Type eventType;

    public enum Type {
        FILE_CHANGED, INDEX_TYPE_CHANGED
    }

    public OpenFileModelEvent(File file, FileIndex.Type indexType, Type eventType) {
        this.file = file;
        this.indexType = indexType;
        this.eventType = eventType;
    }
}
