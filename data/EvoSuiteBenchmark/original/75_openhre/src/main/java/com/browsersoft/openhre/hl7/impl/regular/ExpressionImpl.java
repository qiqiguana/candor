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

import com.browsersoft.openhre.hl7.api.regular.Expression;
import com.browsersoft.openhre.hl7.api.regular.ExpressionPart;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapper;

import java.util.ArrayList;

public class ExpressionImpl implements Expression {

    private ArrayList items;
    private int numberOfElements;

    public ExpressionImpl() {
        items = new ArrayList();
    }

    public int size() {
        return items.size();
    }

    public void addItem( ExpressionPart item ) {
        items.add(item);
    }

    public ExpressionPart getItem( int index ) {
        if ( index >= 0 && index < items.size() ) {
            return (ExpressionPart) items.get(index);
        } else {
            return null;
        }
    }

    public void setItem( int index, ExpressionPart value ) {
        if ( index >= 0 && index < items.size() ) {
            items.set(index, value);
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

    public int getNumberOfElementTypes() {
        return numberOfElements;
    }

    public void setNumberOfElementTypes( int numberOfElements ) {
        this.numberOfElements = numberOfElements;
    }

    public String toString(  ExpressionElementMapper mapper ) {

        StringBuffer buffer = new StringBuffer();

        for ( int i = 0; i < size(); i++ ) {
            switch ( getItem(i).getType()) {
                case ExpressionPart.OPEN_OPTIONAL:
                   buffer.append("[");
                   break;
                case ExpressionPart.OPEN_REPEATABLE:
                   buffer.append("{");
                   break;
                case ExpressionPart.ELEMENT:
                   buffer.append(mapper.getItem(getItem(i).getElementID()));
                   break;
                case ExpressionPart.CLOSE_REPEATABLE:
                   buffer.append("}");
                   break;
                case ExpressionPart.CLOSE_OPTIONAL:
                   buffer.append("]");
                   break;
            }
        }

        return buffer.toString();

    }

    public String toString() {

        StringBuffer buffer = new StringBuffer();

        for ( int i = 0; i < size(); i++ ) {
            buffer.append(getItem(i).toString());
            buffer.append("\n");
        }

        return buffer.toString();

    }

    public void readFromStringForDebug( String string ) {

        int counterElement = 0;
        for ( int i = 0; i < string.length(); i++ ) {
            ExpressionPart newPart = new ExpressionPartImpl();
            char ch = string.charAt(i);
            switch ( ch ) {
                case '[':
                    newPart.setType(ExpressionPart.OPEN_OPTIONAL);
                    addItem(newPart);
                    break;
                case ']':
                    newPart.setType(ExpressionPart.CLOSE_OPTIONAL);
                    addItem(newPart);
                    break;
                case '{':
                    newPart.setType(ExpressionPart.OPEN_REPEATABLE);
                    addItem(newPart);
                    break;
                case '}':
                    newPart.setType(ExpressionPart.CLOSE_REPEATABLE);
                    addItem(newPart);
                    break;
                default:
                    if ( ch >= 'A' && ch <= 'Z' ) {
                        newPart.setType(ExpressionPart.ELEMENT);
                        newPart.setElementID(counterElement);
                        addItem(newPart);
                        counterElement++;
                        break;
                    }
            }
        }
        numberOfElements = counterElement;

    }


}