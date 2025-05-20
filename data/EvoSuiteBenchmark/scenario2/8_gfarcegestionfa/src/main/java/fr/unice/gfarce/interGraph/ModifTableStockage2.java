package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

public class ModifTableStockage {

    /**
     * G&eacute;n&egrave;re en sortie une TableStockage, &agrave; partir de la table d'origine,
     * avec seulement les lignes o&ugrave; les E-Mails ont &eacute;t&eacute; s&eacute;lectionn&eacute;
     *
     * @return TableStockage
     */
    public TableStockage selectMail() {
        int j = 0;
        Boolean bool;
        while ((j < this.nbColonnes) && (!ts.getColumnName(j).equals("Envoyer E-Mail a:"))) {
            // renverra la position de la colonne E-Mail si elle existe
            j++;
        }
        if (j != this.nbColonnes) {
            // la colonne E-Mail (de type Booleen) existe bien
            int newNbLignes = 0;
            for (int i = 0; i < nbLignes - 1; i++) {
                bool = (Boolean) ts.getValueAt(i, j);
                if (bool) {
                    newNbLignes++;
                }
            }
            TableStockage tableS = new TableStockage(newNbLignes + 1, nbColonnes);
            int k = 0;
            for (int ind = 0; ind < nbColonnes; ind++) {
                tableS.setColumnName(ind, ts.getColumnName(ind));
            }
            for (int row = 0; row < nbLignes - 1; row++) {
                bool = (Boolean) ts.getValueAt(row, j);
                if (bool) {
                    for (int column = 0; column < nbColonnes; column++) {
                        tableS.setValueAt(ts.getValueAt(row, column), k, column);
                        bool = false;
                    }
                    k++;
                }
            }
            return tableS;
        } else {
            JOptionPane.showMessageDialog(null, "Le mail sera envoye a toute la liste", "Information", JOptionPane.INFORMATION_MESSAGE);
            return ts;
        }
    }
}
