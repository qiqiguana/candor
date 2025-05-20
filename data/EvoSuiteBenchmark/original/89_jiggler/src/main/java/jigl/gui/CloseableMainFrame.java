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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * CloseableMainFrame allows the user to create a frame that can be closed and exit the program.
 * When the main frame is closed, it will close all the other frames that are open. With just
 * java.awt.Frame it is not possible to close the frame.
 * <p>
 * Among all the methods of the WindowListener, only windowClosing() is implemented.
 */
public class CloseableMainFrame extends CloseableFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	/** Create a CloseableMainFrame with no title. */
	public CloseableMainFrame() {
		this.addWindowListener(this);
	}

	/** Create a CloseableMainFrame with title <code>title</code>. */
	public CloseableMainFrame(String title) {
		super(title);
		this.addWindowListener(this);
	}

	// These are the methods of the WindowListener object.  Only
	// windowClosing() is implemented

	/** Method that detects for when the window is closing and exits the program */
	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == this) {
			this.dispose();
			System.exit(0);
		}
	}

}
