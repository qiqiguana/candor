package net.sourceforge.beanbin.test;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class AnotherEntity implements Serializable {	
	private static final long serialVersionUID = 9130317204010939451L;
	
	private Long id;
	private List<SubTestEntity> subs;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToMany (
			targetEntity=SubTestEntity.class,
			cascade=CascadeType.REMOVE
	)
	@JoinTable (
			name="subtest_to_another",
			joinColumns={@JoinColumn(name="anotherid")},
			inverseJoinColumns={@JoinColumn(name="subtestid")}
	)
	public List<SubTestEntity> getSubs() {
		return subs;
	}	
	public void setSubs(List<SubTestEntity> subs) {
		this.subs = subs;
	}
}
