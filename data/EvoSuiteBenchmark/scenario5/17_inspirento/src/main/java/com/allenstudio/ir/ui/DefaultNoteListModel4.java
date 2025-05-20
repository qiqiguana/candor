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
     * Searches for the first occurrence of <code>elem</code>.
     *
     * @param elem an object
     * @return the index of the first occurrence of the argument in this list;
     *         returns <code>-1</code> if the object is not found
     * @see Vector#indexOf(Object)
     */
    public int indexOf(AbstractNote elem) {
        return delegate.indexOf(elem);
    }
}
