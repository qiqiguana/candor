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
     * Returns the last component of the list. Throws a
     * <code>NoSuchElementException</code> if this vector has no components.
     *
     * @return the last component of the list
     * @see Vector#lastElement()
     */
    public AbstractNote lastElement();
}
