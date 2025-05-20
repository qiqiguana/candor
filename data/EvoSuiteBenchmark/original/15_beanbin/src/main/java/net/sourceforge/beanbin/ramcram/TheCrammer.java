package net.sourceforge.beanbin.ramcram;

import net.sourceforge.beanbin.ActiveList;
import net.sourceforge.beanbin.query.Query;


/**
 * This class is responsible for saving, removing and searching
 * objects that are stored within it.
 * 
 * @author Brian Gorman
 *
 */
public class TheCrammer<E> {
	/**
	 * 
	 * @param obj The object to be saved.
	 */
	public void save(Object obj) {
		
	}
	
	/**
	 * 
	 * @param obj This can be either an Entity or a primary key
	 */
	public void remove(Object obj) {
		
	}
	
	/**
	 * Searches internally against the specified {@link Query}
	 * @param query
	 * @return
	 */
	public ActiveList<E> search(Query query) {
		return null;
	}
}