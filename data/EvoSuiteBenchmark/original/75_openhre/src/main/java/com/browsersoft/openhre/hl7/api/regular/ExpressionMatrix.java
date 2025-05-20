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
 * This class is a matrix, this matrix contains the graph for automat, it means
 * the relations between nodes<br>
 * The example ( [] not required (0x - 1x) , {} may be repeating ( 1x-nx ))
 * <pre>
 * A[B][C]D
 * --------
 * 0 --- A ---> 1
 * 1 --- B ---> 2
 * 2 --- C ---> 3
 * 3 --- D ---> 4
 * 1 --- C ---> 3
 * 1 --- D ---> 4
 * 2 --- D ---> 4
 * </pre>
 *
 * and for this one
 * <pre>
 * A[B]{[C]D}E
 * -----------
 * 0 --- A ---> 1
 * 1 --- B ---> 2
 * 2 --- C ---> 3
 * 3 --- D ---> 4
 * 4 --- E ---> 5
 * 1 --- C ---> 3
 * 1 --- D ---> 4
 * 2 --- D ---> 4
 * 4 --- C ---> 3
 * 4 --- D ---> 4
 * </pre>
 */
public interface ExpressionMatrix {
    /**
     * get number of element ( in this A[B][C]D for example 4 )
     * @return number of element
     */
    public int getNumberOfElements();

    /**
     * get number of nodes ( in this A[B][C]D for example 5.  ). If the matrix is well builded the next expression is always true
     * <pre>getNumberOfElements() + 1 == getNumberOfNodes()</pre>
     * @return number of nodes
     */
    public int getNumberOfNodes();

    /**
     * initialize the matrix
     * @param numberOfElements
     */
    public void creatMatrix( int numberOfElements );

    /**
     * set the realtion node -- elementID --> value
     * @param node
     * @param elementID
     * @param value
     */
    public void setValue( int node, int elementID, int value );

    /**
     * get the relation
     * @param node
     * @param elementID
     * @return target node or -1 if the relation doesn't exist
     */
    public int getValue( int node, int elementID );

    /**
     * get the number of new node
     * @return number of node
     */
    public int addNewNode();

    /**
     * this method write out only the interesting connections for debuging. In this example
     * <pre>
     * A[B]{[C]D}E
     * ------------------------------
     * Non standard forward:
     * 1 --- C ---> 3
     * 1 --- D ---> 4
     * 2 --- D ---> 4

     * Non standard backward:
     * 4 --- C ---> 3
     * 4 --- D ---> 4
     * </pre>
     * @param forward write out forward or backward connections
     * @param mapper mapper for translating names of elements
     * @return debug information
     */
    public String outNoStandardConnections( boolean forward, ExpressionElementMapper mapper );

}