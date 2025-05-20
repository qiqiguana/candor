package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.main.Controler;

public class CreerUnEtudiantAction extends AbstractAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetrePrincipale fenetre;
	private Controler c;

	public CreerUnEtudiantAction(FenetrePrincipale fenetre, String texte,Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c =c;
	}

	public void actionPerformed(ActionEvent e) {

		String nom = FenetrePrincipale.textFieldNomEtudiant.getText();
		String prenom= FenetrePrincipale.textFieldPrenomEtudiant.getText();
		String sexe = FenetrePrincipale.comboBoxSexeEtudiant.getSelectedItem().toString(); 
		String email= FenetrePrincipale.textFieldEmailEtudiant.getText();
		String dateDeNaissance1 = FenetrePrincipale.comboBoxJourDDNEtu.getSelectedItem().toString();
		String dateDeNaissance2 = FenetrePrincipale.comboBoxMoisDDNEtu.getSelectedItem().toString();
		String dateDeNaissance3 = FenetrePrincipale.comboBoxAnneeDDNEtu.getSelectedItem().toString();
		String dateDeNaissance = dateDeNaissance1+"/"+dateDeNaissance2+"/"+dateDeNaissance3;
		String image = FenetrePrincipale.textFieldPhotoEtudiant.getText();
		String diplome = FenetrePrincipale.textFieldDiplomeEtudiant.getText();
		String nationnalite = FenetrePrincipale.textFieldNationnaliteEtudiant.getText();
		String formation = FenetrePrincipale.comboBoxFormation1.getSelectedItem().toString();

		if(nom != null && nom.length()>0 
				&& 
				prenom != null && prenom.length()>0 
				&&
				sexe!=null && sexe.length()>0 
				&& 
				email!=null && email.length()>0 
				&& 
				dateDeNaissance!=null && dateDeNaissance.length()>0
				&&
				image!=null && image.length()>0 
				&&
				diplome!=null && diplome.length()>0) {

			System.out.println(nom);

			System.out.println(prenom);

			System.out.println(sexe);

			System.out.println(email);

			System.out.println(dateDeNaissance1 +"/"+ dateDeNaissance2 +"/"+ dateDeNaissance3);

			System.out.println(image);

			System.out.println(diplome);

			System.out.println(formation);

			/*temporaire tant qu'on a pas le champs bourse et la checkbox acceptation*/
			int bourse = 0;
			int acceptation =1;

			System.out.println(nationnalite);

			File f = new File(image);
			byte[] b =null;
			if(f.exists()){
				FileImageInputStream fi;

				try {
					fi = new FileImageInputStream(f);
					int taille = (int)f.length() * 8 ;

					b = new byte[taille];
					fi.read(b) ;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			Object[] donnee = new Object[]{FenetrePrincipale.textFieldNomEtudiant.getText(), FenetrePrincipale.textFieldPrenomEtudiant.getText(), FenetrePrincipale.comboBoxSexeEtudiant.getSelectedItem().toString(), FenetrePrincipale.textFieldEmailEtudiant.getText(), dateDeNaissance,b, FenetrePrincipale.textFieldDiplomeEtudiant.getText(), FenetrePrincipale.textFieldNationnaliteEtudiant.getText(), FenetrePrincipale.comboBoxFormation1.getSelectedItem().toString(), "non", "0"};
			((Zmodel) FenetrePrincipale.tableEtu.getModel()).addRow(donnee);
			try {
				c.createIdentite(nom, prenom, sexe, email,TypeIdentite.CANDIDAT, dateDeNaissance,diplome,image,nationnalite,null, bourse,acceptation,formation);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FenetrePrincipale.textFieldNomEtudiant.setText("");
			FenetrePrincipale.textFieldPrenomEtudiant.setText("");
			FenetrePrincipale.comboBoxSexeEtudiant.setSelectedIndex(0);
			FenetrePrincipale.textFieldEmailEtudiant.setText("");
			FenetrePrincipale.comboBoxJourDDNEtu.setSelectedIndex(0);
			FenetrePrincipale.comboBoxMoisDDNEtu.setSelectedIndex(0);
			FenetrePrincipale.comboBoxAnneeDDNEtu.setSelectedIndex(0);
			FenetrePrincipale.textFieldPhotoEtudiant.setText("");
			FenetrePrincipale.textFieldDiplomeEtudiant.setText("");
			FenetrePrincipale.textFieldNationnaliteEtudiant.setText("");
			FenetrePrincipale.comboBoxFormation1.setSelectedIndex(0);
			FenetrePrincipale.checkBoxAccept.setEnabled(false);
			FenetrePrincipale.checkBoxBourse.setEnabled(false);
		}
		else{
			System.out.println("erreur");
			JOptionPane.showMessageDialog(fenetre, "Les champ avec un asterix sont obligatoire.", "Erreur",  JOptionPane.ERROR_MESSAGE);
		}
	}
}


