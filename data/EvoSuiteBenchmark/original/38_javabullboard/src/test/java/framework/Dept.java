package framework;

import framework.base.BaseBean;
import java.math.BigDecimal;

public class Dept extends BaseBean
{
  private BigDecimal deptNo;
  String dName;
  protected String loc;

  public Dept()
  {
  }

  public BigDecimal getDeptNo()
  {
    return deptNo;
  }

  public void setDeptNo(BigDecimal deptNo)
  {
    this.deptNo = deptNo;
  }

  public String getDName()
  {
    return dName;
  }

  public void setDName(String dName)
  {
    this.dName = dName;
  }

  public String getLoc()
  {
    return loc;
  }

  public void setLoc(String loc)
  {
    this.loc = loc;
  }
}