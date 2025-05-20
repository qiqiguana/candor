package org.heal.servlet;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * An {@link Action} which emails form data
 */
public class EmailFeedbackAction implements Action {
	public static final String DEFAULT_FROM = new String("info@healcentral.org");
	public static final String DEFAULT_SUBJECT = new String("HEALCENTRAL.ORG mailer");
	public static final String DEFAULT_BODY = new String();
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final String smtpServer;
	private final String xmlFilename;

	public EmailFeedbackAction(String smtpServer, String xmlFilename) {
		this.smtpServer = smtpServer;
		this.xmlFilename = xmlFilename;
	}

	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
		  throws IOException, ServletException {
		saveXml(request.getParameterMap());
        sendEmail(request.getParameterMap());

		RequestDispatcher rd = request.getRequestDispatcher("/feedback/mail_logged_response.html");
		rd.forward(request, response);
	}

	public boolean actionRequiresLogin() {
		return false;
	}

	/**
	 * Sends an email according to the parameters indicated
	 *
	 * @param parameters A <code>Map</code> representing the variables
	 * submitted to the servlet.
	 *
	 * @return <code>true</code> if an email was sent, otherwise <code>false</code>.
	 */
	private boolean sendEmail(final Map parameters) {
		Map formVariables = new HashMap(parameters);
		String to = join((String[])formVariables.get("to"), LINE_SEPARATOR);
		String from = join((String[])formVariables.get("from"), LINE_SEPARATOR);
		String subject = join((String[])formVariables.get("subject"), LINE_SEPARATOR);
		String body = join((String[])formVariables.get("body"), LINE_SEPARATOR);
		formVariables.remove("to");
		formVariables.remove("from");
		formVariables.remove("subject");
		formVariables.remove("body");

		if(null == to) {
			return false; // This field is required
		}
		if(null == from) {
			from = DEFAULT_FROM;
		}
		if(null == subject) {
			subject = DEFAULT_SUBJECT;
		}
		if(null == body) {
			body = DEFAULT_BODY;
		}

		StringBuffer extra = new StringBuffer();
		for(Object temp : formVariables.entrySet()) {
			Map.Entry entry = (Map.Entry)temp;
			extra.append(entry.getKey()).append(" = ").append(join((String[])entry.getValue(), LINE_SEPARATOR)).append(LINE_SEPARATOR);
		}

		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpServer);

			Session session = Session.getDefaultInstance(props, null);

			// Constructs the email from the form variables
			StringBuffer msg = new StringBuffer();
			msg.append(body);
			if(extra.length() > 0) {
				msg.append("\n");
				msg.append("-------\n");
				msg.append(extra.toString());
			}
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(msg.toString());

			Transport.send(message);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Saves the form data to an xml file.
	 *
	 * @param formVariables
	 *
	 * @throws IOException
	 */
	private synchronized void saveXml(Map formVariables) throws IOException {
		boolean fileExists = false;
		File file = new File(xmlFilename);
		if(file.exists()) {
			fileExists = true;
		}
		RandomAccessFile xmlFile = new RandomAccessFile(xmlFilename, "rw");
		xmlFile.seek(xmlFile.length());
		if(!fileExists) {
			xmlFile.writeBytes("<?xml version=\"1.0\"?>" + LINE_SEPARATOR);
			xmlFile.writeBytes("<!DOCTYPE records" + LINE_SEPARATOR);
			xmlFile.writeBytes("[" + LINE_SEPARATOR);
			xmlFile.writeBytes("<!ELEMENT records (record)+>" + LINE_SEPARATOR);
			xmlFile.writeBytes("<!ELEMENT record (");
			StringBuffer fieldNameList = new StringBuffer();
			StringBuffer elementList = new StringBuffer();
			for(Object key : formVariables.keySet()) {
				String fieldName = (String)key;
				if(fieldNameList.length() > 0) {
					fieldNameList.append("," + fieldName);
				} else {
					fieldNameList.append(fieldName);
				}
				elementList.append("<!ELEMENT " + fieldName + " (#PCDATA)>" + LINE_SEPARATOR);
			}

			xmlFile.writeBytes(fieldNameList.toString() + ")>" + LINE_SEPARATOR);
			xmlFile.writeBytes(elementList.toString());
			xmlFile.writeBytes("]>" + LINE_SEPARATOR);
			xmlFile.writeBytes("<records>" + LINE_SEPARATOR);
		} else {
			xmlFile.seek(xmlFile.length() - (long)"records".length() - (long)LINE_SEPARATOR.length() - 3L);
		}
		xmlFile.writeBytes("<record>" + LINE_SEPARATOR);

		for(Object entry : formVariables.entrySet()) {
			final String fieldName = (String)((Map.Entry)entry).getKey();
			final String fieldValue = join((String[])((Map.Entry)entry).getValue(), LINE_SEPARATOR);
			xmlFile.writeBytes("<" + fieldName + ">" + fieldValue + "</" + fieldName + ">" + LINE_SEPARATOR);
		}
		xmlFile.writeBytes("</record>" + LINE_SEPARATOR);
		xmlFile.writeBytes("</records>" + LINE_SEPARATOR);
		xmlFile.close();
	}

	private static final String join(String[] arr, String delim) {
		if(null == arr) { return null; }
		StringBuffer buffer = new StringBuffer();
		final int lastElement = arr.length-1;
		for(int i = 0; i < arr.length; ++i) {
			buffer.append(arr[i]);
			if(i < lastElement) {
				buffer.append(delim);
			}
		}
		return buffer.toString();
	}
}
