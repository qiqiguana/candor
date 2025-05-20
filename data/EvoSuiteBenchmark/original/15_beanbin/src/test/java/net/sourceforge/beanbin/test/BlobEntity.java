package net.sourceforge.beanbin.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlobEntity implements Serializable {
	private static final long serialVersionUID = -15334683604939237L;

	private long id;
	private String string;	
	private BlobOne blobOne;
	private BlobTwo blobTwo;
	
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
	
	public BlobOne getBlobOne() {
		return blobOne;
	}
	public void setBlobOne(BlobOne blobOne) {
		this.blobOne = blobOne;
	}
	
	@Column(name="thesecondblob")
	public BlobTwo getBlobTwo() {
		return blobTwo;
	}
	public void setBlobTwo(BlobTwo blobTwo) {
		this.blobTwo = blobTwo;
	}
}
