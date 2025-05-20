package net.sourceforge.beanbin.data.ejb3.dao.search;

import java.util.Map;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.data.ejb3.dao.EJB3Searcher;
import net.sourceforge.beanbin.data.ejb3.dao.Parameters;
import net.sourceforge.beanbin.query.Criteria;

public class IndexBuilder implements JPAQLBuilder {

	private Criteria criteria;
	private Map<String, Parameters> parameters;
	private IndexDAO dao;
	private Class clazz;
	
	public IndexBuilder(Class clazz) throws BeanBinException {
		this.dao = BeanBinDAOFactory.getIndexDAOLocal();
		this.clazz = clazz;
	}

	public String getJPAQL() throws BeanBinException {
		String property = criteria.getProperty();
		String term = criteria.getTerm().toString();
		
		String query = EJB3Searcher.ALIAS + "." + EntityUtils.getIdProperty(clazz);
		
		Set<Object> results = dao.search(clazz, property, term);		
		if(results.isEmpty()) {
			return query + " is null";
		}
		
		query += " in (";
		for(Object key : results) {
			query += key + " , ";
		}
		query = query.substring(0, query.length() - 2) + ")";
		return query;
	}

	public Map<String, Parameters> getParameters() throws BeanBinException {
		return parameters;
	}

	public void initialize(Criteria criteria, Map<String, Parameters> parameters) {
		this.criteria = criteria;
		this.parameters = parameters;
	}
}
