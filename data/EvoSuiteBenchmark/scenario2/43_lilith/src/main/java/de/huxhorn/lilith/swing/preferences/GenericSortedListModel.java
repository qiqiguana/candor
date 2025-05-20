package de.huxhorn.lilith.swing.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class GenericSortedListModel<T extends Comparable> extends AbstractListModel {

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    public T getElementAt(int index) {
        return data.get(index);
    }
}
