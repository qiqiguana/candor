package net.sf.xbus.technical.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * TODO Kommentierung
 * <p>
 * When the <code>POP3XMLReceiver</code> receives a request it reads the
 * emails into a String and calls the application layer.
 * </p>
 * 
 * @author Dominique Boivin
 * @version 1.3
 */

public class POP3XMLReceiver
		implements
			Receiver,
			TAResource,
			ReceiverSingleInterface
{

	private String mHost = null;
	private String mUser = null;
	private String mPassword = null;

	private Store mStore = null;
	protected Folder mFolder = null;
	private Session mSession = null;
	protected Message mMessage = null;
	private Email mEmailMessage = null;

	private boolean mDeleted = false;
	private String mReturncode = null;

	/**
	 * Resolved action with the file after that it was read <br>
	 * <i>preserve, rename </i> or <i>delete </i>
	 */
	private String mResolution = null;

	private String mOnError = null;

	/**
	 * This method will open the Transaction manager for this ressource adb use
	 * the {@link #doReceive(XBUSSystem)}method to process the reading
	 * 
	 * @see net.sf.xbus.base.xbussystem.XBUSSystem#getSystems(String, String)
	 * @param systemName name of the interface definition
	 * @exception XException if something goes wrong
	 */
	public void receive(String systemName)
	{
		mDeleted = false;

		XBUSSystem xbusSystem = null;

		TAManager taManager = TAManager.getInstance();
		taManager.clearManager();
		taManager.registerResource(this);

		try
		{

			readConfiguration(systemName);

			xbusSystem = new XBUSSystem(systemName);

			Trace.info("Start processing " + xbusSystem.getCompleteName());

			// POP3XMLReceiver will be registered by TAManager
			doReceive(xbusSystem);
		}
		catch (Exception t)
		{
			NotifyError.notifyError(this, xbusSystem, t.getMessage(), null,
					null);
		}
		finally
		{
			// close resources and remove from the resources
			taManager.close();
			taManager.removeResource(this);
		}
	}

	protected boolean doReceive(XBUSSystem xbusSystem)
	{
		boolean mailReceived = false;
		TAManager taManager = TAManager.getInstance();

		try
		{
			taManager.begin();

			// Subclasses may define their own request content
			Object requestContent = getRequestContent(xbusSystem
					.getCompleteName());

			if (requestContent != null)
			{
				Trace.info("Receiving data from " + getAddress());

				// call application layer
				Adapter adapter = new Adapter();
				adapter.callApplication(xbusSystem, requestContent, getType());
				mReturncode = adapter.getReturncode();

				// check the Adapter return code
				// in case of "not success" throw new XException
				if (Constants.RC_OK.equals(adapter.getReturncode()))
				{
					mailReceived = true;
					taManager.commit();
					PostProcessor.start(xbusSystem, adapter.getResponse(),
							Constants.POSTPROCESSING_PERSYSTEM);
					Trace
							.info("End processing "
									+ xbusSystem.getCompleteName());
					Trace.info("----------------------------------------");
				}
				else
				{
					taManager.rollback();
					handleError(null, xbusSystem, requestContent);
					Trace.info("Error while processing "
							+ xbusSystem.getCompleteName());
					Trace.info("----------------------------------------");
				}
			}
			else
			{
				/*
				 * Folder must be closed, otherwise the messageCount of the
				 * folder will not be updated in further calls.
				 */
				closeFolder();
			}
		}
		catch (Exception e)
		{
			taManager.rollback();
			NotifyError.notifyError(this, xbusSystem, e.getMessage(), null,
					null);
		}

		return mailReceived;
	}

	private void handleError(Throwable t, XBUSSystem source, Object request)
			throws XException
	{
		throw new XException(Constants.LOCATION_INTERN,
				Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MAIL,
				"0");
	}

	/**
	 * <code>getRequestContent</code> delivers the content of the request.
	 * Subclasses may define their own request content.
	 * 
	 * @return the interface file content as <code>String</code> but casted to
	 *         <code>Object</code> for compliance reasons
	 */
	protected Object getRequestContent(String system) throws XException
	{
		// read Email
		mEmailMessage = new Email(system);

		try
		{
			if (1 <= mFolder.getMessageCount())
			{
				mMessage = mFolder.getMessage(1);

				// Get some headers
				mEmailMessage.setSentDate(mMessage.getSentDate());

				Address[] address = mMessage.getFrom();
				mEmailMessage.setFromAddress((InternetAddress) address[0]);

				address = mMessage.getRecipients(Message.RecipientType.TO);
				for (int i = 0; (address != null) && (i < address.length); i++)
				{
					mEmailMessage.setToAddress((InternetAddress) address[i]);
				}
				address = mMessage.getRecipients(Message.RecipientType.CC);
				for (int i = 0; (address != null) && (i < address.length); i++)
				{
					mEmailMessage.setCCAddress((InternetAddress) address[i]);
				}
				address = mMessage.getRecipients(Message.RecipientType.BCC);
				for (int i = 0; (address != null) && (i < address.length); i++)
				{
					mEmailMessage.setToAddress((InternetAddress) address[i]);
				}

				mEmailMessage.setSubject(mMessage.getSubject());

				String mimeType = mMessage.getContentType();
				mEmailMessage.setContentType(mimeType);

				Object o = mMessage.getContent();

				if (o instanceof String)
				{
					mEmailMessage.setContent(o.toString());
				}
				else if (o instanceof Multipart)
				{
					Multipart mp = (Multipart) o;

					int count3 = mp.getCount();

					/*
					 * This is for *.eml file j = 0 is for text, j = 1 is for
					 * HTML.
					 */
					for (int j = 0; j < count3; j++)
					{
						// Part are numbered starting at 0
						BodyPart b = mp.getBodyPart(j);

						Object o2 = b.getContent();
						if ((o2 instanceof String))
						{
							mEmailMessage.setContent(o2.toString());
						}
						else if (o2 instanceof Multipart)
						{
							// System.out.print("**This BodyPart is a nested
							// Multipart. ");
							Multipart mp2 = (Multipart) o2;

							int count2 = mp2.getCount();

							for (int k = 0; k < count2; k++)
							{
								b = mp2.getBodyPart(k);
								o2 = b.getContent();

								if ((o2 instanceof String))
								{
									mEmailMessage.setContent(o2.toString());
								}
								else if (o2 instanceof InputStream)
								{
									// nothing
								}
							}

						}
						else if (o2 instanceof InputStream)
						{
							// nothing
						}
					}
				}
				else if (o instanceof InputStream)
				{
					// InputStream is = (InputStream)o;
				}
			}
			return mEmailMessage.getXML();
		}
		catch (MessagingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
	}

	/**
	 * Implemented method <code>commit</code> from TAResource interface. The
	 * purpose of commit actions is to remove any backup information that had
	 * been created during process(tansaction).
	 * <p>
	 * Depending on the FinalResolution, the following acts commit all actions.
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Acts</th>
	 * </tr>
	 * <tr>
	 * <td>Preserve</td>
	 * <td>delete copy file</td>
	 * </tr>
	 * <tr>
	 * <td>Delete</td>
	 * <td>&nbsp;1. delete the email</td>
	 * </tr>
	 * </table>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#commit()
	 * @exception XException if any error occurs
	 */
	public void commit() throws XException
	{
		if (mResolution.equals(Constants.READ_DELETE))
		{
			deleteMail();
		}

		closeFolder();
	}

	/**
	 * Implemented method <code>rollback</code> from TAResource ignores all
	 * changes have made since the beginning of the process (transaction).
	 * <p>
	 * Depending on the FinalResolution, the following acts roll back all
	 * modifications that have been made in the file system:
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Acts</th>
	 * </tr>
	 * <tr>
	 * <td>Delete</td>
	 * <td></td>
	 * </tr>
	 * </table>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#rollback()
	 * @exception XException if any error occurs
	 */
	public void rollback() throws XException
	{
		if (mOnError.equals(Constants.READ_DELETE)
				&& DeletedMessageStore.getInstance().writeMessage())
		{
			deleteMail();
		}
		else
		{
			try
			{
				mMessage.setFlag(Flags.Flag.SEEN, false);
				mMessage.setFlag(Flags.Flag.DELETED, false);
			}
			catch (MessagingException e)
			{
				// do nothing
			}
		}

		closeFolder();
	}

	/**
	 * This method will open the connection with the POP3 server
	 */
	public void open() throws XException
	{
		try
		{
			if (mSession == null)
			{
				mSession = Session.getInstance(new Properties());
			}

			if (mStore == null)
			{
				mStore = mSession.getStore("pop3");
				mStore.connect(mHost, mUser, mPassword);
			}

			if (mFolder == null)
			{
				mFolder = mStore.getFolder("INBOX");
				mFolder.open(Folder.READ_WRITE);
			}
		}
		catch (NoSuchProviderException e)
		{
			mSession = null;
			mStore = null;
			mFolder = null;
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
		catch (MessagingException e)
		{
			mSession = null;
			mStore = null;
			mFolder = null;
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
	}

	/**
	 * This method will close the connection with the POP3 server
	 */
	public void close() throws XException
	{
		closeFolder();

		try
		{
			if (mStore != null)
			{
				mStore.close();
				mStore = null;
				mSession = null;
			}
		}
		catch (MessagingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
	}

	/**
	 * Returns the onError.
	 * 
	 * @return String
	 */
	public String getOnError()
	{
		return mOnError;
	}

	/**
	 * Do the action of deleting the message from the pop3 inbox account
	 */
	public void deleteMail() throws XException
	{
		if (mMessage != null)
		{
			try
			{
				mMessage.setFlag(Flags.Flag.DELETED, true);
				mEmailMessage = null;
				mDeleted = true;
			}
			catch (MessagingException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
			}
		}
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}

	/**
	 * Reads standard configuration and stores follow data in the class
	 * variables:
	 * <p>
	 * <dl>
	 * <dd><table>
	 * <tr>
	 * <td><code>Host</code></td>
	 * <td></td>
	 * <td>specified the host address of the POP3 account</td>
	 * </tr>
	 * <tr>
	 * <td><code>Username</code></td>
	 * <td></td>
	 * <td>specified the useraname of the POP3 account</td>
	 * </tr>
	 * <tr>
	 * <td><code>Password</code></td>
	 * <td></td>
	 * <td>specified the passowrd of the POP3 account</td>
	 * </tr>
	 * </table>
	 * </dl>
	 * 
	 * @param system name of the interface definition
	 * @throws XException if something goes wrong
	 */
	protected void readConfiguration(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		mResolution = getResolution(system);
		mOnError = getOnError(system);

		mHost = config.getValue(Constants.CHAPTER_SYSTEM, system, "Host");
		mUser = config.getValue(Constants.CHAPTER_SYSTEM, system, "User");
		mPassword = config.getValue(Constants.CHAPTER_SYSTEM, system,
				"Password");
	}

	/**
	 * Reads final resolution (resolved action with the email after that it was
	 * read) for the for the given system name from the standard configuration
	 * and checks its conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code> &nbsp;-email is remained without
	 * modifications
	 * <dd><code>Delete</code> &nbsp;&nbsp;&nbsp;- email is deleted
	 * </dl>
	 * 
	 * @param system Sytem name which resoltion must be read.
	 * @return exist resolution (Preserve, Rename or Delete) as String
	 * @exception XException if resolution is falsh or any errors occurs
	 */
	private String getResolution(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String resolution = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_RECEIVE_RESOL);
		if (!resolution.equals(Constants.READ_PRESERVE)
				&& !resolution.equals(Constants.READ_DELETE))
		{
			List params = new Vector();
			params.add(mResolution);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "1", params);
		}
		return resolution;
	}

	/**
	 * Reads the action which should happen with the email after an error for
	 * the for the given system name from the standard configuration and checks
	 * its conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code> &nbsp;-email is remained without
	 * modifications
	 * <dd><code>Delete</code> &nbsp;&nbsp;&nbsp;- email is deleted
	 * </dl>
	 * 
	 * @param system Sytem name which resoltion must be read.
	 * @return exist resolution (Preserve, Rename or Delete) as String
	 * @exception XException if resolution is falsh or any errors occurs
	 */
	private String getOnError(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String onError = config.getValue(Constants.CHAPTER_SYSTEM, system,
				"OnError");
		if (!onError.equals(Constants.READ_PRESERVE)
				&& !onError.equals(Constants.READ_DELETE))
		{
			List params = new Vector();
			params.add(onError);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "2", params);
		}
		return onError;
	}

	/**
	 * @throws XException
	 */
	protected void closeFolder() throws XException
	{
		try
		{
			if (mFolder != null)
			{
				if (mFolder.isOpen())
				{
					if (mDeleted)
					{
						mFolder.close(true);
					}
					else
					{
						mFolder.close(false);
					}
				}
				mFolder = null;
			}
		}
		catch (MessagingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
		}
	}

	protected String getAddress()
	{
		return new StringBuffer(mUser).append("@").append(mHost).toString();
	}

	/**
	 * @return Returns the mReturncode.
	 */
	protected String getReturncode()
	{
		return mReturncode;
	}
}
