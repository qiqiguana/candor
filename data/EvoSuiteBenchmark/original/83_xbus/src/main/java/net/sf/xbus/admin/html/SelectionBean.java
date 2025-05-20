package net.sf.xbus.admin.html;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * Use <code>SelectionBean</code> for getting journal entries from the
 * <code>Configuration</code> between selected time.
 */
public class SelectionBean
{
	/**
	 * Using <@link java.util.GregorianCalendar> adds the
	 * <code>getRequestFromDate()</code> specified amount of time (-1)to the
	 * given time field, based on the calendar's rules.
	 * 
	 * @return String - from date
	 * @see java.util.GregorianCalendar#add(int,int)
	 */
	public String getRequestFromDate()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -1);

		return Constants.getDateFormat().format(cal.getTime());
	}

	/**
	 * Using <@link java.util.GregorianCalendar> adds the
	 * <code>getRequestFromDate()</code> specified amount of time (+1)to the
	 * given time field, based on the calendar's rules.
	 * 
	 * @return String - to date
	 * @see java.util.GregorianCalendar#add(int,int)
	 */

	public String getRequestToDate()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, +1);

		return Constants.getDateFormat().format(cal.getTime());
	}

	/**
	 * Gets all sections from the <code>Configuration</code> for the giving
	 * system.
	 * 
	 * @return String
	 * @see net.sf.xbus.base.core.config.Configuration#getSections(String)
	 */
	public String getSystems()
	{
		try
		{
			Configuration config = Configuration.getInstance();
			List targets = config.getSections("System");
			StringBuffer buf = new StringBuffer(
					"<option selected>--------------------");
			for (Iterator it = targets.iterator(); it.hasNext();)
			{
				buf.append("<option>");
				buf.append((String) it.next());
			}
			return buf.toString();
		}
		catch (XException e)
		{
			return "";
		}
	}

}
