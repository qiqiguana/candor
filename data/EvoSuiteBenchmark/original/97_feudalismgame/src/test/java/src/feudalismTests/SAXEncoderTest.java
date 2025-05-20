package src.feudalismTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import src.Knight;
import src.SAXEncoder;
import src.SAXParser;

import junit.framework.TestCase;

public class SAXEncoderTest extends TestCase {

	public void testFiefdoms() {

	}

	public void testKnights() {
		
		Knight knight1 = new Knight("knight","Mathew", "myPassword123" );
		Knight knight2 = new Knight("knight", "Mathew","passwd" );
		
		HashMap knightsList = new HashMap<String, Knight>();
		knightsList.put("Mathew", "knight");
		knightsList.put("Johan", "knight");
		
		
		
		SAXEncoder.knights(knightsList);

		HashMap knights = SAXParser.knights();
		Knight knightA = (Knight)knights.get("Mathew");

//		Iterator knightAFiefIter = knightA.getFiefdoms().iterator();
		
//		while(knightAFiefIter.hasNext()){
			System.out.println(knightA.getFiefdoms().get(0));
//		}
	}
}