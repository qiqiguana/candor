package net.sourceforge.beanbin.search.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndexField implements Serializable {	
	private static final long serialVersionUID = 6088774925521651919L;
	
	private List<String> values;
	private String property;

	public IndexField(String property) {
		this.property = property;
		this.values = new ArrayList<String>();
	}
	
	public void addValue(String value) {
		this.values.add(value);
	}
	
	public String getProperty() {
		return property;
	}
	
	public List<String> getValues() {
		return values;
	}
}