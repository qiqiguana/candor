package fr.unice.gfarce.connect;

import java.io.*;
import java.net.URISyntaxException;
import java.text.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public abstract class SetConfigPersistance {

	private Document doc;
	private String output;

	public SetConfigPersistance() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating( false );
		DocumentBuilder parser = factory.newDocumentBuilder();
		doc = parser.parse("src/META-INF/persistence.xml");
		this.output = "src/META-INF/persistence.xml";
	}

	public void processNodeRecursively(Node node) throws Exception {
		performUpdates( node );
		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
			processNodeRecursively(child);
		}
	}

	abstract public void performUpdates(Node node) throws Exception;

	public void serialize() throws TransformerConfigurationException, TransformerException, URISyntaxException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		Source source = new DOMSource( doc );
		Result result = new StreamResult(output);
		transformer.transform( source, result );
	}

	static public void main(String adresse,String base,String user, String pass) {
		try {
			final String user2 = user;
			final String pass2 = pass;
			final String base2 = base;
			final String adresse2 = adresse;
			SetConfigPersistance res = new SetConfigPersistance(){

				private String VALEUR = "value";
				private String NAME = "name";
				public void performUpdates(Node node) throws ParseException {
					String s1 ="javax.persistence.jdbc.url";
					String s2 = "javax.persistence.jdbc.user";
					String s3 = "javax.persistence.jdbc.password";
					if ( node.getNodeType() == Node.ELEMENT_NODE ) {
						Element elem = (Element) node;

						if ( elem.hasAttribute( VALEUR ) && elem.hasAttribute(NAME)) {
							String result = elem.getAttribute(NAME);

							if(result.equals(s1)){
								String database = "jdbc:oracle:thin:@"+adresse2+":1521:"+base2 ;	   
								elem.setAttribute(VALEUR, database);

							}
							if(result.equals(s2)){
								elem.setAttribute(VALEUR, user2);
							}

							if(result.equals(s3)){
								elem.setAttribute(VALEUR, pass2);
								
							}

						}
					}
				}
			};

			res.processNodeRecursively(res.doc.getDocumentElement() );
			res.serialize();

		} catch (Exception e) {
			System.out.println("Usage :\n    DomUpdate in.xml out.xml date");
			e.printStackTrace();
		}
	}
}
