package fr.unice.gfarce.interGraph;

import javax.swing.table.AbstractTableModel;

/**
 * Classe TableStockage <br>
 * Table qui stocke toutes les valeurs de type String; la premi&egrave;re
 * ligne sert de description des colonnes <br>
 * @author KOWALSKI Mathias
 */
public class TableStockage extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String [] nomColonnes;
	private Object[][] matrice;


	/**
	 * Constructeur par d&eacute;faut
	 */
	public TableStockage() {
		nomColonnes = new String[0];
		matrice = new Object[0][0];
	}

	/**
	 * Nouvelle table avec nb lignes et colonnes pour initialiser
	 * Attention, nbLignes en local est n-1 plus petit que le nombre
	 * de lignes du tableau car la premi&egrave;re ligne sert de description
	 * des colonnes
	 * @param nbLignes
	 * @param nbColonnes
	 */
	public TableStockage(int nbLignes, int nbColonnes) {
		nomColonnes = new String[nbColonnes];
		matrice = new Object[nbLignes-1][nbColonnes];
	}

	/**
	 * Cr&eacute;e une TableStockage &agrave; partir de noms de colonnes et matrice de donn&eacute;es pr&eacute;d&eacute;finies
	 * @param nomColonnes
	 * @param matrice
	 */
	public TableStockage(String[] nomColonnes, Object[][] matrice) {
		this.nomColonnes = nomColonnes;
		this.matrice = new Object[matrice.length][nomColonnes.length];
		for (int i=0;i<matrice.length;i++) {
			for (int j=0;j<nomColonnes.length;j++) {
				this.setValueAt(matrice[i][j], i, j);
			}
		}
	}


	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return nomColonnes.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return matrice.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		return nomColonnes[columnIndex];
	}

	/**
	 * Recupere la classe des objets de la colonne (String par defaut si pas de ligne)
	 * @param c
	 * @return Class
	 */
	public Class<?> getColumnClass(int c) {
		try {
			return getValueAt(0, c).getClass();
		} catch(NullPointerException ex) {
			return Object.class;
		}
	}

	/**
	 * Change le nom d'une colonne par rapport &agrave; son index
	 * @param columnIndex
	 * @param contenu
	 */
	public void setColumnName(int columnIndex, String contenu) {
		nomColonnes[columnIndex] = contenu;
	}

	public String[] getNomColonnes() {
		return nomColonnes;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return matrice[rowIndex][columnIndex];
	}

	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void setColumnClass(int posColonne, Class<?> type){
		if(type.equals(String.class)){
			for(int i=0; i<getRowCount(); i++){
				setValueAt("", i, posColonne);
			}
		}else if(type.equals(Boolean.class)){
			for(int i=0; i<getRowCount(); i++){
				setValueAt(new Boolean(false), i, posColonne);
			}
		}else{
			for(int i=0; i<getRowCount(); i++){
				setValueAt(new Double(0), i, posColonne);
			}
		}
	}


	/**
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
    @Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		matrice[rowIndex][columnIndex] = value;
	}

	/**
	 * Rempli la matrice contenant les donn&eacute;es par rapport aux index
	 * La premi&egrave;re ligne sert de description aux colonnes
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void remplirMatrice(Object value, int rowIndex, int columnIndex) {
		if(rowIndex == 0) {
			nomColonnes[columnIndex] = (String)value;
		}else{
			matrice[rowIndex-1][columnIndex] = value;
		}
	}
}
