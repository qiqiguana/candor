/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package visu.handball.moves.model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.model.animation.AnimationModel;
import visu.handball.moves.model.animation.Animator;
import visu.handball.moves.model.player.Ball;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.HighlightableItem;
import visu.handball.moves.model.player.MovePoint;
import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

public class HandballModel implements Serializable {

	private static final long serialVersionUID = -5372891552466311536L;

	public enum State {
		INITIAL, PLACE_OFFENDERS, PLACE_DEFENDERS, PLACE_BALL, EDIT, EDIT_EVENT, ANIMATION, ANIMATION_RUNNING, FULL_ANIMATION_ENDED
	}

	private static final int MAX_OFFENDERS = 6;

	private static final int MAX_DEFENDERS = 6;

	private static final int MARK_RADIUS = 12;

	private static final String APP_NAME = "jHandballMoves";

	private transient List<HandballModelListener> listeners;

	private transient List<PlayerRemovedListener> playerRemoveListener;

	private List<Defender> defenders;

	private List<Offender> offenders;

	private SortedSet<MoveEvent> events;

	private State state;

	private MoveEvent actualEvent;

	private Player markedPlayer;

	private HighlightableItem hightlightedItem;

	private Offender ballOwner;

	private Ball ball;

	private Offender firstBallOwner;

	private transient Animator runningAnimator;

	private int animationSequenz;

	private transient AnimationModel animationModel;

	private transient HandballModel lastSavedModel;

	private String comment;

	private String moveName;

	public HandballModel() {
		listeners = new ArrayList<HandballModelListener>();
		playerRemoveListener = new ArrayList<PlayerRemovedListener>();
		initModel();
	}

	public void initModel() {
		defenders = new ArrayList<Defender>();
		Defender.setCounter(0);
		offenders = new ArrayList<Offender>();
		Offender.setCounter(0);

		events = new TreeSet<MoveEvent>();
		// Workaround wegen ToolBar
		state = State.PLACE_OFFENDERS;
		actualEvent = null;
		markedPlayer = null;
		firstBallOwner = null;
		comment = "";
		moveName = "";
		resetBallPosition();
		try {
			lastSavedModel = (HandballModel) Main.deepCopy(this);
		} catch (Exception e) {
			lastSavedModel = null;
		}
		fireModelChanged();
	}

	private void resetBallPosition() {
		ball = new Ball(-500, -500);
	}

	public void addOffender(int x, int y) {
		if (offenders.size() == MAX_OFFENDERS) {
			JOptionPane.showMessageDialog(Main.getWindow(), "Sie haben schon "
					+ MAX_OFFENDERS + " Angreifer angelegt.", "Info:",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			offenders.add(new Offender(x, y));
		}
		fireModelChanged();

	}

	public void removePlayer(Player toRemove) {
		if (eventDefinedWithPlayer(toRemove)) {
			removeEventsOfPlayer(toRemove);
		}
		if (toRemove instanceof Offender) {
			removeOffender((Offender) toRemove);
		} else {
			removeDefender((Defender) toRemove);
		}
	}

	private void removeOffender(Offender toRemove) {
		if (getFirstBallOwner() != null && getFirstBallOwner().equals(toRemove)) {
			setFirstBallOwner(null);
		}
		offenders.remove(toRemove);
		if ((getDefenders().size() + getOffenders().size()) == 0) {
			markedPlayer = null;
		}
		// Spielernummer aktualisieren
		Offender.setCounter(determineMaxPlayerNumber(offenders));
		fireOffenderRemoved(toRemove);
		fireModelChanged();
	}

	public void addDefender(int x, int y) {
		if (defenders.size() == MAX_DEFENDERS) {
			JOptionPane.showMessageDialog(Main.getWindow(), "Sie haben schon "
					+ MAX_OFFENDERS + " Verteidiger angelegt.", "Info:",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			defenders.add(new Defender(x, y));
		}

		fireModelChanged();
	}

	private void removeDefender(Defender toRemove) {
		defenders.remove(toRemove);
		if ((getDefenders().size() + getOffenders().size()) == 0) {
			markedPlayer = null;
		}

		Defender.setCounter(determineMaxPlayerNumber(defenders));

		fireDefenderRemoved(toRemove);
		fireModelChanged();
	}

	public boolean addListener(HandballModelListener o) {
		return listeners.add(o);
	}

	public boolean removeListener(HandballModelListener o) {
		return listeners.remove(o);
	}

	public boolean addPlayerRemovedListener(PlayerRemovedListener o) {
		return playerRemoveListener.add(o);
	}

	public boolean removePlayerRemovedListener(PlayerRemovedListener o) {
		return playerRemoveListener.remove(o);
	}

	private void fireModelChanged() {
		setMoveName(getMoveName());
		for (HandballModelListener listener : listeners) {
			listener.modelChanged();
		}
	}

	private void fireOffenderRemoved(Offender offender) {
		for (PlayerRemovedListener listener : playerRemoveListener) {
			listener.offenderRemoved(offender);
		}
	}

	private void fireDefenderRemoved(Defender defender) {
		for (PlayerRemovedListener listener : playerRemoveListener) {
			listener.defenderRemoved(defender);
		}
	}

	public List<Defender> getDefenders() {
		return defenders;
	}

	public List<Offender> getOffenders() {
		return offenders;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {

		if (state == State.EDIT) {
			if (getActualMoveEvent() == null) {
				// Wenn leer, dann Null, sonst letztes Move Event setzen
				setActualMoveEvent(getEvents().isEmpty() ? null : (getEvents()
						.get(getEvents().size() - 1)));
			} else {
				setActualMoveEvent(getActualMoveEvent());
			}
		}
		if (state == State.PLACE_DEFENDERS || state == State.PLACE_OFFENDERS) {
			if (getFirstBallOwner() != null) {
				setBallOwner(getFirstBallOwner());
			}
			setActualMoveEvent(null);
		}
		this.state = state;
		fireModelChanged();
	}

	public void addMoveEvent(MoveEvent event) {
		events.add(event);
		if (state == State.EDIT) {
			setActualMoveEvent(event);
			state = State.EDIT_EVENT;
		}
		fireModelChanged();
	}

	public void removeMoveEvent(MoveEvent event) {
		events.remove(event);
		fireModelChanged();
	}

	public List<MoveEvent> getEvents() {
		List<MoveEvent> list = new ArrayList<MoveEvent>(events);
		return list;
	}

	public SortedSet<MoveEvent> getSortedEvents() {
		return events;
	}

	public int getAcutalSequenceNr() {
		if (actualEvent == null)
			return 0;
		else {
			return actualEvent.getSequenceNr();
		}
	}

	public void setActualMoveEvent(MoveEvent event) {
		if (this.actualEvent != null) {
			this.actualEvent.setMarked(false);
		}
		this.actualEvent = event;
		computePlayerPositions();
		// aktuellen Spieler setzen
		setMarkedPlayer((event == null) ? null : event.getPlayer());
		if (actualEvent != null) {
			if (!actualEvent.isDestinationPointSet()) {
				state = State.EDIT_EVENT;
			} else {
				if (state == State.FULL_ANIMATION_ENDED) {
					state = State.ANIMATION;
				} else if (state != State.ANIMATION
						&& state != State.ANIMATION_RUNNING) {
					state = State.EDIT;
				}
			}
			actualEvent.setMarked(true);
		}
		fireModelChanged();
	}

	public void setDestinationPointForEvent(MoveEvent event, int x, int y,
			boolean temp) {
		event.setDestinationPoint(x, y, temp);
		fireModelChanged();
	}

	public void setControlPointForEvent(MoveEvent event, int x, int y,
			boolean temp) {
		event.setControlPoint(x, y, temp);
		fireModelChanged();
	}

	public void setMovePointTo(MovePoint point, int x, int y) {
		actualEvent.setPoint(point, x, y);
		fireModelChanged();
	}

	private void computePlayerPositions() {
		computePlayerPositions(getAcutalSequenceNr());
	}

	public void computePlayerPositions(int seqNr) {
		// Spieler Positionen auf Ausgangsstellung
		for (Player offender : offenders) {
			offender.resetPosition();
		}
		for (Player defender : defenders) {
			defender.resetPosition();
		}
		if (seqNr == 1) {
			setBallOwner(firstBallOwner);
		}
		// alle Events mit kleinerer Sequenznummer verarbeiten
		for (MoveEvent event : events) {
			if (event.getSequenceNr() < seqNr) {
				if (event instanceof PassEvent) {
					// Ball an anderen Spieler �bertragen
					setBallOwner(((PassEvent) event).getDestinationPlayer());
				} else {
					if (event.getDestinationX() > -1
							&& event.getDestinationY() > -1) {
						event.getPlayer().setCurrentPosition(
								event.getDestinationX(),
								event.getDestinationY());
					}
				}
			} else {
				break;
			}
		}
	}

	public MoveEvent getActualMoveEvent() {
		return actualEvent;
	}

	public Player getMarkedPlayer() {
		return markedPlayer;
	}

	public void setMarkedPlayer(Player markedPlayer) {
		// alten markierten Spieler demarkieren
		if (this.markedPlayer != null) {
			this.markedPlayer.setMarked(false);
		}
		// evtl. neuen Spieler markieren
		this.markedPlayer = markedPlayer;
		if (markedPlayer != null) {
			markedPlayer.setMarked(true);
		}
		fireModelChanged();
	}

	public void setHighLightedItem(HighlightableItem item) {
		if (this.hightlightedItem != null) {
			// altes Item zur�cksetzen
			this.hightlightedItem.setHighlight(false);
		}
		this.hightlightedItem = item;
		if (item != null) {
			hightlightedItem.setHighlight(true);
		}

		fireModelChanged();
	}

	public HighlightableItem getHightligtedItem() {
		return hightlightedItem;
	}

	public Player getNearestPlayer(int x, int y) {
		List<Player> all = new ArrayList<Player>(offenders);
		all.addAll(defenders);

		return findNearestPlayer(x, y, all);
	}

	public Offender getNearestOffender(int x, int y) {
		List<Player> all = new ArrayList<Player>(offenders);

		return (Offender) findNearestPlayer(x, y, all);
	}

	private Player findNearestPlayer(int x, int y, List<Player> players) {
		double entfernung = Double.MAX_VALUE;
		Player temp = null;
		for (Player player : players) {

			int player_x = player.getCurrent_x();
			int player_y = player.getCurrent_y();

			double tempEntfernung = Point2D.distance(x, y, player_x, player_y);

			// Wenn naeher als der MARK_RADIUS
			if (!(tempEntfernung > MARK_RADIUS)) {

				if (tempEntfernung < entfernung) {
					temp = player;
					entfernung = tempEntfernung;
				}
			}

		}
		return temp;
	}

	public MovePoint getNearestMovePoint(int x, int y) {
		MovePoint point = null;
		if (actualEvent != null) {
			double entfernung = Double.MAX_VALUE;

			if (actualEvent.isDestinationPointSet()) {
				int destination_x = actualEvent.getDestinationX();
				int destination_y = actualEvent.getDestinationY();

				double tempEntfernung = Point2D.distance(x, y, destination_x,
						destination_y);
				// Wenn naeher als der MARK_RADIUS
				if (!(tempEntfernung > MARK_RADIUS)) {

					if (tempEntfernung < entfernung) {
						point = actualEvent.getDestinationPoint();
						entfernung = tempEntfernung;
					}
				}
			}

			if (actualEvent.isControlPointSet()) {
				int controlPoint_x = actualEvent.getControlPointX();
				int controlPoint_y = actualEvent.getControlPointY();

				double tempEntfernung = Point2D.distance(x, y, controlPoint_x,
						controlPoint_y);

				// Wenn naeher als der MARK_RADIUS
				if (!(tempEntfernung > MARK_RADIUS)) {

					if (tempEntfernung < entfernung) {
						point = actualEvent.getControlPoint();
						entfernung = tempEntfernung;
					}
				}
			}
		}
		return point;
	}

	public HighlightableItem getNearestHighlightableItem(int x, int y) {
		HighlightableItem temp = null;

		temp = getNearestMovePoint(x, y);

		if (temp == null) {
			temp = getNearestPlayer(x, y);
		}

		return temp;
	}

	public void movePlayerTo(Player actualPlayer, int x, int y) {
		if (getAcutalSequenceNr() == 0) {
			actualPlayer.setStart_x(x);
			actualPlayer.setStart_y(y);
		}
		// Ansonsten muss die Position im entsprechenden Ereignis gesetzt
		// werden
		// Startposition darf nur im Angreifer/Verteidiger-Modus ge�ndert werden
		fireModelChanged();
	}

	public boolean isSaved() {
		boolean changed = false;
		if (lastSavedModel != null) {
			//Spielzugnamen vergleichen
			if (lastSavedModel.getMoveName() != null) {
				if (getMoveName() == null || !lastSavedModel.getMoveName().equals(getMoveName())) {
					return false;
				} 
			} else if (getMoveName() != null) {
				//alt: kein Name <-> neu: Name vorhanden
				return false;
			}
			
			//Ueberprüfen ob Angreifer und Verteidiger unterschiedlich
			
			// zuerst Spielerlisten vergleichen
			if (changedPlayerList(getOffenders().toArray(
					new Player[getOffenders().size()]), lastSavedModel
					.getOffenders().toArray(
							new Player[lastSavedModel.getOffenders().size()]))
					|| changedPlayerList(getDefenders().toArray(
							new Player[getDefenders().size()]), lastSavedModel
							.getDefenders().toArray(
									new Player[lastSavedModel.getDefenders()
											.size()]))) {
				changed = true;
			} else {
				List<MoveEvent> newEvents = getEvents();
				List<MoveEvent> oldEvents = lastSavedModel.getEvents();

				if (newEvents.size() == oldEvents.size()) {
					for (int i = 0; i < newEvents.size(); i++) {
						if (!newEvents.get(i).equals(oldEvents.get(i))) {
							changed = true;
							break;
						}

					}
				} else {
					changed = true;
				}
			}
			if (!changed
					&& (getFirstBallOwner() != null || lastSavedModel
							.getFirstBallOwner() != null)) {
				changed = (getFirstBallOwner() == null)
						^ (lastSavedModel.getFirstBallOwner() == null);
				if (!changed) {
					changed = !getFirstBallOwner().equals(
							lastSavedModel.getFirstBallOwner());
				}
			}

			// wurde Kommentar geändert?
			if (!changed) {
				if (comment == null || comment.equals("")) {
					if (lastSavedModel.getComment() != null
							&& !lastSavedModel.getComment().equals("")) {
						changed = true;
					}
				} else if (lastSavedModel.getComment() != null) {
					if (!lastSavedModel.getComment().equals(comment)) {
						changed = true;
					}
				}
			}
		}

		return !changed;
	}

	private boolean changedPlayerList(Player[] newList, Player[] oldList) {
		boolean changed = false;
		int size = newList.length;
		int sizeOld = oldList.length;

		if (size == sizeOld) {
			for (int i = 0; i < size; i++) {
				if (!newList[i].equals(oldList[i])) {
					changed = true;
					break;
				}
			}
		} else {
			changed = true;
		}

		return changed;
	}

	public void initWithLoadedModel(HandballModel loadedModel) {
		initModel();
		offenders = loadedModel.getOffenders();
		defenders = loadedModel.getDefenders();
		events = loadedModel.getSortedEvents();
		Offender.setCounter(determineMaxPlayerNumber(offenders));
		Defender.setCounter(determineMaxPlayerNumber(defenders));
		setFirstBallOwner(loadedModel.getFirstBallOwner());
		setState(loadedModel.getState());
		setActualMoveEvent(loadedModel.getActualMoveEvent());

		setComment(loadedModel.getComment());
		// zur Sicherheit
		if (getComment() == null) {
			setComment("");
		}
		setMoveName(loadedModel.getMoveName());

		markAsSaved(loadedModel);

		fireModelChanged();
	}

	private int determineMaxPlayerNumber(List players) {
		int max = 0;
		for (Object o : players) {
			if (o instanceof Player) {
				Player player = (Player) o;
				if (player.getPlayerNumber() > max) {
					max = player.getPlayerNumber();
				}
			}
		}
		return max;
	}

	public void markAsSaved(HandballModel model) {
		try {
			lastSavedModel = (HandballModel) Main.deepCopy(model);
		} catch (Exception e) {
			e.printStackTrace();
			lastSavedModel = null;
		}
	}

	public void setDelay(int eventIndex, int delay) {
		getEvents().get(eventIndex).setDelay(delay);
		fireModelChanged();
	}

	public void setBallOwner(Offender ballOwner) {
		if (this.ballOwner != null) {
			this.ballOwner.setHasBall(false);
		}
		this.ballOwner = ballOwner;
		if (ballOwner != null) {
			ballOwner.setHasBall(true);
		}
		fireModelChanged();
	}

	public void setBallOwnerSilent(Offender ballOwner) {
		if (this.ballOwner != null) {
			this.ballOwner.setHasBall(false);
		}
		this.ballOwner = ballOwner;
		if (ballOwner != null) {
			ballOwner.setHasBall(true);
		}
		// fireModelChanged();
	}

	public void startAnimation(boolean onlyActualSequence) {
		setState(State.ANIMATION_RUNNING);
		MoveEvent event = actualEvent;
		if (event == null) {
			event = getEvents().get(0);
		}
		setActualMoveEvent(event);
		animationSequenz = event.getSequenceNr();
		animationModel = new AnimationModel(event, this, onlyActualSequence);
		runningAnimator = new Animator(this);
		computePlayerPositions(animationSequenz);
		fireModelChanged();

	}

	public void pauseAnimation(boolean pause) {
		if (pause) {
			runningAnimator.setPause(true);
		} else {
			runningAnimator.setPause(false);
		}
	}

	public void initAnimation() {

	}

	public boolean nextAnimationStep() {
		boolean finished = animationModel.nextStep();
		if (finished) {
			if (animationModel.isOnlyOneSequence()) {
				setState(State.ANIMATION);
				// n�chste Sequzenz ausw�hlen (einfaches sequentielles Anschauen des Spielzugs)
				int nextSeq = (getAcutalSequenceNr() == getHighestSequenceNumber()) ? 
						getAcutalSequenceNr(): getAcutalSequenceNr() + 1;
				List<MoveEvent> nextSeqEvents = getMoveEvents(nextSeq);
				if (nextSeqEvents.get(0) != null) {
					setActualMoveEvent(nextSeqEvents.get(0));
				}
			} else {
				setState(State.FULL_ANIMATION_ENDED);
			}
		} else {
			fireModelChanged();
		}
		return finished;
	}

	public void stopAnimation() {
		setState(State.ANIMATION);
		runningAnimator = null;
		setActualMoveEvent(getActualMoveEvent());

	}

	public boolean isMarkedPlayerAddable() {
		int seq = getAcutalSequenceNr();
		return !events.contains(new MoveEvent(markedPlayer, seq));
	}

	public boolean passEventDefined(int seq) {
		boolean defined = false;
		for (MoveEvent event : events) {
			if (event.getSequenceNr() == seq && event instanceof PassEvent) {
				defined = true;
				break;
			}
			if (event.getSequenceNr() > seq) {
				break;
			}
		}
		return defined;
	}

	public boolean passEventDefined() {
		return passEventDefined(getAcutalSequenceNr());
	}

	public boolean passEventDefinedAfter(int seq) {
		boolean defined = false;
		for (MoveEvent event : events) {
			if ((event instanceof PassEvent) && (event.getSequenceNr() > seq)) {
				defined = true;
				break;
			}
		}
		return defined;
	}

	public boolean eventDefinedAfter(int seq) {
		boolean defined = false;
		for (MoveEvent event : events) {
			if (event.getSequenceNr() > seq) {
				defined = true;
				break;
			}
		}
		return defined;
	}

	public Ball getBall() {
		return ball;
	}

	public void setFirstBallOwner(Offender offender) {
		this.firstBallOwner = offender;
		setBallOwner(offender);

		if (firstBallOwner == null) {
			resetBallPosition();
		}
	}

	public Offender getFirstBallOwner() {
		return firstBallOwner;
	}

	public boolean hasPlayerBallInNextSequence(Player player) {
		PassEvent pass = null;
		int seq = getAcutalSequenceNr();
		for (MoveEvent event : events) {
			if (event.getSequenceNr() == seq && event instanceof PassEvent) {
				pass = (PassEvent) event;
				break;
			}
			if (event.getSequenceNr() > seq) {
				break;
			}
		}
		if (pass != null && pass.getDestinationPlayer() != null) {
			return pass.getDestinationPlayer().equals(player)
					&& !passEventDefined(seq + 1);
		} else {
			return false;
		}
	}

	public boolean isBallSet() {
		return ballOwner != null;
	}

	public boolean isGoalPassSet() {
		boolean defined = false;
		for (MoveEvent event : getEvents()) {
			if (event instanceof PassEvent) {
				PassEvent pass = (PassEvent) event;
				if (pass.isGoalPass()) {
					defined = true;
					break;
				}
			}
		}
		return defined;
	}

	public void moveBallTo(int x, int y) {
		ball.setCurrent_x(x);
		ball.setCurrent_y(y);
	}

	public List<MoveEvent> getActualMoveEvents() {
		return getMoveEvents(getAcutalSequenceNr());
	}

	public List<MoveEvent> getMoveEvents(int sequenceNr) {
		List<MoveEvent> sequenceEvents = new ArrayList<MoveEvent>();
		for (MoveEvent event : events) {
			if (event.getSequenceNr() == sequenceNr) {
				sequenceEvents.add(event);
			} else if (event.getSequenceNr() > getAcutalSequenceNr()) {
				break;
			}
		}

		return sequenceEvents;
	}

	/**
	 * Löscht das Aktuelle Ereignis und ALLE Folgenden
	 */
	public void removeEventsAfter() {
		MoveEvent event = getActualMoveEvent();
		List<MoveEvent> toDeleteEvents = new ArrayList<MoveEvent>();

		int sequenceNr = event.getSequenceNr();
		// zu loeschende Events sammeln
		for (MoveEvent evt : getEvents()) {
			if (evt.getSequenceNr() > sequenceNr) {
				// Event ist hoeher, also loeschen
				toDeleteEvents.add(evt);
			}
		}

		toDeleteEvents.add(event);
		// gesammelte Events loeschen
		for (MoveEvent deleteEvent : toDeleteEvents) {
			removeMoveEvent(deleteEvent);
		}

		setActualMoveEvent((getEvents().size() > 0) ? getEvents().get(
				getEvents().size() - 1) : null);
		fireModelChanged();
	}

	/**
	 * Löscht die Ereignisse (auch P�sse) des �bergebenen Spielers. Und die
	 * evtl. davon abh�ngigen Folgep�sse
	 * 
	 * @param player
	 */
	private void removeEventsOfPlayer(Player player) {
		List<MoveEvent> toDeleteEvents = new ArrayList<MoveEvent>();
		boolean playerInvolved = false;

		// zu loeschende Events sammeln
		for (MoveEvent evt : getEvents()) {

			// Pass?
			if (evt instanceof PassEvent) {

				PassEvent pass = (PassEvent) evt;
				// War ein Pass zu dem Spieler? oder Spieler passt selbst?
				if (pass.getDestinationPlayer().equals(player)
						|| pass.getPlayer().equals(player)) {
					playerInvolved = true;
				}

				// War Spieler an einem Pass beteiligt?
				if (playerInvolved) {
					toDeleteEvents.add(pass);
					continue;
				}
			}
			if (evt.getPlayer().equals(player)) {
				toDeleteEvents.add(evt);
			}

		}
		// gesammelte Events loeschen
		for (MoveEvent deleteEvent : toDeleteEvents) {
			removeMoveEvent(deleteEvent);
		}

		setActualMoveEvent((getEvents().size() > 0) ? getEvents().get(
				getEvents().size() - 1) : null);

	}

	public boolean eventDefinedWithPlayer(Player player) {
		boolean defined = false;
		for (MoveEvent event : events) {

			if (event.getPlayer().equals(player)) {
				defined = true;
				break;
			}
			if (event instanceof PassEvent) {
				PassEvent pass = (PassEvent) event;
				if (pass.getDestinationPlayer().equals(player)) {
					defined = true;
					break;
				}
			}
		}
		return defined;
	}

	public int getHighestSequenceNumber() {
		if (events.size() == 0) {
			return 0;
		} else {
			return events.last().getSequenceNr();
		}
	}

	public String getMoveName() {
		return moveName;
	}

	public void setMoveName(String moveName) {
		this.moveName = moveName;
		if (Main.getWindow() != null) {
			StringBuffer buffer = new StringBuffer(APP_NAME);
			buffer.append(" - ");
			if (moveName == null || moveName.equals("")) {
				buffer.append("Unbenannt");
			// Titelleiste aktualisieren
			} else {
				buffer.append(moveName);
			}
			if (!isSaved()) {
				buffer.append("*");  
			}
			Main.getWindow().setTitle(buffer.toString());
		}
	}

	public String getComment() {
		if (comment == null) {
			comment = "";
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
		fireModelChanged();
	}

}
