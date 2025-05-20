package com.hf.sfm.sfmis.department.pdo;

/**
 * ADepartment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ADepartment implements java.io.Serializable {

	// Fields

	private String idno;
	private String name;
	private String mark;

	// Constructors

	/** default constructor */
	public ADepartment() {
	}

	/** full constructor */
	public ADepartment(String name, String mark) {
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