package net.sourceforge.beanbin.data.ejb3.dao.search;

import java.util.Map;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.ejb3.dao.Parameters;
import net.sourceforge.beanbin.query.Criteria;

public interface JPAQLBuilder {
	public void initialize(Criteria criteria, Map<String, Parameters> parameters);
	public String getJPAQL() throws BeanBinException;
	public Map<String, Parameters> getParameters() throws BeanBinException;
}
