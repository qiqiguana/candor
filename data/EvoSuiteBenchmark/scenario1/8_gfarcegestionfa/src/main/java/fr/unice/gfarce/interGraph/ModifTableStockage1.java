package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

public class ModifTableStockage {

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
