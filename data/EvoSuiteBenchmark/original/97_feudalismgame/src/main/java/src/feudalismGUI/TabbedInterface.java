package src.feudalismGUI;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.MutableComboBoxModel;
import javax.swing.UIManager;

public class TabbedInterface extends JApplet{
	private JTabbedPane tabPane = new JTabbedPane();
	public static DefaultListModel listModel = new DefaultListModel();
	public static ArrayList<String> ranksList = new ArrayList<String>();
	public static ArrayListComboBoxModel comboBoxModel = new ArrayListComboBoxModel(ranksList);
	public static JComboBox ranks = new JComboBox();

	public void init(){
		try {
			  UIManager.setLookAndFeel(
			    UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
		}
		ranks.setModel(comboBoxModel);
		comboBoxModel.addElement("knight");
		comboBoxModel.addElement("count");
		comboBoxModel.addElement("duke");
		comboBoxModel.addElement("king");
		comboBoxModel.addElement("administrator");
		
		
		setSize(500,500);
		tabPane.addTab("Add User", new AddUser());
		tabPane.addTab("Edit User", new EditUser());
		tabPane.addTab("Edit Fiefdoms", new EditFiefdom());
		add(tabPane);
		setVisible(true);
	}
	
	static class ArrayListComboBoxModel extends AbstractListModel implements MutableComboBoxModel {
		private ArrayList anArrayList;
		private Object obj;
		
		public ArrayListComboBoxModel(ArrayList anArrayList){
			this.anArrayList = anArrayList;
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
			anArrayList.remove(arg0);
			
		}

		public Object getSelectedItem() {
			return obj;
		}

		public void setSelectedItem(Object arg0) {
			if (anArrayList.contains(arg0)) { 
				obj = arg0;
			}
			
		}

		public Object getElementAt(int arg0) {
			return anArrayList.get(arg0);
		}

		public int getSize() {
			return anArrayList.size();
		}

	}

}
