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
 * Default implementation for interface
 * <code>MonthlyCalendar</code>.
 * 
 * @author Allen Chue
 */
public class MonthlyCalendarModel implements IMonthlyCalendarModel {

    /**An array keeping track of cells whether or not be marked*/
    private boolean[] mark = null;
    
    /**Selected date(1-based)*/
    private int selectedDate;
    
    public MonthlyCalendarModel() {
        //An array of length 31 is preserved in order
        //to archieve convenience, though
        //not every month in a year has 31 days.
        mark = new boolean[31];
    }
    
    public boolean isMarked(int date) {
        if (mark != null) {
            return mark[date - 1];
        }
        return false;
    }

    public void setMarked(int date, boolean b) {
        if (mark != null) {
            mark[date - 1] = b;
        }
    }

    public int getSelectedDate() {
        return selectedDate;
    }
    
    public void setSelectedDate(int date) {
        selectedDate = date;
    }

}
