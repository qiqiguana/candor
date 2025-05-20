package com.hf.sfm.system.pdo;

/**
 * AGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AGroup implements java.io.Serializable {

	// Fields

	private String idno;
	private String name;
	private String mark;

	// Constructors

	/** default constructor */
	public AGroup() {
	}

	/** full constructor */
	public AGroup(String name, String mark) {
		this.name = name;
		this.mark = mark;
	}

	// Property accessors

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}