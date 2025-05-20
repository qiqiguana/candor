package net.sourceforge.beanbin.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.sourceforge.beanbin.annotations.IndexSearch;

@Entity
public class IndexedEntity implements Serializable {
	private static final long serialVersionUID = -2572959880801546704L;
	
	private long id;
	private String string;
	private List<String> keywords;
	private String stringIndex;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
	@IndexSearch
	public List<String> getKeywords() {
		return this.keywords;
	}	
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	@IndexSearch
	public List<String> getGeneratedKeywords() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getString());
		list.add(getStringIndex());
		return list;
	}	
	
	@IndexSearch
	public String getStringIndex() {
		return this.stringIndex;
	}
	
	public void setStringIndex(String index) {
		this.stringIndex = index;
	}
	
	public void addKeyword(String keyword) {
		if(keywords == null) {
			keywords = new ArrayList<String>();
		}
		
		keywords.add(keyword);
	}
}
