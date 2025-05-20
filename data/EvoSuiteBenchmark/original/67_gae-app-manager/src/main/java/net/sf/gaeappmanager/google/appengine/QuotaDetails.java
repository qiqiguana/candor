package net.sf.gaeappmanager.google.appengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Quota details of application.
 * 
 * @author Alois Belaska
 */
public class QuotaDetails {

	private int resetIntervalHours = -1;
	
	private int nextResetWithinHours = -1;
	
	private List<QuotaValue> values = new ArrayList<QuotaValue>();
	
	public int getResetIntervalHours() {
		return resetIntervalHours;
	}
	
	public void setResetIntervalHours(int resetIntervalHours) {
		this.resetIntervalHours = resetIntervalHours;
	}
	
	public int getNextResetWithinHours() {
		return nextResetWithinHours;
	}
	
	public void setNextResetWithinHours(int nextResetWithinHours) {
		this.nextResetWithinHours = nextResetWithinHours;
	}
	
	public List<QuotaValue> getValues() {
		return values;
	}
	
	public QuotaValue findQuota(Quota findQuota) {
		for (QuotaValue qValue : values) {
			if (qValue.getQuota().equals(findQuota)) {
				return qValue;
			}
		}
		return null;
	}
}
