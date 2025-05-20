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

    private Vector<AbstractNote> delegate = new Vector<AbstractNote>();

    // Uni-listener temporary
    private NoteListDataListener dataListener;

    /**
     * Returns the current capacity of this list.
     *
     * @return the current capacity
     * @see Vector#capacity()
     */
    public int capacity();

    /**
     * Returns the number of components in this list.
     *
     * @return the number of components in this list
     * @see Vector#size()
     */
    public int size();

    /**
     * Tests whether this list has any components.
     *
     * @return <code>true</code> if and only if this list has no components,
     *         that is, its size is zero; <code>false</code> otherwise
     * @see Vector#isEmpty()
     */
    public boolean isEmpty();

    /**
     * Returns an enumeration of the components of this list.
     *
     * @return an enumeration of the components of this list
     * @see Vector#elements()
     */
    public Enumeration<AbstractNote> elements();

    /**
     * Tests whether the specified object is a component in this list.
     *
     * @param elem
     *            an object
     * @return <code>true</code> if the specified object is the same as a
     *         component in this list
     * @see Vector#contains(Object)
     */
    public boolean contains(AbstractNote elem);

    /**
     * Searches for the first occurrence of <code>elem</code>.
     *
     * @param elem
     *            an object
     * @return the index of the first occurrence of the argument in this list;
     *         returns <code>-1</code> if the object is not found
     * @see Vector#indexOf(Object)
     */
    public int indexOf(AbstractNote elem);

    /**
     * Searches for the first occurrence of <code>elem</code>, beginning the
     * search at <code>index</code>.
     *
     * @param elem
     *            an desired component
     * @param index
     *            the index from which to begin searching
     * @return the index where the first occurrence of <code>elem</code> is
     *         found after <code>index</code>; returns <code>-1</code> if
     *         the <code>elem</code> is not found in the list
     * @see Vector#indexOf(Object,int)
     */
    public int indexOf(AbstractNote elem, int index);

    /**
     * Returns the index of the last occurrence of <code>elem</code>.
     *
     * @param elem
     *            the desired component
     * @return the index of the last occurrence of <code>elem</code> in the
     *         list; returns <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object)
     */
    public int lastIndexOf(AbstractNote elem);

    /**
     * Searches backwards for <code>elem</code>, starting from the specified
     * index, and returns an index to it.
     *
     * @param elem
     *            the desired component
     * @param index
     *            the index to start searching from
     * @return the index of the last occurrence of the <code>elem</code> in
     *         this list at position less than <code>index</code>; returns
     *         <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object,int)
     */
    public int lastIndexOf(AbstractNote elem, int index);

    /**
     * Returns the component at the specified index. Throws an
     * <code>ArrayIndexOutOfBoundsException</code> if the index is negative or
     * not less than the size of the list. <blockquote> <b>Note:</b> Although
     * this method is not deprecated, the preferred method to use is
     * <code>get(int)</code>, which implements the <code>List</code>
     * interface defined in the 1.2 Collections framework. </blockquote>
     *
     * @param index
     *            an index into this list
     * @return the component at the specified index
     * @see #get(int)
     * @see Vector#elementAt(int)
     */
    public AbstractNote elementAt(int index);

    /**
     * Returns the first component of this list. Throws a
     * <code>NoSuchElementException</code> if this vector has no components.
     *
     * @return the first component of this list
     * @see java.util.Vector#firstElement()
     */
    public AbstractNote firstElement();

    /**
     * Returns the last component of the list. Throws a
     * <code>NoSuchElementException</code> if this vector has no components.
     *
     * @return the last component of the list
     * @see Vector#lastElement()
     */
    public AbstractNote lastElement();

    /**
     * Return the number of components in the list.
     *
     * @return the number of components in the list
     * @see NoteListModel#getSize()
     */
    public int getSize();

    /**
     * Sets the component at the specified <code>index</code> of this list to
     * be the specified object. The previous component at that position is
     * discarded.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * invalid. <blockquote> <b>Note:</b> Although this method is not
     * deprecated, the preferred method to use is <code>set(int,Object)</code>,
     * which implements the <code>List</code> interface defined in the 1.2
     * Collections framework. </blockquote>
     *
     * @param obj
     *            what the component is to be set to
     * @param index
     *            the specified index
     * @see #set(int,Object)
     * @see Vector#setElementAt(Object,int)
     */
    public void setElementAt(AbstractNote obj, int index);

    /**
     * Deletes the component at the specified index.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * invalid. <blockquote> <b>Note:</b> Although this method is not
     * deprecated, the preferred method to use is <code>remove(int)</code>,
     * which implements the <code>List</code> interface defined in the 1.2
     * Collections framework. </blockquote>
     *
     * @param index
     *            the index of the object to remove
     * @see #remove(int)
     * @see Vector#removeElementAt(int)
     */
    public void removeElementAt(int index);

    /**
     * Inserts the specified object as a component in this list at the specified
     * <code>index</code>.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * invalid. <blockquote> <b>Note:</b> Although this method is not
     * deprecated, the preferred method to use is <code>add(int,Object)</code>,
     * which implements the <code>List</code> interface defined in the 1.2
     * Collections framework. </blockquote>
     *
     * @param obj
     *            the component to insert
     * @param index
     *            where to insert the new component
     * @exception ArrayIndexOutOfBoundsException
     *                if the index was invalid
     * @see #add(int,Object)
     * @see Vector#insertElementAt(Object,int)
     */
    public void insertElementAt(AbstractNote obj, int index);

    /**
     * Adds the specified component to the end of this list.
     *
     * @param obj
     *            the component to be added
     * @see Vector#addElement(Object)
     */
    public void addElement(AbstractNote obj);

    /**
     * Removes the first (lowest-indexed) occurrence of the argument from this
     * list.
     *
     * @param obj
     *            the component to be removed
     * @return <code>true</code> if the argument was a component of this list;
     *         <code>false</code> otherwise
     * @see Vector#removeElement(Object)
     */
    public boolean removeElement(AbstractNote obj);

    /**
     * Returns a string that displays and identifies this object's properties.
     *
     * @return a String representation of this object
     */
    @Override
    public String toString();

    /**
     * Returns an array containing all of the elements in this list in the
     * correct order.
     *
     * @return an array containing the elements of the list
     * @see Vector#toArray()
     */
    public AbstractNote[] toArray();

    /**
     * Returns the element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * @param index
     *            index of element to return
     */
    public AbstractNote get(int index);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * @param index
     *            index of element to replace
     * @param element
     *            element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public AbstractNote set(int index, AbstractNote element);

    /**
     * Inserts the specified element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt; size()</code>).
     *
     * @param index
     *            index at which the specified element is to be inserted
     * @param element
     *            element to be inserted
     */
    public void add(int index, AbstractNote element);

    /**
     * Removes the element at the specified position in this list. Returns the
     * element that was removed from the list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * @param index
     *            the index of the element to removed
     */
    public AbstractNote remove(int index);

    /**
     * Returns the value at position <code>index</code>
     *
     * @return the value at position <code>index</code>
     * @see NoteListModel#getElementAt(int)
     */
    public AbstractNote getElementAt(int index);

    /**
     * @see NoteListModel#addNoteListDataListener(com.allenstudio.ir.event.NoteListDataListener)
     */
    public void addNoteListDataListener(NoteListDataListener l);

    /**
     * @see com.allenstudio.ir.ui.NoteListModel#removeNoteListDataListener(com.allenstudio.ir.event.NoteListDataListener)
     */
    public void removeNoteListDataListener();

    /**
     * Fired when one element's value is changed.
     *
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index changed element's index
     */
    protected void fireContentChanged(Object source, int index);

    /**
     * Fired when one new element is added.
     *
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index added element's index
     */
    protected void fireItemAdded(Object source, int index);

    /**
     * Fired when one element is removed.
     *
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index changed element's index
     */
    protected void fireItemRemoved(Object source, int index);
}
