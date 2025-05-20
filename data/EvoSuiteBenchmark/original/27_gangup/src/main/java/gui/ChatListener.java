/* $Id: ChatListener.java,v 1.2 2004/03/31 21:12:02 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Bartek Tatkowski
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.2 $
 *
 */

package gui;

import javax.swing.JTextField;
import java.awt.event.*;

/**
 * Listener that listens if the player sends a message to the chat.
 */
class ChatListener extends KeyAdapter {
    
    /**
     * Called whenever a key is typed.
     */ 
    public void keyTyped(KeyEvent e) {
	if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	    ((JTextField)e.getComponent()).setText("");
	}
    }
}
