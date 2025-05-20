package net.sf.xbus.admin.html;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.technical.database.DBConnection;

/**
 * <code>ReadJournal</code> reads information about the activities of the
 * middleware from the database and handles the information which is stored in
 * the database-table <code>journal</code>. An instance of
 * {@link net.sf.xbus.technical.database.DBConnection} is used for handling the
 * database-connection.
 */
public class ReadJournal
{

	private int mNumber;
	private char mType;
	private String mSystem;
	private String mFunction;
	private String mMessageId;
	private String mRequestMessage;
	private String mRequestTimestamp;
	private String mResponseMessage;
	private String mResponseTimestamp;
	private String mReturncode;
	private int mErrorcode;
	private String mErrormessage;

	/**
	 * Gets the number from the journal
	 */
	public int getNumber()
	{
		return mNumber;
	}

	/**
	 * Allows you to set the number in the journal
	 */
	public void setNumber(int number)
	{
		mNumber = number;
	}

	/**
	 * Gets the type of the message from the journal
	 */
	public char getType()
	{
		return mType;
	}

	/**
	 * Allows you to set the type of the message
	 */
	public void setType(char type)
	{
		mType = type;
	}

	/**
	 * Gets the system from the journal
	 */
	public String getSystem()
	{
		return mSystem;
	}

	/**
	 * Allows you to set the system
	 */
	public void setSystem(String system)
	{
		mSystem = system;
	}

	/**
	 * Gets the function from the journal
	 */
	public String getFunction()
	{
		return mFunction;
	}

	/**
	 * Allows you to set the function
	 */
	public void setFunction(String function)
	{
		mFunction = function;
	}

	/**
	 * Gets the message id from the journal
	 */
	public String getMessageId()
	{
		return mMessageId;
	}

	/**
	 * Allows you to set the message id
	 */
	public void setMessageId(String messageId)
	{
		mMessageId = messageId;
	}

	/**
	 * Gets the request message from the journal
	 */
	public String getRequestMessage()
	{
		return mRequestMessage;
	}

	/**
	 * Allows you to set the request message
	 */
	public void setRequestMessage(String requestMessage)
	{
		mRequestMessage = requestMessage;
	}

	/**
	 * Gets the request timestamp from the journal
	 */
	public String getRequestTimestamp()
	{
		return mRequestTimestamp;
	}

	/**
	 * Allows you to set the request timestamp
	 */
	public void setRequestTimestamp(String requestTimestamp)
	{
		mRequestTimestamp = requestTimestamp;
	}

	/**
	 * Gets the response message from the journal
	 */
	public String getResponseMessage()
	{
		return mResponseMessage;
	}

	public void setResponseMessage(String responseMessage)
	/**
	 * Allows you to set the response message
	 */
	{
		mResponseMessage = responseMessage;
	}

	/**
	 * Gets the response timestamp from the journal
	 */
	public String getResponseTimestamp()
	{
		return mResponseTimestamp;
	}

	/**
	 * Allows you to set the response timestamp
	 */
	public void setResponseTimestamp(String responseTimestamp)
	{
		mResponseTimestamp = responseTimestamp;
	}

	/**
	 * Gets the return code from the journal
	 */
	public String getReturncode()
	{
		return mReturncode;
	}

	/**
	 * Allows you to set the returncode
	 */
	public void setReturncode(String returncode)
	{
		mReturncode = returncode;
	}

	/**
	 * Gets the error code from the journal
	 */
	public int getErrorcode()
	{
		return mErrorcode;
	}

	/**
	 * Allows you to set the error code
	 */
	public void setErrorcode(int errorcode)
	{
		mErrorcode = errorcode;
	}

	/**
	 * Gets the error message from the journal
	 */
	public String getErrormessage()
	{
		return mErrormessage;
	}

	/**
	 * Allows you to set the error message
	 */
	public void setErrormessage(String errormessage)
	{
		mErrormessage = errormessage;
	}

	/**
	 * <code>readSelected(HashMap, String, String)</code> builds the SQL
	 * statement, connects to the database and executes it. All data from the
	 * <code>ResultSet</code> strore erst in the <code>ReadJournal</code>
	 * object and then in the List.
	 * 
	 * @param selectionMap - HashMap with items should be selected
	 * @param orderBy - String (by wich data should be ordered)
	 * @param sorting -String (descending or not)
	 * @return List with the ReadJournal objects
	 */
	static public List readSelected(HashMap selectionMap, String orderBy,
			String sorting) throws XException
	{
		String sqlBefehl = "SELECT * FROM journal ";

		if (!selectionMap.isEmpty())
		{

			sqlBefehl += "WHERE ";
			List keyList = new Vector();
			keyList.addAll(selectionMap.keySet());

			for (int i = 0; i < keyList.size(); i++)
			{
				String fieldName = (String) keyList.get(i);
				String fieldValue = (String) selectionMap.get(fieldName);

				if (fieldName.equals("RequestTimeMin"))
				{
					sqlBefehl += "jo_request_timestamp >='" + fieldValue + "' ";
				}
				else if (fieldName.equals("RequestTimeMax"))
				{
					sqlBefehl += "jo_request_timestamp <='" + fieldValue + "' ";
				}
				else if (fieldName.equals("RequestMessage"))
				{
					sqlBefehl += "jo_request_message LIKE '%" + fieldValue
							+ "%'";
				}
				else if (fieldName.equals("ResponseMessage"))
				{
					sqlBefehl += "jo_response_message LIKE '%" + fieldValue
							+ "%'";
				}
				else if (fieldName.equals("Type"))
				{
					sqlBefehl += "jo_type ='" + fieldValue + "' ";
				}
				else if (fieldName.equals("Returncode"))
				{
					sqlBefehl += "jo_returncode ='" + fieldValue + "' ";
				}
				else if (fieldName.equals("System"))
				{
					sqlBefehl += "jo_system ='" + fieldValue + "' ";
				}
				else if (fieldName.equals("Function"))
				{
					sqlBefehl += "jo_function ='" + fieldValue + "' ";
				}
				else if (fieldName.equals("MessageId"))
				{
					sqlBefehl += "jo_message_id ='" + fieldValue + "' ";
				}
				else
				{
					List params = new Vector();
					params.add(fieldName);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_ADMIN,
							Constants.PACKAGE_ADMIN_ADMIN, "3", params);
				}

				if (i < (keyList.size() - 1))
				{
					sqlBefehl += "AND ";
				}
			}

		}

		String order;
		if (orderBy.equals("Number"))
		{
			order = "jo_id";
		}
		else if (orderBy.equals("Returncode"))
		{
			order = "jo_returncode";
		}
		else if (orderBy.equals("System"))
		{
			order = "jo_system";
		}
		else if (orderBy.equals("Function"))
		{
			order = "jo_function";
		}
		else if (orderBy.equals("MessageId"))
		{
			order = "jo_message_id";
		}
		else if (orderBy.equals("type"))
		{
			order = "jo_type";
		}
		else if (orderBy.equals("RequestTimestamp"))
		{
			order = "jo_request_timestamp";
		}
		else
		{
			List params = new Vector();
			params.add(orderBy);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_ADMIN, "4",
					params);
		}

		sqlBefehl += "ORDER BY " + order;

		if (sorting.equals("Descending"))
		{
			sqlBefehl += " DESC";
		}

		try
		{
			String dbConnectionName = Configuration.getInstance()
					.getValueOptional("Base", "Journal", "DBConnection");
			if (dbConnectionName == null)
			{
				dbConnectionName = DBConnection.UNNAMED;
			}
			DBConnection dbCon = DBConnection.getInstance(dbConnectionName);

			ResultSet result = dbCon.executeRead(sqlBefehl);
			Vector list = new Vector();

			while (result.next())
			{
				ReadJournal journal = new ReadJournal();
				journal.setNumber(result.getInt("jo_id"));
				if ((result.getString("jo_type") == null)
						|| (result.getString("jo_type").length() == 0))
				{
					journal.setType(' ');
				}
				else
				{
					journal.setType(result.getString("jo_type").charAt(0));
				}
				journal.setSystem(result.getString("jo_system"));
				journal.setFunction(result.getString("jo_function"));
				journal.setMessageId(result.getString("jo_message_id"));
				journal.setRequestMessage(result
						.getString("jo_request_message"));
				journal.setRequestTimestamp(result
						.getString("jo_request_timestamp"));
				journal.setResponseMessage(result
						.getString("jo_response_message"));
				journal.setResponseTimestamp(result
						.getString("jo_response_timestamp"));
				journal.setReturncode(result.getString("jo_returncode"));
				journal.setErrorcode(result.getInt("jo_errorcode"));
				journal.setErrormessage(result.getString("jo_errormessage"));

				list.add(journal);
			}

			return list;
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_ADMIN, "0",
					e);
		}

	}

	/**
	 * Gets all necessary information from the journal as one String in the
	 * following format:
	 * 
	 * <pre>
	 * number | messageId | function | type | system | requestTimestamp
	 * 		| ResponseTimestamp | Returncode
	 * </pre>
	 * 
	 * @return String
	 */
	public String toString()
	{
		return mNumber + " | " + mMessageId + " | " + mFunction + " | " + mType
				+ " | " + mSystem + " | " + mRequestTimestamp + " | "
				+ mResponseTimestamp + " | " + mReturncode;
	}

}
