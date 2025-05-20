package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import fr.unice.gfarce.main.Controler;

public class EnvoiFormationAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	int ligneSelectionneFormation;
	public EnvoiFormationAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		ligneSelectionneFormation = FenetrePrincipale.tableFormation.getSelectedRow();
		FenetrePrincipale.outputTitreFormation.setEditable(false);
		FenetrePrincipale.outputDateLimiteFormation.setEditable(false);
		FenetrePrincipale.outputPrixFormation.setEditable(false);
		FenetrePrincipale.outputRespFormation.setEditable(false);
		String titreFormation = FenetrePrincipale.outputTitreFormation.getText();
		String dateLimiteDeFormation = FenetrePrincipale.outputDateLimiteFormation.getText();
		String prixFormation = FenetrePrincipale.outputPrixFormation.getText();
		String respFormation = FenetrePrincipale.outputRespFormation.getText();
		if(ligneSelectionneFormation >-1){
			FenetrePrincipale.tableFormation.setValueAt(titreFormation,ligneSelectionneFormation,0);
			FenetrePrincipale.tableFormation.setValueAt(dateLimiteDeFormation,ligneSelectionneFormation,1);
			FenetrePrincipale.tableFormation.setValueAt(prixFormation,ligneSelectionneFormation,2);
			FenetrePrincipale.tableFormation.setValueAt(respFormation,ligneSelectionneFormation,3);

		}
		
		c.updateFormation(titreFormation, dateLimiteDeFormation,Integer.parseInt(prixFormation), respFormation);
		
		//remise a 0 des champ
		FenetrePrincipale.outputTitreFormation.setText("");
		FenetrePrincipale.outputDateLimiteFormation.setText("");
		FenetrePrincipale.outputPrixFormation.setText("");
		FenetrePrincipale.outputRespFormation.setText("");
	}

}
