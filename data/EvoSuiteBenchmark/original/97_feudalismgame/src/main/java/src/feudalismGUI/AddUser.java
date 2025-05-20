package src.feudalismGUI;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import src.CurrentPlayers;
import src.GameAutoActions;
import src.Knight;


public class AddUser extends JApplet {
	private JPanel panel = new JPanel(new GridLayout(0,1));
	private JPanel panelBtn = new JPanel(new FlowLayout());
	private static ArrayList<Object> users = new ArrayList<Object>();
	private JButton submitBtn = new JButton("Add Player");
	private JPanel knightPanel = new JPanel(new GridLayout(0,2));
	private JPanel leftPanel = new JPanel();
	private JLabel userLabel = new JLabel("Username: ",0);
	private JTextField userTxt = new JTextField(9);
	private JTextField passwdTxt = new JTextField(9);
	private JLabel passwdLabel = new JLabel("Password: ",0);
	private HashMap<String, Knight> allPlayers = CurrentPlayers.getAll();
	private Object[] players = (Object[]) allPlayers.keySet().toArray();
	private JList playersList = new JList(TabbedInterface.listModel);
	private JScrollPane scrollPlayerPane = new JScrollPane(playersList);
	private JLabel rankLbl = new JLabel("Rank: ", 0);
	private JComboBox ranksComboBox = new JComboBox(TabbedInterface.comboBoxModel);


	public AddUser(){
		Container content = this.getContentPane();
		content.setLayout(new FlowLayout());
		fieldsGenerator();
		leftPanel.add(scrollPlayerPane);
		TabbedInterface.listModel.addListDataListener(new ListData());
		for(Object name : players){ TabbedInterface.listModel.addElement(name); }
		playersList.setEnabled(false);
		content.add(leftPanel);
		content.add(panel);
		submitBtn.addActionListener(new addPlayers(this));
		userTxt.addActionListener(new addPlayers(this));
		passwdTxt.addActionListener(new addPlayers(this));
		panelBtn.add(submitBtn);
		panel.add(panelBtn);
		setSize(400,200);
		setVisible(true);
	}
	
	public boolean isThereKing(){
		if(CurrentPlayers.getKing() == null){
			return false;
		}
		else{
			return true;
		}
	}

	public boolean validator(Knight knight){
		String user = knight.getName();
		String passwd = knight.getPassword();
		if(allPlayers.containsKey(user)){
			JOptionPane.showMessageDialog(this, "The username you have provided already exists", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			userTxt.requestFocus();
			userTxt.selectAll();
			return false;
		}
		else if(user.equals("") || user == null){
			JOptionPane.showMessageDialog(this, "You must provide a username", "Error", JOptionPane.ERROR_MESSAGE);
			userTxt.requestFocus();
			return false;
		}

		else if(passwd.equals("") || passwd == null){
			JOptionPane.showMessageDialog(this, "You must provide a password", "Error", JOptionPane.ERROR_MESSAGE);
			passwdTxt.requestFocus();
			return false;
		}
		return true;		
	}

	public void fieldsGenerator(){
			knightPanel.add(userLabel);
			knightPanel.add(userTxt);
			knightPanel.add(passwdLabel);
			knightPanel.add(passwdTxt);
			knightPanel.add(rankLbl);
			knightPanel.add(ranksComboBox);
//			knightPanel.add(new JLabel());
			panel.add(knightPanel);
		}

	private class addPlayers implements ActionListener{
		private JApplet obj;
		
		public addPlayers(JApplet obj){
			this.obj = obj;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			String username = userTxt.getText();
			String password = passwdTxt.getText();
			String rank = TabbedInterface.ranks.getSelectedItem().toString();
			Knight player = new Knight(username, rank, password);
			if(validator(player)){
				if(isThereKing() && ranksComboBox.getSelectedItem().equals("king")){
					ranksComboBox.setSelectedItem("knight");
					ranksComboBox.requestFocus();
					JOptionPane.showMessageDialog(obj, "You cannot create another king while another is existent", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				TabbedInterface.listModel.addElement(player.getName());
				allPlayers.put(player.getName(), player);
				GameAutoActions.saveAll();
				userTxt.setText("");
				passwdTxt.setText("");
				ranksComboBox.setSelectedItem("knight");
				userTxt.requestFocus();
			}
		}
	}
	
	private class ListData implements ListDataListener{

		public void contentsChanged(ListDataEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void intervalAdded(ListDataEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void intervalRemoved(ListDataEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}