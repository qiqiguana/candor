/*
 * @(#)MonthlyCalendarModel.java
 * Created on 2005-8-16
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
package com.allenstudio.ir.ui.calendar;


/**
 * The background model for a <code>MonthlyCalendar</code>
 * class.
 * 
 * @author Allen Chue
 */
public interface IMonthlyCalendarModel {
    /**
     * Returns if the specified date needs
     * to be marked, typically has its font
     * bolded.
     * @param date the specified date in a month, 1-based
     * @return true if the date has to be marked, false otherwise
     */
    boolean isMarked(int date);
    
    /**
     * Sets the specified date the value indicating if
     * it needs to be marked according to <code>b</code>.
     * @param date the date to be set
     * @param b value to assign
     */
    void setMarked(int date, boolean b);

    /**
     * Returns an <code>int</code> representing the selected date
     * @return the selected date number
     */
    int getSelectedDate();
    
    /**
     * Sets the selected date to the specified <code>date</code>
     * @param date new selected date
     */
    void setSelectedDate(int date);
}
