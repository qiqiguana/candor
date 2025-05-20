package net.sf.xbus.base.journal;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.technical.database.DBConnection;

/**
 * <code>DBJournal</code> writes information about the activities of the
 * middleware into the database.
 * <p>
 * 
 * The information is stored in the database-table <code>journal</code>. An
 * instance of {@link net.sf.xbus.technical.database.DBConnection} is used for
 * handling the database-connection.
 */
public class DBJournal implements JournalInterface
{
	private static final String INSERT_JOURNAL = "InsertJournal";
	
	private DBConnection mDBConnection = null;

	/**
	 * Writes a middleware-activity to the database. An activity can be:
	 * <ul>
	 * <li>A message has been received and processed.</li>
	 * <li>A message has been send to a neighbor-system.</li>
	 * </ul>
	 * <p>
	 * The {@link net.sf.xbus.base.core.config.Configuration} stores, which
	 * activities are written. All other activities are ignored.
	 * <p>
	 * 
	 * @param type <b>R</b>: message has been received, <b>S</b>: message has
	 *            been send
	 * @param system The name of the system, from which the message has been
	 *            received or where the message has been sent to.
	 * @param message The {@link Message}-object that represents the data of
	 *            the message.
	 * 
	 */
	public void log(char type, XBUSSystem system, Message message)
			throws XException
	{
		writeToDB(type, system, message, false);
	}

	/**
	 * Commits the database.
	 */
	public void commit() throws XException
	{
		if (mDBConnection != null)
		{
			mDBConnection.commit();
		}
	}

	/**
	 * Does the writing to the database.
	 */
	private void writeToDB(char type, XBUSSystem system, Message message,
			boolean retry) throws XException
	{
		String requestMessage = Journal.formatText(message, system, true);
		String responseMessage = Journal.formatText(message, system, false);

		String requestTimestamp = Journal.formatDate(message
				.getRequestTimestamp());
		String responseTimestamp = Journal.formatDate(message
				.getResponseTimestamp());

		if (mDBConnection == null)
		{
			String dbConnectionName = Configuration.getInstance()
					.getValueOptional("Base", "Journal", "DBConnection");
			if (dbConnectionName == null)
			{
				dbConnectionName = DBConnection.UNNAMED;
			}
			mDBConnection = DBConnection.getInstance(dbConnectionName);
		}

		String statementName = getStatementName();

		try
		{
			if (!mDBConnection.existsPrepared(statementName))
			{
				mDBConnection.prepareStatement(statementName,
						new StringBuffer().append("INSERT INTO journal (")
								.append("jo_type, ").append("jo_system, ")
								.append("jo_function, ").append(
										"jo_message_id, ").append(
										"jo_request_message, ").append(
										"jo_request_timestamp, ").append(
										"jo_response_message, ").append(
										"jo_response_timestamp, ").append(
										"jo_returncode,	").append(
										"jo_errorcode, ").append(
										"jo_errormessage").append(") VALUES (")
								.append("?,?,?,?,?,?,?,?,?,?,?)").toString());
			}

			mDBConnection.bind(statementName, 1, (new Character(type))
					.toString());
			mDBConnection.bind(statementName, 2, system.getCompleteName());
			mDBConnection.bind(statementName, 3, message.getFunction());
			mDBConnection.bind(statementName, 4, message.getId());
			mDBConnection.bind(statementName, 5, requestMessage);
			mDBConnection.bind(statementName, 6, requestTimestamp);
			mDBConnection.bind(statementName, 7, responseMessage);
			mDBConnection.bind(statementName, 8, responseTimestamp);
			mDBConnection.bind(statementName, 9, message.getReturncode());
			mDBConnection.bind(statementName, 10, message.getErrorcode());
			mDBConnection.bind(statementName, 11, message.getErrortext());

			mDBConnection.executeUpdatePrepared(statementName);
		}
		catch (XException e)
		{
			if (!retry)
			{
				Trace.warn("DBConnection maybe gone, trying again");
				mDBConnection.reopen();
				writeToDB(type, system, message, true);
			}
		}
	}

	private String getStatementName()
	{
		return new StringBuffer(INSERT_JOURNAL).append(
				Thread.currentThread().getName()).toString();
	}
}
