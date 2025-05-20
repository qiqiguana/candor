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
public class EnvoiRespAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	static int ligneSelectionneResp;
	public EnvoiRespAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		new Zmodel(fenetre.dataResp, fenetre.columnNamesResp);
		ligneSelectionneResp = FenetrePrincipale.tableResp.getSelectedRow();


		//mise a jour des modif

		FenetrePrincipale.outputNomResp.setEditable(false);
		FenetrePrincipale.outputPrenomResp.setEditable(false);
		FenetrePrincipale.outputSexeResp.setEditable(false);
		FenetrePrincipale.outputEmailResp.setEditable(false);
		FenetrePrincipale.outputTelResp.setEditable(false);

		String nomResp = FenetrePrincipale.outputNomResp.getText();
		String prenomResp = FenetrePrincipale.outputPrenomResp.getText();
		String sexResp = FenetrePrincipale.outputSexeResp.getText();
		String emailResp = FenetrePrincipale.outputEmailResp.getText();
		String telResp = FenetrePrincipale.outputTelResp.getText();

		FenetrePrincipale.tableResp.setValueAt(nomResp,ligneSelectionneResp,0);
		FenetrePrincipale.tableResp.setValueAt(prenomResp,ligneSelectionneResp,1);
		FenetrePrincipale.tableResp.setValueAt(sexResp,ligneSelectionneResp,2);
		FenetrePrincipale.tableResp.setValueAt(emailResp,ligneSelectionneResp,3);
		FenetrePrincipale.tableResp.setValueAt(telResp,ligneSelectionneResp,4);

		try {
			c.updateResponsable(nomResp, prenomResp, sexResp, emailResp, telResp);
		} 
		catch (Exception e1) {
			System.err.println("erreur d'update");
			e1.printStackTrace();
		}
		FenetrePrincipale.outputNomResp.setText("");
		FenetrePrincipale.outputPrenomResp.setText("");
		FenetrePrincipale.outputSexeResp.setText("");
		FenetrePrincipale.outputEmailResp.setText("");
		FenetrePrincipale.outputTelResp.setText("");
	}
}
