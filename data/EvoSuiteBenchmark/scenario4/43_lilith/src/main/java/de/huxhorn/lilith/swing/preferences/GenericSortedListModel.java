package de.huxhorn.lilith.swing.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class GenericSortedListModel<T extends Comparable> extends AbstractListModel {

    private final List<T> data;

    private final Comparator<T> comparator;

    public GenericSortedListModel() {
    }

    public GenericSortedListModel(Comparator<T> comparator) {
    }

    public void setData(List<T> data);

    public List<T> getData();

    public void add(T element);

    public void remove(T element);

    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    public int getSize();

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    public T getElementAt(int index);
}
