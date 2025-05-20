package net.sourceforge.beanbin.data.ejb3.dao.search;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.SearchType;

public class LessThanBuilder extends BaseJPAQLBuilder {
	public String getQueryString() throws BeanBinException {
		if(getCriteria().getType() == SearchType.LESSTHAN) {
			return getProperty() + " < :" + getProperty() + getIndex();	
		} else if(getCriteria().getType() == SearchType.LESSTHANOREQUALTO) {
			return getProperty() + " <= :" + getProperty() + getIndex();			
		} else {
			throw new BeanBinException("Invalid SearchType " + getCriteria().getType() + " was passed to LessThanBuilder");
		}
	}
}
