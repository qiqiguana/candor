/******************************************************************************
 *
 * Copyright (c) 1998,99 by Mindbright Technology AB, Stockholm, Sweden.
 *                 www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *****************************************************************************
 * $Author: webjabber $
 * $Date: 2006/06/13 18:43:57 $
 * $Name:  $
 *****************************************************************************/
package mindbright.util;

import java.awt.*;
import java.awt.event.*;

public abstract class AWTConvenience {

    public static class CloseAction implements ActionListener {
	Dialog dialog;
	public CloseAction(Dialog dialog) {
	    this.dialog = dialog;
	}
	public void actionPerformed(ActionEvent e) {
	    dialog.setVisible(false);
	}
    }

    public static class CloseAdapter extends WindowAdapter {
	Button b;
	public CloseAdapter(Button b) {
	    this.b = b;
	}
	public void windowClosing(WindowEvent e) {
	    b.dispatchEvent(new ActionEvent(b, ActionEvent.ACTION_PERFORMED,
					    b.getActionCommand()));
	}
    }

    public static class OKCancelAdapter extends KeyAdapter {
    
	protected static boolean isMRJ = false;
  
	static {
	    try { // see <http://developer.apple.com/qa/java/java17.html>
		isMRJ = (System.getProperty("mrj.version") != null);
	    } catch (Exception e) {
		// applets may not be able to do this
	    }
	}
 
	Button butOK;
	Button butCancel;
  
	public OKCancelAdapter(Button ok, Button cancel) {
	    this.butOK = ok;
	    this.butCancel = cancel;
	}
  
	protected void pushButton(Button target) {
	    if (isMRJ) { // see <http://developer.apple.com/qa/java/java01.html>
		target.dispatchEvent(new KeyEvent(target, KeyEvent.KEY_PRESSED, 
						  System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 
						  (char)KeyEvent.VK_ENTER));
	    } else { // still can work, just no visual feedback
		target.dispatchEvent(new ActionEvent(target, ActionEvent.ACTION_PERFORMED,
						     target.getActionCommand()));
	    }
	}
  
	public void keyReleased(KeyEvent e) {
	    switch(e.getKeyCode()) {
	    case KeyEvent.VK_ENTER : if (butOK != null) pushButton(butOK); break;
	    case KeyEvent.VK_ESCAPE : if (butCancel != null) pushButton(butCancel); break;
	    }
	}
    }

    public final static void placeDialog(Dialog diag) {
	Dimension sDim = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension mDim = diag.getSize();
	int x, y;
	x = ((sDim.width / 2) - (mDim.width / 2));
	y = ((sDim.height / 2) - (mDim.height / 2));
	diag.setLocation(x, y);
    }

    public final static void setBackgroundOfChildren(Container container) {
	Component[] children = container.getComponents();
	container.setBackground(SystemColor.menu);
	for(int i = 0; i < children.length; i++) {
	    if(children[i] instanceof Choice)
		continue;
	    children[i].setBackground(SystemColor.menu);

	    if(children[i] instanceof Container) {
		setBackgroundOfChildren((Container)children[i]);
	    } else if(children[i] instanceof Choice) {
		continue;
	    } else if(children[i] instanceof TextField || children[i] instanceof List) {
		children[i].setBackground(SystemColor.text);
	    } else {
		children[i].setBackground(SystemColor.menu);
	    }
	}
    }

    public final static void setKeyListenerOfChildren(Container container, KeyListener listener,
						      Class typeOfChild) {
	Component[] children = container.getComponents();
	for(int i = 0; i < children.length; i++) {
	    if(children[i] instanceof Choice)
		continue;
	    if(children[i] instanceof Container) {
		setKeyListenerOfChildren((Container)children[i], listener, typeOfChild);
	    } else if(children[i] != null && (typeOfChild == null ||
					      typeOfChild.isInstance(children[i]))) {
		children[i].addKeyListener(listener);
	    }
	}
    }

}
