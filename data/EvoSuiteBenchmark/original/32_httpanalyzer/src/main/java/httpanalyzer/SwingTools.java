/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 * 
 * Copyright (C) 2010, vlad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package httpanalyzer;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author vlad
 */
public class SwingTools {

    public final Color COOKIE_COLOR = Color.cyan;
    public final Color DATE_COLOR = Color.white;
    public final Color POST_DATA_COLOR = Color.magenta;
    public final Color SPLITER_COLOR = Color.lightGray;
    public final Color REQUEST_COLOR = Color.lightGray;
    public final Color RESPONSE_COLOR = Color.green;
    public final Color ENTITY_COLOR = Color.yellow;
    private HttpAnalyzerView httpView;
    private JFrame parentFrame;

    public SwingTools (JFrame mainFrame) {
        parentFrame = mainFrame;
    }
    public SwingTools (HttpAnalyzerView mainView, JFrame mainFrame) {
        httpView = mainView;
        parentFrame = mainFrame;
    }
    /**
     * Search selected items in Combo
     * Replay TRUE if it is found
     * @param targetCombo
     * @return
     */
    public boolean checkAddComboItem(JComboBox targetCombo) {
        int lenCombo = targetCombo.getItemCount();
        String currentItem = (String) targetCombo.getSelectedItem();
        boolean result = false;
        //System.out.println("Curr"+currentItem);
        for (int i = 0; i < lenCombo; i++) {
            //System.out.println("Check "+targetCombo.getItemAt(i));
            if (targetCombo.getItemAt(i).equals(currentItem)) {
                result = true;
            } else if (currentItem.isEmpty()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Show error frame with additional information
     * @param header Windows header
     * @param error Text of error
     */
    public void showErrorDialog(String header, String error) {
        String message = "\"Information:\n" + error + "\"";
        JOptionPane.showMessageDialog(parentFrame, message, header,
                JOptionPane.ERROR_MESSAGE);
    }

    /*
     * Change text color
     */
    public void spliterOut() {
        Document doc = httpView.replayDataPane.getStyledDocument();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, SPLITER_COLOR);
        //StyleConstants.setBackground(set, Color.blue);
        doc = httpView.replayDataPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(),
                    "----------------------------------------\n", set);
        } catch (BadLocationException ex) {
            Logger.getLogger(SwingTools.class.getName()).log(Level.SEVERE, null, ex);
            new SwingTools(parentFrame).showErrorDialog("Bad location", ex.getLocalizedMessage());
        }
    }

    /**
     * Output text to another Panes.
     * replayTick points destination of output.
     * True = replayPane, False = debugPane.
     *
     * @param text String message
     * @param col Color Text color
     * @param replayTick boolean
     */
    public void setColorTextDoc(String text, Color col, boolean replayTick){
        
        SimpleAttributeSet set = new SimpleAttributeSet();
        set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, col);
        Document doc = httpView.replayDataPane.getStyledDocument();
        if ((replayTick) || (httpView.mergeInfoCheckBox.isSelected())) {
            doc = httpView.replayDataPane.getStyledDocument();
        } else {
            doc = httpView.debugDataPane.getStyledDocument();
        }
        try {
            doc.insertString(doc.getLength(), text, set);
        } catch (BadLocationException ex) {
            Logger.getLogger(SwingTools.class.getName()).log(Level.SEVERE, null, ex);
            new SwingTools(parentFrame).showErrorDialog("Bad location", ex.getLocalizedMessage());
        }
    }

    public void showEntity(HttpEntity entity) {
        // Show content
        String entityString = null;
        try {
            entityString = EntityUtils.toString(entity);
        } catch (IOException ex) {
            Logger.getLogger(SwingTools.class.getName()).log(Level.SEVERE, null, ex);
            new SwingTools(parentFrame).showErrorDialog("IO error", ex.getLocalizedMessage());
        }
        //System.out.println(entityString);
        setColorTextDoc(entityString + "\n", ENTITY_COLOR, true);
    }
}
