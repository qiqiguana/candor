/**
 * 
 */
package messages.round;

import common.GameObject;
import common.GameSession;

import server.ClientInfo;
import server.ServerGameRound;
import server.ServerGameSession;

/**
 * @author andi
 *
 */
public class ClientQuitRunningSessionMsg implements RoundClientMsg {

    private final int playerId;

    /**
     * @param playerId
     */
    public ClientQuitRunningSessionMsg(int playerId) {
        this.playerId = playerId;
    }

    /* (non-Javadoc)
     * @see messages.round.RoundClientMsg#execute(server.ServerGameRound, server.ClientInfo)
     */
    @Override
    public void execute(ServerGameRound round, ClientInfo sender) {
        ServerGameSession session = sender.getGameSession();
        sender.leaveGameSession();
        GameObject gameObject = round.getGameObject(playerId);
        gameObject.setInactive();
        session.multicastMsg(new MagicKillMsg(playerId), sender);
        
        
    }

}
