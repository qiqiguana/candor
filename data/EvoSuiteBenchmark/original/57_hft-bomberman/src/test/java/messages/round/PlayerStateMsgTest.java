package messages.round;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class PlayerStateMsgTest {
	//private int[] moveVector;
	
	//private int[] position;
	Point position = new Point(3,7);
	Point moveVector = new Point(3,7);

	int objektId = 6;
	PlayerStateMsg playerStateMsg = new PlayerStateMsg(objektId, position , moveVector);

	@Test
	public void testPlayerStateMsg() {
		assertNotNull(playerStateMsg);
	}

	@Test
	public void testExecuteServerGameRoundClientInfo() {
		//fail("Not yet implemented");
	}

	@Test
	public void testExecuteClientGameRound() {
		//fail("Not yet implemented");
	}

}


