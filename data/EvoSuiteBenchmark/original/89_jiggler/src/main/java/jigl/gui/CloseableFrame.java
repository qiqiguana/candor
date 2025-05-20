/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.gui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * CloseableFrame allows the user to create a frame that can be closed without exiting the program.
 * With just java.awt.Frame it is not possible to close the frame if you don't implement the
 * windowlistener.
 * <p>
 * Among all the methods of the WindowListener, only windowClosing() is implemented.
 */
public class CloseableFrame extends Frame implements WindowListener {
	private static final long serialVersionUID = 1L;

	/** Creates a CloseableFrame with no title */
	public CloseableFrame() {
		this.addWindowListener(this);
	}

	/**
	 * Creates a CloseableFrame with <CODE>title</CODE>
	 * 
	 * @param title string to display in title bar.
	 */
	public CloseableFrame(String title) {
		super(title);
		this.addWindowListener(this);
	}

	// These are the methods of the WindowListener object.  Only
	// windowClosing() is implemented

	/**
	 * Method that detects for when the window is closing and closes it.
	 * 
	 * @param e windowClosing event.
	 */
	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == this) {
			this.dispose();
		}
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

}
