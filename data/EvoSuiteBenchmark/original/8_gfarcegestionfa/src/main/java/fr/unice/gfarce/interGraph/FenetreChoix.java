/**
 * 
 */
package fr.unice.gfarce.interGraph;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

import fr.unice.gfarce.dao.DaoFactoryException;
import fr.unice.gfarce.main.Controler;
/**
 * @author alex
 *
 */
public class FenetreChoix extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controler c;
	Box centreRadio;
	static String choixTypeDeBase;
	static JRadioButton db4oRadio;
	static JRadioButton jpaRadio;
	public FenetreChoix(Controler c){
		super();
		this.c = c;
		build();//On initialise notre fenêtre

	}
	private void build(){
		setTitle("Choix du type de la base"); //On donne un titre à l'application
		setSize(400,200); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	private JPanel buildContentPane(){

		JLabel label = new JLabel("Choisissez entre les deux type de base de donnée :");
		String db4o = "db4o";
		String jpa = "oracle";


		JRadioButton db4oRadio = new JRadioButton(db4o);
		db4oRadio.setMnemonic(KeyEvent.VK_B);
		db4oRadio.setActionCommand(db4o);
		//db4oRadio.setSelected(true);


		JRadioButton jpaRadio = new JRadioButton(jpa);
		jpaRadio.setMnemonic(KeyEvent.VK_B);
		jpaRadio.setActionCommand(jpa);

		ButtonGroup groupe = new ButtonGroup ();

		groupe.add(db4oRadio);
		groupe.add(jpaRadio);

		db4oRadio.addActionListener(this);
		jpaRadio.addActionListener(this);


		//Put the radio buttons in a column in a panel.
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));

		radioPanel.add(label);
		JPanel panel = new JPanel(false);
		panel.setLayout(new FlowLayout());
		panel.add(db4oRadio);
		panel.add(jpaRadio);
		radioPanel.add(panel);


		return radioPanel;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("db4o")){
			try {
				c.setTypeDataBase("db4o");
			} catch (DaoFactoryException e1) {
				System.err.println("erreur dans le choix de la base");
				e1.printStackTrace();
			}
			ChoixDB4O boiteDeDialogueDB4O = null;
			try {
				boiteDeDialogueDB4O = new ChoixDB4O(c);
			} catch (IOException e1) {
				System.err.println("erreur a l'ouvertire fenetre choix db4o");
				e1.printStackTrace();
			}
			this.setVisible(false);
			boiteDeDialogueDB4O.setVisible(true);
		}
		else{
			System.out.println("jpa");
			try {
				c.setTypeDataBase("oracle");
			} catch (DaoFactoryException e1) {
				System.err.println("erreur dans le choix de la base");
				e1.printStackTrace();
			}
			ChoixJPA boiteDeDialogueJPA = new ChoixJPA(c);
			this.setVisible(false);
			boiteDeDialogueJPA.setVisible(true);//On la rend visible

		}

	}

}
