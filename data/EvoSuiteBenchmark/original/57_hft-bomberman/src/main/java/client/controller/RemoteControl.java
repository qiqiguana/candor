/**
 * 
 */
package client.controller;

import java.awt.Point;

import messages.Message;
import messages.round.PlayerStateMsg;
import client.network.MsgProcessor;

import common.Player;

/**
 * TODO CHECK AND DELETE
 * @author Andi
 *
 */
public class RemoteControl implements MsgProcessor{

	private final Player player;
	
	
	
	/**
	 * @param gameObjectId
	 */
	public RemoteControl(Player player) {
		super();
		this.player = player;
	}



	/* (non-Javadoc)
	 * @see client.network.MsgProcessor#processMsg(messages.Message)
	 */
	@Override
	public void processMsg(Message msg) {
//		if(PlayerStateMsg.class.isInstance(msg)){
//			PlayerStateMsg statusMsg = (PlayerStateMsg) msg;
//			if(player.getId() == statusMsg.getGameObjectId()){				
//				player.updateMovement(statusMsg.getPosition(), statusMsg.getMoveVector());
//			}
//			
//		}
	}

	
	
}
