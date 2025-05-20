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
package de.paragon.explorer;

import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.paragon.explorer.event.ExplorerFrameEventConverter;
import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.model.ExplorerModelBuilder;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;

/**
 * Kommentar: Die Klasse explorer ist ein reines Hilfskonstrukt zur Eroeffnung
 * des Explorers. Sie ruft den ExplorerModelBuilder auf, das erste Model zu
 * Bauen. Da im Verlauf des Bauens die TextBoxFiguren gebaut werden muessen,
 * wird nach den FontMetrics gefragt (TextBoxFigureBuilder::getFontMetrics()).
 * Diese FontMetrics werden von einem neu zu erzeugenden Frame erfragt (In der
 * Methode ExplorerFigure::getFrame() gibt es eine entsprechende
 * lazy-initialisation.). Dieses Frame wird von dieser Klasse hier in einer
 * Klassenmethode nach einem Frame gefragt, um die Zusammensetzung der
 * Komponenten an zentraler Stelle zu halten. Nach dem Bauen erzeugt die
 * ExplorerFigure einen neuen ExplorerEventConverter. Dieser traegt sich als
 * MouseEvent-Listener und als ActionEventListener ein. Weiter merkt er sich die
 * ExplorerFigure. Der EventConverter wird von dieser Klasse hier abgefragt. Es
 * wird also so sein, dass das Frame niemanden "kennt", der EventConverter kennt
 * die ExplorerFigure, und diese kennt das Frame.
 * 
 * @author Christian Lange, Carsten Heins
 * @since JDK 1.1.6
 * @version 1.0
 */
public class Explorer {
	private static final String	APPLICATION_USE_SYSTEM_FONT_SETTINGS					= "Application.useSystemFontSettings";
	private static final String	EXPLORER_ERROR_STARTING_OBJECT_EXPLORER					= "explorer.error_starting_object_explorer";
	private static final String	EXPLORER_ERROR_NO_LOOK_AN_FEEL_FOUND					= "explorer.error_no_look_an_feel_found";
	// für jdk kleiner 1.6.10:
	private static final String	COM_SUN_JAVA_SWING_PLAF_WINDOWS_WINDOWS_LOOK_AND_FEEL	= "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	// für jdk ab 1.6.10:
	private static final String	COM_SUN_JAVA_SWING_PLAF_NIMBUS_NIMBUS_LOOK_AND_FEEL		= "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final Logger	logger													= LoggerFactory.make();
	private static boolean		embedded												= false;

	public static boolean isEmbedded() {
		return Explorer.embedded;
	}

	public static void setEmbedded(boolean embedded) {
		Explorer.embedded = embedded;
	}

	public static void explore(Object object) {
		ExplorerModelBuilder.getInstance().add1stModel(object);
	}

	public ExplorerDrawingPanel explore(Object object, boolean embedded) {
		Explorer.setEmbedded(embedded);
		return ExplorerModelBuilder.getInstance().add1stModel(object);
	}

	public static ExplorerFrameEventConverter getEventConverter(ExplorerFigure explFig) {
		return new ExplorerFrameEventConverter(explFig);
	}

	public static ExplorerDrawingPanel getPanel() {
		ExplorerDrawingPanel panel = new ExplorerDrawingPanel();
		return panel;
	}

	public static void main(String[] args) {
		try {
			UIManager.put(Explorer.APPLICATION_USE_SYSTEM_FONT_SETTINGS, Boolean.valueOf(false));
			UIManager.setLookAndFeel(Explorer.COM_SUN_JAVA_SWING_PLAF_NIMBUS_NIMBUS_LOOK_AND_FEEL);
		}
		catch (Throwable ex) {
			try {
				UIManager.setLookAndFeel(Explorer.COM_SUN_JAVA_SWING_PLAF_WINDOWS_WINDOWS_LOOK_AND_FEEL);
			}
			catch (Throwable ex1) {
				Explorer.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(Explorer.EXPLORER_ERROR_NO_LOOK_AN_FEEL_FOUND,
						Explorer.COM_SUN_JAVA_SWING_PLAF_WINDOWS_WINDOWS_LOOK_AND_FEEL));
			}
		}
		new Explorer(new JPanel());
	}

	public Explorer(Object object) {
		try {
			ExplorerModelBuilder.getInstance().add1stModel(object);
		}
		catch (Exception ex) {
			Explorer.logger.error(ResourceBundlePurchaser.getMessage(Explorer.EXPLORER_ERROR_STARTING_OBJECT_EXPLORER), ex);
		}
	}
}
