package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.unice.gfarce.main.Controler;

public class CreerUneFormationAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetrePrincipale fenetre;
	private Controler c;
	
	public CreerUneFormationAction(FenetrePrincipale fenetre, String texte,Controler c){
		super(texte);
		this.fenetre = fenetre;
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Vous avez cliqué sur le bouton pour creer une formation.");
		String titreDeLaFormation = FenetrePrincipale.textFieldTitreFormation.getText();
		String dateDeLimite1 = FenetrePrincipale.comboBoxJourDLM.getSelectedItem().toString();
		String dateDeLimite2 = FenetrePrincipale.comboBoxMoisDLM.getSelectedItem().toString();
		String dateDeLimite3 = FenetrePrincipale.comboBoxAnneeDLM.getSelectedItem().toString();
		String dateDeLimite = dateDeLimite1+"/"+dateDeLimite2+"/"+dateDeLimite3;
		String prixDeLaFormation = FenetrePrincipale.textFieldPrixDeLaFormation.getText();
		String[] responsableDeLaFormation = FenetrePrincipale.comboBoxResponsableDeLaFormation.getSelectedItem().toString().split(" ");
		String respNom = responsableDeLaFormation[0];
		String respPrenom = responsableDeLaFormation[1];
		String titreDeLaFormationAAdd = FenetrePrincipale.textFieldTitreFormation.getText()+"."+dateDeLimite;

		if(titreDeLaFormation != null && titreDeLaFormation.length()>0 
				&& 
				dateDeLimite != null && dateDeLimite.length()>0 
				&&
				prixDeLaFormation!=null && prixDeLaFormation.length()>0 
				&& 
				responsableDeLaFormation!=null && responsableDeLaFormation[0]!="Responsable"){

			Object[] donnee = new Object[]{FenetrePrincipale.textFieldTitreFormation.getText(), dateDeLimite, FenetrePrincipale.textFieldPrixDeLaFormation.getText(), FenetrePrincipale.comboBoxResponsableDeLaFormation.getSelectedItem().toString()};
			((Zmodel) FenetrePrincipale.tableFormation.getModel()).addRow(donnee);
			
			FenetrePrincipale.comboBoxFormation1.addItem(titreDeLaFormationAAdd);
			FenetrePrincipale.comboBoxFormation2.addItem(titreDeLaFormationAAdd);
			
			String resp = respNom+" "+respPrenom;
			
			c.createFormation(titreDeLaFormation, dateDeLimite, Integer.parseInt(prixDeLaFormation), resp);
			
			//remise a 0 des champs
			FenetrePrincipale.textFieldTitreFormation.setText("");
			FenetrePrincipale.comboBoxJourDLM.setSelectedIndex(0);
			FenetrePrincipale.comboBoxMoisDLM.setSelectedIndex(0);
			FenetrePrincipale.comboBoxAnneeDLM.setSelectedIndex(0);
			FenetrePrincipale.textFieldPrixDeLaFormation.setText("");
			FenetrePrincipale.comboBoxResponsableDeLaFormation.setSelectedIndex(0);
		}
		else{
			System.out.println("erreur");
			JOptionPane.showMessageDialog(fenetre, "Les champ avec un asterix sont obligatoire.", "Erreur",  JOptionPane.ERROR_MESSAGE);
		}
	}


}



