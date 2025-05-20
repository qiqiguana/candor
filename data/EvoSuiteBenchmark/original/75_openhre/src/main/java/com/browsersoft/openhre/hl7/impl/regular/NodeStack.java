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
package com.browsersoft.openhre.hl7.impl.regular;

import com.browsersoft.openhre.hl7.api.regular.InvalidExpressionException;

import java.util.Stack;

public class NodeStack {

    private Stack stack;

    public NodeStack() {
        stack = new Stack();
    }

    public void push ( int node ) {
       stack.add(new Integer(node));
    }

    public int pop ()  throws InvalidExpressionException{
        if ( !stack.isEmpty() ) {
            return ((Integer) stack.pop()).intValue();
        } else {
            throw new InvalidExpressionException();
        }
    }

}