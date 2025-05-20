/*
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.framework.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class EmploymentHistory_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public EmploymentHistory_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EmploymentStatus _employmentStatus;

    /** 
     * Setter for employmentStatus
     * @param employmentStatus the org.sourceforge.ifx.framework.element.EmploymentStatus to set
     */
    public void setEmploymentStatus(org.sourceforge.ifx.framework.element.EmploymentStatus _employmentStatus) {
        this._employmentStatus = _employmentStatus;
    }

    /**
     * Getter for employmentStatus
     * @return a org.sourceforge.ifx.framework.element.EmploymentStatus
     */
    public org.sourceforge.ifx.framework.element.EmploymentStatus getEmploymentStatus() {
        return _employmentStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgInfo _orgInfo;

    /** 
     * Setter for orgInfo
     * @param orgInfo the org.sourceforge.ifx.framework.element.OrgInfo to set
     */
    public void setOrgInfo(org.sourceforge.ifx.framework.element.OrgInfo _orgInfo) {
        this._orgInfo = _orgInfo;
    }

    /**
     * Getter for orgInfo
     * @return a org.sourceforge.ifx.framework.element.OrgInfo
     */
    public org.sourceforge.ifx.framework.element.OrgInfo getOrgInfo() {
        return _orgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Occupation _occupation;

    /** 
     * Setter for occupation
     * @param occupation the org.sourceforge.ifx.framework.element.Occupation to set
     */
    public void setOccupation(org.sourceforge.ifx.framework.element.Occupation _occupation) {
        this._occupation = _occupation;
    }

    /**
     * Getter for occupation
     * @return a org.sourceforge.ifx.framework.element.Occupation
     */
    public org.sourceforge.ifx.framework.element.Occupation getOccupation() {
        return _occupation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Income _income;

    /** 
     * Setter for income
     * @param income the org.sourceforge.ifx.framework.element.Income to set
     */
    public void setIncome(org.sourceforge.ifx.framework.element.Income _income) {
        this._income = _income;
    }

    /**
     * Getter for income
     * @return a org.sourceforge.ifx.framework.element.Income
     */
    public org.sourceforge.ifx.framework.element.Income getIncome() {
        return _income;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.JobTitle _jobTitle;

    /** 
     * Setter for jobTitle
     * @param jobTitle the org.sourceforge.ifx.framework.element.JobTitle to set
     */
    public void setJobTitle(org.sourceforge.ifx.framework.element.JobTitle _jobTitle) {
        this._jobTitle = _jobTitle;
    }

    /**
     * Getter for jobTitle
     * @return a org.sourceforge.ifx.framework.element.JobTitle
     */
    public org.sourceforge.ifx.framework.element.JobTitle getJobTitle() {
        return _jobTitle;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StartDt _startDt;

    /** 
     * Setter for startDt
     * @param startDt the org.sourceforge.ifx.framework.element.StartDt to set
     */
    public void setStartDt(org.sourceforge.ifx.framework.element.StartDt _startDt) {
        this._startDt = _startDt;
    }

    /**
     * Getter for startDt
     * @return a org.sourceforge.ifx.framework.element.StartDt
     */
    public org.sourceforge.ifx.framework.element.StartDt getStartDt() {
        return _startDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EndDt _endDt;

    /** 
     * Setter for endDt
     * @param endDt the org.sourceforge.ifx.framework.element.EndDt to set
     */
    public void setEndDt(org.sourceforge.ifx.framework.element.EndDt _endDt) {
        this._endDt = _endDt;
    }

    /**
     * Getter for endDt
     * @return a org.sourceforge.ifx.framework.element.EndDt
     */
    public org.sourceforge.ifx.framework.element.EndDt getEndDt() {
        return _endDt;
    }


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * EmploymentStatus
       * OrgInfo
       * Occupation
       * Income
       * JobTitle
       * StartDt
       * EndDt
       */
    public final String[] ELEMENTS = {
              "EmploymentStatus"
                 ,"OrgInfo"
                 ,"Occupation"
                 ,"Income"
                 ,"JobTitle"
                 ,"StartDt"
                 ,"EndDt"
          };
}
