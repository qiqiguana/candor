/*
 * Created on 04.10.2004
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.xbus.protocol.xml;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.AdditionalAddress;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.IteratedNodeOperation;
import net.sf.xbus.protocol.Message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Duewel
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
class IteratedStyletApplication implements IteratedNodeOperation
{

	/**
	 * The XML document, in which the stylets are applied. Used for node
	 * creation.
	 */
	private Document mDoc;

	/**
	 * The message, in which the stylet application takes place. Used for
	 * pasting message information into the result document.
	 */
	private Message mMessage;

	/**
	 * Key/value pairs for mapping values.
	 */
	private Hashtable mStyletValues;

	/**
	 * Construct an object for stylet application.
	 * 
	 * @param xmlNew the XML document, in which the stylets are applied.
	 * @param message the message, in which the stylet application takes place.
	 */
	public IteratedStyletApplication(Document xmlNew, Message message,
			Hashtable styletValues)
	{
		mDoc = xmlNew;
		mMessage = message;
		mStyletValues = styletValues;
	} // IteratedStyletApplication(Document xmlNew, Message message)

	/**
	 * @see net.sf.xbus.base.xml.IteratedNodeOperation#iteratedProcedure(org.w3c.dom.Node)
	 */
	public void iteratedProcedure(Node node) throws XException
	{
		try
		{
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& node.getNodeName().equals("XBUS_Stylet"))
			{
				Node parent = node.getParentNode();

				String styletName = ((Element) node).getAttribute("Name");
				if ((styletName == null) || (styletName.length() == 0))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_XML, "18");
				} // if ((styletName == null) || (styletName.length() == 0))
				String tagName = ((Element) node).getAttribute("Tag");
				if ((tagName == null) || (tagName.length() == 0)
						&& !styletName.equals("Value")
						&& !styletName.equals("AddressMapping")
						&& !styletName.equals("FormatDate")
						&& !styletName.equals("DateComparison"))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_XML, "19");
				} // if ((tagName == null) || (tagName.length() == 0) ...

				if (styletName.equals("Id"))
				{
					insertValue(tagName, mMessage.getId(), parent, node);
				} // then (styletName.equals("Id"))

				else if (styletName.equals("Source"))
				{
					insertValue(tagName, mMessage.getSource().getName(),
							parent, node);
				} // then (styletName.equals("Source"))

				else if (styletName.equals("RequestTimestamp"))
				{
					insertValue(tagName, Constants.getDateFormat().format(
							mMessage.getRequestTimestamp()), parent, node);
				} // then (styletName.equals("RequestTimestamp"))

				else if (styletName.equals("Value"))
				{
					String section = ((Element) node).getAttribute("Section");
					String keyName = ((Element) node).getAttribute("Key");
					if ((keyName == null) || (keyName.length() == 0))
					{
						List params = new Vector();
						params.add(styletName);
						params.add(section);
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "20", params);
					} // if ((keyName == null) || (keyName.length() == 0))

					String value = null;
					if (section == null || section.length() == 0)
					{
						value = (String) mStyletValues.get(keyName);
					} // then (section == null || section.length() == 0)
					else
					{
						value = Configuration.getMappingOptional(section,
								keyName);
						if (value == null)
						{
							try
							{
								value = Configuration
										.getMappingDefault(section);
							}
							catch (XException e)
							{ // Catch XException to past the missing key
								// value into
								// the (second) error message.
								List params = new Vector();
								params.add(section);
								params.add(keyName);
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_XML, "24",
										params);
							} // catch (XException e)
						} // if (value==null)
					} // else (section == null || section.length() == 0)
					if (value == null)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "21");
					} // if (value == null)

					// replace the stylet node in the DOM tree
					insertValue(tagName, value, parent, node);

				} // then (styletName.equals("Value"))

				else if (styletName.equals("CDATA"))
				{
					String keyName = ((Element) node).getAttribute("Key");
					if ((keyName == null) || (keyName.length() == 0))
					{
						List params = new Vector();
						params.add(styletName);
						params.add("");
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "20", params);
					} // if ((keyName == null) || (keyName.length() == 0))
					String value = (String) mStyletValues.get(keyName);
					if (value == null)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "21");
					} // if (value == null)
					Element newElement = mDoc.createElement(tagName);
					newElement.appendChild(mDoc.createCDATASection(value));
					parent.replaceChild(newElement, node);
				} // then (styletName.equals("CDATA"))

				else if (styletName.equals("FormatDate"))
				{
					String oldFormat = ((Element) node)
							.getAttribute("SourceFormat");
					String newFormat = ((Element) node)
							.getAttribute("DestinationFormat");
					if (newFormat == null || newFormat.length() == 0)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "22");
					} // if (newFormat==null || newFormat.length()==0)

					String keyName = ((Element) node).getAttribute("Key");
					if (oldFormat == null || oldFormat.length() == 0)
					{
						if (keyName != null && keyName.length() > 0)
						{
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_XML, "22");
						} // if (keyName != null && keyName.length() > 0)
					} // then (oldFormat==null || oldFormat.length()==0)
					else if ((keyName == null) || (keyName.length() == 0))
					{
						List params = new Vector();
						params.add(styletName);
						params.add("");
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "20", params);
					} // if ((keyName == null) || (keyName.length() == 0))

					Date date = null;
					SimpleDateFormat formater = null;
					if (oldFormat == null || oldFormat.length() == 0)
					{
						date = new Date();
						formater = new SimpleDateFormat(newFormat);
					} // then (oldFormat == null || oldFormat.length() == 0)
					else
					{
						formater = new SimpleDateFormat(oldFormat);
						date = formater.parse(keyName);
						formater.applyPattern(newFormat);
					} // else (oldFormat == null || oldFormat.length() == 0)
					String value = formater.format(date);

					insertValue(tagName, value, parent, node);
				} // then (styletName.equals("FormatDate"))

				else if (styletName.equals("AddressMapping"))
				{ // for value mapping from the AddtionalAddressImplementation
					// the originating value type
					String section = ((Element) node).getAttribute("Section");
					// the destination value type
					String toSection = ((Element) node)
							.getAttribute("toSection");
					// the key to map
					String keyName = ((Element) node).getAttribute("Key");
					if ((keyName == null) || (keyName.length() == 0))
					{
						List params = new Vector();
						params.add(styletName);
						params.add(section);
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "20", params);
					} // if ((keyName == null) || (keyName.length() == 0))
					if ((section == null) || (section.length() == 0)
							|| (toSection == null) || (toSection.length() == 0))
					{
						List params = new Vector();
						params.add(styletName);
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "23", params);
					} // if ((section == null) || (section.length() == 0) ||
						// (toSection == null) || (toSection.length() == 0))

					AdditionalAddress addressImpl = XBUSSystem
							.getAdditionalAddressImplementation(mMessage
									.getSource().getName());
					// the mapped value
					String value = addressImpl.getValue(section, toSection,
							keyName);

					if (value == null)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "21");
					} // if (value == null)

					// replace the stylet node in the DOM tree
					insertValue(tagName, value, parent, node);
				} // if (styletName.equals("AddressMapping"))

				else if (styletName.equals("DateComparison"))
				{ // Compare a given or the actual date against an time
					// interval
					// or a single date.

					// Begin of the interval or single date to compare against.
					String beginString = ((Element) node)
							.getAttribute("BeginDate");
					Date beginDate = null;
					if (beginString.length() > 0)
					{ // Begin date is specified. Get it as date object.
						String beginFormat = ((Element) node)
								.getAttribute("BeginFormat");
						SimpleDateFormat formater = new SimpleDateFormat(
								beginFormat);
						beginDate = formater.parse(beginString);
						// Any problem in date handling is handled at the end of
						// the procedure.
					} // then (beginString.length()>0)
					else
					{ // The begin date is mandatory.
						List params = new Vector();
						params.add(beginString);
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_XML, "26", params);
					} // else (beginString.length()>0)

					// The end date of the interval to comaper against.
					// Or empty if comaprison is done date-to-date.
					String endString = ((Element) node).getAttribute("EndDate");
					Date endDate = null;
					if (endString.length() > 0)
					{ // The end date is specified. Get it as date object.
						String endFormat = ((Element) node)
								.getAttribute("EndFormat");
						SimpleDateFormat formater = new SimpleDateFormat(
								endFormat);
						endDate = formater.parse(endString);
						// Any problem in date handling is handled at the end of
						// the procedure.
					} // if (endString.length()>0)

					// The date to compare against begin and end date or begin
					// date
					// alone.
					// If the compare date is empty, the current date is used
					// for
					// the comparison.
					String compareString = ((Element) node)
							.getAttribute("CompareDate");
					Date compareDate = null;
					if (compareString.length() > 0)
					{ // The compare date is specified. Get it as date object.
						String compareFormat = ((Element) node)
								.getAttribute("CompareFormat");
						SimpleDateFormat formater = new SimpleDateFormat(
								compareFormat);
						compareDate = formater.parse(compareString);
						// Any problem in date handling is handled at the end of
						// the procedure.
					} // if (compareString.length()>0)
					else
					{ // The compare date was not specified. Use the actual
						// date.
						SimpleDateFormat formater = new SimpleDateFormat(
								"yyyyMMdd");
						compareDate = formater.parse(formater
								.format(new Date()));
					} // else (compareString.length()>0)

					// First comparison against the begin date.
					int comparison = compareDate.compareTo(beginDate);

					if (comparison > -1 && endDate != null)
					{ // The compared date lies not before the begin date and
						// there is an end date.
						if (beginDate.compareTo(endDate) == 1)
						{ // Check the begin-end-date interval.
							List params = new Vector();
							params.add(beginString);
							params.add(endString);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_XML, "25",
									params);
						} // then (beginDate.compareTo(endDate)==1)
						else
						{ // Begin and end date form a sensilbe interval.
							// Compare against the end date.
							// Already sure: the compare date is not before the
							// begin date.
							comparison = compareDate.compareTo(endDate);
							if (comparison == -1)
								// Before end date means that it is in the
								// interval.
								comparison = 0;
						} // else (beginDate.compareTo(endDate)==1)
					} // if (comparison>-1 && endDate!=null)

					// Convert the comparison value into one of the specified
					// keys.
					String value = null;
					switch (comparison)
					{
						case -1 :
							value = ((Element) node)
									.getAttribute("BeforeValue");
							break;
						case 0 :
							value = ((Element) node)
									.getAttribute("StrikeValue");
							break;
						case 1 :
							value = ((Element) node).getAttribute("AfterValue");
							break;
					} // switch (comparison)

					// Insert the value into the DOM tree according to the
					// specified
					// tag name.
					insertValue(tagName, value, parent, node);
				} // if (styletName.equals("DateComparison"))

				else if (styletName.equals("Max"))
				{ // Calculate the maximum of two integer values.
					// The values are stored in the attributes "Key" and
					// "Value".
					// This stylet is only needed to combine stylet results
					// after
					// the XSLT transformation.
					// Usual comparison is possible in XSLT directly.
					String keyString = ((Element) node).getAttribute("Key");
					int key = Integer.parseInt(keyString);
					String value = ((Element) node).getAttribute("Value");
					int val = Integer.parseInt(value);
					if (val < key)
						value = keyString;
					insertValue(tagName, value, parent, node);
				} // if (styletName.equals("Max"))

				else
				{
					List params = new Vector();
					params.add(styletName);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_XML, "27", params);
				} // else - name attribute unknown

			} // if (node.getNodeType() != Node.ELEMENT_NODE)
		} // try
		catch (XException e)
		{
			throw e;
		} // catch (XException e)
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		} // catch
	} // IteratedStyletApplication(Document xmlNew, Message message)

	/**
	 * <code>insertValue</code> replaces the stlyet node of a value mapping or
	 * generation within the DOM tree. The new value may be placed
	 * <ul>
	 * <li>into the node where the stylet node was in,</li>
	 * <li>into a new node labelled <code>tagname</code></li>, which itself
	 * replaces the stylet node,
	 * <li>into a attribute node labelled <code>tagname</code> if
	 * <code>tagname</code> starts with '@' and the new attribute is linked to
	 * the node which surrounds the stylet node and the stylet node is deleted.
	 * 
	 * @param tagName tag for a new value node, <code>null</code> to put the
	 *            value into the node where the stylet node was in, addressing
	 *            an attribute if beginning with '@'
	 * @param value the value to store into the DOM tree
	 * @param parent the node surrounding the stylet node
	 * @param child the stylet node
	 */
	private void insertValue(String tagName, String value, Node parent,
			Node child)
	{
		if (tagName == null || tagName.length() == 0)
		{ // No tag name - insert mapped value as value of <parent>
			Node valueNode = mDoc.createTextNode(value);
			parent.replaceChild(valueNode, child);
		} // then (tagName == null || tagName.length() == 0)
		else
		{ // Tag name given - create new node for mapped value
			if (tagName.charAt(0) == '@')
			{ // Tag name indicates an attribute name.
				((Element) parent).setAttribute(tagName.substring(1), value);
				parent.removeChild(child);
			} // then (tagName.charAt(0)=='@')
			else
			{ // Tag name indicates an elemnet node name.
				Node valueNode = mDoc.createTextNode(value);
				Node newNode = mDoc.createElement(tagName);
				newNode.appendChild(valueNode);
				parent.replaceChild(newNode, child);
			} // else (tagName.charAt(0)=='@')
		} // else (tagName==null || tagName.length()==0)
	} // insertValue(String tagName, String value, Document xmlNew, Node
		// parent, Node child)

} // IteratedStyletApplication
