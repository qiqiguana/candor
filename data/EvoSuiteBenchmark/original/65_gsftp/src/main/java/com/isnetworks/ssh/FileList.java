/******************************************************************************
 * 
 * Modified by Zhong Li, added modify time 
 *
 * Copyright (c) 2001 by ISNetworks, Seattle, WA.
 *                       www.isnetworks.com, info@isnetworks.com
 * Based on MindTerm from Mindbright Technology AB, Stockholm, Sweden.
 *                        www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *****************************************************************************/

/**
* Subclass of java.awt.List which allows the List to be treated as though
* it directly help FileListItems instead of Strings.  Handles the setting
* and getting of the List's contents as FileListItems.
*/
package com.isnetworks.ssh;

import java.awt.*;
import java.util.*;

public class FileList extends java.awt.List {

	/**
	* Array of FileListItems which corresponds to the contents of the List
	*/
	private Vector mListItems;
	private String formatString(String s1, String s2, int len){
	    int an=len-s1.length();
	    if( an<4 ){
	        an =4;
	    }
	    for(int i=0;i<an;i++){
	        s1 += " ";
	    }
	    s1 += s2;
	    return s1;
	}
	/**
	* Set the contents of the List to be the array
	*/
	public void setListItems( Vector listItems ) {
	    this.setVisible(false);
		clear();

		mListItems = listItems;

		for( int i = 0; i < listItems.size(); i++ ) {
			FileListItem item = (FileListItem)listItems.elementAt( i );
			String text = "";  
			if ( item.isDirectory() ) {
				text = "[" + item.getName() + "]";
			}
			else {
			    text = item.getName(); 
				long s=item.getSize();
				String ls = ""+s+"B";
				if( s>1024 ){
					if( s<1024*1024L) ls = ""+s/1024L+"KB";
					else ls = ""+s/(1024L*1024L)+"MB";
				}
				text = formatString(text,"("+ls+")",24 );
			}
			if( item.getLastModify()>0){
			    text = formatString(text,"<"+new Date(item.getLastModify()).toLocaleString()+">",36);
			}
			add( text );
		}
	    this.setVisible(true);
	}	
	/**
	* Gets the first selected item in the list
	*/
	public FileListItem getSelectedFileListItem() {
		if ( getSelectedIndex() != -1 ) {
			return (FileListItem)mListItems.elementAt( getSelectedIndex() );
		}
		
		return null;
	}
	
	/**
	* Gets all of the current selected items in the list.  Filters out the
	* entry ".." if it exists since the user can't do anything with
	* it, except double click on it to move to the parent directory
	*/
	public FileListItem[] getSelectedFileListItems() {
		int[] selectedIndexes = getSelectedIndexes();
		
		FileListItem[] selectedItems = new FileListItem[ getSelectionCount() ];
		
		// Don't count .. in the selected list
		boolean skipZero = isIndexSelected( 0 ) && ((FileListItem)mListItems.elementAt( 0 )).getName().equals( ".." );
		int itemIndex = 0;
		for( int i = 0; i < selectedIndexes.length; i++ ) {
			if ( selectedIndexes[ i ] != 0 || !skipZero ) {
				selectedItems[ itemIndex++ ] = (FileListItem)mListItems.elementAt( selectedIndexes[ i ] );
			}
		}

		return selectedItems;
	}
	
	/**
	* @return Number of items currently selected in the list, not counting ".." if it is selected
	*/
	public int getSelectionCount() {
		int[] selectedIndexes = getSelectedIndexes();
		// Don't count .. in the selected list
		if ( isIndexSelected( 0 ) && ((FileListItem)mListItems.elementAt( 0 )).getName().equals( ".." ) ) {
			return selectedIndexes.length - 1;
		}
		
		return selectedIndexes.length;
	}
	
	/**
	* @return The FileListItem that matches the given String, displayed in the list, or null if it does not exist
	*/
	public FileListItem getFileListItem( String name ) {
		for( int i = 0; i < getItemCount(); i++ ) {
			if ( name.equals( getItem( i ) ) ) {
				return (FileListItem)mListItems.elementAt( i );
			}
		}
		
		return null;
	}
}