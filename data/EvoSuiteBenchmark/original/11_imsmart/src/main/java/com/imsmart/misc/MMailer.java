package com.imsmart.misc;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MMailer
{
	private String smtpHost;

	private String fromAddress;

	private String toAddress;

	private String subject;

	private String body;

	private String attachment;

	public MMailer()
	{

	}

	public void setFromProperties()
	{
		MProperties properties = MProperties.getInstance();
		smtpHost = properties.getPropertyValue(MProperties.SMTP_HOST);
		fromAddress = properties
				.getPropertyValue(MProperties.MAIL_FROM_ADDRESS);
		toAddress = properties.getPropertyValue(MProperties.MAIL_TO_ADDRESS);
		subject = properties.getPropertyValue(MProperties.MAIL_SUBJECT);
		body = properties.getPropertyValue(MProperties.MAIL_BODY);
	}

	public void setSmtpHost(String host)
	{
		this.smtpHost = host;
	}

	public void setToAddress(String toAddress)
	{
		this.toAddress = toAddress;
	}

	public void setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setAttachment(String fileNameWithPath)
	{
		this.attachment = fileNameWithPath;
	}

	public void sendMail()
	{
		try
		{
			Properties props = new Properties();
			props.put("mail.smtp.host", this.smtpHost);

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			Message msg = new MimeMessage(session);
			// if the there are multiple addresses
			Address[] toUserS = InternetAddress.parse(this.toAddress);

			msg.setFrom(new InternetAddress(this.fromAddress));
			msg.setRecipients(Message.RecipientType.TO, toUserS);

			msg.setSubject(this.subject);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(this.body);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(this.attachment);
			messageBodyPart.setDataHandler(new DataHandler(source));

			messageBodyPart.setFileName(getFileName(attachment));
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);

			Transport.send(msg);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private String getFileName(String fileNameWithPath)
	{
		int lastIndexOfSlash = fileNameWithPath.lastIndexOf("/");
		int nameLengthWithDir = fileNameWithPath.length();

		String fileName = fileNameWithPath.substring(lastIndexOfSlash + 1,
				nameLengthWithDir);

		return fileName;

	}
}
