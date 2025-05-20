package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

public class ModifTableStockage {

    // dimension de la table
    private int nbColonnes = 0;

    // avec la ligne de description (les noms des colonnes)
    private int nbLignes = 0;

    // la table d'origine (String[] nomColonnes ; Object[][] matrice;)
    private TableStockage ts;

    /**
     * Constructeur : recuperation de la TableStockage et initialisation
     *
     * @param ts
     */
    public ModifTableStockage(TableStockage ts) {
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

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine, avec une ligne en plus.
     * Ligne ajoutee a la fin de la JTable
     * @return TableStockage
     */
    public TableStockage ajouterLigne();

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine
     * avec la colonne selectionnee en moins
     * colonne d'indice donne en parametre supprimee
     * @param indice
     * @return TableStockage
     */
    public TableStockage supprimerColonne(int indice);

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine,
     * avec la ligne selectionnee en moins.
     * ligne d'indice donne en parametre supprimee
     * @param indice
     * @return TableStockage
     */
    public TableStockage supprimerLigne(int indice);

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine,
     * avec la colonne d'indice donne en parametre separee.
     * @param indice
     * @return TableStockage
     */
    public TableStockage separerColonne(int indice);

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine,
     * avec la colonne d'indice donne en parametre fusionnee avec la colonne voisine de droite.
     * @param indice
     * @return TableStockage
     */
    public TableStockage fusionnerColonnes(int indice);

    /**
     * Genere en sortie une TableStockage, a partir de la table d'origine,
     * avec une nouvelle colonne de nom "E-Mail"
     * Permettra a l'utilisateur de choisir a qui envoyer un mail pour la suite.
     * @return TableStockage
     */
    public TableStockage colonneMail();

    /**
     * G&eacute;n&egrave;re en sortie une TableStockage, &agrave; partir de la table d'origine,
     * avec seulement les lignes o&ugrave; les E-Mails ont &eacute;t&eacute; s&eacute;lectionn&eacute;
     * @return TableStockage
     */
    public TableStockage selectMail();
}
