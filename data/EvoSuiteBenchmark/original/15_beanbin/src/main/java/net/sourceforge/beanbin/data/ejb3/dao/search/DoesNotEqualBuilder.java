package net.sourceforge.beanbin.data.ejb3.dao.search;

import net.sourceforge.beanbin.BeanBinException;

public class DoesNotEqualBuilder extends BaseJPAQLBuilder {
	public String getQueryString() throws BeanBinException {		
		return getProperty() + " != :" + getProperty() + getIndex();
	}
}
