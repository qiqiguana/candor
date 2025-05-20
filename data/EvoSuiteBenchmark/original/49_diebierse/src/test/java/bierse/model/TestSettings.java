package bierse.model;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSettings {

	@Test
	public void testLoadAndSave() {
		Model m = new Model();
		Settings origSet = new Settings(m);
		
		Settings settings = m.getSettings();
		settings.setCurrency("$");
		settings.setPriceSteps(30);
		settings.setTimeInterval(120);
		File file = settings.save();
		assertNotNull(file);
		
		Settings s2 = new Settings(m);
		s2.load();
		assertNotSame(settings, s2);
		assertEquals(settings.getCurrency(), s2.getCurrency());
		assertEquals(settings.getPriceSteps(), s2.getPriceSteps());
		assertEquals(settings.getTimeInterval(), s2.getTimeInterval());
		
		//Reset xml file to original values
		origSet.save();
	}
}
