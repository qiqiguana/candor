package com.hf.sfm.sfmis.personinfo.pdo;

import java.util.Date;

/**
 * APersonInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class APersonInfo implements java.io.Serializable {

	// Fields

	private String personid;
	private String name;
	private String sex;
	private Date birthday;
	private String department;
	private Date indate;
	private String persontype;
	private Date outdate;
	private String reason;
	private String pym;
	private String wbm;
	private String mobile;

	// Constructors

	/** default constructor */
	public APersonInfo() {
	}

	/** full constructor */
	public APersonInfo(String name, String sex, Date birthday,
			String department, Date indate, String persontype, Date outdate,
			String reason, String pym, String wbm) {
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.department = department;
		this.indate = indate;
		this.persontype = persontype;
		this.outdate = outdate;
		this.reason = reason;
		this.pym = pym;
		this.wbm = wbm;
	}

	// Property accessors

	public String getPersonid() {
		return this.personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getIndate() {
		return this.indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getPersontype() {
		return this.persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public Date getOutdate() {
		return this.outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPym() {
		return this.pym;
	}

	public void setPym(String pym) {
		this.pym = pym;
	}

	public String getWbm() {
		return this.wbm;
	}

	public void setWbm(String wbm) {
		this.wbm = wbm;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}