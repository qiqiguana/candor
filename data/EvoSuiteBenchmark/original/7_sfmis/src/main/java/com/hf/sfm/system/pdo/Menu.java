package com.hf.sfm.system.pdo;

/**
 * Menu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Menu implements java.io.Serializable {

	// Fields

	private String idno;
	private String img;
	private String name;
	private String url;
	private String parentid;
	private String sort;
	private String oper;
	private String status;

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** full constructor */
	public Menu(String img, String name, String url, String parentid,
			String sort, String oper, String status) {
		this.img = img;
		this.name = name;
		this.url = url;
		this.parentid = parentid;
		this.sort = sort;
		this.oper = oper;
		this.status = status;
	}

	// Property accessors

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOper() {
		return this.oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}