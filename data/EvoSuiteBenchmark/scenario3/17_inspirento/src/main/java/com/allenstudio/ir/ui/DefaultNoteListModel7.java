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
     * Searches backwards for <code>elem</code>, starting from the specified
     * index, and returns an index to it.
     *
     * @param elem the desired component
     * @param index the index to start searching from
     * @return the index of the last occurrence of the <code>elem</code> in
     *         this list at position less than <code>index</code>; returns
     *         <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object,int)
     */
    public int lastIndexOf(AbstractNote elem, int index);
}
