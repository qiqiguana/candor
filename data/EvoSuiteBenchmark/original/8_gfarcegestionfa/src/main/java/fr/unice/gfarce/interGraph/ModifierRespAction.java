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
public class ModifierRespAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public ModifierRespAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		FenetrePrincipale.outputNomResp.setEditable(false);
		FenetrePrincipale.outputPrenomResp.setEditable(false);
		FenetrePrincipale.outputSexeResp.setEditable(true);
		FenetrePrincipale.outputEmailResp.setEditable(true);
		FenetrePrincipale.outputTelResp.setEditable(true);
	}
}
