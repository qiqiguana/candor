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
 *
 * @author bernshausen
 */
public class InternalGmHeroFrame extends JInternalFrame implements TreeSelectionListener {

    /**
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
    public InternalGmHeroFrame(GmFrame parent, Vector<Hero> heros, String name) {
    }

    /**
     * create the components that can be viewed and used
     */
    private void createGUI();

    /**
     * get the selected path from the tree
     * @return the selected tree path
     */
    public TreePath getSelectedPath();

    /**
     * get the name of the receiver for a challenge request or the
     * result of a challenge
     * @return the receiver
     */
    public String getReceiver();

    /**
     * get the difficulty for the challenge
     * @return the difficulty
     */
    public int getMod();

    /**
     * enable the execution button
     */
    public void enableButton();

    /**
     * update the combobox for sending data to
     * when a user connects or disconnects
     * @param userlist
     */
    public void updateUsers(String[] userlist);

    /**
     * the game master should not use silent mode.
     * He has to know all results
     * @return false
     */
    public boolean silent();

    public void valueChanged(TreeSelectionEvent e);

    /**
     * Update the view of this tree
     * @param h the new data
     */
    public void updateHero(Vector<Hero> h);

    /**
     * get the frame name (Heros|Enemies)
     * @return the type of the loaded heros
     */
    public String getFrameName();

    /**
     * return if this challenge is a attack or defense
     * @return true if it is an attack
     */
    public boolean isAttack();

    /**
     * get the data of tree
     * @return the heros stored in the tree
     */
    public Vector<Hero> getData();
}
