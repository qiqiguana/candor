package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

public class ModifTableStockage {

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine, avec une ligne en plus.
     * Ligne ajoutee a la fin de la JTable
     *
     * @return TableStockage
     */
    public TableStockage ajouterLigne() {
        if (nbColonnes > 0) {
            // s'il y a au minimum une colonne (impossibilite de creer une ligne sans colonne)
            this.nbLignes++;
            TableStockage tableS = new TableStockage(nbLignes, nbColonnes);
            /* on recopie les noms des colonnes */
            for (int j = 0; j < nbColonnes; j++) {
                tableS.setColumnName(j, ts.getColumnName(j));
            }
            /* on recopie les valeurs dans les colonnes */
            for (int i = 0; i < nbLignes - 2; i++) {
                for (int j = 0; j < nbColonnes; j++) {
                    tableS.setValueAt(ts.getValueAt(i, j), i, j);
                }
            }
            for (int j = 0; j < nbColonnes; j++) {
                if (ts.getColumnClass(j).equals(String.class)) {
                    // la derniere ligne sera a null
                    tableS.setValueAt("", nbLignes - 2, j);
                } else if (ts.getColumnClass(j).equals(Double.class)) {
                    tableS.setValueAt(new Double("0"), nbLignes - 2, j);
                } else
                    // sauf si de la classe Boolean
                    tableS.setValueAt(new Boolean(false), nbLignes - 2, j);
            }
            return tableS;
        } else {
            // pas de colonne pour construire une ligne
            JOptionPane.showMessageDialog(null, "PAS DE COLONNE", "ERREUR", JOptionPane.ERROR_MESSAGE);
            return ts;
        }
    }
}
