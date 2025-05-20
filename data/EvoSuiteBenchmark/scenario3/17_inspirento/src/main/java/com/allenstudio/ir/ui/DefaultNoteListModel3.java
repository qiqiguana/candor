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
     * Tests whether the specified object is a component in this list.
     *
     * @param elem an object
     * @return <code>true</code> if the specified object is the same as a
     *         component in this list
     * @see Vector#contains(Object)
     */
    public boolean contains(AbstractNote elem);
}
