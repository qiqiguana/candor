/*
 * @(#)DateChooserEvent.java
 * Created on 2005-8-19
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
package com.allenstudio.ir.event;

import java.util.EventObject;
import java.util.Calendar;
/**
 * Defines an event that encapsulates the
 * changes of a <code>DateChooser</code>
 * object.
 * 
 * @author Allen Chue
 */
public class DateChooserEvent extends EventObject {

    private Calendar oldDate;
    
    private Calendar newDate;
    
    public DateChooserEvent(Object source, Calendar oldValue,
            Calendar newValue) {
        super(source);
        
        this.oldDate = oldValue;
        this.newDate = newValue;
    }
    
    /**
     * Returns the former date before this date change.
     * @return the former date
     */
    public Calendar getFormerDate() {
        return oldDate;
    }
    
    public Calendar getNewDate() {
        return newDate;
    }

}
