/**
 * 
 */
package fr.unice.gfarce.dao;

import java.io.IOException;

/**
 * @author scarankle
 *
 */
public abstract class DaoFactory {

	public enum TypeFabrique{ORACLE, DB4O};
	private static TypeFabrique typeFabrique;
	
	public abstract IdentiteDao getIdentiteDao() throws IOException;
	public abstract FormationDao getFormationDao() throws IOException;
	
	public static void setTypeDao(TypeFabrique unTypeFabrique){
		typeFabrique = unTypeFabrique;
	}
	
	public static DaoFactory getDaoFactory() throws DaoFactoryException{
		switch(typeFabrique){
		case ORACLE:
			return new OracleDaoFactory();
		case DB4O:
			return new Db4oDaoFactory();
		default:
			throw new DaoFactoryException();
		}
	}
}
