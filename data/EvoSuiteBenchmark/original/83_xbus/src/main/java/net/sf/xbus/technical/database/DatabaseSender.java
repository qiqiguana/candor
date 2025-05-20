package net.sf.xbus.technical.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * The <code>DatabaseSender</code> sends SQL-statements to a database and
 * returns the results. The request and the response are XML documents.
 * <p />
 * Please refer to the documentation about the structure of the XML documents.
 */
public class DatabaseSender implements Sender, ObjectSender
{
	private XBUSSystem mDestination = null;

	/**
	 * The constructor stores the destination.
	 * 
	 * @param destination definition of the interface in
	 *            <code>standard.conf</code>
	 * @throws XException never thrown
	 */
	public DatabaseSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * Sends SQL statements to the database and return the results.
	 * 
	 * @param function not used
	 * @param callData XML document containing SQL statements
	 * @return XML document with result when <code>callData</code> contained
	 *         <code>SELECT</code> statements, NULL otherwise
	 * @throws XException in case of errors
	 */
	public Object execute(String function, Object callData) throws XException
	{
		Document retDocument = XMLHelper.getDocumentBuilder("Default",
				mDestination.getName()).newDocument();
		retDocument.appendChild(retDocument.createElement(mDestination
				.getName()));

		/*
		 * Getting an instance of the NamedDBConnection
		 */
		Configuration config = Configuration.getInstance();
		String connectionName = config.getValue(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "DBConnection");
		DBConnection connection = DBConnection.getInstance(connectionName);

		/*
		 * Execute the given SQL statements
		 */
		Vector statements = extractStatements((Document) callData);
		ResultSet results = null;
		for (Iterator it = statements.iterator(); it.hasNext();)
		{
			String sqlStatement = (String) it.next();
			if (sqlStatement.toUpperCase().startsWith("SELECT"))
			{
				/*
				 * SELECT statements return a ResultSet, that is copied into a
				 * XML structure
				 */
				results = connection.executeRead(sqlStatement);
				addResultSelect(results, retDocument, sqlStatement);
			}
			else
			{
				/*
				 * other statements alter the database content and return no
				 * results
				 */
				int result = connection.executeUpdate(sqlStatement);
				addResultUpdate(result, retDocument, sqlStatement);
			}
		}
		return retDocument;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}

	private Vector extractStatements(Document doc)
	{
		Vector retVector = new Vector();

		String statement = null;

		NodeList nodes = doc.getElementsByTagName("Statement");
		for (int i = 0; i < nodes.getLength(); i++)
		{
			statement = XMLHelper.getNodeText(nodes.item(i));
			if (statement != null)
			{
				retVector.add(statement);
			}
		}

		return retVector;
	}

	private void addResultSelect(ResultSet results, Document doc,
			String sqlStatement) throws XException
	{
		Element statement = doc.createElement("Result");
		statement.setAttribute("statement", sqlStatement);
		doc.getDocumentElement().appendChild(statement);

		Element record = null;
		Element column = null;
		String data = null;
		Text columnData = null;

		try
		{
			ResultSetMetaData metadata = results.getMetaData();
			while (results.next())
			{
				record = doc.createElement("Record");
				for (int i = 1; i <= metadata.getColumnCount(); i++)
				{
					String tableName = metadata.getTableName(i);
					if (tableName == null)
					{
						tableName = "";
					}

					if (tableName.equals(""))
					{
						column = doc.createElement(metadata.getColumnName(i));
					}
					else
					{
						column = doc.createElement(new StringBuffer(tableName)
								.append(".").append(metadata.getColumnName(i))
								.toString());
					}

					data = results.getString(i);
					if (data != null)
					{
						columnData = doc.createTextNode(data);
						column.appendChild(columnData);
					}
					record.appendChild(column);
				}
				statement.appendChild(record);
			}
		}
		catch (SQLException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_DATABASE, "0", e);
		}
	}

	private void addResultUpdate(int result, Document doc, String sqlStatement)
	{
		Element statement = doc.createElement("Result");
		statement.setAttribute("statement", sqlStatement);
		statement.setAttribute("rowcount", new Integer(result).toString());
		doc.getDocumentElement().appendChild(statement);
	}
}
