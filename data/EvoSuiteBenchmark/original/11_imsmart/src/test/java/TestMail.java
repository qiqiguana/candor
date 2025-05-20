import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestMail
{

	public static void main(String arg[]) throws Exception
	{
		int idCounter = 0;
		boolean debug = false;
		Properties props = new Properties();
		props.put("mail.smtp.host", "207.15.48.16");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress from = new InternetAddress("anant.gowerdhan@momed.com");
		InternetAddress to = new InternetAddress("anant.gowerdhan@momed.com");

		msg.setFrom(from);
		msg.setRecipient(Message.RecipientType.TO, to);
		msg.setSubject("Testing Domino");
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("Hi, how are you");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
