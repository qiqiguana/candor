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
     * Removes the first (lowest-indexed) occurrence of the argument from this
     * list.
     *
     * @param obj the component to be removed
     * @return <code>true</code> if the argument was a component of this list;
     *         <code>false</code> otherwise
     * @see Vector#removeElement(Object)
     */
    public boolean removeElement(AbstractNote obj) {
        int index = indexOf(obj);
        boolean an = delegate.removeElement(obj);
        if (index >= 0) {
            fireItemRemoved(this, index);
        }
        return an;
    }
}
