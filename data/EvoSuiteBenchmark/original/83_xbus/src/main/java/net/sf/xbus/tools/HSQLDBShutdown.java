/*
 * Created on 10.07.2003
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.xbus.tools;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.technical.database.DBConnection;

/**
 * @author fleckenstein
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HSQLDBShutdown
{

	public static void main(String[] args)
	{
		try
		{
			String dbConnectionName = Configuration.getInstance()
					.getValueOptional("Base", "Journal", "DBConnection");
			if (dbConnectionName == null)
			{
				dbConnectionName = DBConnection.UNNAMED;
			}
			DBConnection dbcon = DBConnection.getInstance(dbConnectionName);

			if ((dbcon.getUrl() != null)
					&& (dbcon.getUrl().startsWith("jdbc:hsqldb")))
			{
				dbcon.executeUpdate("shutdown compact");
			}
		}
		catch (XException e)
		{
			System.exit(1);
		}

	}
}
