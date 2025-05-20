package messages.global.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import messages.global.MapInfo;
import messages.global.MapListMsg;

import org.junit.Test;

public class MapListMsgTest {
	List<MapInfo> maps;

	@Test
	public void testMapListMsg() {
		maps = new LinkedList<MapInfo>();
		MapListMsg  mapListMsg = new MapListMsg(maps);
		assertNotNull(mapListMsg);
		
	}

	@Test
	public void testExecute() {
		//fail("Not yet implemented");
	}

}
