package com.allenstudio.ir.ui;

import com.allenstudio.ir.core.plugins.AbstractNote;
import com.allenstudio.ir.event.NoteListDataEvent;
import com.allenstudio.ir.event.NoteListDataListener;
import java.util.*;

/**
 * Default implementation of <code>NoteListModel</code>
 *
 * @author Allen Chue
 */
public class DefaultNoteListModel implements NoteListModel {

    /**
     * Returns the index of the last occurrence of <code>elem</code>.
     *
     * @param elem the desired component
     * @return the index of the last occurrence of <code>elem</code> in the
     *         list; returns <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object)
     */
    public int lastIndexOf(AbstractNote elem);
}
