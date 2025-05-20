/**
 * 
 */
package fr.unice.gfarce.interGraph;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author alex
 *
 */
public class SharedListSelectionHandlerFormation implements ListSelectionListener {
	//Controler c;
	static int ligneSelectionneFormation;
	public void valueChanged(ListSelectionEvent e) { 
		ligneSelectionneFormation = FenetrePrincipale.tableFormation.getSelectedRow();
		
		if(ligneSelectionneFormation >-1){
			FenetrePrincipale.outputRespArea.setText("");			

			FenetrePrincipale.outputTitreFormation.setText((String) FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 0));
			FenetrePrincipale.outputDateLimiteFormation.setText((String) FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 1));
			FenetrePrincipale.outputPrixFormation.setText(""+ FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 2));
			FenetrePrincipale.outputRespFormation.setText((String) FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 3));
			String forma =(String) FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 0);
			String datef =(String) FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 1);
			String search = ""+forma+"."+datef;
			int cout = 0;
			int montant = 0;
			montant =Integer.parseInt(FenetrePrincipale.tableFormation.getValueAt(ligneSelectionneFormation, 2).toString());
			int i=0;
			String nomm = "";
			String pnomm ="";
			while(i<FenetrePrincipale.tableEtu.getRowCount()){
				
				if(FenetrePrincipale.tableEtu.getValueAt(i, 8).equals(search) ){
					cout+=montant;
					cout -= Integer.parseInt( FenetrePrincipale.tableEtu.getValueAt(i, 10).toString());
					nomm =(String) FenetrePrincipale.tableEtu.getValueAt(i, 0);
					pnomm =(String) FenetrePrincipale.tableEtu.getValueAt(i, 1);
					FenetrePrincipale.outputRespArea.append("[ " +nomm+" " +pnomm+ " ]"+"\n");				
				}
				i++;
			}
			FenetrePrincipale.outputRespArea.append("cout formation "			+ cout);



		}
	}
	
}

