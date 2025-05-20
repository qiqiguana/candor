package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

public class ModifTableStockage {

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine,
     * avec une nouvelle colonne de nom "E-Mail"
     * Permettra a l'utilisateur de choisir a qui envoyer un mail pour la suite.
     *
     * @return TableStockage
     */
    public TableStockage colonneMail() {
        this.nbColonnes++;
        ModifTableStockage mt = new ModifTableStockage(ts);
        TableStockage tableS = new TableStockage(nbLignes, nbColonnes);
        tableS = mt.ajouterColonne("Envoyer E-Mail a:", Boolean.class);
        for (int i = 0; i < nbLignes - 1; i++) {
            // on ajoute un checkbox (par defaut)
            tableS.setValueAt(new Boolean(true), i, nbColonnes - 1);
        }
        return tableS;
    }
}
