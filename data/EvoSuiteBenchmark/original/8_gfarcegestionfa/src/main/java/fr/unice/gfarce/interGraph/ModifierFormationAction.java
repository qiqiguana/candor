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
public class ModifierFormationAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public ModifierFormationAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		FenetrePrincipale.outputTitreFormation.setEditable(false);
		FenetrePrincipale.outputDateLimiteFormation.setEditable(false);
		FenetrePrincipale.outputPrixFormation.setEditable(true);
		FenetrePrincipale.outputRespFormation.setEditable(false);
	}


}
