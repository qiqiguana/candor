package src.feudalismTests;

import src.Fiefdoms;
import src.Knight;
import junit.framework.TestCase;

public class HashMapSandBoxTest extends TestCase {

	public void testHashMapCreator() {
		HashMapSandBox map = new HashMapSandBox();
		
		Fiefdoms fiefdomA = new Fiefdoms();
		Fiefdoms fiefdomB = new Fiefdoms();
		
		Knight knightA = new Knight();
		knightA.setName("Mathew");
		
		Knight knightB = new Knight();
		knightB.setName("Arthur");
		
		fiefdomA.setOwner(knightA.getName());
		fiefdomA.setName("Champagne");
		
		fiefdomB.setOwner(knightB.getName());
		fiefdomB.setName("Bourbon");
		
		map.hashMapCreator(fiefdomA.getName(), fiefdomA);
		map.hashMapCreator(fiefdomB.getName(), fiefdomB);
		Fiefdoms aFiefdom = map.hashMapGetter(fiefdomB.getName());
		
		
		assertSame("Champagne", aFiefdom.getName());
		
		
	
	}

	public void testHashMapGetter() {

	}

}
