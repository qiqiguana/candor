package net.sourceforge.beanbin.data.ejb3.dao.search;

import java.util.Map;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.ejb3.dao.EJB3Searcher;
import net.sourceforge.beanbin.data.ejb3.dao.Parameters;
import net.sourceforge.beanbin.query.Criteria;

public abstract class BaseJPAQLBuilder implements JPAQLBuilder {
	private Map<String, Parameters> parameters;
	private Criteria criteria;

	public void initialize(Criteria criteria, Map<String, Parameters> parameters) {
		this.criteria = criteria;
		this.parameters = parameters;
	}

	public Map<String, Parameters> getParameters() throws BeanBinException {
		if(parameters == null) {
			throw new BeanBinException("You must initialize this JPA-QL Builder before you can use it!");
		}
		return parameters;
	}
	
	protected Criteria getCriteria() throws BeanBinException {
		if(criteria == null) {
			throw new BeanBinException("You must initialize this JPA-QL Builder before you can use it!");
		}
		return criteria;
	}
	
	public String getJPAQL() throws BeanBinException {		
		String queryString = getQueryString();
		if(queryString.startsWith(EJB3Searcher.ALIAS)) {
			return queryString;
		} else {
			return EJB3Searcher.ALIAS + "." + queryString;	
		}
	}
	
	protected abstract String getQueryString() throws BeanBinException;

	protected String getProperty() throws BeanBinException {
		return getCriteria().getProperty();
	}

	protected int getIndex() throws BeanBinException {
		int index = 0;
		if(getParameters().containsKey(getProperty())) {
			index = getParameters().get(getProperty()).addTerm(getCriteria().getTerm());
		} else {
			Parameters params = new Parameters();
			params.addTerm(getCriteria().getTerm());
			getParameters().put(getProperty(), params);
		}
		return index;
	}
}
