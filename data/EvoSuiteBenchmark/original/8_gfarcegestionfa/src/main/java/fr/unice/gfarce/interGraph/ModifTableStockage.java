/**
 * Classe ModifTableStockage
 */ 
package fr.unice.gfarce.interGraph;

import javax.swing.JOptionPane;

/**
 * Recreee une table modifi&eacute;e par l'utilisateur, a partir d'une TableStockage donnee
 * @author Rol Elsa
 *
 */

public class ModifTableStockage{

	private int nbColonnes = 0; // dimension de la table
	private int nbLignes = 0; // avec la ligne de description (les noms des colonnes)
	private TableStockage ts; // la table d'origine (String[] nomColonnes ; Object[][] matrice;)


	/**
	 * Constructeur : recuperation de la TableStockage et initialisation
     *
     * @param ts
     */
	public ModifTableStockage(TableStockage ts){
		this.nbColonnes = ts.getColumnCount();
		this.nbLignes = ts.getRowCount()+1;
		this.ts = ts;
	}


	/**
	 * G&eacute;n&egrave;re en sortie une TableStockage &agrave; partir de la table d'origine, mais avec une colonne en plus.
	 * Nouvelle colonne de nom "nomNouvelleColonne" et de type "type"
	 * Colonne Ajoutee a la fin de la JTable
	 * @param nomNouvelleColonne
     * @param type
     * @return TableStockage
	 */
	public TableStockage ajouterColonne(String nomNouvelleColonne, Class<?> type) {
		
		this.nbColonnes ++;
		TableStockage tableS = new TableStockage(nbLignes,nbColonnes);	
		
		/* on recopie les noms des colonnes */
		for(int j=0; j<nbColonnes-1; j++) {
			tableS.setColumnName(j, ts.getColumnName(j));
		}
		tableS.setColumnName(nbColonnes-1, nomNouvelleColonne); // la derniere colonne prend le nom donne en parametre
	
		/* on recopie les valeurs dans les colonnes */
		for(int i=0; i<nbLignes-1; i++) {
			for(int j=0; j < nbColonnes-1; j++) {
				tableS.setValueAt(ts.getValueAt(i, j), i, j);
			}
			tableS.setColumnClass(nbColonnes-1, type);  // la derniere colonne sera null
		}
		return tableS;	
	}
	
	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine, avec une ligne en plus.
	 * Ligne ajoutee a la fin de la JTable
	 * @return TableStockage
	 */
	public TableStockage ajouterLigne() {
		if(nbColonnes > 0){ // s'il y a au minimum une colonne (impossibilite de creer une ligne sans colonne)
			this.nbLignes ++;
			TableStockage tableS = new TableStockage(nbLignes,nbColonnes);	
			
			/* on recopie les noms des colonnes */
			for(int j=0; j < nbColonnes; j++) {  
				tableS.setColumnName(j, ts.getColumnName(j));
			}
			
			/* on recopie les valeurs dans les colonnes */
			for(int i=0; i<nbLignes-2; i++) {
				for(int j=0; j < nbColonnes; j++) {  
					tableS.setValueAt(ts.getValueAt(i, j), i, j);
				}
			}
			
			for(int j=0; j < nbColonnes; j++) {
				if(ts.getColumnClass(j).equals(String.class)){
					tableS.setValueAt("", nbLignes-2, j);  // la derniere ligne sera a null
				}else
                if(ts.getColumnClass(j).equals(Double.class)) {
                  tableS.setValueAt(new Double("0"), nbLignes-2, j);
                }else
                    tableS.setValueAt(new Boolean(false), nbLignes-2, j); // sauf si de la classe Boolean
			}
			return tableS;
		}else{ // pas de colonne pour construire une ligne
			JOptionPane.showMessageDialog(
				null,
				"PAS DE COLONNE",
				"ERREUR",
				JOptionPane.ERROR_MESSAGE
			);
			return ts;
		}
	}
	
	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine
	 * avec la colonne selectionnee en moins
	 * colonne d'indice donne en parametre supprimee
	 * @param indice
	 * @return TableStockage
	 */
	public TableStockage supprimerColonne(int indice) {
		this.nbColonnes --;
		TableStockage tableS = new TableStockage(nbLignes,nbColonnes);	
		
		/* on recopie les noms des colonnes, jusque celui a supprimer */
		for(int j=0; j < indice; j++) { 
			tableS.setColumnName(j, ts.getColumnName(j));
		}
		
		/* on recopie les colonnes, sauf celle a supprimer */
		for(int i = 0; i<nbLignes-1; i++){
			for(int j =0; j<indice; j++){
				tableS.setValueAt(ts.getValueAt(i, j), i, j);
			}
		}
		
		/* on recopie toutes les valeurs apres la colonne supprimee */
		if(indice != nbColonnes){  // la colonne supprimee n'est pas la derniere
			for(int j=indice; j < nbColonnes; j++) {
				tableS.setColumnName(j, ts.getColumnName(j+1));
			}
			for(int i = 0; i<nbLignes-1; i++){
				for(int j = indice; j < nbColonnes; j++){
					tableS.setValueAt(ts.getValueAt(i, j+1), i, j);
				}
			}
		}
		return tableS;	
	}

	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine, 
	 * avec la ligne selectionnee en moins.
	 * ligne d'indice donne en parametre supprimee
	 * @param indice
	 * @return TableStockage
	 */
	public TableStockage supprimerLigne(int indice) {
		this.nbLignes --;
		TableStockage tableS = new TableStockage(nbLignes,nbColonnes);	
		
		/* on recopie les noms des colonnes */
		for(int j=0; j < nbColonnes; j++) {  
			tableS.setColumnName(j, ts.getColumnName(j));
		}
		
		/* on recopie les valeurs des colonnes */
		for(int i=0; i<indice; i++){
			for(int j = 0; j < nbColonnes; j++) {
				tableS.setValueAt(ts.getValueAt(i, j), i, j);
			}
		}
		if(indice != nbLignes-1){ // la ligne a supprimer n'est pas la derniere
			for(int i=indice+1; i<nbLignes; i++){
				for(int j = 0; j < nbColonnes; j++) {
					tableS.setValueAt(ts.getValueAt(i, j), i-1, j);
				}
			}
		}
		return tableS;	
	}
	
	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine, 
	 * avec la colonne d'indice donne en parametre separee.
	 * @param indice
	 * @return TableStockage
	 */
	public TableStockage separerColonne(int indice) {
		if(ts.getColumnClass(indice).equals(String.class)){ // on ne peut separer que des valeurs de type String
			int k=0;
			String val = (String)ts.getValueAt(0, indice);
			String val1 = new String();
			
			/* Test qui verifie si la colonne est separable (par rapport a "l'espace") */
			while((int)val.charAt(k) != 160 && k!=(val.length()-1)){ // code possible de separation entre les mots
				val1 = val1.concat(String.valueOf(val.charAt(k)));
				k++;
			}
			if(k==(val.length()-1)){ // le code de separation n'est pas 160
				val1 = new String();
				k=0;
				while((int)val.charAt(k) != 32 && k!=(val.length()-1)){ // si la separation entre les mots est l'espace
					val1 = val1.concat(String.valueOf(val.charAt(k)));
					k++;
				}
			}
			if(k!=(val.length()-1)){ // la colonne est separable car il y a un espace
				this.nbColonnes ++;
				TableStockage tableS = new TableStockage(nbLignes,nbColonnes);
				val1 = new String();
				k=0;
				
				/* On ecrit les noms des colonnes */
				for(int j = 0; j < indice; j++) {
					tableS.setColumnName(j, ts.getColumnName(j));
				}
				for(int j = indice+1; j < nbColonnes; j++) {
					tableS.setColumnName(j, ts.getColumnName(j-1));
				}
				
				String colonne = ts.getColumnName(indice);  // on recupere le nom de la colonne a separer
				String val1Colonne, val2Colonne;
				String[] resNP = colonne.split(" ", 2);
				val1Colonne = resNP[0];
				if(resNP.length > 1) val2Colonne = resNP[1];
				else val2Colonne = "";
				tableS.setColumnName(indice, val1Colonne); // 1ere partie du nom de la colonne
				tableS.setColumnName(indice+1, val2Colonne); // 2eme partie
				
				/* on ecrit les donnees */
				for(int i=0; i<nbLignes-1; i++) {
					for(int j=0; j<indice; j++) {
						tableS.setValueAt(ts.getValueAt(i, j), i, j);
					}
					for(int j=indice+2; j<nbColonnes; j++) {
						tableS.setValueAt(ts.getValueAt(i, j-1), i, j);
					}
				}
				
				/* On remplit les 2 colonnes */
				String val2 = null;
				for(int i=0; i<nbLignes-1; i++){
					val1 = new String();
					val = (String)ts.getValueAt(i, indice);  // on recupere la valeur en [i-1 , indice]
					k = 0;
					if(val.equals("")){ // peut etre du a une ligne ou colonne ajoutée a la suite
						val1 = "";
						val2 = "";
					}else{
						while((int)val.charAt(k) != 160 && k!=(val.length()-1)){
							val1 = val1.concat(String.valueOf(val.charAt(k)));
							k++;
						}
						if(k==(val.length()-1)){
							val1 = new String();
							k=0;
							while((int)val.charAt(k) != 32 && k!=(val.length()-1)){
								val1 = val1.concat(String.valueOf(val.charAt(k)));
								k++;
							}
						}
						val2=val.substring(k+1, val.length());
					}

					double int1 = 0, int2 = 0;
					try{
						int1 = Double.parseDouble(val1);
						tableS.setValueAt(int1, i, indice);
					}catch(NumberFormatException e){
						tableS.setValueAt(val1, i, indice);
					}
					try{
						int2 = Double.parseDouble(val2);
						tableS.setValueAt(int2, i, indice+1);
					}catch(NumberFormatException e){
						tableS.setValueAt(val2, i, indice+1);
					}
				}
				return tableS;
			}else{
				JOptionPane.showMessageDialog(
					null,
					"PAS SEPARABLE",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE
				);
				return ts;	
			}
		}else{
			JOptionPane.showMessageDialog(
				null,
				"non separable: PAS DE TYPE STRING",
				"ERREUR",
				JOptionPane.ERROR_MESSAGE
			);
			return ts;	
		}
	}
	
	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine, 
	 * avec la colonne d'indice donne en parametre fusionnee avec la colonne voisine de droite.
	 * @param indice
	 * @return TableStockage
	 */
	public TableStockage fusionnerColonnes(int indice){
		if(nbColonnes>1){
			// on ne peut fusionner des objets de type Boolean
			if((!ts.getColumnClass(indice).equals(Boolean.class)) && (!ts.getColumnClass(indice+1).equals(Boolean.class))){
				this.nbColonnes --;
				TableStockage tableS = new TableStockage(nbLignes,nbColonnes);
				
				/* On ecrit les noms des colonnes */
				for(int j = 0; j < indice; j++) {
					tableS.setColumnName(j, ts.getColumnName(j));
				}
				String c1 = ts.getColumnName(indice); // on fusionne les 2 noms des colonnes
				String c2 = ts.getColumnName(indice+1);
				
				for(int j = indice+1; j < nbColonnes; j++) {
					tableS.setColumnName(j, ts.getColumnName(j+1));
				}
				tableS.setColumnName(indice, c1.concat(" "+c2));
				
				for(int i=0; i<nbLignes-1; i++) {
					for(int j=0; j<indice; j++) {
						tableS.setValueAt(ts.getValueAt(i, j), i, j);
					}
					
					Object d1 = ts.getValueAt(i, indice);  // on fusionne les donnees des 2 colonnes
					Object d2 = ts.getValueAt(i, indice+1);
					
					String s1 = d1.toString(); // on transforme l'objet en String (si Integer ...)
					String s2 = d2.toString();
					tableS.setValueAt(s1.concat(" "+s2), i, indice); // separees par un espace
				
					for(int j=indice+1; j<nbColonnes; j++) {
						tableS.setValueAt(ts.getValueAt(i, j+1), i, j);
					}
				}
				return tableS;
			}else{
				JOptionPane.showMessageDialog(
					null,
					"PAS DE TYPE STRING",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE
				);
				return ts;	
			}
		}else{
			JOptionPane.showMessageDialog(
				null,
				"UNE SEULE COLONNE",
				"ERREUR",
				JOptionPane.ERROR_MESSAGE
			);
			return ts;
		}
	}
	
	
	/**
	 * Genere en sortie une TableStockage, a partir de la table d'origine, 
	 * avec une nouvelle colonne de nom "E-Mail"
	 * Permettra a l'utilisateur de choisir a qui envoyer un mail pour la suite.
	 * @return TableStockage
	 */
	public TableStockage colonneMail(){
		this.nbColonnes++;
		ModifTableStockage mt = new ModifTableStockage(ts);
		TableStockage tableS = new TableStockage(nbLignes,nbColonnes);
		tableS = mt.ajouterColonne("Envoyer E-Mail a:", Boolean.class);
		
		for(int i =0; i<nbLignes-1; i++){
			tableS.setValueAt(new Boolean(true), i, nbColonnes-1); // on ajoute un checkbox (par defaut)
		}
		return tableS;
	}
	
	
	/**
	 * G&eacute;n&egrave;re en sortie une TableStockage, &agrave; partir de la table d'origine, 
	 * avec seulement les lignes o&ugrave; les E-Mails ont &eacute;t&eacute; s&eacute;lectionn&eacute;
	 * @return TableStockage
	 */
	public TableStockage selectMail(){
		int j=0;
		Boolean bool;
		while((j < this.nbColonnes) && (! ts.getColumnName(j).equals("Envoyer E-Mail a:"))){
			j++; // renverra la position de la colonne E-Mail si elle existe
		}

		if(j != this.nbColonnes){ // la colonne E-Mail (de type Booleen) existe bien
			
			int newNbLignes=0;
			for(int i=0; i< nbLignes-1; i++){

				bool = (Boolean)ts.getValueAt(i, j);
				if(bool){
					newNbLignes++;
				}
			}
			TableStockage tableS = new TableStockage(newNbLignes+1, nbColonnes);
			int k=0;
            for(int ind = 0; ind < nbColonnes; ind++) {
				tableS.setColumnName(ind, ts.getColumnName(ind));
			}


			for(int row=0; row< nbLignes-1; row++){
                bool = (Boolean)ts.getValueAt(row, j);
                if(bool){
                   for(int column=0; column< nbColonnes; column++){
                            tableS.setValueAt(ts.getValueAt(row, column), k, column);
                            bool=false;
                    }
                    k++;
                }
			}
			return tableS;			
		}else{
			JOptionPane.showMessageDialog(
				null,
				"Le mail sera envoye a toute la liste",
				"Information",
				JOptionPane.INFORMATION_MESSAGE
			);
			return ts;
		} 
	}
}
