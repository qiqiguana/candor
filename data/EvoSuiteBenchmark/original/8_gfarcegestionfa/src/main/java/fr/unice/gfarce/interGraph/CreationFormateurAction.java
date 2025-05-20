package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.main.Controler;

public class CreationFormateurAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetrePrincipale fenetre;
	private Controler c;

	public CreationFormateurAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Vous avez cliqué sur le bouton pour creer un formateur.");

		String nom = FenetrePrincipale.textFieldNomResponsable.getText();
		String prenom= FenetrePrincipale.textFieldPrenomResponsable.getText();
		String sexe = FenetrePrincipale.comboBoxSexeResponsable.getSelectedItem().toString(); 
		String email= FenetrePrincipale.textFieldEmailResponsable.getText();
		String telephone = FenetrePrincipale.textFieldTelResponsable.getText();

		if(nom != null && nom.length()>0 
				&&
				prenom != null && prenom.length()>0 ){

			try {
				c.createIdentite(nom, prenom, sexe, email,TypeIdentite.FORMATEUR,null,null,null,null, telephone,0,0,null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			Object[] donnee = new Object[]{FenetrePrincipale.textFieldNomResponsable.getText(), FenetrePrincipale.textFieldPrenomResponsable.getText(), FenetrePrincipale.comboBoxSexeResponsable.getSelectedItem().toString(), FenetrePrincipale.textFieldEmailResponsable.getText(), FenetrePrincipale.textFieldTelResponsable.getText()};
			((Zmodel) FenetrePrincipale.tableResp.getModel()).addRow(donnee);
			FenetrePrincipale.comboBoxResponsableDeLaFormation.addItem(nom+" "+prenom);
			
			FenetrePrincipale.textFieldNomResponsable.setText("");
			FenetrePrincipale.textFieldPrenomResponsable.setText("");
			FenetrePrincipale.comboBoxSexeResponsable.setSelectedIndex(0);
			FenetrePrincipale.textFieldEmailResponsable.setText("");
			FenetrePrincipale.textFieldTelResponsable.setText("");
			

		}
		else{
			System.out.println("erreur");
			JOptionPane.showMessageDialog(fenetre, "Les champ avec un asterix sont obligatoire.", "Erreur",  JOptionPane.ERROR_MESSAGE);

		}



	}
	/*
	public Object[][] addLigneTable(Object[][] donne, Object[] donnee){

		int longueur = donne.length+1;
		int largeur = donne[0].length;

		System.out.println(longueur+" "+largeur);


		Object[][] tmp = new Object[longueur][largeur];

		for(int i=0; i<longueur-1; i++){
			for(int j=0; j<largeur;j++){
				tmp[i][j]= donne[i][j];

			}
		}

		for(int i=0; i<donne[0].length; i++){
			tmp[donne.length][i] = donnee[i];
		}

		donne = tmp;
		affichDonne(tmp);
		return donne;
	}

	public void affichDonne(Object[][] donne){
		for(int i=0; i<donne.length; i++){
			System.out.println();
			for(int j=0; j<donne[0].length;j++){
				System.out.print(donne[i][j]);

			}
		}
	}
	*/


}
