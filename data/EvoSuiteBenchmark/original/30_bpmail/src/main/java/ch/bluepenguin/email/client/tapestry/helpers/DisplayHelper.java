/*
 * Created on 18.05.2005
 *
 */
package ch.bluepenguin.email.client.tapestry.helpers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Christian
 * general purpose formatter helper: instead of having 
 * (e.g.) MailMessage output a formatted date, simply let this 
 * class do the job
 */
public class DisplayHelper {
    private Locale myLocale = Locale.getDefault();
    private String separator =",";
    
    
	public String returnStringList(String[] list) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<list.length;i++) {
			buffer.append(list[i]);
			if(i<(list.length-1)) {
				buffer.append(separator);
			}
		}
		return buffer.toString();
	}

	public String returnFormattedDate(Date date) {
		//todo: locale
		DateFormat formatter = 
			DateFormat.getDateInstance(DateFormat.SHORT,myLocale);
		return formatter.format(date);
	}
	/**
	 * @return Returns the myLocale.
	 */
	public Locale getMyLocale() {
		return myLocale;
	}
	/**
	 * @param myLocale The myLocale to set.
	 */
	public void setMyLocale(Locale myLocale) {
		this.myLocale = myLocale;
	}
	/**
	 * @return Returns the separator.
	 */
	public String getSeparator() {
		return separator;
	}
	/**
	 * @param separator The separator to set.
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
