package fr.unice.gfarce.interGraph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AProposAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetrePrincipale fenetre;
	public AProposAction(FenetrePrincipale fenetre, String texte){
		super(texte);
		this.fenetre = fenetre;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(fenetre, "Ce programme a été développé par la team gfarce.");
	}
}









