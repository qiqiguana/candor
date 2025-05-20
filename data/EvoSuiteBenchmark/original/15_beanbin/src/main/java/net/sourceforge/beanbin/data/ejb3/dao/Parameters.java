package net.sourceforge.beanbin.data.ejb3.dao;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;

public class Parameters {
	private List<Object> params;
	
	public Parameters() {
		this.params = new ArrayList<Object>();
	}
	
	public int addTerm(Object term) {
		params.add(term);
		return params.size() - 1;
	}
	
	public Object getTerm(int i) throws BeanBinException {
		if(i < 0 || i > getSize()) {
			throw new BeanBinException("The index " + i + " is invalid becuase I only have " + getSize() + " parameters.");
		}
		return params.get(i);
	}

	public int getSize() {
		return params.size();
	}	
}
