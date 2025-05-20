package net.sourceforge.beanbin.test;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class SubTestEntity implements Serializable {
	private static final long serialVersionUID = 3953821883006547167L;
	private long id;
	private String someString;
	private TestEntity entity;
	private List<AnotherEntity> anothers;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSomeString() {
		return someString;
	}
	public void setSomeString(String someString) {
		this.someString = someString;
	}
	
	@ManyToOne
	@JoinColumn(name="entityid")	
	public TestEntity getEntity() {
		return entity;
	}
	public void setEntity(TestEntity entity) {
		this.entity = entity;
	}
	
	@ManyToMany (
			targetEntity=AnotherEntity.class, 
			cascade=CascadeType.REMOVE
	)
	@JoinTable (
			name="subtest_to_another",
			joinColumns={@JoinColumn(name="subtestid")},
			inverseJoinColumns={@JoinColumn(name="anotherid")}
	)
	public List<AnotherEntity> getAnothers() {
		return anothers;
	}
	public void setAnothers(List<AnotherEntity> anothers) {
		this.anothers = anothers;
	}
}
