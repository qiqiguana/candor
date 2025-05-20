package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ChargeImageAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetrePrincipale fenetre;
	public ChargeImageAction(FenetrePrincipale fenetre, String texte){
		super(texte);
		this.fenetre = fenetre;
	}

	public void actionPerformed(ActionEvent e) {
		MonFiltre mft = new MonFiltre(new String[] {"gif"},"les fichiers image (*.gif)");

		JFileChooser choix = new JFileChooser();
		choix.addChoosableFileFilter(mft);

		int retour=choix.showOpenDialog(fenetre);
		if(retour==JFileChooser.APPROVE_OPTION){

			String nomFichier = choix.getSelectedFile().getName();
			String[] str=nomFichier.split("\\.");
			String extentionFichier = str[str.length-1];
			String extentionVoulu = "gif";
			if(extentionFichier.equals(extentionVoulu)){
				String cheminAbsolue = choix.getSelectedFile().getAbsolutePath();
				FenetrePrincipale.textFieldPhotoEtudiant.setText(cheminAbsolue);

				// un fichier a été choisi ( sortie par OK)
				// nom du fichier  choisi 
				System.out.println(choix.getSelectedFile().getName());
				// chemin absolu du fichier choisi
				System.out.println(choix.getSelectedFile().getAbsolutePath());
			}
			else{
				System.out.println("erreur d'extention");
				JOptionPane.showMessageDialog(fenetre, "Ce n'est pas une image gif", "Erreur",  JOptionPane.ERROR_MESSAGE);

			
			}
		}
	}
}
