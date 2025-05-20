/**
 * Classe Db4oFormationFactory
 */

package fr.unice.gfarce.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;
import com.db4o.ext.ExtObjectContainer;

import fr.unice.gfarce.connect.Db4oConfig;
import fr.unice.gfarce.identity.Formation;
import fr.unice.gfarce.identity.RespFormation;

/**
 *  Classe permettant d'acc&eacute;der &agrave; la base de donn&eacute;es db4o et d'en modifier les Formations.
 */

public class Db4oFormationFactory implements FormationDao{

	ObjectContainer bd; // Interface qui repr&eacute;sente la connexion à la BD
	String nombase;
	Configuration configuration;

	public Db4oFormationFactory() throws IOException {
		nombase=Db4oConfig.getInfo();
	}

	private void getConfig(){
		configuration =Db4o.newConfiguration();
		configuration.lockDatabaseFile(false);
		configuration.objectClass(Calendar.class).storeTransientFields(true);
	}

/**
 * Ajoute une Formation dans le cache.
 * nomResp devient responsable de cette formation.
 * @param f
 * @param nomResp
 * @param prenomResp
 * @return 
 */
	@Override
	public void insert(Formation f,String nomResp, String prenomResp) {
		getConfig();
		bd = Db4o.openFile(configuration,nombase);
		configuration.objectClass(RespFormation.class).cascadeOnDelete(true); 

		ObjectSet<RespFormation> test = bd.queryByExample(new RespFormation());	   
		for (RespFormation ligne : test) {
			if (ligne.getNom().equals(nomResp) && ligne.getPrenom().equals(prenomResp)) {
				f.setResponsable(ligne);				
				ligne.ajouterFormation(f); // On ajoute la formation a la Collection<Formation> du responsable.
				bd.delete(ligne);
				break;
			}
		}
		bd.commit();
		bd.close();
		insertFormation(f);
	}
	
	public void insertFormation(Formation formation){
		getConfig();
		bd = Db4o.openFile(configuration,nombase);
		bd.store(formation);
		bd.commit();
		bd.close();
	}

/**
 * Modifie une Formation deja pr&eacute;sente dans le cache.
 * @param f
 * @return 
 */
	@Override
	public void update(Formation formation) throws DaoException {
		getConfig();
		bd = Db4o.openFile(configuration,nombase);

		ObjectSet<Formation> result=bd.queryByExample(new Formation(formation.getTitre_formation(), null, 0, null));
		for(Formation ligne : result){
			if(formation.getDate_limite_candidature().get(Calendar.MONTH) == ligne.getDate_limite_candidature().get(Calendar.MONTH) && 
					formation.getDate_limite_candidature().get(Calendar.DAY_OF_MONTH) == ligne.getDate_limite_candidature().get(Calendar.DAY_OF_MONTH)&&
					formation.getDate_limite_candidature().get(Calendar.YEAR) == ligne.getDate_limite_candidature().get(Calendar.YEAR)){
		
				ligne.setMontant_inscription(formation.getMontant_inscription());

				ExtObjectContainer eoc = bd.ext();
				if (! eoc.isStored(ligne)) {
					System.out.println(ligne + " n'a pas d'identité dans la base");
				}
				bd.store(ligne);
				bd.commit();
				bd.close();
				break;
			}
		}
	}
	
/**
 * Supprime une Formation dans le cache.
 * @param f
 * @return 
 */
	@Override
	public void delete(Formation formation){
		getConfig();
		bd = Db4o.openFile(configuration,nombase);
		ObjectSet<Formation> result=bd.queryByExample(new Formation(formation.getId()));
		List<Formation> res = (List<Formation>) result;
		if(res!=null){
			bd.delete(res.get(0));
		}
		bd.commit();
		bd.close();
	}

/**
 * Recherche des Formations du cache selon certains crit&egrave;res.
 * Retourne toutes les formations du cache si name=date=null.
 * @param name
 * @param date
 * @return Formation[]
 */
	@Override
	public Formation[] findFormation(String name,Calendar date){
		getConfig();
		bd = Db4o.openFile(configuration,nombase);
		List<Formation> tab = new ArrayList<Formation>();
		ObjectSet<Formation> f=bd.queryByExample(new Formation (name,null,0,null));	
		List<Formation> res = (List<Formation>) f;
		for(int i=0 ; i<res.size(); i++){
			if(date!=null){
				Calendar d =res.get(i).getDate_limite_candidature();
				if(d.get(Calendar.MONTH) == date.get(Calendar.MONTH) && d.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)&&
					d.get(Calendar.YEAR) == date.get(Calendar.YEAR)){
					tab.add(res.get(i));
				}
			}
			else tab.add(res.get(i));
		}
		bd.close();
		return tab.toArray(new Formation[tab.size()]);
	}
}
