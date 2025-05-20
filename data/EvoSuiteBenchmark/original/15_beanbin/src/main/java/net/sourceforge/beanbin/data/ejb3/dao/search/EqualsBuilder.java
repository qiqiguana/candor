package net.sourceforge.beanbin.data.ejb3.dao.search;

import net.sourceforge.beanbin.BeanBinException;

public class EqualsBuilder extends BaseJPAQLBuilder {
	public String getQueryString() throws BeanBinException {		
		String term = getCriteria().getTerm().toString();
		
		if(term.indexOf("\\*") != -1) {
			if(term.replaceAll("\\\\\\*", "ESCAPEDASTRICK").indexOf("*") != -1) {
				return getWildcardJPAQL();				
			} else {
				getCriteria().setTerm(term.replaceAll("\\\\\\*", "*"));
				return getEqualsJPAQL();				
			}
		}
		
		if(term.indexOf("*") != -1) {
			return getWildcardJPAQL();
		} else {
			return getEqualsJPAQL();	
		}
	}

	private String getEqualsJPAQL() throws BeanBinException {
		return getProperty() + " = :" + getProperty() + getIndex();
	}

	private String getWildcardJPAQL() throws BeanBinException {
		WildcardBuilder builder = new WildcardBuilder();
		builder.initialize(getCriteria(), getParameters());
		return builder.getQueryString();
	}
}
