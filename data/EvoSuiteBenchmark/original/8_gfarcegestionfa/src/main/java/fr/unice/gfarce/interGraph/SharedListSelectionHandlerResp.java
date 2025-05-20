/**
 * 
 */
package fr.unice.gfarce.interGraph;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.unice.gfarce.main.Controler;

/**
 * @author alex
 *
 */
public class SharedListSelectionHandlerResp implements ListSelectionListener {
	Controler c;
	JTable tableResp;
	static int ligneSelectionneResp;
	public void valueChanged(ListSelectionEvent e) { 
		ligneSelectionneResp = FenetrePrincipale.tableResp.getSelectedRow();


		if(ligneSelectionneResp >-1){

			FenetrePrincipale.outputNomResp.setText((String) FenetrePrincipale.tableResp.getValueAt(ligneSelectionneResp, 0));
			FenetrePrincipale.outputPrenomResp.setText((String) FenetrePrincipale.tableResp.getValueAt(ligneSelectionneResp, 1));
			FenetrePrincipale.outputSexeResp.setText((String) FenetrePrincipale.tableResp.getValueAt(ligneSelectionneResp, 2));
			FenetrePrincipale.outputEmailResp.setText((String) FenetrePrincipale.tableResp.getValueAt(ligneSelectionneResp, 3));
			FenetrePrincipale.outputTelResp.setText((String) FenetrePrincipale.tableResp.getValueAt(ligneSelectionneResp, 4));

		}
	}

}
