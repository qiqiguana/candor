package net.sf.xbus.technical.mail;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.mail.internet.InternetAddress;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xml.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * <p>
 * Description: An instance of that class will store an manupulate the email
 * receive by the POP3XMLReceiver.
 * </p>
 * 
 * @author Dominique Boivin
 * @version 1.3
 */

public class Email
{
	private String mSystem;
	private InternetAddress mFromAddress;
	private List mToAddresses;
	private List mCCAddresses;
	private List mBCCAddresses;
	private String mSubject;
	private String mContent;
	private Date mSentDate;
	private String mContentType;

	public Email(String system)
	{
		mSystem = system;
	}

	public String toString()
	{
		try
		{
			return XMLHelper.serializeXML(getXML(), null);
		}
		catch (XException e)
		{
			return e.getMessage();
		}
	}

	public Document getXML() throws XException
	{
		if (isEmpty())
		{
			return null;
		}

		Document doc = XMLHelper.getDocumentBuilder("Default", null)
				.newDocument();

		Element root = doc.createElement(mSystem);
		doc.appendChild(root);

		Element emailElement = null;
		Text emailNode = null;

		if (mFromAddress != null)
		{
			root.appendChild(createAddressNode(doc, "From", mFromAddress));
		}

		if ((mToAddresses != null) && (!mToAddresses.isEmpty()))
		{
			for (Iterator it = mToAddresses.iterator(); it.hasNext();)
			{
				root.appendChild(createAddressNode(doc, "To",
						(InternetAddress) it.next()));
			}
		}

		if ((mCCAddresses != null) && (!mCCAddresses.isEmpty()))
		{
			for (Iterator it = mCCAddresses.iterator(); it.hasNext();)
			{
				root.appendChild(createAddressNode(doc, "CC",
						(InternetAddress) it.next()));
			}
		}

		if ((mBCCAddresses != null) && (!mBCCAddresses.isEmpty()))
		{
			for (Iterator it = mBCCAddresses.iterator(); it.hasNext();)
			{
				root.appendChild(createAddressNode(doc, "BCC",
						(InternetAddress) it.next()));
			}
		}

		if (mSentDate != null)
		{
			emailElement = doc.createElement("SentDate");
			emailNode = doc.createTextNode(Constants.getDateFormat().format(
					mSentDate));
			emailElement.appendChild(emailNode);
			root.appendChild(emailElement);
		}

		if (mContentType != null)
		{
			emailElement = doc.createElement("ContentType");
			emailNode = doc.createTextNode(mContentType);
			emailElement.appendChild(emailNode);
			root.appendChild(emailElement);
		}

		if (mSubject != null)
		{
			emailElement = doc.createElement("Subject");
			emailNode = doc.createTextNode(mSubject);
			emailElement.appendChild(emailNode);
			root.appendChild(emailElement);
		}

		if (mContent != null && !mContent.trim().equals(""))
		{
			emailElement = doc.createElement("Content");
			emailNode = doc.createTextNode(mContent);
			emailElement.appendChild(emailNode);
			root.appendChild(emailElement);

			emailElement = doc.createElement("IsHTMLMessage");
			if (isHTMLMessage())
			{
				emailNode = doc.createTextNode("true");
			}
			else
			{
				emailNode = doc.createTextNode("false");
			}
			emailElement.appendChild(emailNode);
			root.appendChild(emailElement);

		}

		return doc;
	}

	public boolean isEmpty()
	{
		return ((mContent == null) || (mContent.trim().equals("")));
	}

	public InternetAddress getFromAddress()
	{
		return mFromAddress;
	}

	public void setFromAddress(InternetAddress fromAddress)
	{
		mFromAddress = fromAddress;
	}

	public void setToAddress(InternetAddress toAddress)
	{
		if (mToAddresses == null)
		{
			mToAddresses = new Vector();
		}
		mToAddresses.add(toAddress);
	}

	public List getToAddresses()
	{
		return mToAddresses;
	}

	public void setCCAddress(InternetAddress ccAddress)
	{
		if (mCCAddresses == null)
		{
			mCCAddresses = new Vector();
		}
		mCCAddresses.add(ccAddress);
	}

	public List getCCAddresses()
	{
		return mCCAddresses;
	}

	public void setBCCAddress(InternetAddress bccAddress)
	{
		if (mBCCAddresses == null)
		{
			mBCCAddresses = new Vector();
		}
		mBCCAddresses.add(bccAddress);
	}

	public List getBCCAddresses()
	{
		return mBCCAddresses;
	}

	public void setSubject(String subject)
	{
		mSubject = subject;
	}

	public String getSubject()
	{
		return mSubject;
	}

	public void setContent(String content)
	{
		mContent = content;
	}

	public String getContent()
	{
		return mContent;
	}

	public void setSentDate(Date sentDate)
	{
		mSentDate = sentDate;
	}

	public Date getSentDate()
	{
		return mSentDate;
	}

	public String getContentType()
	{
		return mContentType;
	}

	public void setContentType(String contentType)
	{
		mContentType = contentType;
	}

	private Node createAddressNode(Document doc, String tag,
			InternetAddress internetAddress)
	{
		Node retNode = doc.createElement(tag);
		String name = internetAddress.getPersonal();
		String address = internetAddress.getAddress();

		Node tmpNode = null;

		if (name != null)
		{
			tmpNode = doc.createElement("Name");
			tmpNode.appendChild(doc.createTextNode(name));
			retNode.appendChild(tmpNode);
		}
		if (address != null)
		{
			tmpNode = doc.createElement("Address");
			tmpNode.appendChild(doc.createTextNode(address));
			retNode.appendChild(tmpNode);
		}

		return retNode;
	}

	private boolean isHTMLMessage()
	{
		/*
		 * Whe cannot use directly the </HTML> tag because the <> can be
		 * converted to &lt;/HTML&gt;
		 */
		return ((mContent != null) && (mContent.toUpperCase().indexOf("/HTML") != -1));
	}

}
