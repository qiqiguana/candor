package net.sourceforge.beanbin.query;

import java.io.Serializable;

/**
 * A single form of criteria to add to a search {@link Query}
 * @author Brian Gorman
 *
 */
public class Criteria implements Serializable {
	private static final long serialVersionUID = 1249321323400263260L;
	private String property;
	private Object term;
	private SearchType type;
	private Conditional previousCondition;

	/**
	 * 
	 * @param property Property of bean being searched
	 * @param term	Search term
	 * @param type	operation....
	 */
	public Criteria(String property, Object term, SearchType type) {
		this.property = property;
		this.term = term;
		this.type = type;
		this.previousCondition = null;
	}
	
	/**
	 * 
	 * @return Returns null if the first criteria otherwise see {@link Conditional}
	 */
	public Conditional getPreviousCondition() {
		return previousCondition;
	}
	
	/**
	 * This gets set by {@link Query}
	 * @param condition
	 */
	protected void setPreviousCondition(Conditional condition) {
		this.previousCondition = condition;
	}
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getTerm() {
		return term;
	}

	public void setTerm(Object term) {
		this.term = term;
	}

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Criteria) {
			return toString().equals(obj.toString());	
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	public String toString() {
		return getProperty() + " " + getType() + " " + getTerm();
	}
}
