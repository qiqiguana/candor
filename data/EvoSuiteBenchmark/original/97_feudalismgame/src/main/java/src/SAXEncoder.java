package src;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

public class SAXEncoder {
	
	public static void fiefdoms(HashMap<String, Fiefdoms> map){
		XMLEncoder file = null;
		try {
			file = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("fiefdomsSax.xml")));
			file.writeObject(map);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			file.close();
		}
	}

	public static void knights(HashMap<String, Knight> players){
		XMLEncoder file = null;
		try {
			file = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("KnightsSax.xml")));
			file.writeObject(players);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			file.close();
		}
	}
}