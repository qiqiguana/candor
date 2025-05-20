/*
 * @(#)DateChooser.java
 * Created on 2005-8-17
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

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.allenstudio.ir.event.DateChooserEvent;
import com.allenstudio.ir.event.DateChooserListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Encapsulating a <code>MonthlyCalendar inside,
 * this class also adds a combo box to choose
 * a month, and a spinner to pick a year number.
 * 
 * @author Allen Chue
 */
public class DateChooser extends JPanel {
    private JSpinner yearSpinner;
    
    private JComboBox monthBox;
    
    private MonthlyCalendar calendarPane;
    
    /** This variable keeps track of current selected date */
    private Calendar current;
    
    /** Listener for receiving DateChooserEvent */
    private DateChooserListener listener;
    
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Font font = new Font("Dialog", Font.PLAIN, 12);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Spinner.font", font);
        
        JFrame f = new JFrame("Test");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DateChooser chooser = new DateChooser();
        chooser.addDateChooserListener(new DateChooserListener() {

            public void dateChanged(DateChooserEvent e) {
                System.out.println(e.getFormerDate().get(Calendar.YEAR) + "." +
                        (e.getFormerDate().get(Calendar.MONTH) + 1) + "." +
                        e.getFormerDate().get(Calendar.DATE) + "\n" + 
                        e.getNewDate().get(Calendar.YEAR) + "." +
                        (e.getNewDate().get(Calendar.MONTH) + 1) + "." +
                        e.getNewDate().get(Calendar.DATE));
            }
            
        });
        f.add(chooser);
        f.pack();
        f.setVisible(true);
    }
    
    public DateChooser() {
        super(new BorderLayout());
        
        initComponents();
        
        initActions();
    }
    
    private void initComponents() {

        calendarPane = new MonthlyCalendar() {

            @Override
            protected void dateSelected() {
                super.dateSelected();
                if (!calendarPane.getSelectedDate().equals(
                        getCurrent())) {
                    fireDateChanged(this, getCurrent(),
                            calendarPane.getSelectedDate());
                    
                    updateCurrent();
                }
            }
            
        };
        
        calendarPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(calendarPane);
        updateCurrent();//Important initializtion

        yearSpinner = new JSpinner();
        Calendar lowerRange = Calendar.getInstance();
        lowerRange.set(2004, 0, 1);//FIXME why 2004? a bug?
        Calendar upperRange = Calendar.getInstance();
        upperRange.set(2100, 11, 31);
        SpinnerDateModel yearModel = new SpinnerDateModel(Calendar.getInstance().getTime(),
                lowerRange.getTime(), upperRange.getTime(), Calendar.YEAR);
        yearSpinner.setModel(yearModel);
        yearSpinner.setEditor(new JSpinner.DateEditor(yearSpinner, "yyyy"));
        
        monthBox = new JComboBox(getDefaultMonthNames());
        monthBox.setSelectedIndex(getCurrent().get(Calendar.MONTH));
        
        JPanel topPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 10, 3));
        topPanel.add(monthBox);
        topPanel.add(yearSpinner);
        
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void initActions() {
        monthBox.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                monthChanged();
            }
            
        });
        
        yearSpinner.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                monthChanged();
            }
            
        });
    }
    
    void monthChanged() {
        //XXX Could it be easier?
        Date date = (Date)yearSpinner.getValue();
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, monthBox.getSelectedIndex());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                calendarPane.setYearMonth(c.get(Calendar.YEAR), monthBox.getSelectedIndex() + 1);  
                fireDateChanged(this, getCurrent(), calendarPane.getSelectedDate());
            }
        });   
        
        updateCurrent();
    }
    
    protected void fireDateChanged(Object source, Calendar oldValue,
            Calendar newValue) {
        if (listener != null) {
            listener.dateChanged(new DateChooserEvent(source, oldValue, newValue));
        }
    }
    
    /**
     * Updates the <code>current</code> variable
     * if needed. It should be invoked when changes
     * on <code>monthBox</code> or <code>yearSpinner</code>
     * happen or the user selects another date in the
     * <code>calendarPane</code>.
     *
     */
    private void updateCurrent() {
        if (current == null) {
            current = Calendar.getInstance();
        }
        current = calendarPane.getSelectedDate();
    }
    
    /**
     * A utility method that returns the twelve
     * @return
     */
    private static String[] getDefaultMonthNames() {
        String[] months = new String[12];
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM",
                Locale.getDefault());
        
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            months[i] = formatter.format(calendar.getTime());
        }
        
        return months;
    }

    public DateChooserListener getDateChooserListener() {
        return listener;
    }

    public void addDateChooserListener(DateChooserListener listener) {
        this.listener = listener;
    }
    
    public void removeDateChooserListener() {
        this.listener = null;
    }

    public Calendar getCurrent() {
        return current;
    }
}
