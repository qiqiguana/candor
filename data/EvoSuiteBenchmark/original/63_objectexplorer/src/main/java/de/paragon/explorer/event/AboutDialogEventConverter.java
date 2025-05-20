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
package de.paragon.explorer.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.apache.log4j.Logger;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.gui.AboutDialog;
import de.paragon.explorer.util.LoggerFactory;

/**
 * Diese Klasse erzeugt den About Dialog und verarbeitet die zugehoerigen Events
 */
public class AboutDialogEventConverter extends WindowAdapter implements ActionListener, HyperlinkListener {
	private static final String	THROWABLE									= "aboutdialogeventconverter.throwable";
	private static final String	IOEXCEPTION									= "aboutdialogeventconverter.ioexception";
	private static final String	RUNDLL32_SHELL32_DLL_SHELL_EXEC_RUN_D_L_L	= "rundll32 shell32.dll,ShellExec_RunDLL ";
	private AboutDialog			aboutDialog;
	private ExplorerFigure		explorerFigure;
	private static final Logger	logger										= LoggerFactory.make();

	/**
	 * AboutDialogEventConverter constructor comment.
	 */
	public AboutDialogEventConverter(ExplorerFigure expl) {
		super();
		this.setExplorerFigure(expl);
	}

	public void actionPerformed(ActionEvent event) {
		this.getAboutDialog().setVisible(false);
		this.getExplorerFigure().getFrame().getGlassPane().setVisible(false);
	}

	private AboutDialog getAboutDialog() {
		if (this.aboutDialog == null) {
			this.setAboutDialog(new AboutDialog(this.getExplorerFigure().getFrame(), false));
			this.getAboutDialog().addActionListener(this);
			this.getAboutDialog().addWindowListener(this);
			this.getAboutDialog().setLocationRelativeTo(this.getExplorerFigure().getFrame());
		}
		return this.aboutDialog;
	}

	private ExplorerFigure getExplorerFigure() {
		return this.explorerFigure;
	}

	private void setAboutDialog(AboutDialog newAboutDialog) {
		this.aboutDialog = newAboutDialog;
	}

	private void setExplorerFigure(ExplorerFigure newExplorerFigure) {
		this.explorerFigure = newExplorerFigure;
	}

	public void hyperlinkUpdate(HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				Runtime.getRuntime().exec(AboutDialogEventConverter.RUNDLL32_SHELL32_DLL_SHELL_EXEC_RUN_D_L_L + event.getDescription(), null);
			}
			catch (IOException ex) {
				AboutDialogEventConverter.logger.error(AboutDialogEventConverter.IOEXCEPTION, ex);
			}
			catch (Throwable t) {
				AboutDialogEventConverter.logger.error(AboutDialogEventConverter.THROWABLE, t);
			}
		}
	}

	public final void showDialog() {
		this.getAboutDialog().setVisible(true);
		this.getExplorerFigure().getFrame().getGlassPane().setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent event) {
		this.getExplorerFigure().getFrame().getGlassPane().setVisible(false);
	}
}
