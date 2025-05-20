package net.sourceforge.beanbin;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.data.IndexDAO;

public class BeanBinDAOFactory {
	private static InitialContext initialContext;
	
	public static BeanBinDAO getDAO() throws BeanBinException {
		try {
			return (BeanBinDAO)getInitialContext().lookup("EJB3BeanBinDAO/remote");
		} catch (NamingException e) {
			throw new BeanBinException("BeanBinDAOFactory: " + e.getMessage(), e);
		}
	}
	
	public static IndexDAO getIndexDAORemote() throws BeanBinException {
		try {
			return (IndexDAO)getInitialContext().lookup("EJB3IndexDAO/remote");
		} catch (NamingException e) {
			throw new BeanBinException("BeanBinDAOFactory: " + e.getMessage(), e);
		}		
	}
	
	public static IndexDAO getIndexDAOLocal() throws BeanBinException {
		try {
			return (IndexDAO)getInitialContext().lookup("EJB3IndexDAO/local");
		} catch (NamingException e) {
			throw new BeanBinException("BeanBinDAOFactory: " + e.getMessage(), e);
		}		
	}
	
	public static void setInitialContext(InitialContext ic) {
		initialContext = ic;
	}
	
	public static InitialContext getInitialContext() throws NamingException {
		if(initialContext == null) {
			initialContext = new InitialContext();
		}
		return initialContext;
	}
}
