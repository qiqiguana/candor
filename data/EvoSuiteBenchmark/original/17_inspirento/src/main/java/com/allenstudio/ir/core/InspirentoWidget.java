/*
 * @(#)InspirentoWidget.java
 * Created on 2005-8-20
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package com.allenstudio.ir.core;
/**
 * Basic interface for interacting among
 * different widgets in this program.
 * 
 * @author Allen Chue
 */
public interface InspirentoWidget {
    /**
     * Invoked when the widget's state changes.
     */
    public void changed();
    
    /**
     * Returns the unique name of this widget.
     * Why the name should be unique is that
     * this name is used to identify the widget
     * while processing widget-change event.
     */
    public String getName();
}
