package framework;
import framework.base.BaseBean;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Emp extends BaseBean
{
  private String empNo;
  String ename;
  protected String job;
  public Integer mgr;
  Date hireDate;
  Double sal;
  private double comm;
  BigDecimal deptNo;
  Dept deptObject;
  private Timestamp lastLogin;

  public Emp()
  {
  }

  public String getEmpNo()
  {
    return empNo;
  }

  public void setEmpNo(String empNo)
  {
    this.empNo = empNo;
  }

  public String getEname()
  {
    return ename;
  }

  public void setEname(String ename)
  {
    this.ename = ename;
  }

  public String getJob()
  {
    return job;
  }

  public void setJob(String job)
  {
    this.job = job;
  }

  public Integer getMgr()
  {
    return mgr;
  }

  public void setMgr(Integer mgr)
  {
    this.mgr = mgr;
  }

  public Date getHireDate()
  {
    return hireDate;
  }

  public void setHireDate(Date hireDate)
  {
    this.hireDate = hireDate;
  }

  public Double getSal()
  {
    return sal;
  }

  public void setSal(Double sal)
  {
    this.sal = sal;
  }

  public double getComm()
  {
    return comm;
  }

  public void setComm(double comm)
  {
    this.comm = comm;
  }

  public BigDecimal getDeptNo()
  {
    return deptNo;
  }

  public void setDeptNo(BigDecimal deptNo)
  {
    this.deptNo = deptNo;
  }

  public Dept getDeptObject()
  {
    return deptObject;
  }

  public void setDeptObject(Dept deptObject)
  {
    this.deptObject = deptObject;
  }

  public Timestamp getLastLogin()
  {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin)
  {
    this.lastLogin = lastLogin;
  }


    
}