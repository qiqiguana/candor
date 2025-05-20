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

import java.util.ArrayList;

public class ExpressionNodeList {

    private ArrayList items;

    public ExpressionNodeList() {
        items = new ArrayList();
    }

    public int size() {
        return items.size();
    }

    public void addExpressionList ( ExpressionNodeList list ) {

        for ( int i = 0; i < list.size() ; i++ ) {
            int item = list.getItem(i);
            if ( !isInList(item)) {
                addItem(item);
            }
        }

    }

    public boolean isInList( int item ) {
       for ( int i = 0; i < size() ; i++ ) {
          if ( getItem(i) == item ) {
              return true;
          }
       }
        return false;
    }

    public void addItem( int item ) {
        items.add(new Integer(item));
    }

    public int getItem( int index ) {
        if ( index >= 0 && index < items.size() ) {
            return ((Integer) items.get(index)).intValue();
        } else {
            return -1;
        }
    }

    public void setItem( int index, int value ) {
        if ( index >= 0 && index < items.size() ) {
            items.set(index, new Integer(value));
        }
    }

    public void removeItem( int index ) {
        if ( index >= 0 && index < items.size() ) {
            items.remove(index);
        }
    }

    public void clearAll() {
        items.clear();
    }

    public String toString() {

        StringBuffer buffer = new StringBuffer();

        for ( int i = 0; i < size(); i++ ) {
            buffer.append(getItem(i));
            buffer.append("\n");
        }

        return buffer.toString();

    }

}