/*
 * @(#)AbstractNote.java
 * Created on 2005-7-14
 * 
 * Copyright AllenStudio
 */
package com.allenstudio.ir.core.plugins;

import javax.swing.*;

/**
 * Abstract definition for a note.
 * This class provides some necessary
 * methods that I suppose a note must
 * have in this program.<br>
 * Data stored in a note is a background
 * class implementing the <code>NoteModel</code>
 * interface. This is for the MVC design pattern.<br>
 * Also for this pattern purpose, the concrete
 * implementation for painting this component
 * is defined in {@link BasicNoteUI}.
 * 
 * @author Allen Chue
 * @see BasicNoteUI
 */
public abstract class AbstractNote extends JComponent {
    
    private Icon thumbnailIcon;
    
    private String feature = "C:\\boot.ini";
    
    public Icon getIcon() {
        return this.thumbnailIcon;
    }
    
    /**
     * Returns a descriptive <code>String</code> as the
     * title of this note.
     * @return the title of the note
     */
    public abstract String getTitle();
    
    /**
     * Returns a description about the note. These description
     * words are typically the first paragragh of the contents
     * of the note.
     * @return the description of the note
     */
    public abstract String getDescription();

    /**
     * Returns some other info of the note. Different
     * note types may have different words. Here an
     * empty String or <code>null</code> value is allowed.
     * @return other info of the note
     */
    public abstract String getOtherInfoText();

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
