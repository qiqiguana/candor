package src.feudalismTests;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import src.Fiefdoms;

public class XercesSandBox{
	
	public static String FILENAME = "fiefdoms.xml";
	
	public static boolean addInfo(Fiefdoms o){
		XMLEncoder os = null;
		
		try{
			os = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(FILENAME)));
			os.writeObject(o);
			
			return true;
		}catch(IOException e){
			return false;
		}
		
		finally{
			os.close();
		}
	}
	
	public static HashMap<String,String> getInfo(String key, Fiefdoms value){
		HashMap h = new HashMap();
		
		return h;
	}
	
}
