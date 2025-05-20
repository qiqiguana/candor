package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	public static HashMap<String,Fiefdoms> fiefdoms(){

		HashMap<String,Fiefdoms> map = new HashMap<String,Fiefdoms>();
		ArrayList<Fiefdoms> fiefdoms = new ArrayList<Fiefdoms>();
		Document doc;
		String FILENAME = "fiefdoms.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try { //Load document as DOM
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(FILENAME);
			Element e = doc.getDocumentElement();
			NodeList nl = e.getElementsByTagName("Fiefdom");

			for(int i = 0 ; i < nl.getLength() ; i++){
				Element el = (Element)nl.item(i); //Look for <Fiefdom> elements
				//get the Fiefdom object
				Fiefdoms fiefdom = getFiefdom(el);
				fiefdoms.add(fiefdom);
				//Populating map with fiefdoms
				map.put(fiefdom.getName(), fiefdom);
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SAXEncoder.fiefdoms(map);
		return map;
	}

	private static Fiefdoms getFiefdom(Element fiefEle){

		ArrayList<String> neighbors = getListValue(fiefEle, "Neighbor");
		String name = fiefEle.getAttribute("Name");
//		int vassals = Integer.parseInt(fiefEle.getAttribute("Vassals"));
		String owner = fiefEle.getAttribute("Owner");
		int value = Integer.parseInt(fiefEle.getAttribute("Value"));
		int fCastles = Integer.parseInt(fiefEle.getAttribute("FiefdomCastles"));
		int pCastles = Integer.parseInt(fiefEle.getAttribute("PersonalCastles"));
		int intruders = Integer.parseInt(fiefEle.getAttribute("Intruders"));
		int peasants = Integer.parseInt(fiefEle.getAttribute("Peasants"));
		int loyalVassals = Integer.parseInt(fiefEle.getAttribute("LoyalVassals"));
		int rebelliousVassals = Integer.parseInt(fiefEle.getAttribute("RebelliousVassals"));
		boolean isKingdom = Boolean.parseBoolean(fiefEle.getAttribute("Kingdom"));
		ArrayList<String> invaderType = getListValue(fiefEle, "invaderType");
		Fiefdoms fiefdom = new Fiefdoms(neighbors, name, owner, value, isKingdom, invaderType);

		return fiefdom;
	}

	private static ArrayList<String> getListValue(Element e, String tagName){
		ArrayList<String> valuesList = new ArrayList<String>();
		String textVal = null;
		NodeList nl = e.getElementsByTagName(tagName);
		if((nl != null) && (nl.getLength() > 0)){
			for(int i = 0; i < nl.getLength(); i++){
				Element el = (Element) nl.item(i);
				textVal = el.getFirstChild().getNodeValue();
//				System.out.println(textVal);
				valuesList.add(textVal);
			}
		}
		return valuesList;
	}

//	public static HashMap<String,Knight> knights(){
//
//		HashMap<String,Knight> knights = new HashMap<String,Knight>();
//		Document doc;
//		String FILENAME = "knights.xml";
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		try { //Load document as DOM
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			doc = db.parse(FILENAME);
//			Element e = doc.getDocumentElement();
//			NodeList nl = e.getElementsByTagName("Knight");
//			for(int i = 0 ; i < nl.getLength() ; i++){
//				Element el = (Element)nl.item(i); //Look for <Knight> elements
//				//get the Fiefdom object
//				Knight knight = getKnight(el);
//				//Populating map with fiefdoms
//				knights.put(knight.getName(), knight);
//			}
//
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return knights;
//	}

//	private static Knight getKnight(Element knightEle){
//		HashMap fiefdoms = XMLParser.fiefdoms(); //Get  hashmap of all fiefdoms
//		ArrayList<String> fiefdomNames = getListValue(knightEle, "Fiefdom"); //Get the list of fiefdoms owned by this knight
//		ArrayList<String> fiefdomList = new ArrayList<String>();
//		String fiefdomName = null;
//		Iterator fiefdomNamesIter = fiefdomNames.iterator();
//		while(fiefdomNamesIter.hasNext() && (fiefdomName = (String)fiefdomNamesIter.next()) != null){
//			Fiefdoms fiefdom = (Fiefdoms)fiefdoms.get(fiefdomName);
////			System.out.println(fiefdom.getName());
//			fiefdomList.add(fiefdom.getName());
////			System.out.println(fiefdom.getName());
////			System.out.println(fiefdom.getOwner().getName());
//		}
//
//		String name = knightEle.getAttribute("Name");
//		String rank = knightEle.getAttribute("Rank");
//		int money = Integer.parseInt(knightEle.getAttribute("Money"));
//		int diceNumber = Integer.parseInt(knightEle.getAttribute("Dice"));
//		Knight knight = new Knight(name, money, fiefdomList, rank);

//		return knight;
//	}
}