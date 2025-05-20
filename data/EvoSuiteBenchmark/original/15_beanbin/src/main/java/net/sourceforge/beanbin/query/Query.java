package net.sourceforge.beanbin.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that contains all of the query information
 * for a given search.
 * 
 * @author Brian Gorman
 *
 */
public class Query implements Serializable {
	private static final long serialVersionUID = -599887042334939313L;
	
	private List<Criteria> criterias;
	private Conditional nextAdd;
	private int position;
	private int fetchSize;
	private String sortBy;

	private SortBy sort;

	public Query() {
		this.criterias = new ArrayList<Criteria>();
		this.position = -1;
		this.fetchSize = -1;
	}
	
	public Query(Criteria criteria) {
		this.criterias = new ArrayList<Criteria>();
		this.criterias.add(criteria);
		this.position = -1;
		this.fetchSize = -1;
	}
	
	/**
	 * The is an implied 'and' and is just there to make the
	 * interface nice for those of you that created query 
	 * with the default constructor...
	 * 
	 * @param criteria 
	 */
	public void add(Criteria criteria) {
		if(nextAdd != null && nextAdd == Conditional.OR) {
			or(criteria);
		} else {
			and(criteria);	
		}
	}
	
	/**
	 * Adds all criteria from this query to ours
	 * @param query
	 */
	public void add(Query query) {
		for(Criteria criteria : query.getCriterias()) {
			add(criteria);
		}
	}
	
	/**
	 * next time something is added to the query this conditional 
	 * is attributed as its "previous condition"
	 * @param condition
	 */
	public void setNextConditional(Conditional condition) {
		this.nextAdd = condition;
	}
	
	/**
	 * Adds a {@link Criteria} to this Query "anded" together with the
	 * previous {@link Criteria}
	 * @param criteria
	 */
	public void and(Criteria criteria) {
		if(!criterias.isEmpty()) {
			criteria.setPreviousCondition(Conditional.AND);	
		}
		this.criterias.add(criteria);
	}
	
	/**
	 * Adds a {@link Criteria} to this Query "ored" together with the
	 * previous {@link Criteria}
	 * @param criteria
	 */	
	public void or(Criteria criteria) {
		if(!criterias.isEmpty()) {
			criteria.setPreviousCondition(Conditional.OR);	
		}
		this.criterias.add(criteria);		
	}
	
	/**
	 * Returns a list of the built up criterias bound together 
	 * with conditionals.
	 * @return criterias
	 */
	public List<Criteria> getCriterias() {
		return criterias;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Query) {
			return toString().equals(obj.toString());
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	public String toString() {
		String string = "";
		for(Criteria criteria : getCriterias()) {
			if(criteria.getPreviousCondition() != null) {
				string += " " + criteria.getPreviousCondition() + " ";
			}
			string += criteria.toString();
		}
		return string;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		setPosition(0);
		this.fetchSize = fetchSize;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getSortByProperty() {
		return sortBy;
	}
	
	public SortBy getSortType() {
		return sort;
	}

	public void setSortBy(String sortBy, SortBy sort) {
		this.sortBy = sortBy;
		this.sort  = sort;
	}
}
