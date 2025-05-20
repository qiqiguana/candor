package net.sourceforge.beanbin.data.ejb3.dao.search;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.SearchType;

public class ContainsBuilder extends BaseJPAQLBuilder {
	public String getQueryString() throws BeanBinException {		
		EqualsBuilder builder = new EqualsBuilder();
		Criteria criteria = new Criteria(getCriteria().getProperty(), "*" + getCriteria().getTerm().toString() + "*", SearchType.EQUALS);
		builder.initialize(criteria, getParameters());
		return builder.getJPAQL();
	}
}
