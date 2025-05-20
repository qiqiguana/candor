package dsachat.gm.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import dsachat.share.hero.Hero;
import dsachat.share.hero.Weapon;

/**
 * this displays the heros or the enemies
 * it can send challenges or challenge requests.
 * @author bernshausen
 *
 */
public class InternalGmHeroFrame extends JInternalFrame implements TreeSelectionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8647088721012280920L;
	private GmFrame parent;
	private JTree tree;
	private JButton btnExec;
	private JSpinner spnMod;
	private MultiHeroTreeModel model;
	private JComboBox cmbUsers;
	private JRadioButton radAttack;
	private JRadioButton radDefense;
	private String frameName;
	
	/**
	 * set up a new window
	 * @param parent the parent component and action listener
	 * @param heros the data to display in tree
	 * @param name the name and caption of this frame 
	 */
	public InternalGmHeroFrame(GmFrame parent, Vector<Hero> heros, String name)  {
	    super(name,
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    frameName=name;
	    this.parent=parent;
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    model = new MultiHeroTreeModel(name, heros);
	    createGUI();
	    //Set the window's location.
	    Dimension drawSize = parent.getSize();
		setSize(400,drawSize.height-55);
	    setLocation(drawSize.width-405, 0);
	}
	
	/**
	 * create the components that can be viewed and used
	 */
	private void createGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//the tree
		tree = new JTree(model);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		JScrollPane scrTree = new JScrollPane(tree);
		
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=0.8;
		c.insets=new Insets(0,0,5,0);
		add(scrTree,c);
		
		//the rest is done in a panel
		JPanel pnlConfigure = new JPanel(new GridBagLayout());
		
		//enemies can execute attacks and defenses
		if(!frameName.equals("Heros")){
			//button if attack or not.
			radAttack = new JRadioButton("attack");
			radAttack.setEnabled(false);
			c.gridx=0;
			c.gridy=0;
			c.fill=GridBagConstraints.NONE;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlConfigure.add(radAttack,c);
			
			radDefense = new JRadioButton("defense");
			radDefense.setEnabled(false);
			c.gridx++;
			c.fill=GridBagConstraints.NONE;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlConfigure.add(radDefense,c);
			
			ButtonGroup bg = new ButtonGroup();
			bg.add(radAttack);
			bg.add(radDefense);
			
			JLabel lblUser = new JLabel("To user");
			c.gridx=0;
			c.gridy++;
			c.fill=GridBagConstraints.NONE;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlConfigure.add(lblUser,c);
			
			cmbUsers = new JComboBox();
			cmbUsers.addItem("All");
			c.gridx++;
			c.fill=GridBagConstraints.NONE;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlConfigure.add(cmbUsers,c);
		}
		
		//set a difficulty for the challenge
		JLabel lblMod = new JLabel("Modification");
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(lblMod,c);
		
		
		SpinnerModel model =
	        new SpinnerNumberModel(0, //initial value
	                               -20, //min
	                               20, //max
	                               1);//step
		spnMod = new JSpinner(model);
		c.gridx++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(spnMod,c);
		
		//execute the challenge or challenge request
		btnExec = new JButton("roll");
		btnExec.setActionCommand("challenge");
		btnExec.addActionListener(parent);
		btnExec.setEnabled(true);
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(btnExec,c);
		
		c.gridx=0;
		c.gridy=1;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=0.2;
		c.insets=new Insets(5,0,0,0);
		add(pnlConfigure,c);
		
		//TODO Button for initiative
		
		setVisible(true);
	}
	
	/**
	 * get the selected path from the tree
	 * @return the selected tree path
	 */
	public TreePath getSelectedPath(){
		if(tree.getSelectionPath() != null){
			return tree.getSelectionPath();
		}
		return null;
	}
	
	/**
	 * get the name of the receiver for a challenge request or the
	 * result of a challenge
	 * @return the receiver
	 */
	public String getReceiver(){
		return (String)cmbUsers.getSelectedItem();
	}
	
	/**
	 * get the difficulty for the challenge
	 * @return the difficulty
	 */
	public int getMod(){
		return (Integer)spnMod.getValue();
	}
	
	/**
	 * enable the execution button
	 */
	public void enableButton(){
		btnExec.setEnabled(true);
	}
	
	/**
	 * update the combobox for sending data to
	 * when a user connects or disconnects
	 * @param userlist
	 */
	public void updateUsers(String[] userlist){
		cmbUsers.removeAllItems();
		cmbUsers.addItem("All");
		for(int i=0; i<userlist.length; i++){
			cmbUsers.addItem(userlist[i]);
		}
	}
	
	/**
	 * the game master should not use silent mode.
	 * He has to know all results 
	 * @return false
	 */
	public boolean silent(){
		return false;			
	}

	public void valueChanged(TreeSelectionEvent e) {
		//do not select anything when the user doesn't select a leaf
		//GM can't execute fights for the clients
		if(!model.isLeaf(e.getPath().getLastPathComponent()) ||
				( e.getPath().getLastPathComponent().getClass().equals(Weapon.class)) &&
					frameName.equals("Heros")){
			tree.getSelectionModel().clearSelection();
			btnExec.setEnabled(false);
		}else{
			//let the GM execute a challenge 
			btnExec.setEnabled(true);
		}
		//enable attack of defense when the GM selected a weapon of an enemy
		if(!frameName.equals("Heros")){
			try{
				if(tree.getSelectionPath().getLastPathComponent().getClass()
						.equals(Weapon.class)){
					radDefense.setEnabled(true);
					radAttack.setEnabled(true);
				}else{
					radDefense.setEnabled(false);
					radAttack.setEnabled(false);
				}
			}catch(Exception e1){
				//not so bad
				radDefense.setEnabled(false);
				radAttack.setEnabled(false);
				return;
			}
		}
	}
	
	/**
	 * Update the view of this tree
	 * @param h the new data
	 */
	public void updateHero(Vector<Hero> h){
		model.fireTreeStructureChanged(h);
	}
	
	/**
	 * get the frame name (Heros|Enemies)
	 * @return the type of the loaded heros
	 */
	public String getFrameName() {
		return frameName;
	}

	/**
	 * return if this challenge is a attack or defense
	 * @return true if it is an attack
	 */
	public boolean isAttack(){
		return radAttack.isSelected();
	}
	
	/**
	 * get the data of tree
	 * @return the heros stored in the tree
	 */
	public Vector<Hero> getData(){
		return model.heros;
	}
}
