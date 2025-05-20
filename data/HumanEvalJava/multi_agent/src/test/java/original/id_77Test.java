package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Iscube.
*/
class IscubeTest {
	@Test
	void testIscube(){
		int n = 8;
		assertTrue(original.Iscube.iscube(n));
	}
}
