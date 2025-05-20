package server;

import java.awt.Point;

import common.Constants;

import messages.round.RoundTimeOverMsg;
import messages.round.RoundTimeOneSecondLeftMsg;

import common.GameModel;

/**
 * The GameModel for the Server.
 * 
 * @author Björn
 * 
 */
public class ServerGameModel extends GameModel {

	/**
	 * Count of the GameLoop Frames
	 */
	private int frames = 0;

	/**
	 * Tile count for the borders
	 */
	private int count = 0;

	/**
	 * Tile count for the borders
	 */
	private int count1 = 1;

	/**
	 * Tile count for the borders
	 */
	private int count2 = 18;

	/**
	 * Tile count for the borders
	 */
	private int count3 = 13;

	/**
	 * 
	 */
	private ServerGameRound round;

	/**
	 * Saves weather the time ticking has stopped or not.
	 */
	private boolean stopped = false;

	public ServerGameModel(ServerGameRound round) {
		this.round = round;
	}

	@Override
	public void update() {
		super.update();
		if (!stopped) {
			frames++;
			tick();
			if (frames == 62) {
				oneSecondLeft();
			}
			if (time == 120000) {
				twoMinutesToEnd();
			}
			if (time == 90000) {
				oneAndAHalfMinuteToEnd();
			}
			if (time == 60000) {
				oneMinuteToEnd();
			}
			if (time == 30000) {
				halfMinuteToEnd();
			}
			if (time <= 0) {
				timeExpired();
			}
			if (round.getBorder() == 1 && frames % 10 == 0) {
				int id = 10000 + count;
				count++;
				if (count < 19) {
					round.createNewTile(new Point(count * Constants.TILE_BORDER
							+ Constants.HALF_TILE, Constants.TILE_BORDER
							+ Constants.HALF_TILE), id, "stone", false, false,
							true);
				} else if (count >= 19 && count < 31) {
					count1++;
					round.createNewTile(new Point(18 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count1
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 30) {
						count1 = 2;
					}
				} else if (count >= 31 && count < 48) {
					count2--;
					round.createNewTile(new Point(count2
							* Constants.TILE_BORDER + Constants.HALF_TILE, 13
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 47) {
						count2 = 17;
					}
				} else if (count >= 48 && count < 59) {
					count3--;
					round.createNewTile(new Point(Constants.TILE_BORDER
							+ Constants.HALF_TILE, count3
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 58) {
						count3 = 12;
					}
				} else if (count == 59) {
					count = 1;
					round.setBorder(0);
				}
			}
			if (round.getBorder() == 2 && frames % 10 == 0) {
				int id = 20000 + count;
				count++;
				if (count < 18) {
					round.createNewTile(new Point(count * Constants.TILE_BORDER
							+ Constants.HALF_TILE, 2 * Constants.TILE_BORDER
							+ Constants.HALF_TILE), id, "stone", false, false,
							true);
				} else if (count >= 18 && count < 28) {
					count1++;
					round.createNewTile(new Point(17 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count1
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 27) {
						count1 = 3;
					}
				} else if (count >= 28 && count < 43) {
					count2--;
					round.createNewTile(new Point(count2
							* Constants.TILE_BORDER + Constants.HALF_TILE, 12
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 42) {
						count2 = 16;
					}
				} else if (count >= 43 && count < 52) {
					count3--;
					round.createNewTile(new Point(2 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count3
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 51) {
						count3 = 11;
					}
				} else if (count == 52) {
					count = 2;
					round.setBorder(0);
				}
			}
			if (round.getBorder() == 3 && frames % 10 == 0) {
				int id = 30000 + count;
				count++;
				if (count < 17) {
					round.createNewTile(new Point(count * Constants.TILE_BORDER
							+ Constants.HALF_TILE, 3 * Constants.TILE_BORDER
							+ Constants.HALF_TILE), id, "stone", false, false,
							true);
				} else if (count >= 17 && count < 25) {
					count1++;
					round.createNewTile(new Point(16 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count1
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 24) {
						count1 = 4;
					}
				} else if (count >= 25 && count < 38) {
					count2--;
					round.createNewTile(new Point(count2
							* Constants.TILE_BORDER + Constants.HALF_TILE, 11
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 37) {
						count2 = 15;
					}
				} else if (count >= 38 && count < 45) {
					count3--;
					round.createNewTile(new Point(3 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count3
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 44) {
						count3 = 10;
					}
				} else if (count == 45) {
					count = 3;
					round.setBorder(0);
				}
			}
			if (round.getBorder() == 4 && frames % 10 == 0) {
				int id = 40000 + count;
				count++;
				if (count < 16) {
					round.createNewTile(new Point(count * Constants.TILE_BORDER
							+ Constants.HALF_TILE, 4 * Constants.TILE_BORDER
							+ Constants.HALF_TILE), id, "stone", false, false,
							true);
				} else if (count >= 16 && count < 22) {
					count1++;
					round.createNewTile(new Point(15 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count1
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 21) {
						count1 = 1;
					}
				} else if (count >= 22 && count < 33) {
					count2--;
					round.createNewTile(new Point(count2
							* Constants.TILE_BORDER + Constants.HALF_TILE,
							10 * 40 + Constants.HALF_TILE), id, "stone", false,
							false, true);
					if (count == 32) {
						count2 = 18;
					}
				} else if (count >= 33 && count < 38) {
					count3--;
					round.createNewTile(new Point(4 * Constants.TILE_BORDER
							+ Constants.HALF_TILE, count3
							* Constants.TILE_BORDER + Constants.HALF_TILE), id,
							"stone", false, false, true);
					if (count == 37) {
						count = 13;
					}
				} else if (count == 38) {
					count = 1;
					round.setBorder(0);
				}
			}
			if (frames == 62) {
				frames = 0;
			}
		}
	}

	/**
	 * The time will get 16 ms smaller every GameLoop update.
	 */
	public void tick() {
		this.time -= 16;
	}

	/**
	 * One Second has left, sending a message with the new time to the clients
	 */
	private void oneSecondLeft() {
		time -= 8;
		RoundTimeOneSecondLeftMsg msg = new RoundTimeOneSecondLeftMsg(time);
		round.getSession().broadcastMsg(msg);
	}

	/**
	 * Two minutes till the round ends, sending a message to all clients and
	 * make the first border with tiles.
	 */
	public void twoMinutesToEnd() {
		round.setBorder(1);
	}

	/**
	 * One and a half minute till the round ends, sending a message to all
	 * clients and make the second border with tiles.
	 */
	private void oneAndAHalfMinuteToEnd() {
		round.setBorder(2);
	}

	/**
	 * One minute till the round ends, sending a message to all clients and make
	 * the third border with tiles.
	 */
	public void oneMinuteToEnd() {
		round.setBorder(3);

	}

	/**
	 * A half minute till the round ends, sending a message to all clients and
	 * make the fourth border with tiles.
	 */
	private void halfMinuteToEnd() {
		round.setBorder(4);
	}

	/**
	 * The time of the round has expired, sending a message to all clients and
	 * "doPostRoundProcessing()"
	 */
	public void timeExpired() {
		RoundTimeOverMsg msg = new RoundTimeOverMsg();
		round.getSession().broadcastMsg(msg);
		stopped = true;
		round.doPostRoundProcessing();
	}
}
