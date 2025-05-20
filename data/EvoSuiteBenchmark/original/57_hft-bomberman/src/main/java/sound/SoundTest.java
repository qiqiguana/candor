package sound;

import static org.junit.Assert.*;

import org.junit.Test;

public class SoundTest {

	@Test
	public void testObject() {
		
		
		Thread t1 = new Thread( new Sound( ISound.INTRO ) );
		Thread t2 = new Thread( new Sound( ISound.OUTRO ) );
		t1.start();
		t2.start();
		assertSame(t1.getClass(), t2.getClass());
		assertNull(t1);
	}

}
