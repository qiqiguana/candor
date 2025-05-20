package src.feudalismGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import src.Fiefdoms;
import src.Map;


public class EditFiefdom extends JPanel
{
//	private JPanel content = new JPanel(new FlowLayout());
	private JPanel leftPanel = new JPanel(new GridBagLayout());
	private JPanel rightPanel = new JPanel(new GridLayout(9, 2));
	private JLabel lFiefdoms;
	private JLabel lLoyal;
	private JLabel lRebellious;
	private JLabel lViking;
	private JLabel lMagyar;
	private JLabel lMuslim;
	private JLabel lPeasants;
	private JLabel lPersonalCastles;
	private JLabel lFiefdomCastles;
	private JLabel lEvents;
	private JTextField tLoyal = new JTextField();
	private JTextField tRebellious = new JTextField();
	private JTextField tViking = new JTextField();
	private JTextField tMagyar = new JTextField();
	private JTextField tMuslim = new JTextField();
	private JTextField tPeasants = new JTextField();
	private JTextField tPersonalCastles = new JTextField();
	private JTextField tFiefdomCastles = new JTextField();
	private JTextArea tEvents = new JTextArea(); // something else?
	private HashMap<String, Fiefdoms> mapFiefdoms;
	private Object[] fiefdoms;
	private DefaultListModel listModel = new DefaultListModel();
	private JList fiefdomsList;
	private JScrollPane scrollFiefdomsPane;
	
	public EditFiefdom()
	{
		setSize(300, 300);
		lFiefdoms = new JLabel("Fiefdoms");
		lLoyal = new JLabel("Loyal Vassals");
		lRebellious = new JLabel("Rebelious Vassals");
		lViking = new JLabel("Vikings");
		lMagyar = new JLabel("Magyars");
		lMuslim = new JLabel("Muslims");
		lPeasants = new JLabel("Peasants");
		lPersonalCastles = new JLabel("Personal Castles");
		lFiefdomCastles = new JLabel("Fiefdom Castles");
		lEvents = new JLabel("Events");
		mapFiefdoms = Map.getAll();
		fiefdoms = mapFiefdoms.keySet().toArray();
//		listModel.addListDataListener(new ListData());  // like AddUser
		for(Object name : fiefdoms) { listModel.addElement(name); }
		fiefdomsList = new JList(listModel);
		fiefdomsList.addMouseListener(new ListMouseListener());
		// only allow one selection at a time
		DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
		dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fiefdomsList.setSelectionModel(dlsm);
		scrollFiefdomsPane = new JScrollPane(fiefdomsList);
		generateFields();
	}

	private void generateFields()
	{
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		leftPanel.add(lFiefdoms, gbConstraints);
		gbConstraints.gridy = 1;
		leftPanel.add(scrollFiefdomsPane, gbConstraints);
		
		rightPanel.add(lLoyal);
		rightPanel.add(tLoyal);
		rightPanel.add(lRebellious);
		rightPanel.add(tRebellious);
		rightPanel.add(lViking);
		rightPanel.add(tViking);
		rightPanel.add(lMagyar);
		rightPanel.add(tMagyar);
		rightPanel.add(lMuslim);
		rightPanel.add(tMuslim);
		rightPanel.add(lPeasants);
		rightPanel.add(tPeasants);
		rightPanel.add(lPersonalCastles);
		rightPanel.add(tPersonalCastles);
		rightPanel.add(lFiefdomCastles);
		rightPanel.add(tFiefdomCastles);
		rightPanel.add(lEvents);
		rightPanel.add(tEvents);
		
		this.add(leftPanel);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(rightPanel);
	}
		
	private class ListMouseListener implements MouseListener
	{
		private void populateFields()
		{
			String selected = (String) fiefdomsList.getSelectedValue();
			Fiefdoms currentFiefdom = mapFiefdoms.get(selected);
			tLoyal.setText(Integer.toString(currentFiefdom.getLoyalVassals().size()));
			tRebellious.setText(Integer.toString(currentFiefdom.getRebelliousVassals().size()));
			tViking.setText(Integer.toString(currentFiefdom.getViking().size()));
			tMagyar.setText(Integer.toString(currentFiefdom.getMagyar().size()));
			tMuslim.setText(Integer.toString(currentFiefdom.getMuslim().size()));
			tPeasants.setText(Integer.toString(currentFiefdom.getPeasants()));
			tPersonalCastles.setText(Integer.toString(currentFiefdom.getPersonalCastles()));
			tFiefdomCastles.setText(Integer.toString(currentFiefdom.getFiefdomCastles()));
			tEvents.setText(checkEvents(currentFiefdom));
		}
		
		private String checkEvents(Fiefdoms currentFiefdom)
		{
			String events = "";
			if (currentFiefdom.getViking().size() > 0)
			{
				if (events != "")
					events += "\n";
				events += "Vikings attacking";
			}
			if (currentFiefdom.getMagyar().size() > 0)
			{
				if (events != "")
					events += "\n";
				events +="Magyars attacking";
			}
			if (currentFiefdom.getMuslim().size() > 0)
			{
				if (events != "")
					events += "\n";
				events +="Muslims attacking";
			}
			if (currentFiefdom.getPeasants() > 0)
			{
				if (events != "")
					events += "\n";
				events += "Peasant uprising";
			}
			if (currentFiefdom.getRebelliousVassals().size() > 0)
			{
				if (events != "")
					events += "\n";
				events += "Rebellious Knights";
			}
			return events;
		}
		
		public void mouseClicked(MouseEvent e) 
		{
			populateFields();
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
	}
}
