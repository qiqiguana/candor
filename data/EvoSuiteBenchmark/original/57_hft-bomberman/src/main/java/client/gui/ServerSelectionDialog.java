package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Constants;

import client.BomberClient;
import client.ClientProperties;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

public class ServerSelectionDialog extends MyPanel implements MouseListener, ActionListener {

	/**
	 * @param args
	 */
	JTextField jTextFieldIP = new JTextField();
	JTextField jTextFieldPort = new JTextField();

	// JTextField jTextFieldName = new JTextField();
	//
	// JComboBox jComboBoxLoad = new JComboBox();
	
	// MyButton jButtonLoad = new MyButton("Load");
	//
	// MyButton jButtonStore = new MyButton("Store");

	MyButton jButtonCancel = new MyButton("Exit");
	MyButton jButtonOkay = new MyButton("Ok");

	// MyButton jButtonConfig = new MyButton("Config");

	JLabel jLabelIP = new JLabel("IP:");
	// JLabel jLabelLoad = new JLabel("Load");
	// JLabel jLabelName = new JLabel("Name");
	JLabel jLabelPort = new JLabel("Port:");
	JLabel jLabelSelectedServer = new JLabel("Selected Server");
	JLabel jLabelMessage = new JLabel("");

	String bomberServer;
	String bomberServerPort;
	ClientProperties clientProperties;

	public ServerSelectionDialog() {
		//super();
		// read server from properties.xml
		clientProperties = new ClientProperties(Constants.PROPERTIES_FILE);
		String bomberServer = clientProperties.getProperty("server");
		String bomberServerPort = clientProperties.getProperty("port");

		if (bomberServer.isEmpty() || bomberServerPort.isEmpty()) {
			// no data is saved
			jTextFieldIP.setText(Constants.DEFAULT_SERVER);
			jTextFieldPort.setText(Integer
					.toString(Constants.DEFAULT_SERVER_PORT));
		} else {
			// data is found
			jTextFieldIP.setText(bomberServer);
			jTextFieldPort.setText(bomberServerPort);
		}

		setLayout(null);
		setSize(new Dimension(800, 600));
		// jPanelSelectServer.add(new JLabel("SelectServer"));

		//this.setBackground(new Color(0, 100, 100));

		jButtonOkay.addActionListener(this);
		jButtonCancel.addActionListener(this);

		jLabelIP.setBounds(170, 200, 40, 30);
		// jLabelLoad.setBounds(20, 90, 40, 30);
		// jLabelName.setBounds(20, 140, 40, 30);
		jLabelSelectedServer.setBounds(170, 20, 140, 20);

		jTextFieldIP.setBounds(230, 200, 120, 25);
		jLabelPort.setBounds(400, 200, 30, 25);
		jTextFieldPort.setBounds(450, 200, 80, 25);
		// jComboBoxLoad.setBounds(80, 90, 200, 25);
		// jTextFieldName.setBounds(80, 140, 200, 25);
		// jButtonLoad.setBounds(300, 90, 80, 25);
		// jButtonStore.setBounds(300, 140, 80, 25);
		jButtonCancel.setBounds(230, 255, 80, 25);
		// jButtonConfig.setBounds(200, 190, 80, 25);
		jButtonOkay.setBounds(450, 255, 80, 25);
		
		jLabelMessage.setBounds(230, 300, 300, 25);
		
		addLabel(jLabelPort);
		add(jTextFieldIP);
		// add(jTextFieldName);
		// add(jComboBoxLoad);
		// add(jButtonLoad);
		// add(jButtonStore);
		add(jButtonCancel);
		addLabel(jLabelIP);
		// addLabel(jLabelLoad);
		// addLabel(jLabelName);
		addLabel(jLabelSelectedServer);
		add(jButtonOkay);
		// add(jButtonConfig);
		add(jTextFieldPort);
		addLabel(jLabelMessage);

	}

	/**
	 * @author Bj�rn, christian
	 */
	public void actionPerformedOkay() {
		StartFrame frame = StartFrame.getInstance();
		jLabelMessage.setText("");
		String bomberServer = jTextFieldIP.getText();
		String bomberServerPort = jTextFieldPort.getText();
		// write new properties to properties.xml
		clientProperties.setProperty("server", bomberServer);
		clientProperties.setProperty("port", bomberServerPort);

		BomberClient client = BomberClient.getInstance();
		client.setServerName(bomberServer);
		client.setServerPort(Integer.parseInt(bomberServerPort));
		if(client.connectToSrv()){
			// connected successfully
			frame.changePanel(frame.jPanelLogin);
		}else{
			// connect to server not possible
			//jLabelMessage.setBackground(new Color(255,255,204));
			jLabelMessage.setText("<HTML>Cannot connect to server "+bomberServer+"<br>on port "+bomberServerPort+"");
		}
		
		repaint();
	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getSource().equals(jButtonOkay)) {
			actionPerformedOkay();
		}else
		if (evt.getSource().equals(jButtonCancel)) {
			System.exit(0);
		}
		

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if (evt.getSource().equals(jButtonOkay)) {
			actionPerformedOkay();
		}else
		if (evt.getSource().equals(jButtonCancel)) {
			System.exit(0);
		}
		
	}
}
