package messages.global;

import java.util.List;

import javax.swing.JOptionPane;

import client.BomberClient;
import client.gui.StartFrame;

/**
 * This message is sent to a client in response to a JoinSessionMsg. It indicates to the
 * receiver whether or not its try to join the desired session was successful.
 * It also includes the clients idOffset in case the session was successfully
 * joined.
 * 
 * @author Steffen, Andi
 * @see JoinSessionMsg
 *
 */
public class JoinAckMsg implements GlobalServerMsg {
	
	private boolean joinSuccessful;
	private int idOffset;
	private List<ParticipantInfo> participants;

	public JoinAckMsg(boolean joinSuccessful, int idOffset) {
		super();
		this.joinSuccessful = joinSuccessful;
		this.idOffset = idOffset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
		if(joinSuccessful){
			BomberClient.getInstance().setIdOffset(idOffset);
			BomberClient.getInstance().openSession();
			
			// add the player itself to the session
			bomberClient.getCurrentSession().join(idOffset, bomberClient.getPlayerName());
			
			// add all other players that already were in the session
			for (ParticipantInfo participant : participants) {
				bomberClient.getCurrentSession().join(participant.getId(), participant.getName());
			}
			StartFrame.getInstance().showSessionLobby();
			//System.out.println("Assigned id: " + idOffset);
		}else{
			JOptionPane.showMessageDialog(StartFrame.getInstance(), "Sorry. Session already full!", "Error", JOptionPane.ERROR_MESSAGE);
			StartFrame.getInstance().showSessionLobby();
		}
		
	}

	public void setParticipants(List<ParticipantInfo> participants) {
		this.participants = participants;
	}

}
