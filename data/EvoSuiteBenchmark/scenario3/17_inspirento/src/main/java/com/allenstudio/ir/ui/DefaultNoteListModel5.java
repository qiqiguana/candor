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
     * Searches for the first occurrence of <code>elem</code>, beginning the
     * search at <code>index</code>.
     *
     * @param elem an desired component
     * @param index the index from which to begin searching
     * @return the index where the first occurrence of <code>elem</code> is
     *         found after <code>index</code>; returns <code>-1</code> if
     *         the <code>elem</code> is not found in the list
     * @see Vector#indexOf(Object,int)
     */
    public int indexOf(AbstractNote elem, int index);
}
