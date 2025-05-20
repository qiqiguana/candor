 package client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import messages.global.JoinSessionMsg;

import client.controller.LocalControl;
import client.gui.StartFrame;

import common.Map;
import common.Player;

public class TestJoiner {
	
	private static final int playerNr = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		GameWindow gameWindow = new GameWindow(false);
		// uncomment for fullscreen mode
//		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment(); // uncomment for fullscreen mode
//		GraphicsDevice defaultDevice = gEnv.getDefaultScreenDevice();
//		defaultDevice.setDisplayMode(new DisplayMode(800,600,32,60));
//		defaultDevice.setFullScreenWindow(gameWindow);
		BomberClient client = BomberClient.getInstance();
		client.connectToSrv();
		
//		List<String> maps = new ArrayList<String>();
//		maps.add("map.xml");
//		client.createSession("session", maps, playerNr, 2);
		client.sendMsg(new JoinSessionMsg("session", "dummy"));
		
//		StartFrame frame = StartFrame.init();
//		frame.getGa
		
//		ClientGameRound clientGame = new ClientGameRound(gameWindow.getGameCanvas());
//		clientGame.setMap(new Map("resources/map/map.xml"));
//		
//		Player clientPlayer = new ClientPlayer(new Point(50,50));
//		Player clientPlayer2 = new ClientPlayer(new Point(150,150));
//		
//		
//		LocalControl lControl = new LocalControl(clientPlayer);
//		clientPlayer.setId(1000000);
//		clientPlayer2.setId(2000000);
//		clientGame.setLocalControl(lControl);
//		gameWindow.addGameKeyListener(lControl);
//		clientGame.addPlayer(clientPlayer.getId(), clientPlayer);
//		clientGame.addPlayer(clientPlayer2.getId(), clientPlayer2);
//		System.out.println("Starting game");
//		Manipulator manipulator = new Manipulator(clientPlayer, clientPlayer2);
//		clientGame.start();

	}

}
