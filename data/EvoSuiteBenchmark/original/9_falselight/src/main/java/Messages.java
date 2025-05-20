/**
 * 
 */
import java.util.*;
//import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;
/**
 * @author Gilles
 *
 */
public class Messages {

public static void send(String to, String from, String host, String subject, String msgText)
{

	// Données de connexion
	Properties props = new Properties();
	props.put("mail.smtp.host", host);
	props.put("mail.debug", false);
	Session session = Session.getInstance(props, null);
	session.setDebug(false);
	
	try {
	    // Création du message
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	    InternetAddress[] address = {new InternetAddress(to)};
	    msg.setRecipients(Message.RecipientType.TO, address);
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
	    msg.setText(msgText);
	    // Envoie
	    Transport.send(msg);
	}
	//gestion des erreurs
	catch (AddressException e)
	{

	}
	catch (MessagingException e)
	{

	}
  }
}