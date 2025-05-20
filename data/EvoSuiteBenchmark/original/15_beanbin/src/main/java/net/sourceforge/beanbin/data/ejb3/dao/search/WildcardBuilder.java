package net.sourceforge.beanbin.data.ejb3.dao.search;

import net.sourceforge.beanbin.BeanBinException;

public class WildcardBuilder extends BaseJPAQLBuilder {
	public String getQueryString() throws BeanBinException {
		String term = getCriteria().getTerm().toString();
		term = term.replaceAll("\\\\\\*", "ESCAPEDASTRICK");
		term = term.replaceAll("\\'", "\\\\'");
		term = term.replaceAll("\\%", "\\\\%");
		term = term.replaceAll("\\*", "%");
		term = term.replaceAll("ESCAPEDASTRICK", "*");
		String queryString = getCriteria().getProperty() + " like '" + term + "'";
		return queryString;
	}
}
