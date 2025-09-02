package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseMusic.
*/
class ParseMusicTest {
	@Test
	void testParseMusic_EmptyString_ReturnsEmptyList() {
		// Arrange and Act
		List<Object> result = ParseMusic.parseMusic("");
		// Assert
		assertTrue(result.isEmpty());
	}
}