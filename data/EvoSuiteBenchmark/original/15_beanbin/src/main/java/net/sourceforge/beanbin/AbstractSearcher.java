package net.sourceforge.beanbin;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.query.Conditional;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.IndexCriteria;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.query.SortBy;
import net.sourceforge.beanbin.search.index.IndexUtils;

public abstract class AbstractSearcher<E> implements Searcher<E> {
	private List<String> indexProperties;

	public AbstractSearcher() {
		this.indexProperties = new ArrayList<String>();
	}
	public AbstractSearcher(Class clazz) throws BeanBinException {
		this.indexProperties = IndexUtils.getIndexProperties(clazz);
	}
	public ActiveList<E> and() throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().setNextConditional(Conditional.AND);
		return list;
	}
	
	public ActiveList<E> fetchSize(int fetchSize) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().setFetchSize(fetchSize);
		return list;
	}
	
	public ActiveList<E> or() throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().setNextConditional(Conditional.OR);
		return list;
	}

	public ActiveList<E> contains(String property, Object term) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, term, SearchType.CONTAINS));		
		return list;
	}

	public ActiveList<E> greaterThan(String property, Number number) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, number, SearchType.GREATERTHAN));		
		return list;
	}

	public ActiveList<E> greaterThanOrEqualTo(String property, Number number) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, number, SearchType.GREATERTHANOREQUALTO));		
		return list;
	}

	public ActiveList<E> lessThan(String property, Number number) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, number, SearchType.LESSTHAN));		
		return list;
	}

	public ActiveList<E> lessThanOrEqualTo(String property, Number number) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, number, SearchType.LESSTHANOREQUALTO));		
		return list;
	}

	public ActiveList<E> lookup(Query query) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(query);
		return list;
	}

	public ActiveList<E> matches(String property, Object term) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().add(makeCriteria(property, term, SearchType.EQUALS));		
		return list;
	}
	private Criteria makeCriteria(String property, Object term, SearchType searchType) throws BeanBinException {
		if(indexProperties.contains(property)) {
			if(searchType == SearchType.LESSTHAN
					|| searchType == SearchType.LESSTHANOREQUALTO
					|| searchType == SearchType.GREATERTHAN
					|| searchType == SearchType.GREATERTHANOREQUALTO
					)
			{
				throw new BeanBinException("You cannot search a string index with the search type " + searchType);
			} else if(searchType == SearchType.DOESNOTEQUAL) {
				term = "NOT \"" + term + "\"";
			} else if(searchType == SearchType.CONTAINS) {
				term = "*" + term + "*";
			}
			
			return new IndexCriteria(property, term.toString());
		} else {
			return new Criteria(property, term, searchType);	
		}
	}
	
	public ActiveList<E> sortBy(String property) throws BeanBinException {
		return sortBy(property, SortBy.ASCENDING);
	}
	
	public ActiveList<E> sortBy(String property, SortBy sort) throws BeanBinException {
		ActiveList<E> list = getList();
		list.getQuery().setSortBy(property, sort);
		return list;
	}
	
	protected abstract ActiveList<E> getList() throws BeanBinException;
	public List<String> getIndexProperties() {
		return indexProperties;
	}
	public void setIndexProperties(List<String> indexProperties) {
		this.indexProperties = indexProperties;
	}
}
