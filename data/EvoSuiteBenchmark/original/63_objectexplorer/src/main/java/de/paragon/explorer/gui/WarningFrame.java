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
package de.paragon.explorer.gui;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import de.paragon.explorer.util.LoggerFactory;

public class WarningFrame extends Frame implements WindowListener {
	private static final long	serialVersionUID			= -1954387833083058570L;
	private static final String	UVM_ABT_EDIT_WINDOW_CLOSER	= "uvm.abt.edit.WindowCloser";
	private static final Logger	logger						= LoggerFactory.make();

	public static void main(String[] args) {
		try {
			WarningFrame aWarningFrame = new WarningFrame();
			try {
				Class<?> aCloserClass = Class.forName(WarningFrame.UVM_ABT_EDIT_WINDOW_CLOSER);
				Class<?>[] parmTypes = { Window.class };
				Object[] parms = { aWarningFrame };
				Constructor<?> aCtor = aCloserClass.getConstructor(parmTypes);
				aCtor.newInstance(parms);
			}
			catch (Throwable exc) {
				WarningFrame.logger.error("Exception occurred in main() of WarningFrame", exc);
			}
			aWarningFrame.setVisible(true);
		}
		catch (Throwable t) {
			WarningFrame.logger.error("Exception occurred in main() of WarningFrame", t);
			// System.err.println("Exception occurred in main() of java.awt.Frame");
		}
	}

	private java.awt.List	ivjList1	= null;

	public WarningFrame() {
		super();
		this.initialize();
	}

	public WarningFrame(String title) {
		super(title);
	}

	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void conn0(java.awt.event.WindowEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.dispose();
			// user code begin {2}
			// user code end
		}
		catch (Throwable ivjExc) {
			// user code begin {3}
			// user code end
			this.handleException(ivjExc);
		}
	}

	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private java.awt.List getList() {
		if (this.ivjList1 == null) {
			try {
				this.ivjList1 = new java.awt.List();
				this.ivjList1.setName("List1");
				this.ivjList1.setBackground(java.awt.Color.white);
				this.ivjList1.setBounds(75, 75, 350, 150);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjList1;
	}

	/**
	 * Called whenever the part throws an exception.
	 * 
	 * @param exception
	 *            Throwable
	 */
	private void handleException(Throwable exception) {
		/* Uncomment the following lines to print uncaught exceptions to stdout */
		// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
		// exception.printStackTrace(System.out);
	}

	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initConnections() {
		// user code begin {1}
		// user code end
		this.addWindowListener(this);
	}

	/**
	 * Initialize class
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		// user code begin {1}
		// user code end
		this.setName("WarningFrame");
		this.setName("WarningFrame");
		this.setLayout(null);
		this.setBackground(java.awt.Color.gray);
		this.setBounds(300, 300, 500, 300);
		this.add(this.getList(), this.getList().getName());
		this.initConnections();
		this.setVisible(true);
		// user code begin {2}
		// user code end
	}

	public void setText(String[] text) {
		for (int i = 0; i < text.length; i++) {
			this.getList().add(text[i]);
		}
		return;
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowActivated(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowClosed(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowClosing(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		if ((e.getSource() == this)) {
			this.conn0(e);
		}
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowDeactivated(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowDeiconified(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowIconified(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}

	/**
	 * Method to handle events for the WindowListener interface.
	 * 
	 * @param e
	 *            java.awt.event.WindowEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void windowOpened(java.awt.event.WindowEvent e) {
		// user code begin {1}
		// user code end
		// user code begin {2}
		// user code end
	}
}
