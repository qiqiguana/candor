package fr.unice.gfarce.interGraph;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.main.Controler;

public class SupprimeEtuAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public SupprimeEtuAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;
	}
	public void actionPerformed(ActionEvent e) {
		int ligneSelectionne;
		ligneSelectionne = FenetrePrincipale.tableEtu.getSelectedRow();
		String nom=FenetrePrincipale.outputNom.getText();
		String prenom = FenetrePrincipale.outputPrenom.getText();
		FenetrePrincipale.outputNom.setEditable(false);
		FenetrePrincipale.outputPrenom.setEditable(false);
		FenetrePrincipale.outputSexe.setEditable(false);
		FenetrePrincipale.outputEmail.setEditable(false);
		FenetrePrincipale.outputDateDeNaissance.setEditable(false);
		FenetrePrincipale.outputPhoto.setFocusable(false);
		FenetrePrincipale.outputDiplome.setEditable(false);
		FenetrePrincipale.outputNationalite.setEditable(false);
		FenetrePrincipale.outputFormation.setEditable(false);
		new Zmodel(FenetrePrincipale.dataEtu, fenetre.columnNamesEtu);
		((Zmodel) FenetrePrincipale.tableEtu.getModel()).removeRow(ligneSelectionne);
		c.deleteIdentite(nom, prenom,TypeIdentite.CANDIDAT);
		
		FenetrePrincipale.outputNom.setText("");
		FenetrePrincipale.outputPrenom.setText("");
		FenetrePrincipale.outputSexe.setText("");
		FenetrePrincipale.outputEmail.setText("");
		FenetrePrincipale.outputDateDeNaissance.setText("");
		FenetrePrincipale.outputPhoto.setIcon(null);
		FenetrePrincipale.outputDiplome.setText("");
		FenetrePrincipale.outputNationalite.setText("");
		FenetrePrincipale.outputFormation.setText("");
		FenetrePrincipale.checkBoxAccept.setEnabled(false);
		FenetrePrincipale.checkBoxBourse.setEnabled(false);
		FenetrePrincipale.checkBoxAccept.setSelected(false);
		FenetrePrincipale.checkBoxBourse.setSelected(false);
		FenetrePrincipale.montant.setText("");
	}

}
