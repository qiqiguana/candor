package net.sf.gaeappmanager.google.appengine;

import java.math.BigDecimal;

/**
 * POJO class for parsed quota values.
 * 
 * @author Alois Belaska
 */
public class QuotaValue {

	private Quota quota;
	
	private boolean okay = false;
	
	private BigDecimal percent = BigDecimal.ZERO;
	
	private String unit = "";
	
	private BigDecimal value = BigDecimal.ZERO;
	
	private BigDecimal limit = BigDecimal.ZERO;

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

	public boolean isOkay() {
		return okay;
	}

	public void setOkay(boolean okay) {
		this.okay = okay;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
}
