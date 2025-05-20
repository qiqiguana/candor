/**
 * 
 */
package fr.unice.gfarce.interGraph;

import javax.swing.table.AbstractTableModel;

/**
 * @author alex
 *
 */
public class Zmodel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object[][] data;
	private String[] title;
	/**
	 * Constructeur
	 * @param data
	 * @param title
	 */
	public Zmodel(Object[][] data, String[] title){
		this.data = data;
		this.title = title;
	}
	/**
	 * Retourne le titre de la colonne à l'indice spécifé
	 */
	public String getColumnName(int col) {
		return this.title[col];
	}

	/**
	 * Retourne le nombre de colonnes
	 */
	public int getColumnCount() {
		return this.title.length;
	}

	/**
	 * Retourne le nombre de lignes
	 */
	public int getRowCount() {
		return this.data.length;
	}

	/**
	 * Retourne la valeur à l'emplacement spécifié
	 */
	public Object getValueAt(int row, int col) {
		return this.data[row][col];
	}

	/**
	 * Défini la valeur à l'emplacement spécifié
	 */
	public void setValueAt(Object value, int row, int col) {
		//On interdit la modification sur certaine colonne !
		this.data[row][col] = value;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
	}

	/**
	 * Retourne la classe de la donnée de la colonne
	 * @param col
	 */
	/*
	public Class getColumnClass(int col){
		//On retourne le type de la cellule à la colonne demandée
		//On se moque de la ligne puisque les données sur chaque ligne sont les mêmes
		//On choisit donc la première ligne
		return this.data[0][col].getClass();
	}
	*/

	/**
	 * Méthode permettant de retirer une ligne du tableau
	 * @param position
	 */
	public void removeRow(int position){

		int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
		Object temp[][] = new Object[nbRow][nbCol];
		System.out.println("indice initial = "+indice);
		for(Object[] value : this.data){
			if(indice != position){
				temp[indice2++] = value;
			}
			System.out.println("Indice = " + indice);
			indice++;
		}
		this.data = temp;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
	}

	/**
	 * Permet d'ajouter une ligne dans le tableau
	 * @param data
	 */
	public void addRow(Object[] data){
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();

		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];

		for(Object[] value : temp)
			this.data[indice++] = value;


		this.data[indice] = data;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
	}


	public boolean isCellEditable(int row, int col){
		return false;
	}
	
	/*
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        //ici la cellule (1, 2) est non-editable
        if (rowIndex == 0 && columnIndex == 0) return false;
        
        //le reste est editable
        return true;
    }
    */



	public void afficheData(Object[][] data){
		for(int i=0; i<data.length; i++){
			System.out.println();
			for(int j=0; j<data[0].length; j++){
				System.out.print(data[i][j]);
			}
		}
	}
}






