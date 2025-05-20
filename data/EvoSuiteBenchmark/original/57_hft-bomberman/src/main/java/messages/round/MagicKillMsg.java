/**
 * 
 */
package messages.round;

import common.GameObject;
import common.Player;

import client.ClientGameRound;

/**
 * @author andi
 *
 */
public class MagicKillMsg implements RoundServerMsg {

    private final int playerId;

    public MagicKillMsg(int playerId){
        this.playerId = playerId;
    }
    
    /* (non-Javadoc)
     * @see messages.round.RoundServerMsg#execute(client.ClientGameRound)
     */
    @Override
    public void execute(ClientGameRound round) {
        Player player = (Player) round.getGameObjectById(playerId);
        if(player != null){
            player.die();
        }
    }

}
