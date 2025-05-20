package src.feudalismTests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import src.Fiefdoms;
import src.Knight;
import src.XMLable;

public class XMLHandler extends DefaultHandler{

	Document dom;
	Element rootEle;

	public XMLHandler(ArrayList<XMLable> f){
		XMLGenerator(f);
	}

	public boolean XMLGenerator(ArrayList<XMLable> l){
		//Get a DOM object
		createDocument();
		if(l.iterator().next().getClass().toString().equals("class feudalism.Fiefdoms"))
			XMLFiefdomsCreator(l);

		else if(l.iterator().next().getClass().toString().equals("class feudalism.Knight")){
			XMLKnightsCreator(l);
		}
		else
			return false;

		return true;
	}

	public void XMLFiefdomsCreator(ArrayList<XMLable> f){
		System.out.println("XML Fiefdoms Creator started... ");
		Iterator fiefdoms = f.iterator();
		createDOMTree("fiefdoms");
		while(fiefdoms.hasNext()){
			Element fiefdomEle = createFiefdomElement((Fiefdoms)fiefdoms.next());
			rootEle.appendChild(fiefdomEle);
			printToFile("fiefdoms");
		}
	}

	public void XMLKnightsCreator(ArrayList<XMLable> k){
		System.out.println("XML Knights Creator started...");
		Iterator knights = k.iterator();
		createDOMTree("knights");
		while(knights.hasNext()) {
			Element knightsEle = createKnightElement((Knight)knights.next());
			rootEle.appendChild(knightsEle);
			printToFile("knights");
		}
	}

	private void createDocument() {
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		//get an instance of builder
		DocumentBuilder db = dbf.newDocumentBuilder();

		//create an instance of DOM
		dom = db.newDocument();

		}catch(ParserConfigurationException pce) {
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}
	}

	private void createDOMTree(String xmlType){
		rootEle = dom.getDocumentElement();
		if (xmlType.equals("fiefdoms"))
			rootEle = dom.createElement("Fiefdoms");
		else if(xmlType.equals("knights"))
			rootEle = dom.createElement("Knights");
			dom.appendChild(rootEle);
	}

	private Element createFiefdomElement(Fiefdoms f){
		//Create attributes for the fiefdom element
		Element fiefdomEle = dom.createElement("Fiefdom");
		fiefdomEle.setAttribute("Name", f.getName());
		fiefdomEle.setAttribute("Value", Integer.toString(f.getValue()));
		fiefdomEle.setAttribute("Owner", f.getOwner());
		fiefdomEle.setAttribute("FiefdomCastles", Integer.toString(f.getFiefdomCastles()));
		fiefdomEle.setAttribute("PersonalCastles", Integer.toString(f.getPersonalCastles()));
		fiefdomEle.setAttribute("LoyalVassals", Integer.toString(f.getValue()));
		fiefdomEle.setAttribute("RebelliousVassals", Integer.toString(f.getRebelliousVassals().size()));
//		fiefdomEle.setAttribute("Intruders", Integer.toString(f.getIntruders()));
		fiefdomEle.setAttribute("Peasants", Integer.toString(f.getPeasants()));
		fiefdomEle.setAttribute("Kingdom", Boolean.toString(f.isKingdom()));

		//create author element and author text node and attach it to bookElement
		Iterator neiIter = f.getNeighbors().iterator();
		while(neiIter.hasNext()){
			String neighbor = (String) neiIter.next();
			Element neiEle = dom.createElement("Neighbor");
			Text neiName = dom.createTextNode(neighbor);
			neiEle.appendChild(neiName);
			fiefdomEle.appendChild(neiEle);
		}
		return fiefdomEle;
	}

	private Element createKnightElement(Knight k) {
		//Create attributes for the fiefdom element
		Element knightEle = dom.createElement("Knight");
		knightEle.setAttribute("Name", k.getName());
		knightEle.setAttribute("Rank", k.getRank());
		knightEle.setAttribute("Money", Integer.toString(k.getTotalMoney()));
		Iterator fieIter = k.getFiefdoms().iterator();
		while(fieIter.hasNext()){
			Fiefdoms fiefdom = (Fiefdoms) fieIter.next();
			
			
			Element fieEle = dom.createElement("Fiefdom");
			Text fieName = dom.createTextNode(fiefdom.getName());
			System.out.println(fieEle);
			fieEle.appendChild(fieName);
			knightEle.appendChild(fieEle);
		}
		return knightEle;
	}
	
	private void printToFile(String xmlType){
		XMLSerializer serializer = null;
		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);
			//to generate a file output use fileoutputstream instead of system.out
			if(xmlType.equalsIgnoreCase("fiefdoms")){
				serializer = new XMLSerializer(
					new FileOutputStream(new File("fiefdoms.xml")), format);
			}
			else if(xmlType.equalsIgnoreCase("knights")){
				serializer = new XMLSerializer(
						new FileOutputStream(new File("knights.xml")), format);
			}
			else
				System.out.println("Invalid XML type");
			serializer.serialize(dom);
			System.out.println("Generated file successfully.");
		} catch(IOException ie) {
		    ie.printStackTrace();
		}
	}
// XML Parsers	
	
}