package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;


import messages.global.GlobalChatMsg;
import messages.session.SessionChatMsg;

import client.BomberClient;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;
import client.network.ClientMsgSender;

/**
 * @author Tobi
 * 
 */
public class Chat extends MyPanel implements MouseListener, KeyListener, ActionListener {
	private static Logger logger = Logger.getLogger(BomberClient.class);
	private int id;
	public final int GLOBAL = 1;
	public final int SESSION = 2;

	public void setType(int id) {
		this.id = id;
	}

	public boolean isGlobal() {
		return this.id == GLOBAL;
	}

	public boolean isSession() {
		return this.id == SESSION;
	}

	MyButton jButtonSend = new MyButton("Send");
	JTextField jTextFieldChat = new JTextField("");

	JLabel jLabelChat = new JLabel("Your Text:");

	JTextArea jTextAreaChat = new JTextArea();

	JScrollPane pane = new JScrollPane();

	public Chat(int width, int height) {
		super();
		setLayout(null);
		// setBounds(0,0,800,600);
		setSize(new Dimension(width, height));
		setVisible(true);
		setBackground(new Color(50, 50, 200));

		jTextFieldChat.addKeyListener(this);
		jButtonSend.addKeyListener(this);

		jTextFieldChat.addMouseListener(this);
		jButtonSend.addActionListener(this);

		jLabelChat.setBounds(20, height - 40, 60, 25);
		jTextFieldChat.setBounds(100, height - 40, width - 210, 25);
		jButtonSend.setBounds(width - 100, height - 40, 80, 25);

		jTextAreaChat.setBounds(20, 20, width -40, height- 70);
		jTextAreaChat.setEditable(false);
		
		pane.setBounds(20, 20, width - 40, height -70);

		// pane.setViewportView(new JTextArea());
		pane.setViewportView(jTextAreaChat);
		pane.setAutoscrolls(true);

		// pane.setV

		addLabel(jLabelChat);
		add(jTextFieldChat);
		add(jButtonSend);
		add(pane);
		setPicture("/images/simple_background.png");
	}

	public void actionPerformed(ActionEvent arg0) {
		actionPreformed();
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	public void mouseClicked(MouseEvent arg0) {
		actionPreformed();
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void actionPreformed() {
		if (jTextFieldChat.getText().equals("")) {
			// System.out.println("nothing sent");
			// logger.debug("nothin sent");
			return;
		}
		// System.out.println("sending");
		logger.debug("sending chatmsg");
		
		// jTextAreaChat.setText(jTextAreaChat.getText() + "\n" +
		// jTextFieldChat.getText());
		// System.out.println("Nachricht2: " + jTextFieldChat.getText());

		if (isGlobal()) {
			logger.info("sending chat message glob");
			GlobalChatMsg chatmsg = new GlobalChatMsg(jTextFieldChat.getText());
			BomberClient.getInstance().sendMsg(chatmsg);
		}
		if (isSession()) {
			logger.info("sending chat message sess");
			SessionChatMsg chatmsg = new SessionChatMsg(jTextFieldChat
					.getText());
			BomberClient.getInstance().sendMsg(chatmsg);
		}
		jTextFieldChat.setText("");
		jTextFieldChat.requestFocus();
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent evt) {
		if (evt.getKeyCode() == evt.VK_ENTER) {
			actionPreformed();
		}

	}

	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * @param sender
	 * @param msg
	 */
	public void addChatMessage(String sender, String msg) {
		String newline = "\n";
		if (jTextAreaChat.getText().equals("")) {
			newline = "";
		}
		Calendar cal = Calendar.getInstance();
	    DateFormat df;
	    df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
	    String time = df.format(cal.getTime() ); //18:34:08
	    
		//logger.info(msg+" said by "+sender);
		//Date d = new Date();
		jTextAreaChat.append(newline+"["+time+"] " + sender + ": "
				+ msg);
		jTextAreaChat.setCaretPosition(jTextAreaChat.getDocument().getLength());

	}
	
	/**
	 * 
	 */
	public void clearChat(){
		logger.info("clearing the chat text");
		//logger.info(jTextAreaChat.setText(""));
		this.jTextAreaChat.setText("");
		}

}
