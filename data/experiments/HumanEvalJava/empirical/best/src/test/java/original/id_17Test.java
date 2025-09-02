package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParseMusicTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(4, 2, 1, 2, 2, 1, 1, 1, 1, 4, 4), original.ParseMusic.parseMusic("o o| .| o| o| .| .| .| .| o o"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(), original.ParseMusic.parseMusic(""));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(4, 4, 4, 4), original.ParseMusic.parseMusic("o o o o"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 1, 1, 1), original.ParseMusic.parseMusic(".| .| .| .|"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(2, 2, 1, 1, 4, 4, 4, 4), original.ParseMusic.parseMusic("o| o| .| .| o o o o"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(2, 1, 2, 1, 4, 2, 4, 2), original.ParseMusic.parseMusic("o| .| o| .| o o| o o|"));
	}

	
}