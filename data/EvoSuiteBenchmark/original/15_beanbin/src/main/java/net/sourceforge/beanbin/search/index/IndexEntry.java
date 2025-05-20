package net.sourceforge.beanbin.search.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndexEntry implements Serializable {
	private static final long serialVersionUID = 7725437492296294825L;
	
	private Object key;
	private List<IndexField> fields;
	private Class clazz;
	
	public IndexEntry(Class clazz, Object key) {
		this.clazz = clazz;
		this.key = key;
		this.fields = new ArrayList<IndexField>();;
	}
	
	public Class getTargetClass() {
		return clazz;
	}
	
	public IndexField getField(String property) {
		for(IndexField field : fields) {
			if(field.getProperty().equals(property)) {
				return field;
			}
		}
		
		return null;
	}
	
	public void addField(IndexField field) {
		this.fields.add(field);
	}
	
	public Object getKey() {
		return key;
	}
	
	public List<IndexField> getFields() {
		return fields;
	}
}