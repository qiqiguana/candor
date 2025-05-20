/*
Copyright 2007 DB-Everywhere
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.gbshape.dbe.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ValueListHandler implements Serializable {

	private List list;
	private transient ListIterator listIterator;

	public ValueListHandler() {
	}

	public void setList(List list) throws IteratorException {
		this.list = list;
		if (list != null){
			listIterator = list.listIterator();
		}else{
			throw new IteratorException("List empty");
		}
	}

	public Collection getList() {
		return list;
	}

	public int getSize() throws IteratorException {
		int size = 0;

		if (list != null){
			size = list.size();
		}else{
			throw new IteratorException(""); // No Data
		}

		return size;
	}

	public Object getCurrentElement() throws IteratorException {

		Object obj = null;
		// Will not advance iterator
		if (list != null) {
			int currIndex = listIterator.nextIndex();
			obj = list.get(currIndex);
		} else {
			throw new IteratorException("");
        }
		return obj;

	}

	public int getCurrentIndex() throws IteratorException {

		int currIndex =0;
		// Will not advance iterator
		if (list != null) {
			currIndex = listIterator.nextIndex();
		} else {
			throw new IteratorException("");
        }
		return currIndex;

	}

	public void setIndex(int index) throws IteratorException {

		resetIndex();
		getNextElements(index);

	}

	public List getPreviousElements(int count) throws IteratorException {
		int i = 0;
		Object object = null;
		LinkedList list = new LinkedList();
		if (listIterator != null) {
			while (listIterator.hasPrevious() && (i < count)) {
				object = listIterator.previous();
				list.add(object);
				i++;
			}
		} else {
			throw new IteratorException(""); // No data
		}

		return list;
	}

	public List getNextElements(int count) throws IteratorException {
		int i = 0;
		Object object = null;
		LinkedList list = new LinkedList();
		if (listIterator != null) {
			while (listIterator.hasNext() && (i < count)) {
				object = listIterator.next();
				list.add(object);
				i++;
			}
		} else {
			throw new IteratorException(""); // No data
		}

		return list;
	}

	public void resetIndex() throws IteratorException {
		if (listIterator != null) {
			listIterator = list.listIterator();
		} else {
			throw new IteratorException(""); // No data

		}
	}
}
