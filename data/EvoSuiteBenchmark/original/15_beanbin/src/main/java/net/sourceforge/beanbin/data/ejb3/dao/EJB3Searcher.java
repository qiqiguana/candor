package net.sourceforge.beanbin.data.ejb3.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.ejb3.Lazifier;
import net.sourceforge.beanbin.data.ejb3.dao.search.ContainsBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.DoesNotEqualBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.EqualsBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.GreaterThanBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.IndexBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.JPAQLBuilder;
import net.sourceforge.beanbin.data.ejb3.dao.search.LessThanBuilder;
import net.sourceforge.beanbin.query.Conditional;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.IndexCriteria;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.query.SortBy;

public class EJB3Searcher {
	private EntityManager em;
	private Query query;
	protected Class clazz;
	
	public static String ALIAS = "beanbinmainclass";
	

	public EJB3Searcher(Class clazz, Query query, EntityManager em) {
		this.em = em;
		this.query = query;
		this.clazz = clazz;
	}
	
	public int getSize() throws BeanBinException {
		javax.persistence.Query realQuery = makeRealQuery("select count(" + ALIAS + ")");
		return ((Number)realQuery.getResultList().get(0)).intValue();
	}
	
	public List<Object> getResults() throws BeanBinException {		
		javax.persistence.Query realQuery = makeRealQuery(getPrefix());
		
		if(query.getFetchSize() != -1) {
			realQuery.setFirstResult(query.getPosition());
			realQuery.setMaxResults(query.getFetchSize());
		}

		em.getTransaction().begin();
		List<Object> results = getResults(realQuery);
		em.getTransaction().commit();		
		
		// inject lazy entities after the commit becuase if you
		// set anything inside of the transaction it'll think
		// it should update...
		Lazifier lazy = new Lazifier();
		ArrayList<Object> retval = new ArrayList<Object>();
		for(Object entity : results) {
			lazy.execute(entity);
			retval.add(entity);
		}
		return retval;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Object> getResults(javax.persistence.Query query) throws BeanBinException {
		ArrayList<Object> tmp = new ArrayList<Object>();
		List<Object> list = query.getResultList();		
		tmp.addAll(list);		
		return tmp;
	}
	
	private javax.persistence.Query makeRealQuery(String prefix) throws BeanBinException {
		Map<String, Parameters> map = new HashMap<String, Parameters>();
		String queryString = prefix + " from " + clazz.getName() + " as " + ALIAS + "";
		
		if(!query.getCriterias().isEmpty()) {
			queryString += " where ";
		}
		
		for(Criteria criteria : query.getCriterias()) {
			queryString += assignConditional(criteria.getPreviousCondition());
			
			JPAQLBuilder builder = getBuilder(criteria);
			builder.initialize(criteria, map);
			
			String jpaql = builder.getJPAQL();
			queryString += jpaql;
			map = builder.getParameters();
		}
		
		if(query.getSortByProperty() != null) {
			queryString += " order by " + ALIAS + "." + query.getSortByProperty();
			if(query.getSortType() == SortBy.ASCENDING) {
				queryString += " asc";
			} else if(query.getSortType() == SortBy.DESENDING) {
				queryString += " desc";				
			}
		}
		
		javax.persistence.Query realQuery = em.createQuery(queryString);
		
		for(String prop : map.keySet()) {
			Parameters params = map.get(prop);
			for(int i = 0; i < params.getSize(); ++i) {
				realQuery.setParameter(prop + i, params.getTerm(i));	
			}
		}
		
		return realQuery;
	}

	private String assignConditional(Conditional condition) {
		if(condition != null) {
			return condition == Conditional.AND ? " AND " : " OR ";
		} else {
			return "";
		}
	}
	
	protected String getPrefix() {
		return "select " + ALIAS + "";
	}
	
	private JPAQLBuilder getBuilder(Criteria criteria) throws BeanBinException {
		if(criteria instanceof IndexCriteria) {
			return new IndexBuilder(clazz);
		}
		
		SearchType type = criteria.getType();
		if(type == SearchType.EQUALS) {
			return new EqualsBuilder();
		} else if(type == SearchType.CONTAINS) {
			return new ContainsBuilder();
		} else if(type == SearchType.DOESNOTEQUAL) {
			return new DoesNotEqualBuilder();
		} else if(type == SearchType.LESSTHAN || type == SearchType.LESSTHANOREQUALTO) {
			return new LessThanBuilder();
		} else if(type == SearchType.GREATERTHAN || type == SearchType.GREATERTHANOREQUALTO) {
			return new GreaterThanBuilder();
		} else {
			throw new BeanBinException("Invalid SearchType " + type + " was passed to getBuilder()");
		}
	}
}
