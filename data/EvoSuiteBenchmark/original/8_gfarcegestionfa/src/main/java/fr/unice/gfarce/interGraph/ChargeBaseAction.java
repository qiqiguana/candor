package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
/**
 * 
 * 
 * @author alex
 *
 *
 */
public class ChargeBaseAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChoixDB4O choixDB4OGui;
	public ChargeBaseAction(ChoixDB4O choixDB4OGui, String texte){
		super(texte);
		this.choixDB4OGui = choixDB4OGui;
	}
	
	/**
	 * ouvre une fenetre
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {

		JFileChooser choix = new JFileChooser();

		choix.showOpenDialog(choixDB4OGui);

		String cheminAbsolue = choix.getSelectedFile().getAbsolutePath();
		choixDB4OGui.champBaseDB4O.setText(cheminAbsolue);

		// un fichier a été choisi ( sortie par OK)
		// nom du fichier  choisi 
		System.out.println(choix.getSelectedFile().getName());
		// chemin absolu du fichier choisi
		System.out.println(choix.getSelectedFile().getAbsolutePath());

	}
}

