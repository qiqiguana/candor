/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.util;

import java.awt.Color;

import de.paragon.explorer.figure.TextBoxFigure;

/**
 * Diese Klasse stellt die zu benutzenden Farben zur Verfuegung
 */
public class ExplorerColorManager {
	private static final int	NUMBER_255	= 255;
	private static final int	NUMBER_153	= 153;
	private static final int	NUMBER_0	= 0;
	private static final int	NUMBER_51	= 51;

	public static java.awt.Color getDefaultExplorerObjectBackground() {
		if (ExplorerColorManager.defaultExplorerObjectBackground == null) {
			ExplorerColorManager.setDefaultExplorerObjectBackground(new java.awt.Color(ExplorerColorManager.NUMBER_153, ExplorerColorManager.NUMBER_153,
					ExplorerColorManager.NUMBER_255));
		}
		return ExplorerColorManager.defaultExplorerObjectBackground;
	}

	private static void setDefaultExplorerFocusBackground(java.awt.Color newDefaultExplorerFocusBackground) {
		ExplorerColorManager.defaultExplorerFocusBackground = newDefaultExplorerFocusBackground;
	}

	private static void setDefaultExplorerObjectBackground(java.awt.Color newDefaultExplorerObjectBackground) {
		ExplorerColorManager.defaultExplorerObjectBackground = newDefaultExplorerObjectBackground;
	}

	private Color			objectBackground;
	private Color			attributeBackground;
	private Color			attributeForeground;
	private Color			objectForeground;
	private Color			focusForeground;
	private Color			focusBackground;
	private Color			copyBackground;
	private static Color	defaultExplorerObjectBackground;
	private static Color	defaultExplorerFocusBackground;

	public static Color getDefaultExplorerFocusBackground() {
		if (ExplorerColorManager.defaultExplorerFocusBackground == null) {
			ExplorerColorManager.setDefaultExplorerFocusBackground(new java.awt.Color(ExplorerColorManager.NUMBER_51, ExplorerColorManager.NUMBER_0,
					ExplorerColorManager.NUMBER_153));
		}
		return ExplorerColorManager.defaultExplorerFocusBackground;
	}

	/**
	 * ExplorerColorManager constructor comment.
	 */
	public ExplorerColorManager() {
		super();
	}

	private Color getAttributeBackground() {
		if (this.attributeBackground == null) {
			this.setAttributeBackground(this.getDefaultAttributeBackground());
		}
		return this.attributeBackground;
	}

	private Color getAttributeForeground() {
		if (this.attributeForeground == null) {
			this.setAttributeForeground(this.getDefaultForeground());
		}
		return this.attributeForeground;
	}

	public Color getBackground(TextBoxFigure tebofi) {
		if (tebofi.getModel().isAttributeModel()) {
			return this.getAttributeBackground();
		}
		return this.getObjectBackground();
	}

	public Color getCopyBackground() {
		if (this.copyBackground == null) {
			this.setCopyBackground(this.getDefaultCopyBackground());
		}
		return this.copyBackground;
	}

	private Color getDefaultAttributeBackground() {
		return Color.white;
	}

	public Color getDefaultCopyBackground() {
		return Color.red;
	}

	private Color getDefaultFocusBackground() {
		return ExplorerColorManager.getDefaultExplorerFocusBackground();
	}

	private Color getDefaultFocusForeground() {
		return Color.white;
	}

	private Color getDefaultForeground() {
		return Color.black;
	}

	private Color getDefaultObjectBackground() {
		return ExplorerColorManager.getDefaultExplorerObjectBackground();
	}

	public Color getFocusBackground() {
		if (this.focusBackground == null) {
			this.setFocusBackground(this.getDefaultFocusBackground());
		}
		return this.focusBackground;
	}

	public Color getFocusForeground() {
		if (this.focusForeground == null) {
			this.setFocusForeground(this.getDefaultFocusForeground());
		}
		return this.focusForeground;
	}

	public Color getForeground(TextBoxFigure tebofi) {
		if (tebofi.getModel().isAttributeModel()) {
			return this.getAttributeForeground();
		}
		return this.getObjectForeground();
	}

	private Color getObjectBackground() {
		if (this.objectBackground == null) {
			this.setObjectBackground(this.getDefaultObjectBackground());
		}
		return this.objectBackground;
	}

	private Color getObjectForeground() {
		if (this.objectForeground == null) {
			this.setObjectForeground(this.getDefaultForeground());
		}
		return this.objectForeground;
	}

	public void setAttributeBackground(Color newAttributeBackground) {
		this.attributeBackground = newAttributeBackground;
	}

	public void setAttributeForeground(Color newAttributeForeground) {
		this.attributeForeground = newAttributeForeground;
	}

	public void setCopyBackground(Color newCopyBackground) {
		this.copyBackground = newCopyBackground;
	}

	public void setFocusBackground(Color newFocusBackground) {
		this.focusBackground = newFocusBackground;
	}

	public void setFocusForeground(Color newFocusForeground) {
		this.focusForeground = newFocusForeground;
	}

	public void setObjectBackground(Color newObjectBackground) {
		this.objectBackground = newObjectBackground;
	}

	public void setObjectForeground(Color newObjectForeground) {
		this.objectForeground = newObjectForeground;
	}
}
