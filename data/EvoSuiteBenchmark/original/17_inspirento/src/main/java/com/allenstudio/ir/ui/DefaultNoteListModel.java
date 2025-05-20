/*
 * @(#)DefaultNoteListModel.java
 * Created on 2005-8-4
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
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

    private NoteListDataListener dataListener;// Uni-listener temporary

    /**
     * Returns the current capacity of this list.
     * 
     * @return the current capacity
     * @see Vector#capacity()
     */
    public int capacity() {
        return delegate.capacity();
    }

    /**
     * Returns the number of components in this list.
     * 
     * @return the number of components in this list
     * @see Vector#size()
     */
    public int size() {
        return delegate.size();
    }

    /**
     * Tests whether this list has any components.
     * 
     * @return <code>true</code> if and only if this list has no components,
     *         that is, its size is zero; <code>false</code> otherwise
     * @see Vector#isEmpty()
     */
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * Returns an enumeration of the components of this list.
     * 
     * @return an enumeration of the components of this list
     * @see Vector#elements()
     */
    public Enumeration<AbstractNote> elements() {
        return delegate.elements();
    }

    /**
     * Tests whether the specified object is a component in this list.
     * 
     * @param elem
     *            an object
     * @return <code>true</code> if the specified object is the same as a
     *         component in this list
     * @see Vector#contains(Object)
     */
    public boolean contains(AbstractNote elem) {
        return delegate.contains(elem);
    }

    /**
     * Searches for the first occurrence of <code>elem</code>.
     * 
     * @param elem
     *            an object
     * @return the index of the first occurrence of the argument in this list;
     *         returns <code>-1</code> if the object is not found
     * @see Vector#indexOf(Object)
     */
    public int indexOf(AbstractNote elem) {
        return delegate.indexOf(elem);
    }

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
    public int indexOf(AbstractNote elem, int index) {
        return delegate.indexOf(elem, index);
    }

    /**
     * Returns the index of the last occurrence of <code>elem</code>.
     * 
     * @param elem
     *            the desired component
     * @return the index of the last occurrence of <code>elem</code> in the
     *         list; returns <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object)
     */
    public int lastIndexOf(AbstractNote elem) {
        return delegate.lastIndexOf(elem);
    }

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
    public int lastIndexOf(AbstractNote elem, int index) {
        return delegate.lastIndexOf(elem, index);
    }

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
    public AbstractNote elementAt(int index) {
        return delegate.elementAt(index);
    }

    /**
     * Returns the first component of this list. Throws a
     * <code>NoSuchElementException</code> if this vector has no components.
     * 
     * @return the first component of this list
     * @see java.util.Vector#firstElement()
     */
    public AbstractNote firstElement() {
        return delegate.firstElement();
    }

    /**
     * Returns the last component of the list. Throws a
     * <code>NoSuchElementException</code> if this vector has no components.
     * 
     * @return the last component of the list
     * @see Vector#lastElement()
     */
    public AbstractNote lastElement() {
        return delegate.lastElement();
    }

    /**
     * Return the number of components in the list.
     * 
     * @return the number of components in the list
     * @see NoteListModel#getSize()
     */
    public int getSize() {
        return delegate.size();
    }

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
    public void setElementAt(AbstractNote obj, int index) {
        delegate.setElementAt(obj, index);
        fireContentChanged(this, index);
    }

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
    public void removeElementAt(int index) {
        delegate.removeElementAt(index);
        fireItemRemoved(this, index);
    }

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
    public void insertElementAt(AbstractNote obj, int index) {
        delegate.insertElementAt(obj, index);
        fireItemAdded(this, index);
    }

    /**
     * Adds the specified component to the end of this list.
     * 
     * @param obj
     *            the component to be added
     * @see Vector#addElement(Object)
     */
    public void addElement(AbstractNote obj) {
        int index = delegate.size();
        delegate.addElement(obj);
        fireItemAdded(this, index);
    }

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
    public boolean removeElement(AbstractNote obj) {
        int index = indexOf(obj);
        boolean an = delegate.removeElement(obj);
        if (index >= 0) {
            fireItemRemoved(this, index);
        }
        return an;
    }

    /**
     * Returns a string that displays and identifies this object's properties.
     * 
     * @return a String representation of this object
     */
    @Override
    public String toString() {
        return delegate.toString();
    }

    /*
     * The remaining methods are included for compatibility with the Java 2
     * platform Vector class.
     */

    /**
     * Returns an array containing all of the elements in this list in the
     * correct order.
     * 
     * @return an array containing the elements of the list
     * @see Vector#toArray()
     */
    public AbstractNote[] toArray() {
        AbstractNote[] an = new AbstractNote[delegate.size()];
        delegate.copyInto(an);
        return an;
    }

    /**
     * Returns the element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
     * out of range (<code>index &lt; 0 || index &gt;= size()</code>).
     * 
     * @param index
     *            index of element to return
     */
    public AbstractNote get(int index) {
        return delegate.elementAt(index);
    }

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
    public AbstractNote set(int index, AbstractNote element) {
        AbstractNote an = delegate.elementAt(index);
        delegate.setElementAt(element, index);
        fireContentChanged(this, index);
        return an;
    }

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
    public void add(int index, AbstractNote element) {
        delegate.insertElementAt(element, index);
        fireItemAdded(this, index);
    }

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
    public AbstractNote remove(int index) {
        AbstractNote an = delegate.elementAt(index);
        delegate.removeElementAt(index);
        fireItemRemoved(this, index);
        return an;
    }

    /**
     * Returns the value at position <code>index</code>
     * 
     * @return the value at position <code>index</code>
     * @see NoteListModel#getElementAt(int)
     */
    public AbstractNote getElementAt(int index) {
        return delegate.elementAt(index);
    }

    /**
     * @see NoteListModel#addNoteListDataListener(com.allenstudio.ir.event.NoteListDataListener)
     */
    public void addNoteListDataListener(NoteListDataListener l) {
        this.dataListener = l;
    }

    /**
     * @see com.allenstudio.ir.ui.NoteListModel#removeNoteListDataListener(com.allenstudio.ir.event.NoteListDataListener)
     */
    public void removeNoteListDataListener() {
        this.dataListener = null;
    }

    /**
     * Fired when one element's value is changed.
     * 
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index changed element's index
     */
    protected void fireContentChanged(Object source, int index) {
        dataListener.contentChanged(
                new NoteListDataEvent(source, index));
    }

    /**
     * Fired when one new element is added.
     * 
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index added element's index
     */
    protected void fireItemAdded(Object source, int index) {
        dataListener.itemAdded(
                new NoteListDataEvent(source, index));
    }

    /**
     * Fired when one element is removed.
     * 
     * @param source the <code>NoteListModel</code> changed, typically "this"
     * @param index changed element's index
     */
    protected void fireItemRemoved(Object source, int index) {
        dataListener.itemRemoved(
                new NoteListDataEvent(source, index));
    }
}
