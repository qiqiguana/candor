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

    public boolean removeElement(AbstractNote obj) {
        int index = indexOf(obj);
        boolean an = delegate.removeElement(obj);
        if (index >= 0) {
            fireItemRemoved(this, index);
        }
        return an;
    }
}
