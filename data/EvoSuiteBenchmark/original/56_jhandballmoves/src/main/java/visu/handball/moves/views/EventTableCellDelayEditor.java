/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package visu.handball.moves.views;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 * Die Klasse ist zuständig für das Ändern des Delay-Wertes innerhalb der Tabelle.
 * Dazu wird ein JSpinner-Objekt als Editor-Komponente in die Tabelle eingefügt.
 * 
 * @author Tommy
 *
 */
public class EventTableCellDelayEditor extends AbstractCellEditor implements
		TableCellEditor{

	private JSpinner spinner;

	public EventTableCellDelayEditor() {
		spinner = new JSpinner();
		//nur Werte zwischen 0 und 100 auswaehlbar
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
		spinner.setModel(model);
	}

	public Object getCellEditorValue() {
		return spinner.getValue();
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		spinner.setValue(value);
		return spinner;
	}
	
}
