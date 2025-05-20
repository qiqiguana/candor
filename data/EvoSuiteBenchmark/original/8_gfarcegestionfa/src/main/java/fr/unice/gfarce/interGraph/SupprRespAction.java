/**
 * 
 */
package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.main.Controler;

/**
 * @author alex
 *
 */
public class SupprRespAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public SupprRespAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;
		
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println("action sur le bouton suprime : ");
		int ligneSelectionne;
		ligneSelectionne = FenetrePrincipale.tableResp.getSelectedRow();
		
		String nom=FenetrePrincipale.outputNomResp.getText();
		String prenom=FenetrePrincipale.outputPrenomResp.getText();
		
		new Zmodel(fenetre.dataResp, fenetre.columnNamesResp);
		//interfertir la ligne a supprimer avec celle a lindice 0....
		
		//model.afficheData(fenetre.dataResp);
		((Zmodel) FenetrePrincipale.tableResp.getModel()).removeRow(ligneSelectionne);
		//model.afficheData(fenetre.dataResp);

		c.deleteIdentite(nom, prenom,TypeIdentite.FORMATEUR);
		
		FenetrePrincipale.outputNomResp.setText("");
		FenetrePrincipale.outputPrenomResp.setText("");
		FenetrePrincipale.outputSexeResp.setText("");
		FenetrePrincipale.outputEmailResp.setText("");
		FenetrePrincipale.outputTelResp.setText("");
	}
}



