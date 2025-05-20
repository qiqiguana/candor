package dsachat.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import dsachat.share.hero.FightValue;
import dsachat.share.hero.Hero;
import dsachat.share.hero.Weapon;


public class InternalHeroFrame extends JInternalFrame
implements TreeSelectionListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3492106812827564696L;
	private MainFrame parent;
	private JTree tree;
	private JButton btnExec;
	private JSpinner spnMod;
	private SingleHeroTreeModel model;
	private JCheckBox chkSilent;
	private JComboBox cmbUsers;
	private JRadioButton radAttack;
	private JRadioButton radDefense;
	private JLabel lblATPA;
	private JComboBox cmbWeapon;
	private JButton btnIni;
	private JLabel lblINIval;
	private JLabel lblLEPval;
	private JLabel lblAUPval;
	private JLabel lblASPval;
	private JLabel lblKAPval;
	
	public InternalHeroFrame(MainFrame parent, Hero h)  {
	    super("Hero",
	          true, //resizable
	          false, //closable
	          true, //maximizable
	          true);//iconifiable
	    this.parent=parent;
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    model = new SingleHeroTreeModel(h);
	    createGUI();
	    //Set the window's location.
	    Dimension drawSize = parent.getSize();
		setSize(400,drawSize.height-55);
	    setLocation(drawSize.width-405, 0);
	}
	
	private void createGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JTabbedPane tabbed = new JTabbedPane();
		
		//##### Set first tab #####
		JPanel pnlTree = new JPanel(new GridBagLayout());
		
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
		pnlTree.add(scrTree,c);
		
		JPanel pnlConfigure = new JPanel(new GridBagLayout());
		
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
		
		JLabel lblMod = new JLabel("Modification");
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(lblMod,c);
		
		
		SpinnerModel spnModel =
	        new SpinnerNumberModel(0, //initial value
	                               -20, //min
	                               20, //max
	                               1);//step
		spnMod = new JSpinner(spnModel);
		c.gridx++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(spnMod,c);
		
		
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
		
		chkSilent = new JCheckBox("only for specified user");
		chkSilent.setToolTipText("If this is checked you're not going to see the result");
		c.gridx=1;
		c.gridy++;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlConfigure.add(chkSilent,c);
		
		
		btnExec = new JButton("roll");
		btnExec.setActionCommand("challenge");
		btnExec.addActionListener(parent);
		btnExec.setEnabled(false);
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
		pnlTree.add(pnlConfigure,c);
		
		tabbed.add("Tree view", pnlTree);
		
		
		//##### second tab: select weapon, roll initiative #####
		
		JPanel pnlFight = new JPanel(new GridBagLayout());
		
		JLabel lblWeapon = new JLabel("Select weapon:");
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlFight.add(lblWeapon,c);
		
		//select weapon
		Vector<Weapon> vw = new Vector<Weapon>();
		for(FightValue fv : model.h.getFightvalues()){
			vw.addAll(fv.getWeapons());
		}
		cmbWeapon = new JComboBox(vw);
		cmbWeapon.addActionListener(this);
		c.gridx++;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlFight.add(cmbWeapon,c);
		
		//show at/pa after selecting
		lblATPA = new JLabel();
		Weapon w = (Weapon)cmbWeapon.getItemAt(0);
		lblATPA.setText(w.getType().toString());
		c.gridx=1;
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlFight.add(lblATPA,c);
		
		//Button for initiative
		btnIni = new JButton("roll initiative");
		btnIni.addActionListener(this); //TODO
		btnIni.setActionCommand("initiative");
		btnIni.setToolTipText("does nothing at the moment");
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlFight.add(btnIni,c);
		
		JTabbedPane tabFight = new JTabbedPane();
		
		
		//TODO show armor
		//TODO select special moves
		//TODO select additional difficulty
		//TODO 
		
//		tabbed.add("Fight", pnlFight);
		
		
		//##### third tab: show current Values #####
		JPanel pnlStats = new JPanel(new GridBagLayout());
		c.anchor=GridBagConstraints.NORTHWEST;
		JLabel lblINI = new JLabel("INI:");
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.NONE;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblINI,c);
		
		lblINIval = new JLabel();
		c.gridx++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblINIval,c);
		
		JLabel lblLEP = new JLabel("LEP:");
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblLEP,c);
		lblLEPval = new JLabel();
		c.gridx++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblLEPval,c);
		
		JLabel lblAUP = new JLabel("AUP:");
		c.gridx=0;
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblAUP,c);
		lblAUPval = new JLabel();
		c.gridx++;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.weighty=0.0;
		c.insets=new Insets(5,5,5,5);
		pnlStats.add(lblAUPval,c);
		
		if(model.h.isMagican()){
			JLabel lblASP = new JLabel("ASP:");
			c.gridx=0;
			c.gridy++;
			c.fill=GridBagConstraints.BOTH;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlStats.add(lblASP,c);
			lblASPval = new JLabel();
			c.gridx++;
			c.fill=GridBagConstraints.BOTH;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlStats.add(lblASPval,c);
		}
		
		if(model.h.isPriest()){
			JLabel lblKAP = new JLabel("KAP:");
			c.gridx=0;
			c.gridy++;
			c.fill=GridBagConstraints.BOTH;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlStats.add(lblKAP,c);
			lblKAPval = new JLabel();
			c.gridx++;
			c.fill=GridBagConstraints.BOTH;
			c.weightx=0.0;
			c.weighty=0.0;
			c.insets=new Insets(5,5,5,5);
			pnlStats.add(lblKAPval,c);
		}
		update();
		
		tabbed.add("Values", pnlStats);
		
		//##### add tabbed pane #####
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=1.0;
		c.insets=new Insets(0,0,0,0);
		add(tabbed,c);
		
	}
	
	public void setHero(Hero h){
		model = new SingleHeroTreeModel(h);
	}
	
	public Object getSelectedItem(){
		if(tree.getSelectionPath() != null){
			return tree.getSelectionPath().getLastPathComponent();
		}
		return null;
	}
	
	public String getReceiver(){
		return (String)cmbUsers.getSelectedItem();
	}
	
	public Hero getHero(){
		return model.h;
	}
	
	public int getMod(){
		return (Integer)spnMod.getValue();
	}
	
	public void enableButton(){
		btnExec.setEnabled(true);
	}
	
	public void updateUsers(String[] userlist){
		cmbUsers.removeAllItems();
		cmbUsers.addItem("All");
		for(int i=0; i<userlist.length; i++){
			cmbUsers.addItem(userlist[i]);
		}
	}
	
	public boolean silent(){
		return chkSilent.isSelected();
	}

	public void valueChanged(TreeSelectionEvent e) {
		if(!model.isLeaf(e.getPath().getLastPathComponent())){
			tree.getSelectionModel().clearSelection();
		}
		try{
			if(tree.getSelectionPath().getLastPathComponent().getClass().equals(Weapon.class)){
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
	
	public void updateHero(Hero h){
		model.fireTreeStructureChanged(h);
	}
	
	public boolean isAttack(){
		return radAttack.isSelected();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(cmbWeapon)){
			Weapon w = (Weapon)cmbWeapon.getSelectedItem();
			if(w!=null){
				lblATPA.setText(w.getType().toString());
				model.h.setSelectedWeapon(w);
				//TODO send new hero to server
			}
		}
	}
	
	public void update(){
		lblINIval.setText(String.valueOf(model.h.getCurrINI()));
		lblLEPval.setText(model.h.getCurrLEP()+" ("+model.h.getAttrValue("LEP")+")");
		lblAUPval.setText(model.h.getCurrAUP()+" ("+model.h.getAttrValue("AUP")+")");
		if(model.h.isMagican())
			lblASPval.setText(model.h.getCurrASP()+" ("+model.h.getAttrValue("ASP")+")");
		if(model.h.isPriest())
			lblKAPval.setText(model.h.getCurrKAP()+" ("+model.h.getAttrValue("KAP")+")");
		
		cmbWeapon.setSelectedItem(model.h.getSelectedWeapon());
	}

}
