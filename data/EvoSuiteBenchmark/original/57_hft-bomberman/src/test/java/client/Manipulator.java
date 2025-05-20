/**
 * 
 */
package client;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.Bomb;
import common.Player;

/**
 * @author andi
 * 
 */
public class Manipulator extends JFrame {

	private Player[] players;

	/**
	 * @param player
	 * @throws HeadlessException
	 */
	public Manipulator(Player... players) throws HeadlessException {
		super("Cheat console");
		
		setLayout(new GridLayout(1,players.length));
		this.players = players;
		for (int i = 0; i < players.length; i++) {
			final int iCopy = i;
			JButton kill = new JButton("Kill #" + players[i].getId());
			kill.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Manipulator.this.players[iCopy].die();
				}
			});
			add(kill);
		}
		
		pack();
		setVisible(true);
	}

}
