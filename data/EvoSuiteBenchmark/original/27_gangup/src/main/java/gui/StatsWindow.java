/* $Id: StatsWindow.java,v 1.10 2004/05/05 20:42:41 njursten Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Rasmus Ahlberg
 * @version: $Revision: 1.10 $
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

import state.*;


public class StatsWindow extends JFrame implements ActionListener {
    private DefaultTableModel dtmdata;
    private GameState state;
    private int money;
    private String type;

    public StatsWindow(GameState state) {
	super("Endgame stats");
	this.state = state;

	createComponents();
	setBounds(100,100,400,400);
	setVisible(true);
    }

    private void createComponents() {
	Container c = getContentPane();
	c.setLayout(new BorderLayout());
	dtmdata = new DefaultTableModel
	    (new String[] {"ID", "Name", "IP", "Rank", "Money"}, 0);
	setupTable();

	JTable statsTable = new JTable(dtmdata);
	JScrollPane scrollpane = new JScrollPane(statsTable);
	c.add(scrollpane, BorderLayout.CENTER);

	JButton jbclose = new JButton("Close");
	jbclose.setActionCommand("close");
	jbclose.addActionListener(this);
	c.add(jbclose, BorderLayout.SOUTH);

	statsTable.setDefaultRenderer(Object.class, new ToolTipRenderer());
	statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("close")) {
	    this.dispose();
	}
    }

    private void setupTable() {
	Player[] players = new Player[GameState.MAX_PLAYER_LIMIT];
	for (int i=0; i<players.length; i++) {
	    players[i] = state.player(i);
	}

	java.util.Arrays.sort(players, new PlayerMoneyComparator());

	int[] rank = new int[players.length];
	int i = 0;
	float prevmoney = -1;
	int currcount = 0;
	while (players[i] != null && i < players.length) {
	    if (players[i].getMoney() != prevmoney) {
		currcount = i+1;
		prevmoney = players[i].getMoney();
	    }
	    rank[i] = currcount;
	    i++;
	}

	i = 0;
	while (players[i] != null && i < players.length) {
	    Player player = players[i];

	    dtmdata.addRow(new Object[] 
		{ new Integer(player.getId()),
		  player.getName(),
		  player.getIP(),
		  rank[i],
		  player.getMoney(),
		  0 } );
	    i++;
	}
    }

    /*
     * Class for comparing players' earned money.
     */
    private class PlayerMoneyComparator implements Comparator<Player> {
	public int compare(Player a, Player b) {
	    if (b == null)
		return -1;
	    if (a == null)
		return 1;

	    return (int) (b.getMoney() - a.getMoney());
	}

	public boolean equals(Object o) {
	    return this == o;
	}
    }
}
