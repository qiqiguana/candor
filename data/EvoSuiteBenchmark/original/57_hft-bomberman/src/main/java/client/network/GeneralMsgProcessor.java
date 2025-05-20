package client.network;

import messages.Message;

/**
 * @deprecated
 * @author andi
 *
 */
public class GeneralMsgProcessor implements MsgProcessor {

	@Override
	public void processMsg(Message msg) {
		// TODO Auto-generated method stub
		
	}

//	private Player remotePlayer;
//
//	private int id;
//	
//	private BomberClient bomberClient;
//
//	private ClientGameRound clientGame;
//	
//	private static final Logger logger = Logger.getLogger(GeneralMsgProcessor.class);
//	
//	/**
//	 * @param bomberClient
//	 */
//	public GeneralMsgProcessor(BomberClient bomberClient) {
//		super();
//		this.bomberClient = bomberClient;
//	}
//
//
//
//
//	public void processMsg(Message msg) {
//		if (PlayerJoinedMsg.class.isInstance(msg)) {
//			PlayerJoinedMsg joinMsg = (PlayerJoinedMsg) msg;
//			id = joinMsg.getIdOffset();
//			System.out.println("Got id: " + id);
//			System.out.println("Waiting for other players");
//		}
//		
//		//TODO Preliminary "game starter", ugly as hell
//		if (InitializeRoundMsg.class.isInstance(msg)) {
//			InitializeRoundMsg startMsg = (InitializeRoundMsg) msg;
////			GameWindow gameWindow = new GameWindow(true); // uncomment for fullscreen mode
//			GameWindow gameWindow = new GameWindow(false); // comment for fullscreen mode
//			clientGame = new ClientGameRound(gameWindow.getGameCanvas());
//			clientGame.setMap(new Map("resources/map/map.xml"));
//			ForwardingObserver observer = new ForwardingObserver(bomberClient);
//			System.out.printf("Adding %d players\n", startMsg.getPlayers().size());
//			for (Player player : startMsg.getPlayers()) {
//				ClientPlayer clientPlayer = new ClientPlayer(player.getPosition());
//				int idOffset = player.getId();
//				clientPlayer.setId(idOffset);
//				BomberClient.setIdOffset(idOffset);
////				clientPlayer.move(player.getMoveVector());
//				if (!(player.getId() == id)) {
//					System.out.println("Adding remote control for player " + player.getId());
//					clientPlayer.setRemote(true);
//					RemoteControl remoteControl = new RemoteControl(clientPlayer);
//					bomberClient.addMsgProcessor(remoteControl);
//				}else{
//					System.out.println("Adding keyboard control for player " + player.getId());
//					LocalControl localControl = new LocalControl(clientPlayer);
//					clientGame.setLocalControl(localControl);
//					gameWindow.addGameKeyListener(localControl);
//					clientPlayer.addObserver(observer);
//				}
//				
//				clientGame.addPlayer(clientPlayer.getId(), clientPlayer);
//				
//			}
//			
//			// uncomment for fullscreen mode
////			GraphicsDevice g = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
////			g.setFullScreenWindow(gameWindow);
////			g.setDisplayMode(new DisplayMode(800,600,32,60));
//			
//			
//			System.out.println("Starting game");
//			clientGame.start();
//		} else if (PlayerStateMsg.class.isInstance(msg)) {
//			//
//		} else if(NewBombMsg.class.isInstance(msg)){
//			NewBombMsg bombMsg = (NewBombMsg) msg;
//			Point bombPos = new Point(bombMsg.getPosition()[0],bombMsg.getPosition()[1]);
//			Bomb bomb = new Bomb(bombPos, clientGame.getGameObjectById(bombMsg.getPlanterId()));
//			int id = bombMsg.getBombID();
//			bomb.setId(id);
//			clientGame.addBomb(id, bomb);
//		} else if (BombExplodedMsg.class.isInstance(msg)) {
//			BombExplodedMsg expl = (BombExplodedMsg) msg;
//			Bomb bomb = (Bomb) clientGame.getGameObjectById(expl.getBombID());
//			logger.info("Received BombExplodedMsg! Bomb id:" + expl.getBombID());
//			for (int id : expl.getPlayerHits()) {
//				Player player = (Player) clientGame.getGameObjectById(id);
//				player.die();
//			}
//			for (int id : expl.getTileHits()) {
//				Tile tile = (Tile) clientGame.getGameObjectById(id);
//				tile.destroy();
//			}
//			bomb.explode();
//		}
//
//	}
}
