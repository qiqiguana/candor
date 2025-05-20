/**
 * This class provides the tooltip rendering behavior. When the mouse is
 * above a table element it will show the entire content of that cell in
 * a tooltip.
 */

package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

class ToolTipRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(
	JTable table, Object color, boolean isSelected, boolean hasFocus,
	int row, int column) {
	JComponent c = null;
	try {
	    Object v = null;
	    ToolTipManager t = ToolTipManager.sharedInstance();
	    t.setInitialDelay(10);
	    t.setReshowDelay(10);
	    c = (JComponent) super.getTableCellRendererComponent(
		table, color, isSelected, hasFocus, row, column);
	    v = table.getValueAt(row, column);
	    if (v != null) {
		c.setToolTipText(v.toString());
	    }
	} catch (NullPointerException e) {
	    System.err.println("ToolTipRenderer: [NPE] ");
	} catch (ClassCastException e) {
	    System.err.println("ToolTipRenderer: [CCE] ");
	} catch (Exception e) {
	    System.err.println("ToolTipRenderer: [X] ");
	}
	return c;
    }
}
