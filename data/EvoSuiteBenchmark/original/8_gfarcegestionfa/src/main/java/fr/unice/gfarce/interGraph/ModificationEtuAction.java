package fr.unice.gfarce.interGraph;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


import fr.unice.gfarce.main.Controler;

public class ModificationEtuAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public ModificationEtuAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;
		
	}
public void actionPerformed(ActionEvent e) {
	FenetrePrincipale.outputNom.setEditable(false);
	FenetrePrincipale.outputPrenom.setEditable(false);
	FenetrePrincipale.outputSexe.setEditable(true);
	FenetrePrincipale.outputEmail.setEditable(true);
	FenetrePrincipale.outputDateDeNaissance.setEditable(true);
	FenetrePrincipale.outputPhoto.setFocusable(true);
	FenetrePrincipale.outputDiplome.setEditable(true);
	FenetrePrincipale.outputNationalite.setEditable(true);
	FenetrePrincipale.outputFormation.setEditable(false);
	FenetrePrincipale.checkBoxAccept.setEnabled(true);
	FenetrePrincipale.checkBoxBourse.setEnabled(true);
	
	}
}
