package net.sf.xbus.technical.http;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * The <code>HTTPByteArrayListSender</code> sends a ByteArrayList in the form
 * of a String to an URL.
 */
public class HTTPByteArrayListSender extends HTTPSender
		implements
			Sender,
			ObjectSender
{

	/**
	 * @see HTTPSender#HTTPSender(XBUSSystem)
	 */
	public HTTPByteArrayListSender(XBUSSystem destination)
	{
		super(destination);
	} // HTTPByteArrayListSender(XBUSSystem destination)

	/**
	 * Sends the <code>callData</code> to the system. <code>function</code>
	 * is ignored.
	 */
	public Object execute(String function, Object callData) throws XException
	{
		String callString = ((ByteArrayList) callData)
				.getContentAsString(mDestination.getName());

		super.execute(function, callString);

		return null;
	} // execute(String function, Object callData)

	/**
	 * @see net.sf.xbus.technical.Sender#getType()
	 * 
	 * @return Constants.TYPE_OBJECT
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	} // getType()

} // HTTPByteArrayListSender
