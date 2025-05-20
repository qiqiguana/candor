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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.TableHandballModel;

/**
 * Der Renderer ist zuständig für das Rendern aller Daten des JTable.
 * Für jeden Wert wird ein JLabel (aus Superklasse) entsprechend formatiert.
 * 
 * @author Tommy
 */
public class EventTableCellRenderer extends DefaultTableCellRenderer {

	private static Color actualColor = new Color(116, 182, 214);

	private static Color oldColor = new Color(160, 207, 207);

	private static Color selektionsHintergrund = new Color(128, 128, 255);

	public EventTableCellRenderer() {
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		MoveEvent event = ((TableHandballModel) table.getModel())
				.getHandballModel().getEvents().get(row);
		setForeground(Color.BLACK);

		if (event.getSequenceNr() % 2 == 0)
			setBackground(actualColor);
		else
			setBackground(oldColor);

		int selectedRow = ((TableHandballModel) table.getModel())
				.getHandballModel().getEvents().indexOf(
						((TableHandballModel) table.getModel())
								.getHandballModel().getActualMoveEvent());

		if (isSelected || row == selectedRow) {
			setBackground(selektionsHintergrund);
			setForeground(Color.WHITE);
		}

		if (column == 0 || column == 2 || column == 3)
			setHorizontalAlignment(SwingConstants.CENTER);
		else
			setHorizontalAlignment(SwingConstants.LEFT);

		if (value != null)
			setText(value.toString());
		
		if (((TableHandballModel) table.getModel()).isDisabled()) {
			table.setEnabled(false);
		} else {
			table.setEnabled(true);
		}
		
		return this;
	}
}
