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

import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapper;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapperItem;

import java.util.ArrayList;

public class ExpressionElementMapperImpl implements ExpressionElementMapper {

    private ArrayList items;

    public ExpressionElementMapperImpl() {
        items = new ArrayList();
    }

    public int size() {
        return items.size();
    }

    public void addItem( ExpressionElementMapperItem item ) {
        items.add(item);
    }

    public ExpressionElementMapperItem getItem( int index ) {
        if ( index >= 0 && index < items.size() ) {
            return (ExpressionElementMapperItem) items.get(index);
        } else {
            return null;
        }
    }

    public void setItem( int index, ExpressionElementMapperItem value ) {
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

    public String toString() {

        StringBuffer buffer = new StringBuffer();

        for ( int i = 0; i < size(); i++ ) {
            buffer.append(getItem(i).toString());
            buffer.append("\n");
        }

        return buffer.toString();

    }
}