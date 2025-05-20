package client.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.Constants;

import messages.global.LoginMsg;
import client.BomberClient;
import client.ClientProperties;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

public class LoginDialog extends MyPanel implements KeyListener, MouseListener, ActionListener {

	MyPanel jPanelRegUser = new MyPanel();
	MyPanel jPanelGuestUser = new MyPanel();
	MyPanel jPanelNewAccount = new MyPanel();

	JLabel jLabelRegUser = new JLabel("User: ");
	JLabel jLabelRegPassword = new JLabel("Password: ");

	JLabel jLabelGuestUser = new JLabel("User: ");

	JLabel jLabelNewUser = new JLabel("User: ");
	JLabel jLabelNewPassword_01 = new JLabel("new Password: ");
	JLabel jLabelNewPassword_02 = new JLabel("retype Password: ");

	JLabel jLabelMessage = new JLabel(" ");

	JTextField jTextFieldGuestUser = new JTextField("");
	JPasswordField jTextFieldGuestPassword = new JPasswordField("");

	JTextField jTextFieldRegUser = new JTextField("");
	JPasswordField jTextFieldRegPassword = new JPasswordField("");

	JTextField jTextFieldNewUser = new JTextField("");
	JPasswordField jTextFieldNewPassword_01 = new JPasswordField("");
	JPasswordField jTextFieldNewPassword_02 = new JPasswordField("");

	MyButton jButtonLogin = new MyButton("Login");
	MyButton jButtonBack = new MyButton("Back");
	MyButton jButtonGast = new MyButton("Login");
	MyButton jButtonNewAccount = new MyButton("New Account");
	MyButton jButtonCreate = new MyButton("Create");
	MyButton jButtonReturn = new MyButton("Back");
	String userName;
	String userType;
	ClientProperties clientProperties;

	public static LoginDialog loginDialog;

	public static LoginDialog init() {
		if (loginDialog == null) {
			loginDialog = new LoginDialog();
		}
		return loginDialog;
	}

	private LoginDialog() {
		setLayout(null);
		setSize(800, 600);
		//setBackground(new Color(255, 150, 255));

		// check properties in properties.xml
		clientProperties = new ClientProperties(Constants.PROPERTIES_FILE);
		userName = clientProperties.getProperty("nickname");
		if (!userName.isEmpty()) {
			userType = clientProperties.getProperty("usertype");
			if (userType.equals("guest")) {
				jTextFieldGuestUser.setText(userName);
			} else {
				jTextFieldRegUser.setText(userName);
				jTextFieldRegPassword.setText(clientProperties
						.getProperty("password"));
			}
			super.repaint();
		}

		//jPanelRegUser.setBackground(Color.cyan);
		//jPanelGuestUser.setBackground(Color.blue);

		jPanelGuestUser.setBounds(150, 215, 500, 60);
		jPanelRegUser.setBounds(150, 285, 500, 150);
		jPanelNewAccount.setBounds(150, 225, 500, 150);

		jLabelMessage.setBounds(150, 150, 500, 50);
		
		jPanelRegUser.setLayout(null);
		jPanelGuestUser.setLayout(null);
		jPanelNewAccount.setLayout(null);

		// TODO Kay
		jPanelRegUser.setPicture("/images/simple_background.png");
		jPanelGuestUser.setPicture("/images/simple_background.png");
		jPanelNewAccount.setPicture("/images/simple_background.png");

		jPanelRegUser.setTitel("Registered User");
		jPanelGuestUser.setTitel("Guest");
		jPanelNewAccount.setTitel("New Account");
		add(jPanelRegUser);
		add(jPanelGuestUser);
		add(jPanelNewAccount);
		jPanelNewAccount.setVisible(false);
		jLabelMessage.setVisible(true);
		addLabel(jLabelMessage);
//		addLabel(jLabelTest);
		jButtonLogin.addActionListener(this);
		jButtonGast.addActionListener(this);
		jButtonNewAccount.addActionListener(this);
		jButtonCreate.addActionListener(this);
		jButtonReturn.addActionListener(this);
		jButtonBack.addActionListener(this);

		jTextFieldRegPassword.addKeyListener(this);
		jTextFieldGuestUser.addKeyListener(this);

		// Guestpanel init:
		jLabelGuestUser.setBounds(20, 20, 70, 25);

		jTextFieldGuestUser.setBounds(90, 20, 250, 25);

		jButtonGast.setBounds(350, 20, 120, 25);
		jButtonBack.setBounds(20, 100, 120, 25);

		jPanelGuestUser.add(jButtonGast);
		jPanelGuestUser.addLabel(jLabelGuestUser);
		jPanelGuestUser.add(jTextFieldGuestUser);

		// LoginPanel init:
		jLabelRegUser.setBounds(20, 20, 100, 25);
		jLabelRegPassword.setBounds(20, 55, 70, 25);

		jTextFieldRegUser.setBounds(90, 20, 250, 25);
		jTextFieldRegPassword.setBounds(90, 55, 250, 25);

		jButtonLogin.setBounds(350, 55, 120, 25);
		jButtonNewAccount.setBounds(350, 20, 120, 25);

		jPanelRegUser.add(jButtonLogin);
		jPanelRegUser.add(jButtonBack);
		jPanelRegUser.add(jButtonNewAccount);
		jPanelRegUser.addLabel(jLabelRegPassword);
		jPanelRegUser.addLabel(jLabelRegUser);
		jPanelRegUser.add(jTextFieldRegPassword);
		jPanelRegUser.add(jTextFieldRegUser);

		// new Accout init:
		jLabelNewUser.setBounds(20, 20, 120, 25);
		jLabelNewPassword_01.setBounds(20, 55, 120, 25);
		jLabelNewPassword_02.setBounds(20, 90, 120, 25);

		jTextFieldNewUser.setBounds(150, 20, 220, 25);
		jTextFieldNewPassword_01.setBounds(150, 55, 220, 25);
		jTextFieldNewPassword_02.setBounds(150, 90, 220, 25);

		jButtonCreate.setBounds(390, 90, 100, 25);
		jButtonReturn.setBounds(390, 20, 100, 25);

		jPanelNewAccount.addLabel(jLabelNewUser);
		jPanelNewAccount.addLabel(jLabelNewPassword_01);
		jPanelNewAccount.addLabel(jLabelNewPassword_02);
		jPanelNewAccount.add(jTextFieldNewPassword_01);
		jPanelNewAccount.add(jTextFieldNewPassword_02);
		jPanelNewAccount.add(jTextFieldNewUser);
		jPanelNewAccount.add(jButtonCreate);
		jPanelNewAccount.add(jButtonReturn);

	}

	/**
	 * ActionListener fuer <br>
	 * jButtonLogin<br>
	 * jButtonGast<br>
	 * jButtonNewAccount<br>
	 * <br>
	 * Es wird geprueft ob alle notwendigen Felder ausgefuellt sind.<br>
	 * Anschliessend wird die entsprechende Session gestartet.
	 * 
	 * 
	 */

	public void actionPerformed(ActionEvent evt) {

		jLabelGuestUser.setForeground(Color.BLACK);
		jLabelRegUser.setForeground(Color.BLACK);
		jLabelRegPassword.setForeground(Color.BLACK);
		jLabelNewPassword_01.setForeground(Color.black);
		jLabelNewPassword_02.setForeground(Color.black);
		jLabelNewUser.setForeground(Color.black);
		jLabelMessage.setText("");
		String s = "";

		if (evt.getSource().equals(jButtonLogin)) {

			if (jTextFieldRegUser.getText().equals("")) {
				s += "<br>- Username";
				jLabelRegUser.setForeground(Color.red);
			}
			if (jTextFieldRegPassword.getText().equals("")) {
				s += "<br>- Password";
				jLabelRegPassword.setForeground(Color.red);
			}
			if (!s.equals("")) {
				jLabelMessage
						.setText("<Html>The following things are requried:" + s
								+ "");
			} else {
				clientProperties = new ClientProperties(
						Constants.PROPERTIES_FILE);
				clientProperties.setProperty("nickname", jTextFieldRegUser
						.getText());
				clientProperties.setProperty("usertype", "registered");
				if (Integer.parseInt(clientProperties.getProperty("savepwd")) == 1) {
					clientProperties.setProperty("password",
							jTextFieldRegPassword.getText());
				} else {
					clientProperties.setProperty("password", "");
				}

				loginSession(jTextFieldRegUser.getText(), jTextFieldRegPassword
						.getText());
				StartFrame.getInstance().setCursorWait();
			}

		} else if (evt.getSource().equals(jButtonGast)) {
			if (jTextFieldGuestUser.getText().equals("")) {
				jLabelMessage.setText("You need a username to play as guest!");
				jLabelGuestUser.setForeground(Color.red);
			} else {
				clientProperties = new ClientProperties(
						Constants.PROPERTIES_FILE);
				clientProperties.setProperty("nickname", jTextFieldGuestUser
						.getText());
				clientProperties.setProperty("usertype", "guest");
				clientProperties.setProperty("password", "");
				guestSession(jTextFieldGuestUser.getText());
				StartFrame.getInstance().setCursorWait();
			}
		} else if (evt.getSource().equals(jButtonNewAccount)) {
			jPanelRegUser.setVisible(false);
			jPanelGuestUser.setVisible(false);
			jPanelNewAccount.setVisible(true);
		} else if (evt.getSource().equals(jButtonCreate)) {
			//System.out.println("Create");
			if (jTextFieldNewUser.getText().equals("")) {
				s += "<br>- Username";
				jLabelNewUser.setForeground(Color.red);
			}
			if (jTextFieldNewPassword_01.getText().equals("")) {
				s += "<br>- Password";
				jLabelNewPassword_01.setForeground(Color.red);
			}
			if (jTextFieldNewPassword_02.getText().equals("")) {
				s += "<br>- Password confirm";
				jLabelNewPassword_02.setForeground(Color.red);
			}
			//System.out.println("Pruefen");
			if (!s.equals("")) {
				jLabelMessage
						.setText("<Html>The following things are requried:" + s
								+ "</Html>");
			} else if (!jTextFieldNewPassword_01.getText().equals(
					jTextFieldNewPassword_02.getText())) {
			//	System.out.println("Passwoerter stimmen nicht!");
				jLabelMessage.setText("The passwords are not equal!");
				jTextFieldNewPassword_01.setText("");
				jTextFieldNewPassword_02.setText("");
				jTextFieldNewPassword_01.requestFocus();
				jLabelNewPassword_01.setForeground(Color.red);
				jLabelNewPassword_02.setForeground(Color.red);

			} else {
				newAccountSession(jTextFieldNewUser.getText(),
						jTextFieldNewPassword_01.getText());
				StartFrame.getInstance().setCursorWait();
			}
		} else if (evt.getSource().equals(jButtonReturn)) {
			jPanelRegUser.setVisible(true);
			jPanelGuestUser.setVisible(true);
			jPanelNewAccount.setVisible(false);
		}else if(evt.getSource().equals(jButtonBack)){
			StartFrame.getInstance().logout();
		}
		repaint();

	}

	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (evt.getSource().equals(jTextFieldGuestUser)) {
				if (!jTextFieldGuestUser.getText().equals("")) {
					guestSession(jTextFieldGuestUser.getText());
					jLabelMessage.setText("GastLogin nach Enter!");
				}
			} else if (evt.getSource().equals(jTextFieldRegPassword)) {
				if (!jTextFieldRegUser.getText().equals("")
						&& !jTextFieldRegPassword.getText().equals("")) {
					loginSession(jTextFieldRegUser.getText(),
							jTextFieldRegPassword.getText());
					jLabelMessage.setText("Login nach Enter!");
				}
			} else if (evt.getSource().equals(jLabelNewPassword_02)) {
				if (!jTextFieldNewPassword_01.getText().equals("")
						&& !jTextFieldNewPassword_02.getText().equals("")
						&& !jTextFieldNewUser.getText().equals("")) {
					newAccountSession(jTextFieldNewUser.getText(),
							jTextFieldNewPassword_01.getText());
				}
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * @author Bj�rn
	 * 
	 * @param username
	 * @param password
	 */
	public void loginSession(String username, String password) {
		LoginMsg msg = new LoginMsg(username, password, LoginMsg.LOGIN);
		BomberClient.getInstance().sendMsg(msg);
		BomberClient.getInstance().setPlayerName(username);
	}

	/**
	 * @author Bj�rn
	 * 
	 * @param username
	 */
	public void guestSession(String username) {
		LoginMsg msg = new LoginMsg(username, LoginMsg.GUEST_LOGIN);
		BomberClient.getInstance().sendMsg(msg);
		BomberClient.getInstance().setPlayerName(username);
	}

	/**
	 * @author Bj�rn
	 * 
	 * @param username
	 * @param password
	 */
	public void newAccountSession(String username, String password) {
		LoginMsg msg = new LoginMsg(username, password, LoginMsg.REGISTER);
		BomberClient.getInstance().sendMsg(msg);
		BomberClient.getInstance().setPlayerName(username);
	}

	public void mouseClicked(MouseEvent e) {
//		actionPerformed(e);

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Handles the action, when the server sends, that the entered username is
	 * wrong
	 * 
	 * @author Bj�rn
	 */
	public void loginWrongUsername() {
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelLogin);
		jLabelMessage.setText("The username you have entered isn't correct!");
		jLabelRegUser.setForeground(Color.red);
	}

	/**
	 * Handles the action, when the server sends, that the entered password is
	 * wrong
	 * 
	 * @author Bj�rn
	 */
	public void loginWrongPassword() {
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelLogin);
		jLabelMessage.setText("The password you have entered isn't correct!");
		jLabelRegPassword.setForeground(Color.red);
	}

	/**
	 * Handles the action, when the server sends, that the entered username is
	 * assigned
	 * 
	 * @author Bj�rn
	 */
	public void loginUsernameTaken() {
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelLogin);
		jLabelMessage.setText("The username you have entered is assigned!");
		jLabelGuestUser.setForeground(Color.red);
		jLabelNewUser.setForeground(Color.red);
	}

	/**
	 * Handles the action, when the server sends, that the entered user is still
	 * logged in
	 * 
	 * @author Bj�rn
	 */
	public void loginUserStillLogedIn() {
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelLogin);
		jLabelMessage
				.setText("The username you have entered is still logged in on this server!");
	}

	/**
	 * Handles the action, when the server sends, that the login was successful
	 * 
	 * @author Bj�rn
	 */
	public void loginSuccessful() {
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelGlobalLobby);
	}


	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		actionPerformed(e);
//		
//	}
}
