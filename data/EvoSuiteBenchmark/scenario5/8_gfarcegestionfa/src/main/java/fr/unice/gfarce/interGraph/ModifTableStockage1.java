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

    /**
     * G&eacute;n&egrave;re en sortie une TableStockage &agrave; partir de la table d'origine, mais avec une colonne en plus.
     * Nouvelle colonne de nom "nomNouvelleColonne" et de type "type"
     * Colonne Ajoutee a la fin de la JTable
     * @param nomNouvelleColonne
     * @param type
     * @return TableStockage
     */
    public TableStockage ajouterColonne(String nomNouvelleColonne, Class<?> type);
}

/**
 * Classe TableStockage <br>
 * Table qui stocke toutes les valeurs de type String; la premi&egrave;re
 * ligne sert de description des colonnes <br>
 *
 * @author KOWALSKI Mathias
 */
public class TableStockage extends AbstractTableModel {

    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex);
}
