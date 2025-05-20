package net.sourceforge.beanbin;

import java.util.List;

import net.sourceforge.beanbin.command.ActiveListCommand;
import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.ramcram.TheCrammer;

public class TestBeanBinDAO implements BeanBinDAO {	
	private TheCrammer crammer;

	public TestBeanBinDAO() {
		this.crammer = new TheCrammer();
	}
	
	public void load(Class clazz) throws BeanBinException {
		// this method does nothing in the test implementation
	}
	
	public void execute(Transaction transaction) throws BeanBinException {
		for(ActiveListCommand command : transaction.getCommands()) {
			
		}
	}

	public void save(Object entity) throws BeanBinException {
		crammer.save(entity);
	}

	public List<Object> search(Class clazz, Query query) throws BeanBinException {
		return null;
	}

	public int getSize(Class clazz, Query query) throws BeanBinException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Object> initializeLazy(Class<?> clazz, Object key, String property) throws BeanBinException {
		// TODO Auto-generated method stub
		return null;
	}
}