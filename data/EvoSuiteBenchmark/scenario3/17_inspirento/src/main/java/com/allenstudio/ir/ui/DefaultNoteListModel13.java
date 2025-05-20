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
     * Returns the element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * @param index index of element to return
     */
    public AbstractNote get(int index);
}
