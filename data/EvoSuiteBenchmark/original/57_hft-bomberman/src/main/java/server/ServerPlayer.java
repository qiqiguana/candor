package server;

import java.awt.Point;
import java.util.Collection;

import common.GameObject;
import common.Player;

public class ServerPlayer extends Player {

	public ServerPlayer(Point position) {
		super(position);
	}
	
	@Override
	protected synchronized void move() {
		
	}
	
	@Override
	public synchronized void updateMovement(Point targetPosition) {
		position.setLocation(targetPosition);
	}
}
