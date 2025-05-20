package net.sourceforge.beanbin;

import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SortBy;

/**
 * This interface provides search functionality within BeanBin.
 * 
 * All methods return an {@link ActiveList} that actually serves
 * as an argument accumulator for automatically building a {@link Query}
 * object behind the scenes.
 *  
 * @author Brian Gorman
 *
 * @param <E> an {@link com.persistence.Entity} type
 */
public interface Searcher<E> {
	
	/**
	 * Basic search method with a premade {@link Query}
	 * @param query
	 * @return ActiveList 
	 * @throws BeanBinException
	 */
	public ActiveList<E> lookup(Query query) throws BeanBinException;
	
	/**
	 * Insert an implicit AND
	 * @return ActiveList
	 * @throws BeanBinException
	 */
	public ActiveList<E> and() throws BeanBinException;

	/**
	 * Insert an implicit AND
	 * @return ActiveList
	 * @throws BeanBinException
	 */
	public ActiveList<E> or() throws BeanBinException;

	/**
	 * This is an exact match search
	 * @param property of bean
	 * @param searchTerm
	 * @return ActiveList
	 * @throws BeanBinException
	 */
	public ActiveList<E> matches(String property, Object term) throws BeanBinException;
	
	/**
	 * This is an contains search that automatically puts wild cards on both sides of the term
	 * @param property of bean
	 * @param searchTerm
	 * @return ActiveList
	 * @throws BeanBinException
	 */
	public ActiveList<E> contains(String property, Object term) throws BeanBinException;
	
	/**
	 * Greater than search on property added to internal {@link Query}
	 * @param property
	 * @param number
	 * @return
	 * @throws BeanBinException
	 */
	public ActiveList<E> greaterThan(String property, Number number) throws BeanBinException;
	
	/**
	 * Greater than or equal to search on property added to internal {@link Query}
	 * @param property
	 * @param number
	 * @return
	 * @throws BeanBinException
	 */
	public ActiveList<E> greaterThanOrEqualTo(String property, Number number) throws BeanBinException;
	
	/**
	 * Less than search on property added to internal {@link Query}
	 * @param property
	 * @param number
	 * @return
	 * @throws BeanBinException
	 */	
	public ActiveList<E> lessThan(String property, Number number) throws BeanBinException;
	
	/**
	 * Less than or equal to search on property added to internal {@link Query}
	 * @param property
	 * @param number
	 * @return
	 * @throws BeanBinException
	 */
	public ActiveList<E> lessThanOrEqualTo(String property, Number number) throws BeanBinException;
	
	public ActiveList<E> fetchSize(int size) throws BeanBinException;
	public ActiveList<E> sortBy(String property) throws BeanBinException;
	public ActiveList<E> sortBy(String property, SortBy sort) throws BeanBinException;
}