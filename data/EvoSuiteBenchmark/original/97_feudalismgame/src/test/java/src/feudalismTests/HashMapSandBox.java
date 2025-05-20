package src.feudalismTests;

import java.util.HashMap;

import src.Fiefdoms;

public class HashMapSandBox {
	HashMap<String, Fiefdoms> map = new HashMap<String, Fiefdoms>();
	
	public void hashMapCreator(String key, Fiefdoms fiefdom){
		map.put(key, fiefdom);
	}
	
	public Fiefdoms hashMapGetter(String key){
		Fiefdoms fiefdom = map.get(key);
		return fiefdom;

	}
}