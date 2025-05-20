package net.sourceforge.beanbin.test;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class TestEntity implements Serializable {
	private static final long serialVersionUID = 7110502909738727266L;
	
	private int anInt;
	private String string;
	private long id;
	private ImplOne ione;
	private ImplTwo itwo;

	private List<SubTestEntity> subs;
		
	@Column(name="int")
	public int getAnInt() {
		return anInt;
	}
	public void setAnInt(int anInt) {
		this.anInt = anInt;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="entity")
	public List<SubTestEntity> getSubs() {
		return subs;
	}
	public void setSubs(List<SubTestEntity> subs) {
		this.subs = subs;
	}
	
	@Column(name="thestring")
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
	@OneToOne
	@JoinColumn(name="imploneid")
	public ImplOne getIone() {
		return ione;
	}
	public void setIone(ImplOne ione) {
		this.ione = ione;
	}
	
	@OneToOne
	@JoinColumn(name="impltwoid")	
	public ImplTwo getItwo() {
		return itwo;
	}
	public void setItwo(ImplTwo itwo) {
		this.itwo = itwo;
	}
	
	@Transient
	public String getStaticData() {
		return "static data";
	}
}