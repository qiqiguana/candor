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
public class ExportExelAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FenetrePrincipale fenetre;
	Controler c;
	public ExportExelAction(FenetrePrincipale fenetre, String texte, Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;

	}
	public void actionPerformed(ActionEvent e) {
		try{
			String form = FenetrePrincipale.comboBoxFormation2.getSelectedItem().toString();
			c.exportExcel(form);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
