/* $Id: ChatPanel.java,v 1.6 2004/05/02 23:01:54 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Bartek Tatkowski
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.6 $
 *
 */

package gui;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Hashtable;

import module.GUIModule;
import module.Kernel;
import module.AbstractModule;
import module.Message;
import module.ModuleRegisterException;
import module.Module;
import module.MessageProcessingException;
import module.MessageDeliveryException;
import static module.MessageFactory.*;


/**
 * The chat panel.
 */
public class ChatPanel extends JPanel {

    /** Localized strings. */
    protected ResourceBundle locale;
    
    /** The tabs representing different chat windows. */
    JTabbedPane tabs;
    ChatListener chatlistener;
    //DebugPanel debugPanel;

    GUIModule module;
    Hashtable<Object,ChatTabPanel> chattabs;

    /**
     * This class subscribes to every message topic, so it will
     * receive all messages sent with null recipient.
     */
    @module.mod(name="SnoopyModule")
    private class SnoopyModule extends AbstractModule {

	SnoopyModule(Kernel krn) 
	    throws ModuleRegisterException {
	    super(krn);
	}

	public void sendTextMessage(String topic, String data) {
	    try {
		createMessage(topic, data).send(this);
	    } catch (MessageDeliveryException e) {
		e.printStackTrace(System.err);
	    }
	}

	protected void processKernelMessage(Message m) 
	    throws MessageProcessingException {
	    try {
		//debugPanel.append(m);
		if (m.getBody().equals("REGISTERED")) {
		    subscribe("*");
		}
	    } catch (Exception e) {
		throw new MessageProcessingException(this, m, e);
	    }
	}

	protected void processMessage(Message m) {
	    //debugPanel.append(m);
	}
    };

    /** Creates a new ChatPanel. */
    public ChatPanel(GUIModule mod) {
       
	module = mod;
	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());

	chatlistener = new ChatListener();
	chattabs = new Hashtable<Object,ChatTabPanel>();
	
	tabs = new JTabbedPane();
	setLayout(new BorderLayout());
	add(tabs,BorderLayout.CENTER);
	addTab(module.GENERAL_MESSAGE);
	addTab(module.GROUP_MESSAGE);

	//try {
	    //SnoopyModule snoopy = new SnoopyModule(mod.getKernel());
	    //addDebugTab(snoopy);
	    //snoopy.start();
	    //} catch (ModuleRegisterException e) {
	    // e.printStackTrace(System.err);
	    //}

	tabs.setSelectedComponent(chattabs.get(module.GENERAL_MESSAGE));
	tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    /**
     * Adds a tab to the tabbed pane.
     *
     * @param name The identifying object of this tab. This must be
     * the same object passed to a removeTab call, or when specifying
     * which tab to write to with a writeTo call. Also, when text is
     * written in this inputfield, this Object is sent as target.
     * @return The tab just created.
     */
    public ChatTabPanel addTab(Object id) {
	if (!chattabs.containsKey(id)) {
	    ChatTabPanel panel = new ChatTabPanel(id);
	    chattabs.put(id,panel);
	    tabs.add(panel,id.toString());
	    tabs.setSelectedComponent(panel);
	    return panel;
	}
	return null;
    }

    /*
    private DebugPanel addDebugTab(Module source) {
	if (debugPanel == null) {
	    debugPanel = new DebugPanel(module.getKernel());
	    tabs.add(debugPanel,"Debug");
	}
	return debugPanel;
    }
    */

    /**
     * Removes a tab to the tabbed pane.
     *
     * @param id The identifying object of this tab. This must be
     * the same object passed to the addTab call, or when specifying
     * which tab to write to with a writeTo call. Also, when text is
     * written in this inputfield, this Object is sent as target.
     */
    public void removeTab(Object id) {
	ChatTabPanel panel = chattabs.get(id);
	tabs.remove(panel);
	chattabs.remove(id);
    }

    /**
     * Writes the specified text to the text area identified by id.
     *
     * @param id The identifying object of this tab. This must be
     * the same object passed to the addTab call, or when specifying
     * which tab to write to with a writeTo call. Also, when text is
     * written in this inputfield, this Object is sent as target.
     */
    public void writeTo(Object id, String text) {
	ChatTabPanel ctp = chattabs.get(id);
	if (ctp == null) {
	    ctp = addTab(id);
	}

	ctp.outputarea.append(text + "\n");
	ctp.scroll.getVerticalScrollBar().
	    setValue(ctp.scroll.getVerticalScrollBar().getMaximum());
    }

    
    /**
     * Listener that listens if the player sends a message to the chat.
     */
    private class ChatListener extends KeyAdapter implements ActionListener {
	
	/**
	 * Called whenever a key is typed.
	 */ 
	public void keyTyped(KeyEvent e) {
	    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		sendText();
	    }
	}

	private void sendText() {
	    ChatTabPanel p = (ChatTabPanel)tabs.getSelectedComponent();
	    JTextField txt = p.inputfield;
	    module.sendTextMessage(txt.getText(),p.identifier);
	    txt.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("send")) {
		sendText();
	    }
	    else if (e.getActionCommand().equals("close")) {
		ChatTabPanel p = (ChatTabPanel)tabs.getSelectedComponent();
		if (p != chattabs.get(module.GENERAL_MESSAGE) &&
		    p != chattabs.get(module.GROUP_MESSAGE)) {
		    removeTab(p.identifier);
		}
	    }
	}

    }

    private class ChatTabPanel extends JPanel {

	Object identifier;
	JTextField inputfield;
	JButton inputsend;
	JButton inputclose;
	JTextArea outputarea;
	JScrollPane scroll;
	
	ChatTabPanel(Object id) {
	    identifier = id;
	    
	    setLayout(new BorderLayout());
	    
	    outputarea = new JTextArea();
	    outputarea.setEditable(false);
	    outputarea.setRows(5);
	    scroll = new JScrollPane(outputarea);
	    
	    JPanel inputpanel = new JPanel();
	    inputfield = new JTextField();
	    JLabel inputtext =
		new JLabel(" "+locale.getString("_GUI_INPUTTEXT")+" ");
	    inputsend = new JButton(locale.getString("_GUI_SEND_BUTTON"));
	    inputclose = new JButton(locale.getString("_GUI_CLOSE_BUTTON"));
	    inputsend.setActionCommand("send");
	    inputclose.setActionCommand("close");
	    inputsend.addActionListener(chatlistener);
	    inputclose.addActionListener(chatlistener);
	    
	    JPanel temp = new JPanel();
	    temp.setLayout(new BorderLayout());
	    temp.add(inputsend,BorderLayout.CENTER);
	    temp.add(inputclose,BorderLayout.EAST);
	    
	    inputpanel.setLayout(new BorderLayout());
	    inputpanel.add(inputtext,BorderLayout.WEST);
	    inputpanel.add(inputfield,BorderLayout.CENTER);
	    inputpanel.add(temp,BorderLayout.EAST);
			   
	    
	    add(scroll,BorderLayout.CENTER);
	    add(inputpanel,BorderLayout.SOUTH);
	    
	    inputfield.addKeyListener(chatlistener);
	}
    }
}
