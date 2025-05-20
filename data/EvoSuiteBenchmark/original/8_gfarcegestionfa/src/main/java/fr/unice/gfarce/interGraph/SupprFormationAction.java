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
public class SupprFormationAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	ModifTableStockage modifTableStockage;
	public SupprFormationAction(FenetrePrincipale fenetre, String texte, Controler c, ModifTableStockage modifTableStockage){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		int ligneSelectionne;
		ligneSelectionne = FenetrePrincipale.tableFormation.getSelectedRow();
		String titre = FenetrePrincipale.outputTitreFormation.getText();
		String date = FenetrePrincipale.outputDateLimiteFormation.getText();
		
		new Zmodel(fenetre.dataFormation, fenetre.columnNamesFormation);
		((Zmodel) FenetrePrincipale.tableFormation.getModel()).removeRow(ligneSelectionne);
		c.deleteFormation(titre, date);
		
		FenetrePrincipale.outputTitreFormation.setText("");
		FenetrePrincipale.outputDateLimiteFormation.setText("");
		FenetrePrincipale.outputPrixFormation.setText("");
		FenetrePrincipale.outputRespFormation.setText("");
	}


}
