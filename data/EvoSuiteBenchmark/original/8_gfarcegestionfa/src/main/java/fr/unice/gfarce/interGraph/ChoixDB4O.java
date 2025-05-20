/**
 * 
 */
package fr.unice.gfarce.interGraph;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.unice.gfarce.connect.Db4oConfig;
import fr.unice.gfarce.main.Controler;

/**
 * @author alex
 *
 */
public class ChoixDB4O extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controler c;
	String CheminDeLaBase;
	JTextField champBaseDB4O;
	ChoixDB4O fenChoix;
	public ChoixDB4O(Controler c) throws IOException{
		super();
		this.c=c;
		build();//On initialise notre fenêtre
	}
	private void build() throws IOException{
		setTitle("Choix du type de la base"); //On donne un titre à l'application
		setSize(400,150); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	private JPanel buildContentPane() throws IOException{
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(makeChargeBase());
		JButton envoiDB4O = new JButton("envoi");
		envoiDB4O.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(champBaseDB4O.getText() != null && champBaseDB4O.getText().length()>0 ){
				CheminDeLaBase = champBaseDB4O.getText();
				try {
					Db4oConfig.setInfo(CheminDeLaBase);
					Db4oConfig.setInfo(champBaseDB4O.getText());
					c.launchDatabase();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.err.println("erreur");
					e1.printStackTrace();
				}
				setVisible(false);
				//choixTypeDeBaseAction.chFen.setVisible(true);
				FenetrePrincipale fenetre = new FenetrePrincipale(c);
				
				fenetre.setVisible(true);//On la rend visible
				}
				else{
					System.out.println("erreur");
					JOptionPane.showMessageDialog(fenChoix, "Le champ ne sont pas remplit.", "Erreur",  JOptionPane.ERROR_MESSAGE);

				}
			}
        });
		panel.add(envoiDB4O);
	return panel;
	}
	JComponent makeChargeBase() throws IOException{
		JPanel panel = new JPanel(false);
		panel.setLayout(new FlowLayout());
		champBaseDB4O = new JTextField(Db4oConfig.getInfo(),20);
		JButton boutonChargeImage = new JButton(new ChargeBaseAction(this, "CHARGER"));
		panel.add(champBaseDB4O);
		panel.add(boutonChargeImage);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			Db4oConfig.setInfo(champBaseDB4O.getText());
			c.launchDatabase();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.err.println("erreur de set de base de donnée");
			e1.printStackTrace();
		}
		
	}
}
