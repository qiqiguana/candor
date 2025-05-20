/*
	This code is (c) Don Stewart 2001.

This file is part of OMJState.

	OMJState is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    OMJState is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with OMJState; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA */

package uk.me.jockmacmad.jstate.state;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class representing the state machine.
 * Creation date: (14/02/01 6:08:25 PM)
 * @author: Don Stewart
 */
public class StateMachine implements java.util.Observer, IStateMachine {
    /**
     * Apache Commons Logging - Log.
     */
    private Log log = LogFactory.getLog(StateMachine.class);
    /**
     * A <code>List&lt;Transition&gt;</code>of the
     * available transitions between states
     * supported by this State Machine.
     */
    private java.util.List < Transition > transitions;
    /**
     * The current State this State Machine is in.
     */
    private IState currentState = null;

    /**
     * Is this State Machine currently functional.
     */
    private boolean isFunctional = false;

    /**
     * Static String defining the <code>Exception</code>
     * message to output if our StateMAchine is not complete.
     */
    private static final String NOT_COMPLETE_MSG = 
        "Invalid transition List supplied. No transition from the start state to a user defined state supplied.";

    /**
     * We hide the default Constructor as the list of
     * Transitions must be specified at construction.
     */
    private StateMachine() { }
    /**
     * StateMachine constructor. This is packaged scope as it
     * should only be accessed from the State Machine Factory class.
     * @param transitions the <code>List&lt;Transition&gt;</code>of the
     * available transitions between states supported by this State Machine.
     * @throws StateMachineNotCompleteException this <code>Exception</code>
     * is thrown if there is no Transition from the <code>StartState</code>
     * to another State.
     * @throws NonDeterministicException not yet implemented
     */
    public StateMachine(final List < Transition > transitions)
    throws StateMachineNotCompleteException,
    NonDeterministicException {
        super();
        setCurrentState(StartState.getSingleton());
        // This needs to be replaced using a protected method
        // that validates the transitions
        // Quick just assign the transitions vector to the internal vector
        this.transitions = transitions;
        // We need to check that a Transition exists,
        //   which transitions the state machine from
        //   the start state to a user defined state.
        //   If there isn't we throw an exception as the
        //   state machine will be 'stuck' in its start state.
        for (Transition transition : transitions) {
            // For each transition get it's initial state
            IState s = transition.getInitialState();
            // Check to see if the transition initial state is the start state
            if ((StartState.getSingleton()).equals(s)) {
                // We found what we wanted
                isFunctional = true;
                // No need to keep looping
                break;
            }
        }
        // Finished the loop, if startState is still false
        //   we need to throw an exception
        if (!isFunctional) {
            if (log.isDebugEnabled()) {
                log.debug(
                        "Invalid transition List supplied."
                        + "No transition from the start state to"
                        + "a user defined state supplied."
                );
            }
            StateMachineNotCompleteException e =
                new StateMachineNotCompleteException(
                        "Invalid transition List supplied."
                        + "No transition from the start state to"
                        + "a user defined state supplied."
                );
            throw e;
        }
    }

    /**
     * Return the current State of this State Machine.
     * Creation date: (15/02/01 12:26:53 PM)
     * @return uk.me.jockmacmad.jstate.state.IState
     */
    public final IState getCurrentState() {
        return currentState;
    }

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    /**
     * @param observable
     * @param arg
     */
    public final void update(final java.util.Observable observable,
            final java.lang.Object arg) {
        IState oldState = null;
        // Look round the existing transitions supported by the state machine
        for (Transition transition : transitions) {
            oldState = getCurrentState();
            setCurrentState(transition.trigger(getCurrentState(), (Event) arg));
            if (!oldState.equals(getCurrentState()) && log.isDebugEnabled()) {
                log.debug(
                        "Transition from "
                        + oldState.toString()
                        + " to "
                        + getCurrentState().toString()
                );
            }
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (14/02/01 6:57:48 PM)
     * @param pNewTransition com.objectmentors.state.Transition
     * @exception uk.me.jockmacmad.jstate.state.NonDeterministicException
     * The exception description.
     */
    private void addTransition(final Transition pNewTransition)
    throws NonDeterministicException {
        if (transitions.size() > 0) {
            for (Transition transition : transitions) {
                // Check to see if the transitions already exists
                if (transition.equals(pNewTransition)) {
                    if (log.isDebugEnabled()) {
                        log.debug(
                                "Attempt to add a duplicate "
                                + "transition to the StateMachine."
                                + " Throwing a NonDeterministicException"
                        );
                    }
                    // It already exists, throw a nondeterministic exception
                    NonDeterministicException exception =
                        new NonDeterministicException();
                    throw exception;
                }
            }
        }
        // Transition does not already exist so add
        //   it to the Vector of supported transitions
        transitions.add(pNewTransition);
    }
    /**
     * @param currentState the currentState to set
     */
    final void setCurrentState(final IState currentState) {
        this.currentState = currentState;
    }
}