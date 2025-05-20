package com.hf.sfm.system.pdo;

/**
 * AWorker entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AWorker implements java.io.Serializable {

	// Fields

	private String idno;
	private String account;
	private String password;
	private String groupid;
	private String state;
	private String personid;
	private String flatid;

	// Constructors

	/** default constructor */
	public AWorker() {
	}

	/** full constructor */
	public AWorker(String account, String password, String groupid,
			String state, String personid, String flatid) {
		this.account = account;
		this.password = password;
		this.groupid = groupid;
		this.state = state;
		this.personid = personid;
		this.flatid = flatid;
	}

	// Property accessors

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPersonid() {
		return this.personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getFlatid() {
		return this.flatid;
	}

	public void setFlatid(String flatid) {
		this.flatid = flatid;
	}

}