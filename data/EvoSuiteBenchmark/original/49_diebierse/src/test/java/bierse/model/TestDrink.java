package bierse.model;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDrink {
	
	@Test
	public void testSaveAndLoad() {
		Model m = new Model();
		Drink d1 = new Drink("JUnitTestDrink", m);
		d1.setMinPrice(100);
		d1.setMaxPrice(200);
		d1.setStartPrice(300);
		d1.setTargetAmount(400);
		d1.setDeltaAmount(500);
		d1.setMaxStep(600);
		d1.setUsed(true);
		d1.setKey(123);
		File file = d1.save();
		assertNotNull(file);
		assertTrue(file.exists());
		
		Drink d2 = new Drink("JUnitTestDrink", m);
		assertEquals(d1.getName(), d2.getName());
		assertNotSame(d1, d2);
		d2.load();
		assertNotNull(d2);
		assertEquals(d1.getMinPrice(), d2.getMinPrice());
		assertEquals(d1.getMaxPrice(), d2.getMaxPrice());
		assertEquals(d1.getStartPrice(), d2.getStartPrice());
		assertEquals(d1.getTargetAmount(), d2.getTargetAmount());
		assertEquals(d1.getDeltaAmount(), d2.getDeltaAmount());
		assertEquals(d1.getMaxStep(), d2.getMaxStep());
		assertEquals(d1.isUsed(), d2.isUsed());
		assertEquals(d1.getKey(), d2.getKey());
		
		// Remove file after test
		file.delete();
	}
	
	@Test
	public void testRecalculation() {
		Model m = new Model();
		m.getSettings().setPriceSteps(10);
		m.getSettings().setStandardLogic(true);
		Drink d = new Drink("JUnitTestDrink", m);
		
		d.setDeltaAmount(30);
		d.setTargetAmount(50);
		d.setCurrentPrice(100);
		d.setMaxStep(100);
		d.setLastSold(30);
		d.setMaxPrice(200);
		d.setMinPrice(0);
		m.getLstDrinks().add(d);
		
		m.enableDrink(d);
		
		m.recalculate();
		int newPrice = d.getCurrentPrice();
		assertEquals(30, newPrice);
		assertEquals(d.getTotalSold(), 30);
		assertTrue(d.getAverageAmount() == 30);
		
		d.setCurrentPrice(100);
		d.setLastSold(70);
		
		m.recalculate();
		newPrice = d.getCurrentPrice();
		assertTrue(newPrice == 170);
		assertEquals(d.getTotalSold(), 100);
		assertTrue(d.getAverageAmount() == 50);
		
		d.setCurrentPrice(100);
		d.setLastSold(40);
		
		m.recalculate();
		newPrice = d.getCurrentPrice();
		assertTrue(newPrice == 70);
		assertEquals(d.getTotalSold(), 140);
		
		d.setCurrentPrice(100);
		d.setLastSold(60);
		
		m.recalculate();
		newPrice = d.getCurrentPrice();
		assertTrue(newPrice == 130);
		assertEquals(d.getTotalSold(), 200);
		assertTrue(d.getAverageAmount() == 50);
		
		d.setCurrentPrice(100);
		d.setLastSold(40);
		d.setMaxStep(20);
		
		m.recalculate();
		newPrice = d.getCurrentPrice();
		assertTrue(newPrice == 90);
		assertEquals(d.getTotalSold(), 240);
		
		d.setCurrentPrice(100);
		d.setLastSold(60);
		d.setMaxStep(20);
		
		m.recalculate();
		newPrice = d.getCurrentPrice();
		assertTrue(newPrice == 110);
		assertEquals(d.getTotalSold(), 300);
		assertTrue(d.getAverageAmount() == 50);
	}
	
}
