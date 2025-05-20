package net.sf.xbus.admin.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.xbus.base.core.XException;

/**
 * Depending on the obtained parameters from the <code>SelectPage</code> the
 * <code>JournalBean</code> supplies the <code>JournalPage</code> with the
 * results from data base in the form of the table. This class contains instance
 * from the {@link  ReadJournal}classe.
 */
public class JournalBean
{

	private List entries;
	private static final String collumnBgCollor = "#D1D0C7";
	private static final String greyCollor = "#E7E9E6";
	private static final String whiteCollor = "white";
	private boolean collor = false;
	private HashMap journalMap = new HashMap();
	private String message;
	private String details;
	private String journIndex;

	// necessary html tages
	private static final String openTagTable_message = "<table width=\"400\" border=0 cellpadding=0 cellspacing=0><tr class=\"journal\"><td colspan=\"2\"><p class=\"zwkopf\">";
	private static final String detailsTableMessage = "<tr bgcolor=\"#E7E9E6\"><td width=\"10\">&nbsp;</td><td>";
	private static final String deatilsTable = "</td></tr><tr class=\"journal\"><td colspan=\"2\">&nbsp;&nbsp;&nbsp;</td></tr>";
	private static final String detailsTagFirst = "<tr bgcolor=\"#E7E9E6\"><td width=\"160\" valign=\"top\">";
	private static final String detailsTagOne = "</td></tr><tr bgcolor=\"#E7E9E6\"><td width=\"160\" valign=\"top\">";
	private static final String detailsTagTwo = "</td></tr><tr class=\"journal\"><td width=\"160\" valign=\"top\">";
	private static final String detailsTd = "</td><td>";
	private static final String openTagTdTr = "<tr><td>";
	private static final String openTagTd = "<td>";
	private static final String openTagTd_journal = "<td class=\"journal\">";
	private static final String openTagTd_function = "<td class=\"function\">";
	private static final String openTagTd_selection = "<td class=\"selection\">";
	private static final String openTagTr_selection = "<tr align=\"center\" bgcolor=";
	private static final String openTagTr_bgcolor = "<tr bgcolor=";
	private static final String openTagP = "<p class=\"zwkopf\">";
	private static final String fullTagH4 = "<h4>Details</h4>";
	private static final String paramDetails = "message=leer&details=true&index=";
	private static final String openTagA_messageIdPage = "<a class = \"call\" href=\"MessageIdPage.jsp?messageId=";
	private static final String openTagA_messagePage = "<a class = \"section\" href=\"MessagePage.jsp?";
	private static final String openTagA_memo = "<a class=\"section\" name=\"memo\" href=\"#memo\" title=\"";
	private static final String openTagImage = "<image src=\"/xbus/admin/images/memo.gif\" border=0 align=left></a>";
	private static final String openTagTable_details = "<br><br><table width=\"100%\" border=0 cellpadding=0 cellspacing=0>";
	private static final String openTagTrTdColspan = "<tr class=\"journal\"><td colspan=\"2\">";
	private static final String fullTagTrTdColspan = "<tr><td colspan=\"2\">&nbsp;&nbsp;</td></tr>";
	private static final String fullTagA_javasriptBack = "<tr class=\"journal\"><td colspan=2 align=left><a class=\"section\" href= \"javascript:history.back()\"><--- Back</a>";
	private static final String fullTagA_javascriptClose = "<tr class=\"journal\" ><td colspan=2 align=left><a class=\"section\" href= \"javascript:self.close()\"><--- Close</a>";
	private static final String fullTagTrTdColspanColor = "<tr bgcolor=\"#E7E9E6\"><td colspan=\"2\">&nbsp;&nbsp;</td></tr>";
	private static final String closeTagA_memoRequest = "request_memo\" onClick = \"openMessage(\'request\',\'false\',\'";
	private static final String closeTagA_memoResponse = "response_memo\" onClick = \"openMessage(\'response\',\'false\',\'";
	private static final String closeTagA_memoError = "error_memo\" onClick = \"openMessage(\'error\',\'false\',\'";
	private static final String closeTagA_messageId = "\" title=\"select to message_id\" target=\"haupt\">";
	private static final String closeTagA = "</a>";
	private static final String closeTagA_details = "\" title=\"go to the details\" target=\"haupt\">--></a>";
	private static final String closeTagP = "</p>";
	private static final String closeTag = ">";
	private static final String closeTagTd = "</td>";
	private static final String closeTagTdTr = "</td></tr>";
	private static final String closeTagA_memo = "\')\">";
	private static final String closeTagTdTrTable = "</td></tr></table>";
	private static final String noData = "No entries found!";
	private static final String backspace = "&nbsp;";

	private String type;
	private String system;
	private String function;
	private String message_id;
	private String request_message;
	private String response_message;
	private String returncode;
	private String requestTimeMin;
	private String requestTimeMax;
	private String orderBy;
	private String sorting;
	private HashMap selectionMap = new HashMap();

	/**
	 * Allows you to set the property <b><i>selectionMap </i> </b>
	 */

	public void setSelectionMap(String selection)
	{
		if ("leer".equals(selection))
		{
			selectionMap = new HashMap();
		}
		else
		{
			selectionMap = new HashMap();
			selectionMap.put("MessageId", selection);
		}
	}

	/**
	 * * Allows you to set the property <b><i>sorting </i> </b>
	 */
	public void setSorting(String newSorting)
	{
		if (newSorting != null)
		{
			sorting = newSorting;
		}
		else
		{
			sorting = "Descending";
		}
	}

	/**
	 * Allows you to set the property <b><i>orderBy </i> </b>
	 */
	public void setOrderBy(String newOrderBy)
	{

		orderBy = newOrderBy;

	}

	/**
	 * Allows you to set the property <b><i>type </i> </b> and puts the
	 * selected values into the <code>selectionMap</code>
	 */
	public void setType(String newType)
	{
		if ((newType != null) && (!newType.equals("--------------------")))
		{
			type = newType;
			selectionMap.put("Type", type);
		}
	}

	/**
	 * Allows you to set the property <b><i>system </i> </b> and puts the
	 * selected values into the <code>selectionMap</code>
	 */
	public void setSystem(String newSystem)
	{
		if ((newSystem != null) && (!newSystem.equals("--------------------")))
		{

			system = newSystem;
			selectionMap.put("System", system);
		}
	}

	/**
	 * Allows you to set the property <b><i>function </i> </b> and puts the
	 * selected values into the <code>selectionMap</code>
	 */
	public void setFunction(String newFunction)
	{
		if (newFunction != null)
		{
			function = newFunction;
			selectionMap.put("Function", function);
		}
	}
	/**
	 * Allows you to set the property <b><i>message_id </i> </b> and puts the
	 * selected values into the <code>selectionMap</code>
	 */

	public void setMessageId(String newMessageId)
	{
		if (newMessageId != null)
		{
			message_id = newMessageId;
			selectionMap.put("MessageId", message_id);
		}
	}

	/**
	 * Allows you to set the property <b><i>request_message </i> </b> and puts
	 * the selected values into the <code>selectionMap</code>
	 */
	public void setRequest_message(String newRequest_message)
	{
		if (newRequest_message != null)
		{
			request_message = newRequest_message;
			selectionMap.put("RequestMessage", request_message);
		}
	}

	/**
	 * Allows you to set the property <b><i>response_message </i> </b> and puts
	 * the selected values into the <code>selectionMap</code>
	 */
	public void setResponse_message(String newResponse_message)
	{
		if (newResponse_message != null)
		{
			response_message = newResponse_message;
			selectionMap.put("ResponseMessage", response_message);
		}
	}

	/**
	 * Allows you to set the property <b><i>returncode </i> </b> and puts the
	 * selected values into the <code>selectionMap</code>
	 */
	public void setReturncode(String newReturncode)
	{
		if ((newReturncode != null)
				&& (!newReturncode.equals("--------------------")))
		{
			returncode = newReturncode;
			selectionMap.put("Returncode", returncode);
		}
	}

	/**
	 * Allows you to set the property <b><i>requestTimeMin </i> </b> and puts
	 * the selected values into the <code>selectionMap</code>
	 */
	public void setRequestTimeMin(String newRequestTimeMin)
	{
		if (newRequestTimeMin != null)
		{
			requestTimeMin = newRequestTimeMin;
			selectionMap.put("RequestTimeMin", requestTimeMin);
		}
	}

	/**
	 * Allows you to set the property <b><i>requestTimeMax </i> </b> and puts
	 * the selected values into the <code>selectionMap</code>
	 */
	public void setRequestTimeMax(String newRequestTimeMax)
	{
		if (newRequestTimeMax != null)
		{
			requestTimeMax = newRequestTimeMax;
			selectionMap.put("RequestTimeMax", requestTimeMax);
		}
	}

	/**
	 * Allows you to set the property <b><i>message </i> </b>
	 */

	public void setMessage(String newMessage)
	{
		message = newMessage;
	}

	/**
	 * Allows you to set the property <b><i>journalIndex </i> </b>
	 */
	public void setJournIndex(String newIndex)
	{
		journIndex = newIndex;
	}

	/**
	 * Allows you to set the property <b><i>details </i> </b>
	 */
	public void setDetails(String newDetails)
	{
		details = newDetails;
	}

	/**
	 * Translates the xml tages to the html tages
	 */
	private String replaceChar(String message)
	{
		StringBuffer messageBuffer = new StringBuffer();

		for (int i = 0; i < message.length(); i++)
		{
			char messageChar = message.charAt(i);
			if (messageChar == '<')
			{
				messageBuffer.append("&lt;");
			}
			else if (messageChar == '>')
			{
				messageBuffer.append("&gt;");
			}
			else if (messageChar == '\'')
			{
				messageBuffer.append("&quot;");
			}

			else
			{
				messageBuffer.append(messageChar);
			}

		}

		return messageBuffer.toString();
	}

	/**
	 * Checks and changes the color
	 */
	private String checkColor(boolean newCollor)
	{
		if (newCollor)
		{
			collor = false;
			return JournalBean.greyCollor;
		}
		else
		{
			collor = true;
			return JournalBean.whiteCollor;
		}
	}

	/**
	 * <code>getDataAsTableRows()</code> invokes the static method
	 * {@link ReadJournal#readSelected(HashMap, String, String)}and returns all
	 * selected data int the Html tages as String.
	 * 
	 * @return String with all selected data in the Html tages
	 * @see ReadJournal#readSelected(HashMap, String, String)
	 * @see net.sf.xbus.technical.database.DBConnection
	 */
	public String getDataAsTableRows() throws XException
	{
		// conected to the data base and get all selected entries
		entries = ReadJournal.readSelected(selectionMap, orderBy, sorting);

		StringBuffer dataBuffer = new StringBuffer();
		int index = 0;
		ReadJournal journal;
		collor = false;

		if (!entries.iterator().hasNext())
		{
			dataBuffer.append(JournalBean.openTagTdTr);
			dataBuffer.append(JournalBean.openTagP);
			dataBuffer.append(JournalBean.noData);
			dataBuffer.append(JournalBean.closeTagP);
			dataBuffer.append(JournalBean.closeTagTdTr);
		}
		else
		{
			dataBuffer.append(getCollumnsAsTableRows());

			for (Iterator it = entries.iterator(); it.hasNext();)
			{

				journal = (ReadJournal) (it.next());
				journalMap.put(new Integer(index).toString(), journal);

				dataBuffer.append(getJournalAsTableRows(journal, index));

				index++;
			}

		}
		entries.clear();
		return dataBuffer.toString();

	}

	/**
	 * <code>getJournalAsTableRows(ReadJournal, int)</code> composes String
	 * with the values from the data base.
	 * 
	 * @param journal - ReadJournal object
	 * @param index - int
	 * @return String
	 */

	public String getJournalAsTableRows(ReadJournal journal, int index)
	{
		StringBuffer journalBuffer = new StringBuffer();
		// link to the details
		journalBuffer.append(JournalBean.openTagTr_bgcolor);
		journalBuffer.append(checkColor(collor));
		journalBuffer.append(JournalBean.closeTag);
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(JournalBean.openTagA_messagePage);
		journalBuffer.append(JournalBean.paramDetails);
		journalBuffer.append(index);
		journalBuffer.append(JournalBean.closeTagA_details);

		// number
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(new Integer(journal.getNumber()).toString());
		journalBuffer.append(JournalBean.closeTagTd);

		// type
		journalBuffer.append(JournalBean.openTagTd_function);
		journalBuffer.append(new Character(journal.getType()).toString());
		journalBuffer.append(JournalBean.closeTagTd);

		// system
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(journal.getSystem());
		journalBuffer.append(JournalBean.closeTagTd);

		// function
		journalBuffer.append(JournalBean.openTagTd_function);

		if (journal.getFunction() == null)
		{
			journalBuffer.append(JournalBean.backspace);
		}
		else
		{
			journalBuffer.append(journal.getFunction());
		}
		journalBuffer.append(JournalBean.closeTagTd);

		// message_id

		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(JournalBean.openTagA_messageIdPage);
		journalBuffer.append(journal.getMessageId());
		journalBuffer.append(JournalBean.closeTagA_messageId);
		journalBuffer.append(journal.getMessageId());
		journalBuffer.append(JournalBean.closeTagA);
		journalBuffer.append(JournalBean.closeTagTd);

		// request message
		journalBuffer.append(JournalBean.openTagTd);

		if (journal.getRequestMessage() == null
				|| journal.getRequestMessage().equals("<null>"))
		{
			journalBuffer.append(JournalBean.backspace);
		}
		else
		{
			journalBuffer.append(JournalBean.openTagA_memo);
			journalBuffer.append(JournalBean.closeTagA_memoRequest);
			journalBuffer.append(index);
			journalBuffer.append(closeTagA_memo);
			journalBuffer.append(JournalBean.openTagImage);

		}

		journalBuffer.append(JournalBean.closeTagTd);

		// request timestamp
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(journal.getRequestTimestamp());
		journalBuffer.append(JournalBean.closeTagTd);

		// response message
		journalBuffer.append(JournalBean.openTagTd);

		if (journal.getResponseMessage() == null
				|| journal.getResponseMessage().equals("<null>"))
		{
			journalBuffer.append(JournalBean.backspace);
		}
		else
		{
			journalBuffer.append(JournalBean.openTagA_memo);
			journalBuffer.append(JournalBean.closeTagA_memoResponse);
			journalBuffer.append(index);
			journalBuffer.append(closeTagA_memo);
			journalBuffer.append(JournalBean.openTagImage);

		}

		journalBuffer.append(JournalBean.closeTagTd);

		// response timestamp
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(journal.getResponseTimestamp());
		journalBuffer.append(JournalBean.closeTagTd);

		// return code
		journalBuffer.append(JournalBean.openTagTd_journal);
		journalBuffer.append(journal.getReturncode());
		journalBuffer.append(JournalBean.closeTagTd);

		// error message
		journalBuffer.append(JournalBean.openTagTd);
		if (journal.getErrormessage() == null
				|| journal.getErrormessage().equals("<null>"))
		{
			journalBuffer.append(JournalBean.backspace);
		}
		else
		{

			journalBuffer.append(JournalBean.openTagA_memo);
			journalBuffer.append(JournalBean.closeTagA_memoError);
			journalBuffer.append(index);
			journalBuffer.append(closeTagA_memo);
			journalBuffer.append(JournalBean.openTagImage);

		}

		journalBuffer.append(JournalBean.closeTagTdTr);

		return journalBuffer.toString();

	}
	/**
	 * Depending on the obtained parameters, <code>getDetailsAsTable()</code>
	 * composes String with the request/response/error message or with the
	 * detail liste.
	 * 
	 * @return String
	 */

	public String getDetailsAsTable()
	{
		StringBuffer detailsBuffer = new StringBuffer();
		ReadJournal journal = (ReadJournal) journalMap.get(journIndex);

		if (details.equals("true"))
		{
			detailsBuffer.append(JournalBean.openTagTable_details);
			detailsBuffer.append(JournalBean.openTagTrTdColspan);
			detailsBuffer.append(JournalBean.fullTagH4);
			detailsBuffer.append(JournalBean.deatilsTable);

			detailsBuffer.append(JournalBean.detailsTagFirst);
			detailsBuffer.append("Number:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getNumber());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Type:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getType());
			detailsBuffer.append(JournalBean.detailsTagOne);
			detailsBuffer.append("System:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getSystem());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Function:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getFunction());
			detailsBuffer.append(JournalBean.detailsTagOne);
			detailsBuffer.append("Message Id:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getMessageId());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Returncode:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getReturncode());
			detailsBuffer.append(JournalBean.detailsTagOne);
			detailsBuffer.append("Request Timestamp:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getRequestTimestamp());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Request Message:");
			detailsBuffer.append(JournalBean.closeTagP);
			detailsBuffer.append(JournalBean.detailsTd);
			if (journal.getRequestMessage() != null)
			{
				detailsBuffer.append("<PRE>");
				detailsBuffer.append(replaceChar(journal.getRequestMessage()));
				detailsBuffer.append("</PRE>");
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.detailsTagOne);
			detailsBuffer.append("Response Timestamp:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getResponseTimestamp());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Response Message:");
			detailsBuffer.append(JournalBean.closeTagP);
			detailsBuffer.append(JournalBean.detailsTd);
			if (journal.getResponseMessage() != null)
			{
				detailsBuffer.append("<PRE>");
				detailsBuffer.append(replaceChar(journal.getResponseMessage()));
				detailsBuffer.append("</PRE>");
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.detailsTagOne);
			detailsBuffer.append("Errorcode:");
			detailsBuffer.append(JournalBean.detailsTd);
			detailsBuffer.append(journal.getErrorcode());
			detailsBuffer.append(JournalBean.detailsTagTwo);
			detailsBuffer.append("Errormessage:");
			detailsBuffer.append(JournalBean.closeTagP);
			detailsBuffer.append(JournalBean.detailsTd);
			if (journal.getErrormessage() != null)
			{
				detailsBuffer.append(replaceChar(journal.getErrormessage()));
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.closeTagTdTr);

			detailsBuffer.append(JournalBean.fullTagTrTdColspanColor);
			detailsBuffer.append(JournalBean.fullTagTrTdColspan);
			detailsBuffer.append(JournalBean.fullTagA_javasriptBack);

			detailsBuffer.append(JournalBean.closeTagTdTrTable);

		}

		else if ("request".equals(message))
		{

			detailsBuffer.append(JournalBean.openTagTable_message);
			detailsBuffer.append("Request Message");
			detailsBuffer.append(JournalBean.deatilsTable);

			detailsBuffer.append(JournalBean.detailsTableMessage);

			if (journal.getRequestMessage() != null)
			{
				detailsBuffer.append("<PRE>");
				detailsBuffer.append(replaceChar(journal.getRequestMessage()));
				detailsBuffer.append("</PRE>");
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.closeTagTdTr);
			detailsBuffer.append(JournalBean.fullTagTrTdColspan);
			detailsBuffer.append(JournalBean.fullTagA_javascriptClose);
			detailsBuffer.append(JournalBean.closeTagTdTrTable);
		}

		else if ("response".equals(message))
		{

			detailsBuffer.append(JournalBean.openTagTable_message);
			detailsBuffer.append("Response Message");
			detailsBuffer.append(JournalBean.deatilsTable);

			detailsBuffer.append(JournalBean.detailsTableMessage);
			if (journal.getResponseMessage() != null)
			{
				detailsBuffer.append("<PRE>");
				detailsBuffer.append(replaceChar(journal.getResponseMessage()));
				detailsBuffer.append("</PRE>");
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.closeTagTdTr);
			detailsBuffer.append(JournalBean.fullTagTrTdColspan);
			detailsBuffer.append(JournalBean.fullTagA_javascriptClose);
			detailsBuffer.append(JournalBean.closeTagTdTrTable);

		}

		else if ("error".equals(message))
		{
			detailsBuffer.append(JournalBean.openTagTable_message);
			detailsBuffer.append("Errormessage");
			detailsBuffer.append(JournalBean.deatilsTable);

			detailsBuffer.append(JournalBean.detailsTableMessage);
			if (journal.getErrormessage() != null)
			{
				detailsBuffer.append(replaceChar(journal.getErrormessage()));
			}
			else
			{
				detailsBuffer.append("null");
			}
			detailsBuffer.append(JournalBean.closeTagTdTr);
			detailsBuffer.append(JournalBean.fullTagTrTdColspan);
			detailsBuffer.append(JournalBean.fullTagA_javascriptClose);
			detailsBuffer.append(JournalBean.closeTagTdTrTable);

		}

		return detailsBuffer.toString();
	}

	/**
	 * <code>getCollumnsAsTableRows()</code> composes String with the names of
	 * the collumns.
	 * 
	 * @return String
	 */
	public String getCollumnsAsTableRows()
	{
		StringBuffer columnNameBuffer = new StringBuffer();

		columnNameBuffer.append(JournalBean.openTagTr_selection);
		columnNameBuffer.append(JournalBean.collumnBgCollor);
		columnNameBuffer.append(JournalBean.closeTag);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append(JournalBean.backspace);
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("No");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Type");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("System");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Function");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Message Id");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append(JournalBean.backspace);
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Request Timestamp");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append(JournalBean.backspace);
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Response Timestamp");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append("Returncode");
		columnNameBuffer.append(JournalBean.closeTagTd);
		columnNameBuffer.append(JournalBean.openTagTd_selection);
		columnNameBuffer.append(JournalBean.backspace);

		columnNameBuffer.append(JournalBean.closeTagTdTr);

		return columnNameBuffer.toString();
	}

}
