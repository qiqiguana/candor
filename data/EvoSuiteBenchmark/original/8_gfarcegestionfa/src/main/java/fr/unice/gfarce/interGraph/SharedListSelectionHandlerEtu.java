package fr.unice.gfarce.interGraph;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.unice.gfarce.main.Controler;

public class SharedListSelectionHandlerEtu implements ListSelectionListener{
	Controler c;
	JTable table;
	static int ligneSelectionneEtu;
	public void valueChanged(ListSelectionEvent e) { 
		ligneSelectionneEtu = FenetrePrincipale.tableEtu.getSelectedRow();
		//public void valueChanged(MouseEvent e) {	

		//ListSelectionModel lsm = (ListSelectionModel)e.getSource(); 

		//System.out.println("a");
		if(ligneSelectionneEtu >-1){
			FenetrePrincipale.outputNom.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 0));
			FenetrePrincipale.outputPrenom.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 1));
			FenetrePrincipale.outputSexe.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 2));
			FenetrePrincipale.outputEmail.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 3));
			FenetrePrincipale.outputDateDeNaissance.setText((String)FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 4));
			byte[] gg = (byte[]) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 5);
			ImageIcon img = new ImageIcon(gg);
			FenetrePrincipale.outputPhoto.setIcon( img);
			//fenetre.outputPhoto.setText((String) fenetre.tableEtu.getValueAt(ligneSelectionneEtu, 5));
			FenetrePrincipale.outputDiplome.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 6));
			FenetrePrincipale.outputNationalite.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 7));
			FenetrePrincipale.outputFormation.setText((String) FenetrePrincipale.tableEtu.getValueAt(ligneSelectionneEtu, 8));
		}
		
	}




}