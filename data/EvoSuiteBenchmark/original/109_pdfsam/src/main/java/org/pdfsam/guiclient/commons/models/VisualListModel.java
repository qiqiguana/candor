/*
 * Created on 18-Jun-2008
 * Copyright (C) 2008 by Andrea Vacondio.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.pdfsam.guiclient.commons.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;

import org.pdfsam.guiclient.dto.VisualPageListItem;
/**
 * Model for the JList in the JVisualPdfPageSelectionPanel
 * @author Andrea Vacondio
 *
 */
public class VisualListModel extends AbstractListModel {
	
	private static final long serialVersionUID = -1468591826451724954L;

	protected Vector<VisualPageListItem> data = new Vector<VisualPageListItem>();
    
	public Object getElementAt(int index) {
		VisualPageListItem retVal = null;
    	if(index < data.size()){
    		retVal = (VisualPageListItem)(data.get(index));
    	}
        return retVal;
	}
	
	/**
     * @return data size
     */
    public int getSize() {        
        return (data != null)? data.size(): 0;
    }    

    /**
     * set data source for the model
     * @param inputData array <code>VisualPageListItem[]</code> as data source
     */
    public void setData(VisualPageListItem[] inputData){
    	clearData();
    	int inputDataSize = inputData.length;
    	if (inputDataSize > 0) {
	        for(int i=0; i<inputDataSize; i++){
	            data.add(inputData[i]);
	        }
        	fireIntervalAdded(this, 0, inputDataSize -1);
    	}
    }
   
    /**
     * set data source for the model
     * @param inputData Vector of VisualPageListItem
     */
    public void setData(Vector<VisualPageListItem> inputData){
    	clearData();
    	int inputDataSize = inputData.size();
    	if (inputDataSize > 0) {
	       this.data = inputData;
	       fireIntervalAdded(this, 0, inputDataSize -1);	       
    	}
    }
    /**
     * Removes any data source for the model
     */
    public void clearData(){
    	int dataSize = data.size();
        data.clear();
        if (dataSize > 0) {
		    fireIntervalRemoved(this, 0, dataSize-1);
		}
    }
    
    /**
     * removes the element at the index
     * @param index
     * @param physicalDeletion if true the element is removed, if false it's set as "deleted" (rendered with a red cross)
     */    
    public void removeElementAt(int index, boolean physicalDeletion) {
    	if(physicalDeletion){
    		data.remove(index);
    	}else{
    		(data.get(index)).setDeleted(true);
    	}
    	fireIntervalRemoved(this, index, index);
    }
    
    /**
     * <p>Remove a set of rows from the list data source and fire to Listeners
     * 
     * @param rows rows number to remove from the data source
     * @param physicalDeletion if true the element is removed, if false it's set as "deleted" (rendered with a red cross)
     * @throws Exception if an exception occurs
     * */   
    public void removeElements(int[] rows, boolean physicalDeletion)  throws IndexOutOfBoundsException{
        if (rows.length > 0 && rows.length <= data.size()){
        	if(physicalDeletion){
        		data.subList(rows[0], rows[rows.length-1]+1).clear();
        	}else{
        		for(int i=0; i<rows.length; i++){
        			(data.get(rows[i])).setDeleted(true);
        		}
        	}
            this.fireIntervalRemoved(this, rows[0], rows[rows.length -1]);
        }
    }
    
    /**
     * <p>Remove a set of rows from the list data source and fire to Listeners
     * 
     * @param fromIndex remove from the index (inclusive)
     * @param toIndex remove to the index (inclusive)
     * @param physicalDeletion if true the element is removed, if false it's set as "deleted" (rendered with a red cross)
     * @throws Exception if an exception occurs
     * */   
    public void removeElements(int fromIndex, int toIndex, boolean physicalDeletion)  throws IndexOutOfBoundsException{
        if (fromIndex >= 0 && toIndex < data.size() && fromIndex<=toIndex){
        	if(physicalDeletion){
        		data.subList(fromIndex, toIndex+1).clear();
        	}else{
        		for(int i=fromIndex; i<toIndex; i++){
        			(data.get(i)).setDeleted(true);
        		}
        	}
            this.fireIntervalRemoved(this, fromIndex, toIndex);
        }
    }  
    
	/**
     * Add an item to the model and fire to Listeners
     * @param index index to add to
     * @param inputData <code>VisualPageListItem</code> to add to the data source
     */
    public void addElementAt(VisualPageListItem inputData, int index) {
    	if(inputData!=null){
    		data.add(index, inputData);
    		fireIntervalAdded(this, index, index);
    	}
    }

    /**
     * Add an item to the model and fire to Listeners
     * @param inputData <code>VisualPageListItem</code> to add to the data source
     */
    public void addElement(VisualPageListItem inputData) {	
		data.add(inputData);
		fireIntervalAdded(this, data.size(), data.size());
    }
    
    /**
     * delegated to the Vector addAll
     * @param c
     * @see Vector#addAll(Collection)
     */
    public void addAllElements(Collection<VisualPageListItem> c){
    	if(c!=null){
    		int i=0;
    		for(VisualPageListItem element : c){
    			data.add(element);
    			i++;
    		}
    		fireIntervalAdded(this, data.size(), data.size()+i-1);
    	}
    }
    
    /**
     * same as addAllElements. Appends elements to the end
     * @param c
     */
    public void appendAllElements(Collection<VisualPageListItem> c){
    	addAllElements(c);
    }
     
    /**
     * Add elements at the beginning
     * @param c
     */
    public void prependAllElements(Collection<VisualPageListItem> c){
    	if(c!=null && c.size()>0){
    		data.addAll(0, c);
    		fireIntervalAdded(this, 0, c.size()-1);
    	}
    }
    
    /**
     * Add elements in c at the given index
     * @param index
     * @param c
     */
    public void addAllElements(int index, Collection<VisualPageListItem> c){
    	if(c!=null && index>=0 && index<=data.size()){
    		int i = index;
    		for(VisualPageListItem element : c){
    			data.add(i, element);
    			i++;
    		}
    		fireIntervalAdded(this, index, i-1);
    	}
    }
    
    /**
     * delegated to the Vector subList
     * @param fromIndex start inclusive
     * @param toIndex end exclusive
     * @return null if limits are not corrected
     * @see Vector#subList(int, int)
     */
    public Collection<VisualPageListItem> subList(int fromIndex, int toIndex){
    	return subList(fromIndex, toIndex, false);
    }
    
    /**
     * @param fromIndex start inclusive
     * @param toIndex end exclusive
     * @param newInstance if false delegates to the Vector#subList(int, int) if true return a new Vector
     * @return a portion of the data Vector, null if limits are not corrected
     * @see Vector#subList(int, int)
     */
    public Collection<VisualPageListItem> subList(int fromIndex, int toIndex, boolean newInstance){
    	Collection<VisualPageListItem> retVal = null;
    	if(fromIndex>=0 && toIndex<=data.size()){
    		if(newInstance){
    			List<VisualPageListItem> itemsList = data.subList(fromIndex, toIndex);
    			if(itemsList!=null && itemsList.size()>0){
        			retVal = new Vector<VisualPageListItem>(itemsList.size());
        			for(VisualPageListItem currItem : itemsList){
        				retVal.add((VisualPageListItem) currItem.clone());
        			}

    			}else{
        			retVal = new Vector<VisualPageListItem>();    				
    			}
    		}else{
        		retVal = data.subList(fromIndex, toIndex);    			
    		}
    	}
    	return retVal;
    }
    
    /**
     * @return items of the model
     */
    public VisualPageListItem[] getElements(){
    	VisualPageListItem[] retVal = null;
    	if (data != null){
    		retVal = (VisualPageListItem[]) data.toArray(new VisualPageListItem[data.size()]);
    	}
    	return retVal;
    }
    
    /**
     * Replace an element of the model and fire to Listeners
     * @param index index to be replaced
     * @param inputData new <code>VisualPageListItem</code> to replace the data source
     */
    public void updateElementAt(int index, VisualPageListItem inputData){
        if (inputData != null && index>=0 && index<data.size()){
            data.set(index, inputData);
            this.fireContentsChanged(this, index,index);
        }
    }

    /**
     * Look for the inputData and repaint it if found
     * @param inputData
     */
    public void elementChanged(VisualPageListItem inputData){
        if (inputData != null && data.size()>0){
            int position = data.indexOf(inputData);
            if(position >= 0){
            	this.fireContentsChanged(this, position,position);
            }
        }
    }
    
    /**
     * Repaints all the elements
     */
    public void elementsChanged(){
    	this.fireContentsChanged(this, 0,data.size());
    }
    
    /**
     * Undelete the given elements
     * @param indexes
     * @throws IndexOutOfBoundsException
     */
    public void undeleteElements(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length>0 && indexes.length <= data.size()){
        	for (int i=0; i<indexes.length; i++){
        		(data.get(indexes[i])).setDeleted(false);
        	}  
        	fireContentsChanged(this,indexes[0]-1, indexes[indexes.length-1]);
        }
    }
    
    /**
     * Reverse the given elements
     * @param indexes
     * @throws IndexOutOfBoundsException
     */
    public void reverseElements(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length>0 && indexes.length <= data.size()){
        	ArrayList<VisualPageListItem> items = new ArrayList<VisualPageListItem>(data.size());
        	for (int i=0; i<indexes.length; i++){
        		items.add(data.get(indexes[i]));
        	}
        	Collections.reverse(items);
        	for (int i=0; i<indexes.length; i++){
        		data.set(indexes[i], items.get(i));
        	}
        	fireContentsChanged(this,indexes[0]-1, indexes[indexes.length-1]);
        }
    }
    /**
     * rotates the given elements
     * @param indexes
     * @throws IndexOutOfBoundsException
     */
    public void rotateClockwiseElements(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length>0 && indexes.length <= data.size()){
        	for (int i=0; i<indexes.length; i++){
        		VisualPageListItem item = data.get(indexes[i]);
        		item.rotateClockwise();
        	}  
        	fireContentsChanged(this,indexes[0]-1, indexes[indexes.length-1]);
        }
    }
    
    /**
     * rotates anti clockwise the given elements
     * @param indexes
     * @throws IndexOutOfBoundsException
     */
    public void rotateAnticlockwiseElements(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length>0 && indexes.length <= data.size()){
        	for (int i=0; i<indexes.length; i++){
        		VisualPageListItem item = data.get(indexes[i]);
        		item.rotateAnticlockwise();
        	}  
        	fireContentsChanged(this,indexes[0]-1, indexes[indexes.length-1]);
        }
    }
    
    /**
     * Moves up a item at the given index fire to Listeners
     * @param index element index to move from the data source
     */
    public void moveUpIndex(int index)throws IndexOutOfBoundsException{
            if (index >= 1 && index < (data.size())){
            	VisualPageListItem tmpElement = data.get(index);
                data.set(index, data.get((index-1)));
                data.set((index-1), tmpElement);
                fireContentsChanged(this,index-1, index);
            }
    }
    
    /**
     * Moves up a set of items at the given indexes
     * @param indexes Indexes to move from the data source
     */
    public void moveUpIndexes(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length > 0 && indexes.length < data.size()){
           //no move up if i'm selecting the first element of the table
           if (indexes[0] > 0){
        	   VisualPageListItem tmpElement = data.get(indexes[0]-1);
               for (int i=0; i<indexes.length; i++){    
                   if (indexes[i] > 0){
                           data.set(indexes[i]-1, data.get(indexes[i]));
                   }
               }
               data.set(indexes[indexes.length-1], tmpElement);
               fireContentsChanged(this,indexes[0]-1, indexes[indexes.length-1]);
           }
       }
    } 
   
    /**
     * Moves down a item at the given index fire to Listeners
     * @param index element index to move from the data source
     */
    public void moveDownIndex(int index) throws IndexOutOfBoundsException{
            if (index >= 0 && index < (data.size()-1)){                
            	VisualPageListItem tmpElement = data.get(index);
                data.set(index, data.get((index+1)));
                data.set((index+1), tmpElement);
                fireContentsChanged(this,index, index+1);
            }
    } 
    
    /**
     * Moves down a set of items at the given indexes
     * @param indexes Indexes to move from the data source
     */
    public void moveDownIndexes(int[] indexes)throws IndexOutOfBoundsException{
        if (indexes.length > 0 && indexes.length < data.size()){
            //no move down if i'm selecting the last element of the table
            if (indexes[indexes.length-1] < (data.size()-1)){
            	VisualPageListItem tmpElement = data.get(indexes[indexes.length-1]+1);
                for (int i=(indexes.length-1); i>=0; i--){    
                    if (indexes[indexes.length-1] < (data.size()-1)){
                            data.set(indexes[i]+1, data.get(indexes[i]));
                    }
                }
                data.set(indexes[0], tmpElement);
                fireContentsChanged(this,indexes[0], indexes[indexes.length-1]+1);
            }
        }
    }
    
    /**
     * @return a List<VisualPageListItem> of elements with the isDeleted=false, null if there are no elements
     */
    public List<VisualPageListItem> getValidElements(){
    	ArrayList<VisualPageListItem> retVal = null;
    	if(data!=null && data.size()>0){
    		retVal = new ArrayList<VisualPageListItem>(data.size());
    		 for (int i=0; i<data.size(); i++){   
    			 if(!(data.get(i)).isDeleted()){
    				 retVal.add(data.get(i));
    			 }
    		 }
    	}
    	return retVal;
    }
    
}
