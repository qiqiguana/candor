/**
 * 
 */
package fr.unice.gfarce.interGraph;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.unice.gfarce.connect.GetConfigPersistance;
import fr.unice.gfarce.connect.SetConfigPersistance;
import fr.unice.gfarce.main.Controler;

/**
 * @author alex
 *
 */
public class ChoixJPA extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controler c;
	String connection;
	String base;
	String login;
	String password;
	JTextField champConn;
	JTextField champBase;
	JTextField champLogin;
	JPasswordField champPass;
	ChoixJPA fenChoix;
	public ChoixJPA(Controler c) {
		super();
		this.c=c;
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
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(makeJPAGui());
		JButton envoiJPA = new JButton("envoi");
		envoiJPA.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(champConn.getText() != null && champConn.getText().length()>0 
						&&
						champBase.getText() != null && champBase.getText().length()>0 
						&&
						champLogin.getText() != null && champLogin.getText().length()>0 
						&&
						champPass.getText() != null && champPass.getText().length() > 0){
					connection = champConn.getText();
					base = champBase.getText();
					login = champLogin.getText();
					password = champPass.getText();
					SetConfigPersistance.main(connection,base,login,password);
					try {
						c.launchDatabase();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
					//choixTypeDeBaseAction.chFen.setVisible(true);
					FenetrePrincipale fenetre = new FenetrePrincipale(c);
					
					fenetre.setVisible(true);//On la rend visible
					System.out.println(FenetreChoix.choixTypeDeBase);
					/*
                System.out.println(connection);
                System.out.println(login);
                System.out.println(password);

					 */
				}
				else{
					System.out.println("erreur");
					JOptionPane.showMessageDialog(fenChoix, "Un ou plusieurs champ ne sont pas remplit.", "Erreur",  JOptionPane.ERROR_MESSAGE);

				}
				
			}
		});
		panel.add(envoiJPA);
		return panel;
	}
	JComponent makeJPAGui(){
		String[] info = GetConfigPersistance.main();
		JPanel panelJPA = new JPanel(false);
		Box jpaV = Box.createVerticalBox();
		panelJPA.add(jpaV);
		//infoConn
		Box infoConnH = Box.createHorizontalBox();
		JLabel textConn = new JLabel("connection :");
		infoConnH.add(textConn);
		champConn = new JTextField(info[0],10);
		infoConnH.add(champConn);
		
		Box infoBaseH = Box.createHorizontalBox();
		JLabel textbase = new JLabel("Nom de la base :");
		infoBaseH.add(textbase);
		champBase = new JTextField(info[1],10);
		infoBaseH.add(champBase);

		Box login = Box.createHorizontalBox();
		JLabel textLogin = new JLabel("login :");
		login.add(textLogin);
		champLogin = new JTextField(info[2],10);
		login.add(champLogin);

		Box password = Box.createHorizontalBox();
		JLabel textPass = new JLabel("password :");
		password.add(textPass);
		champPass = new JPasswordField(info[3],10);
		password.add(champPass);

		jpaV.add(infoConnH);
		jpaV.add(infoBaseH);
		jpaV.add(login);
		jpaV.add(password);

		return panelJPA;
	}
}
