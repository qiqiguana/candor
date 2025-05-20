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

package visu.handball.moves.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;


public class TableHandballModel extends AbstractTableModel implements
		HandballModelListener {

	private HandballModel model;

	private boolean disabeld = false;

	public TableHandballModel(HandballModel model) {
		super();
		this.model = model;
		model.addListener(this);
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Spieler";
		case 1:
			return "Beschreibung";
		case 2:
			return "Sequenz";
		case 3:
			return "Verzögerung";
		}
		return "";
	}

	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return model.getEvents().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		List<MoveEvent> events = model.getEvents();
		switch (columnIndex) {
		case 0:
			return events.get(rowIndex).getPlayer();
		case 1:
			return events.get(rowIndex);
		case 2:
			return events.get(rowIndex).getSequenceNr();
		case 3:
			return events.get(rowIndex).getDelay();
		}
		return "";

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// if (disabeld) {
		// return false;
		// }
		if (columnIndex == 3) {
			return true;
		} else {
			return false;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// Delay anpassen
		if (columnIndex == 3) {
			model.setDelay(rowIndex, ((Integer) aValue).intValue());
		}
	}

	public void modelChanged() {
		switch (model.getState()) {
		case EDIT:
		case FULL_ANIMATION_ENDED:
		case ANIMATION:
			disabeld = false;
			break;
		default:
			disabeld = true;
			break;
		}
		fireTableDataChanged();
	}

	public HandballModel getHandballModel() {
		return model;
	}

	public void setDisabled(boolean disabled) {
		this.disabeld = disabled;
		fireTableDataChanged();
	}

	public boolean isDisabled() {
		return disabeld;
	}
}
