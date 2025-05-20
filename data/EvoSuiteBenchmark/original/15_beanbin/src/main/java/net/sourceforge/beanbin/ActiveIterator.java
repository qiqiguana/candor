package net.sourceforge.beanbin;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ActiveIterator<E> implements Iterator<E> {
	int cursor = 0;

	private ActiveList<E> list;
	
	public ActiveIterator(ActiveList<E> list) {
		this.list = list;
	}

	public boolean hasNext() {
		return cursor != list.size();
	}

	public E next() {
	    try {
	    	E next = list.get(cursor++);
	    	return next;
	    } catch(IndexOutOfBoundsException e) {
	    	throw new NoSuchElementException();
	    }
	}

	public void remove() {
		list.remove(cursor);
	}
}