package net.sf.xbus.technical.mail;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

/**
 * The <code>SMTPSender</code> sends messages to mail accounts via the SMTP
 * protocol.
 * <p>
 * This sender is very simple. It does not support the transaction mechanism.
 * Instead of it, the message will directly be sent when calling the execute
 * method.
 * 
 * <p>
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>Host</td>
 * <td>Hostname of the SMTP server</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>Subject</td>
 * <td>Subject line for the mail message</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>ToAddress <i>i</i></td>
 * <td>List of mail addresses where the message will be sent to</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>FromAddress</td>
 * <td>Mail Address of the mail sender</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>FromName</td>
 * <td>Optional: Name of the mail sender</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>User</td>
 * <td>Optional: User needed for authentication</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td><i>logical name of the sender</i></td>
 * <td>Password</td>
 * <td>Optional: Password needed for authentication</td>
 * </tr>
 * </table>
 */
public class SMTPSender implements Sender, TextSender
{

	XBUSSystem mDestination = null;
	String mUser = null;
	String mPassword = null;

	/**
	 * The constructor stores the given destination.
	 */
	public SMTPSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * Sends the <code>callData</code>. <code>function</code> is ignored.
	 * 
	 * @return <code>null</code>
	 */
	public String execute(String function, String callData) throws XException
	{
		if (callData == null)
			callData = "";

		/*
		 * Getting some parameters from the configuration
		 */
		Configuration config = Configuration.getInstance();

		String host = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
				.getName(), "Host");
		String subject = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
				.getName(), "Subject");

		Address fromAddress;
		Address[] toAddresses;
		try
		{
			String address = null;
			Vector addressList = new Vector();
			address = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
					.getName(), "ToAddress1");
			int i = 1;
			while (address != null)
			{
				i++;
				addressList.add(address);
				address = config.getValueOptional(Constants.CHAPTER_SYSTEM,
						mDestination.getName(), "ToAddress" + i);
			}

			int amountAddresses = addressList.size();
			toAddresses = new Address[amountAddresses];
			for (int k = 0; k < amountAddresses; k++)
			{
				toAddresses[k] = new InternetAddress((String) addressList
						.elementAt(k));
			}

			String fromAddressString = config.getValue(
					Constants.CHAPTER_SYSTEM, mDestination.getName(),
					"FromAddress");
			String fromName = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "FromName");
			if (fromName != null)
			{
				fromAddress = new InternetAddress(fromAddressString, fromName);
			}
			else
			{
				fromAddress = new InternetAddress(fromAddressString);
			}
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}

		mUser = config.getValueOptional(Constants.CHAPTER_SYSTEM, mDestination
				.getName(), "User");
		if (mUser != null)
		{
			mPassword = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
					.getName(), "Password");
		}

		/*
		 * Setting the session
		 */
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		if (mUser != null)
		{
			props.put("mail.smtp.auth", "true");
		}
		Authenticator auth = new MyAuthenticator();
		Session session = Session.getInstance(props, auth);
		try
		{
			/*
			 * Creating the message
			 */
			MimeMessage msg = new MimeMessage(session);
			if (mUser != null)
			{
				msg.setHeader("AUTH", "PLAIN");
			}
			msg.setFrom(fromAddress);
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(callData, getEncoding());

			/*
			 * Sending the message
			 */
			Transport.send(msg);
		}
		catch (Exception e) // (MessagingException e) per casting below
		{ // HP Java runtime did not recognise MessagingException as a subtype
			// of
			// Throwable.
			try
			{
				MessagingException me = (MessagingException) e;
				Exception nextException = me.getNextException();
				if (nextException != null)
				{
					Trace.error(nextException.getMessage());
					if (nextException instanceof SendFailedException)
					{
						Address[] invalid = ((SendFailedException) nextException)
								.getInvalidAddresses();
						for (int i = 0; i < invalid.length; i++)
						{
							Trace.error("Invalid address: "
									+ ((InternetAddress) invalid[i])
											.getAddress());
						}
					}
				}
			}
			catch (ClassCastException ce)
			{}
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
		return null;
	}

	/**
	 * Reads the file encoding for the given system name from the
	 * {@link net.sf.xbus.base.core.config.Configuration}. If this one is
	 * non-existent, returns default value from the system property.
	 * 
	 * @param system name which encoding must be read
	 * @return file encoding from the standart configuration or default value
	 *         from the system property
	 * @exception XException if any errors occurs.
	 */
	private String getEncoding() throws XException
	{
		Configuration config = Configuration.getInstance();

		String configEncoding = config.getValueOptional(
				Constants.CHAPTER_SYSTEM, mDestination.getName(),
				Constants.KEY_ENCODING);

		return (configEncoding == null)
				? Constants.SYS_ENCODING
				: configEncoding;

	}

	private class MyAuthenticator extends Authenticator
	{
		protected PasswordAuthentication getPasswordAuthentication()
		{
			if (mUser != null)
			{
				return new PasswordAuthentication(mUser, mPassword);
			}
			else
			{
				return null;
			}
		}
	}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
