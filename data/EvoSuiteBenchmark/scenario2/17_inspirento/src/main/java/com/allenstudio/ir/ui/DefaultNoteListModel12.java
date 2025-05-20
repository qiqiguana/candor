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
     * Returns an array containing all of the elements in this list in the
     * correct order.
     *
     * @return an array containing the elements of the list
     * @see Vector#toArray()
     */
    public AbstractNote[] toArray() {
        AbstractNote[] an = new AbstractNote[delegate.size()];
        delegate.copyInto(an);
        return an;
    }
}
