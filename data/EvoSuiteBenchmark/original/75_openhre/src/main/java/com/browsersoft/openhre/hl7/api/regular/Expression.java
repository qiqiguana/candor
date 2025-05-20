/*
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2,
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */

package com.browsersoft.openhre.hl7.api.regular;

/**
 * This class represents expression like this
 * {[A[B]}
 */
public interface Expression {

    public void readFromStringForDebug( String string );

    /**
     * return number of elements in expresion
     * @return number of elements in expresion
     */
    public int getNumberOfElementTypes();

    /**
     * set the number of elements expresion
     * @param numberOfElements
     */
    public void setNumberOfElementTypes( int numberOfElements );

    /**
	 * Returns size of list
	 *
	 * @return size of list
	 */
	public int size();

	/**
	 * Add new item to list
	 *
	 * @param item new item
	 */
	public void addItem( ExpressionPart item );

	/**
	 * Returns part with <CODE>index</CODE>
	 *
	 * @return item part
	 */
	public ExpressionPart getItem( int index );

	/**
	 * Sets item with <CODE>index</CODE> to new value
	 *
	 * @param index index
	 * @param value value
	 */
	public void setItem( int index, ExpressionPart value );

	/**
	 * Remove item with <CODE>index</CODE>
	 *
	 * @param index index
	 */
	public void removeItem( int index );

	/**
	 * Remove all items in list
	 */
	public void clearAll();

    public String toString(  ExpressionElementMapper mapper );

}