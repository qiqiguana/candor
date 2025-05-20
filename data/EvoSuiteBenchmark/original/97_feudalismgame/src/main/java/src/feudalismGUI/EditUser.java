package src.feudalismGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import src.CurrentPlayers;
import src.GameAutoActions;
import src.Knight;
import src.Map;



public class EditUser extends JPanel {
	private JPanel panelLeft = new JPanel(new FlowLayout());
	private JPanel panelRight = new JPanel(new GridLayout(0,2)); //GridLayout2?
	private JPanel content = new JPanel(new GridBagLayout());
	private GridBagConstraints gbConstraints = new GridBagConstraints();
	private JPanel fiefdomsPnl = new JPanel(new FlowLayout());
	private JPanel fiefBtns = new JPanel(new GridLayout(2,1)); //GridLayout2?
	private JPanel belongingFiefsPnl = new JPanel(new GridBagLayout());
	private JPanel restFiefsPnl = new JPanel(new GridBagLayout());
	private JLabel userLbl = new JLabel("Username: ");
	private JLabel passwdLbl = new JLabel("Password: ");
	private JLabel moneyLbl = new JLabel("Money: ");
	private JLabel scoreLbl = new JLabel("Score: ");
	private JLabel ranksLbl = new JLabel("Rank: ");
	private JLabel belongingFiefsLbl = new JLabel("User's Fiefdoms");
	private JLabel restFiefsLbl = new JLabel("Other Fiefdoms");
	private JTextField fiefOwner = new JTextField(9);
	private JTextField userTxt = new JTextField(9);
	private JTextField passwdTxt = new JTextField(9);
	private JTextField moneyTxt = new JTextField(9);
	private JTextField scoreTxt = new JTextField(9);
	private FiefdomsListModel allFiefsModel = new FiefdomsListModel();
	private FiefdomsListModel belongingListModel = allFiefsModel;
	private FiefdomsListModel belongingFiefsModel = new FiefdomsListModel();
	private FiefdomsListModel restFiefsModel = new FiefdomsListModel();
	private ArrayList<String> fiefdomsArray = new ArrayList<String>(); // probably not necessary
	private HashMap<String, Knight> players = CurrentPlayers.getAll();
	private JList playersList = new JList(TabbedInterface.listModel);
	private JList allFiefsList = new JList(allFiefsModel); // probably not necessary
	private JList belongingFiefsList = new JList(belongingFiefsModel);
	private JList restFiefsList = new JList(restFiefsModel);
	private JScrollPane scrollBelongingFiefsPane = new JScrollPane(belongingFiefsList);
	private JScrollPane scrollRestFiefsPane = new JScrollPane(restFiefsList);
	private JScrollPane scrollPlayerPane = new JScrollPane(playersList);
	private JComboBox ranksComboBox = new JComboBox(TabbedInterface.comboBoxModel);
	
	private JButton addFiefBtn = new JButton("<");
	private JButton removeFiefBtn = new JButton(">");
	private JButton updateBtn = new JButton("Update");
	private JButton deleteBtn = new JButton("Delete");
	
	public EditUser(){
		setSize(300,300);
		// populate all fiefdoms list
		Object[] oFiefdoms = Map.getAll().keySet().toArray();
		for(Object name : oFiefdoms) { allFiefsModel.addElement(name); }
		for(Object name : oFiefdoms) { belongingFiefsModel.addElement(name); }
		for(Object name : oFiefdoms) { restFiefsModel.addElement(name); }
		// only allow one selection at a time
		DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
		dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersList.setSelectionModel(dlsm);
		playersList.addListSelectionListener(new PlayerListListener());
		generateFields();
		enableBtns();
		deleteBtn.setBackground(new Color(255,00,00));
		deleteBtn.addActionListener(new Delete(this));
		updateBtn.addActionListener(new Update(this));

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 &&
						TabbedInterface.listModel.size() > 0 &&
						playersList.getSelectedValue() != null) 
				{
					String player = (String)playersList.getSelectedValue();
					updateFields(player);
				}
				enableBtns();
			}
		};
		
		MouseListener mouseListenerFiefs = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 &&
						restFiefsList.getSelectedValue() != null) 
				{
					fiefOwner.setText(Map.getAll().get(restFiefsList.getSelectedValue()).getOwner());
					
				}
			}
		};
		
		playersList.addMouseListener(mouseListener);
		restFiefsList.addMouseListener(mouseListenerFiefs);
	}

		
		
private void updateFields(String player)
	{
		belongingFiefsModel.removeAllElements();
		restFiefsModel.removeAllElements();
//		allFiefsModel.removeAllElements();
		
		for(Object name : CurrentPlayers.getAll().get(player).getFiefdoms()) 
		{
			belongingFiefsModel.addElement(name); 
		}
		for(Object name : allFiefsModel.getAnArrayList()){
			if(!belongingFiefsModel.getAnArrayList().contains(name))
				restFiefsModel.addElement(name);
		}
		scrollBelongingFiefsPane.updateUI();
		scrollRestFiefsPane.updateUI();
//		restFiefsModel.setModel(allFiefsModel.getAnArrayList());
//		restFiefsModel.copy(allFiefsModel);
//		restFiefsModel.removeThese(belongingFiefsModel.getAnArrayList());
		userTxt.setText(player);
		passwdTxt.setText(players.get(player).getPassword());
		ranksComboBox.setSelectedItem(players.get(player).getRank());
		ranksComboBox.updateUI();
		scoreTxt.setText(String.valueOf(players.get(player).getScore()));
		moneyTxt.setText(String.valueOf(players.get(player).getTotalMoney()));
	}
	
	public boolean isThereKing(){
		if(CurrentPlayers.getKing() == null){
			return false;
		}
		else{
			return true;
		}
	}

	public void enableBtns(){
		if(TabbedInterface.listModel.getSize() == 0 || 
				playersList.isSelectionEmpty()){ 
			deleteBtn.setEnabled(false);
			userTxt.setText("");
			passwdTxt.setText("");
		}else{
			deleteBtn.setEnabled(true);
		}
	}
	
	public boolean validator(){
		if(passwdTxt.getText().equals("")){
			JOptionPane.showMessageDialog(this, "You must provide a password", "Invalid Password", JOptionPane.ERROR_MESSAGE);
			passwdTxt.requestFocus();
			return false;
		}
		return true;
	}
	
	public void generateFields()
	{
		panelLeft.add(scrollPlayerPane);
		panelRight.add(userLbl);
		userTxt.setEditable(false);
		panelRight.add(userTxt);
		panelRight.add(passwdLbl);
		panelRight.add(passwdTxt);
		panelRight.add(ranksLbl);
		panelRight.add(ranksComboBox);
		panelRight.add(scoreLbl);
		panelRight.add(scoreTxt);
		panelRight.add(moneyLbl);
		panelRight.add(moneyTxt);
		panelRight.add(updateBtn);
		panelRight.add(deleteBtn);

		// populate fiefdoms list
		belongingFiefsModel.addListDataListener(new DataListChange());
		scrollBelongingFiefsPane.setSize(10, 10);
		scrollRestFiefsPane.setSize(10, 10);  // DataListener?
		fiefBtns.add(addFiefBtn);
		fiefBtns.add(removeFiefBtn);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		belongingFiefsPnl.add(belongingFiefsLbl, gbConstraints);
		gbConstraints.gridy = 1;
		belongingFiefsPnl.add(scrollBelongingFiefsPane, gbConstraints);
		gbConstraints.gridy = 0;
		restFiefsPnl.add(restFiefsLbl, gbConstraints);
		gbConstraints.gridy = 1;
		restFiefsPnl.add(scrollRestFiefsPane, gbConstraints);
		fiefdomsPnl.add(belongingFiefsPnl);
		fiefdomsPnl.add(fiefBtns);
		fiefdomsPnl.add(restFiefsPnl);
		fiefdomsPnl.add(fiefOwner);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.anchor = GridBagConstraints.EAST;
		content.add(panelLeft, gbConstraints);
		content.add(new JSeparator(SwingConstants.VERTICAL)); // doesn't show
		gbConstraints.gridx = 1;
		gbConstraints.anchor = GridBagConstraints.WEST;
		content.add(panelRight, gbConstraints);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 2;
		gbConstraints.anchor = GridBagConstraints.NORTH;
		content.add(fiefdomsPnl, gbConstraints);
		add(content);
	}

	private class Update implements ActionListener{
		private EditUser obj;

		public Update(EditUser obj){
			this.obj = obj;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			if(validator()){				
				Knight player = players.get(playersList.getSelectedValue());
				System.out.println(player.getName());
				if(isThereKing() &&
						!players.get(player.getName()).getRank().equals("king") &&
						ranksComboBox.getSelectedItem().equals("king")){
					ranksComboBox.setSelectedItem(player.getRank());
					ranksComboBox.requestFocus();
					JOptionPane.showMessageDialog(obj, "To promote this knight to a king you must demote the king first", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				player.setPassword(passwdTxt.getText());
				player.setRank((String)TabbedInterface.ranks.getSelectedItem());
				GameAutoActions.saveAll();
			}
		}
	}
	
	private class Delete implements ActionListener{
		private EditUser obj;

		public Delete(EditUser obj){
			this.obj = obj;
		}

		public void actionPerformed(ActionEvent arg0) {
			int confirm = 0;
			confirm = JOptionPane.showConfirmDialog(obj, "This action cannot cannot be undone!" + playersList.getComponentCount(), "Are you sure you want to delete this user?", JOptionPane.YES_NO_OPTION);
			if(confirm == 0){
				Knight player = CurrentPlayers.getAll().get(playersList.getSelectedValue());
				player.die();
				players.remove(playersList.getSelectedValue());
				TabbedInterface.listModel.removeElement(playersList.getSelectedValue());
				GameAutoActions.saveAll();
				if(TabbedInterface.listModel.getSize() == 0){ deleteBtn.setEnabled(false); }
			}
		}
	}

	private class DataListChange implements ListDataListener{

		public void contentsChanged(ListDataEvent arg0) {
			
		}

		public void intervalAdded(ListDataEvent arg0) {
			
		}

		public void intervalRemoved(ListDataEvent arg0) {
			
		}
		
	}
	// why static?
	static class FiefdomsListModel extends AbstractListModel implements MutableComboBoxModel {
		private ArrayList anArrayList = new ArrayList();
		private Object obj = null;
		
		public FiefdomsListModel(ArrayList anArrayList){
			this.anArrayList = anArrayList;
		}
		
		public FiefdomsListModel() {
			// TODO Auto-generated constructor stub
		}

		public ArrayList getAnArrayList()
		{
			return anArrayList;
		}
		
		public void addElement(Object arg0) {
			anArrayList.add(arg0);
			
		}

		public void insertElementAt(Object arg0, int arg1) {
			anArrayList.add(arg1, arg0);
			
		}

		public void removeElement(Object arg0) {
			anArrayList.remove(arg0);
			
		}

		public void removeElementAt(int arg0) {
			if (anArrayList.size() > arg0)
			{
				anArrayList.remove(arg0);
			}
			else System.err.println("No element at index: " + arg0);
		}

		public void removeAllElements()
		{
			anArrayList.clear();
			
		}
		
		public void removeThese(Collection c)
		{
			anArrayList.removeAll(c);
		}
		
		public Object getSelectedItem() {
			return obj;
		}

		public void setSelectedItem(Object arg0) {
			if (anArrayList.contains(arg0)) { 
				obj = arg0;
//				System.out.println("Set Selected to: " + obj.toString());
			}
			
		}

		public Object getElementAt(int arg0) {
			return anArrayList.get(arg0);
		}

		public int getSize() {
			try{
				return anArrayList.size();
			}catch(NullPointerException e){
				
			}
			return 0;
		}

		public void setModel(ArrayList list){
			this.anArrayList = list;
		}
		
		public void copy(FiefdomsListModel in)
		{
			this.anArrayList = (ArrayList) in.anArrayList.clone();
			this.obj = in.obj;
		}
		
		public void printList()
		{
			for(Object o : anArrayList)
			{
				System.out.println(o.toString());
			}
		}
	}	
	
	public class PlayerListListener implements ListSelectionListener
	{

		public void valueChanged(ListSelectionEvent lse) 
		{
			JList selectedList = (JList) lse.getSource();
			String selected = selectedList.getSelectedValue().toString();
			System.out.println("Selection is " + selected);
			updateFields(selected);
		}
		
	}
}