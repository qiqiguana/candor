/**
 * 
 */
package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.unice.gfarce.main.Controler;

/**
 * @author alex
 *
 */
public class EnvoiAcceptBourseAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controler c;
	static int ligneSelectionneEtu;
	public EnvoiAcceptBourseAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.c = c;
	}
	public void actionPerformed(ActionEvent e) {
		ligneSelectionneEtu = FenetrePrincipale.tableEtu.getSelectedRow();
		String formdate=null;
		//mise a jour des modif
		
		FenetrePrincipale.outputNom.setEditable(false);
		FenetrePrincipale.outputPrenom.setEditable(false);
		FenetrePrincipale.outputSexe.setEditable(false);
		FenetrePrincipale.outputEmail.setEditable(false);
		FenetrePrincipale.outputDateDeNaissance.setEditable(false);
		FenetrePrincipale.outputPhoto.setFocusable(false);
		FenetrePrincipale.outputDiplome.setEditable(false);
		FenetrePrincipale.outputNationalite.setEditable(false);
		FenetrePrincipale.outputFormation.setEditable(false);
		
		String nom = FenetrePrincipale.outputNom.getText();
		String prenom = FenetrePrincipale.outputPrenom.getText();
		String sexe = FenetrePrincipale.outputSexe.getText();
		String email = FenetrePrincipale.outputEmail.getText();
		String datenaissance = FenetrePrincipale.outputDateDeNaissance.getText();
		String diplome =  FenetrePrincipale.outputDiplome.getText();
		String nationalite = FenetrePrincipale.outputNationalite.getText();
		String formation = FenetrePrincipale.outputFormation.getText();
		String acceptation;
		int bourse;
		
		
		FenetrePrincipale.tableEtu.setValueAt(nom,ligneSelectionneEtu,0);
		FenetrePrincipale.tableEtu.setValueAt(prenom,ligneSelectionneEtu,1);
		FenetrePrincipale.tableEtu.setValueAt(sexe,ligneSelectionneEtu,2);
		FenetrePrincipale.tableEtu.setValueAt(email,ligneSelectionneEtu,3);
		FenetrePrincipale.tableEtu.setValueAt(datenaissance,ligneSelectionneEtu,4);
		
		//pour photo
		
		FenetrePrincipale.tableEtu.setValueAt(diplome,ligneSelectionneEtu,6);
		FenetrePrincipale.tableEtu.setValueAt(nationalite,ligneSelectionneEtu,7);
		FenetrePrincipale.tableEtu.setValueAt(formation,ligneSelectionneEtu,8);
		if(FenetrePrincipale.accept){
			FenetrePrincipale.tableEtu.setValueAt("ok", ligneSelectionneEtu, 9);
			acceptation = "ok";
		}
		else{
			FenetrePrincipale.tableEtu.setValueAt("non", ligneSelectionneEtu, 9);
			acceptation = "non";
			
		}
		if(FenetrePrincipale.bourse){
			FenetrePrincipale.tableEtu.setValueAt(FenetrePrincipale.montant.getText(), ligneSelectionneEtu, 10);
			bourse = Integer.parseInt(FenetrePrincipale.montant.getText());
		}
		else{
			FenetrePrincipale.tableEtu.setValueAt("0", ligneSelectionneEtu, 10);
			bourse = 0;
		}
		
		try {
			byte[] photo=null;
			formdate = formation +"."+ datenaissance; 
			System.out.println(formdate);
			c.updateCandidat(nom, prenom, sexe, email, datenaissance,diplome,photo,nationalite, bourse, acceptation, formdate);
		} catch (Exception e1) {
			System.err.println("erreur d'update candidat "+ formdate);
			e1.printStackTrace();
		}
		FenetrePrincipale.outputNom.setText("");
		FenetrePrincipale.outputPrenom.setText("");
		FenetrePrincipale.outputSexe.setText("");
		FenetrePrincipale.outputEmail.setText("");
		FenetrePrincipale.outputDateDeNaissance.setText("");
		FenetrePrincipale.outputPhoto.setIcon(null);
		FenetrePrincipale.outputDiplome.setText("");
		FenetrePrincipale.outputNationalite.setText("");
		FenetrePrincipale.outputFormation.setText("");
		
		FenetrePrincipale.checkBoxAccept.setSelected(false);
		FenetrePrincipale.checkBoxBourse.setSelected(false);
		FenetrePrincipale.checkBoxAccept.setEnabled(false);
		FenetrePrincipale.checkBoxBourse.setEnabled(false);
		FenetrePrincipale.montant.setText("");
	}

	
}
