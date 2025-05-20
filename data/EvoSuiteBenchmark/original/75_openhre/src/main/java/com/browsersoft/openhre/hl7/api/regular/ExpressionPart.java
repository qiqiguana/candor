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
 * This class represents one part of expresion ( for example - expression A[B]{C}D contains 8 parts )
 */
public interface ExpressionPart {

    /**
     * represents element ( A, B )
     */
    public static final int ELEMENT           = 0;

    /**
     * represents "{"
     */
    public static final int OPEN_REPEATABLE   = 1;

    /**
     * represents "}"
     */
    public static final int CLOSE_REPEATABLE  = 2;

    /**
     * represents "["
     */
    public static final int OPEN_OPTIONAL     = 3;

    /**
     * represents "]"
     */
    public static final int CLOSE_OPTIONAL    = 4;

    /**
     * return the element type. Possible values are:
     * <CODE>ELEMENT</CODE>,<CODE>OPEN_REPEATABLE</CODE>, <CODE>CLOSE_REPEATABLE</CODE>, <CODE>OPEN_OPTIONAL</CODE>, <CODE>CLOSE_OPTIONAL</CODE>
     * @return type
     */
    public int getType();

    /**
     * set the element type. Possible values are:
     * <CODE>ELEMENT</CODE>,<CODE>OPEN_REPEATABLE</CODE>, <CODE>CLOSE_REPEATABLE</CODE>, <CODE>OPEN_OPTIONAL</CODE>, <CODE>CLOSE_OPTIONAL</CODE>
     * @param type type
     */
    public void setType( int type );

    /**
     * get the ID of element ( only in case getType() == ELEMENT )
     * @return element id
     */
    public int getElementID();

    /**
     * set the ID of element ( only in case getType() == ELEMENT )
     * @param elementID element id
     */
    public void setElementID( int elementID );


}