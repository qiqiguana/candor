package src.feudalismTests;

import java.util.ArrayList;
import java.util.HashMap;

import src.Fiefdoms;
import src.Map;
import src.SAXEncoder;

import junit.framework.TestCase;

public class FiefdomsTest extends TestCase {

	public void testGetName() {

	}

	public void testSetName() {

	}

	public void testGetNeighbors() {

	}

	public void testSetNeighbors() {

	}

	public void testGetOwner() {

	}

	public void testSetOwner() {

	}

	public void testGetValue() {

	}

	public void testSetValue() {

	}

	public void testSetPersonalCastles() {

	}

	public void testGetPersonalCastles() {

	}

	public void testSetFiefdomCastles() {

	}

	public void testGetFiefdomCastles() {

	}

	public void testGetIntruders() {

	}

	public void testSetIntruders() {
		ArrayList<String> al = new ArrayList();
		al.add("Viking");
		al.add("Muslim");
		Fiefdoms fief = new Fiefdoms();
		fief.setName("America");
		fief.setInvaderType(al);
		HashMap<String, Fiefdoms> map = Map.getAll();
		map.put(fief.getName(), fief);
		SAXEncoder.fiefdoms(map);
//		new TabbedInterface().init();
	}

	public void testIsKingdom() {

	}

	public void testSetKingdom() {

	}

	public void testGetLoyalVassals() {

	}

	public void testGetPeasants() {

	}

	public void testSetPeasants() {

	}

	public void testGetRebelliousVassals() {

	}

	public void testSetRebelliousVassals() {

	}

	public void testSetMoveVassals() {

	}

	public void testGetMoveVassals() {

	}

}