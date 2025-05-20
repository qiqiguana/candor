/*
 * @(#)DatePicker.java
 * Created on 2005-8-15
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
package test;

import javax.swing.*;
import java.awt.*;

import com.allenstudio.ir.ui.calendar.MonthlyCalendar;

public class DatePicker {
    
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        
        Font f = new Font("Dialog", Font.PLAIN, 12);
        UIManager.put("ComboBox.font", f);
        UIManager.put("Button.font", f);
        
        JFrame frame = new JFrame("Calendar Test");
        frame.setLayout(new BorderLayout());
        MonthlyCalendar calendar = new MonthlyCalendar();
        
        calendar.setMarked(31, true);
        //calendar.setYearMonth(2005, 2);
        frame.add(calendar, BorderLayout.CENTER);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
