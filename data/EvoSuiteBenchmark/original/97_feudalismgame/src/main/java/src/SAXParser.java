package src;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
public class SAXParser {

	@SuppressWarnings("unchecked")
	public static HashMap<String, Fiefdoms> fiefdoms(){
		XMLDecoder file = null;
	
		HashMap<String, Fiefdoms> map = new HashMap<String, Fiefdoms>();

		try {
			file = new XMLDecoder(new BufferedInputStream(
					new FileInputStream("fiefdomsSax.xml")));
			map = (HashMap<String, Fiefdoms>)file.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			file.close();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Knight> knights(){
		XMLDecoder file = null;
		
		HashMap<String, Knight> currentknights = new HashMap<String, Knight>();

		try {
			file = new XMLDecoder(new BufferedInputStream(
					new FileInputStream("knightsSax.xml")));
			currentknights = (HashMap<String, Knight>)file.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			file.close();
		}
		return currentknights;
	}
}
