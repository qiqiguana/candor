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
     * Returns the component at the specified index. Throws an
     * <code>ArrayIndexOutOfBoundsException</code> if the index is negative or
     * not less than the size of the list. <blockquote> <b>Note:</b> Although
     * this method is not deprecated, the preferred method to use is
     * <code>get(int)</code>, which implements the <code>List</code>
     * interface defined in the 1.2 Collections framework. </blockquote>
     *
     * @param index an index into this list
     * @return the component at the specified index
     * @see #get(int)
     * @see Vector#elementAt(int)
     */
    public AbstractNote elementAt(int index) {
        return delegate.elementAt(index);
    }
}
