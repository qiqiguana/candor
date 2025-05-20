package org.character;

import org.character.data.config.CharConfig;
import org.character.data.config.XMLCharConfig;
import org.character.data.map.MapIndex;
import org.character.data.map.XMLMapPersistence;
import org.javathena.core.utiles.Functions;



/**
 * Hello world!
 * 
 */
public class CharServer
{
	
	public CharServer()
	{
		
	}
	public static void main(String[] args)
	{
		System.out.println("".split("\t").length);
		/*CharServer cc = new CharServer();
		cc.doInit();

		/**/
	}
	private void doInit()
	{
		MapIndex.setPersistenceMethod(new XMLMapPersistence());
		MapIndex.load();
		CharConfig.setPersistenceMethod(new XMLCharConfig());
		CharConfig.load();
		Functions.showInfo("Initializing char server.\n");
	}
}
