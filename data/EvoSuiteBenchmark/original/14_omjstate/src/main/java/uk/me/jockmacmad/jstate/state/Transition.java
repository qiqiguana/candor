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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * A transition that is evaluated upon receiving an event.
 * <p>
 *   Given a start state and an event the transition
 *     firstly evaluates if it applies by checking
 *     the start state and and event match its own.
 *     Once it ascertains it is for the start state/event
 *     combination, then the transition evaluates any
 *     guard conditions it has associated to it.
 *     If all the guard conditions evaluate to true,
 *     then the transition executes and returns the
 *     new state that the state machine is in.
 * Creation date: (14/02/01 6:14:35 PM)
 * @author:
 */
public class Transition {
    /**
     * The initial state that the state machine must
     * be in for this transition to apply.
     */
    private final IState initialState;
    /**
     * The outcome state that the state machine will be
     *   in if this transition applies.
     */
    private final IState outcomeState;
    /**
     * List of guard conditions that must be
     * evaluated and return true before the transition
     * can execute.
     */
    private final java.util.List<IGuardCondition> guardConditions =
        new java.util.ArrayList<IGuardCondition>();
    /**
     * The event upon which this transition takes place.
     */
    private final Event triggerEvent;
    /**
     * The name of the transition.
     */
    private final java.lang.String name;

    /**
     * Transition constructor comment.
     *
     * @param pName the name of this Transition
     * @param pTriggerEvent the Event that causes
     * this Transition
     * @param pInitialState the State which we must
     * be in to initiate this Transition
     * @param pOutcomeState the State we will be
     * in once we have performed this Transition
     */
    public Transition(final java.lang.String pName,
                      final Event pTriggerEvent,
                      final IState pInitialState,
                      final IState pOutcomeState) {
        super();
        name = pName;
        initialState = pInitialState;
        outcomeState = pOutcomeState;
        triggerEvent = pTriggerEvent;
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 2:57:02 PM)
     * @param pGuard com.objectmentors.state.IGuardCondition
     */
    public final void addGuardCondition(
            final uk.me.jockmacmad.jstate.predicate.IPredicate pGuard
            ) {
        guardConditions.add((IGuardCondition) pGuard);
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 2:57:02 PM)
     * @param pGuard com.objectmentors.state.IGuardCondition
     */
    public final void addGuardCondition(final IGuardCondition pGuard) {
        guardConditions.add(pGuard);
    }

    /**
     * Method to compare two Transition objects.
     *
     * Returns true if both Transition objects are identical,
     * otherwise returns false.
     * <p>
     * Uses the Apache Commons Lang
     *  <code>EqualsBuilder.reflectionEquals(this, pIState);</code> function.
     * @since 0.1
     * @return boolean
     * @param pTransition the Transition to compare against
     * the State to compare against
     */
    @Override
    public final boolean equals(final Object pTransition) {
        return EqualsBuilder.reflectionEquals(this, pTransition);
    }

    /**
     *  Builds the <code>hashCode</code> of this <code>Object</code>
     *  using the Apache Commons Lang
     *  <code>HashCodeBuilder.reflectionHashCode(this);</code> function.
     * @return int the HashCode of this <code>Object</code>
     */
    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/14/01 6:19:57 PM)
     */
    protected void evaluateGuardConditions() {
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 9:34:41 AM)
     * @return com.objectmentors.state.IState
     */
    public final IState getInitialState() {
        return initialState;
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 9:34:41 AM)
     * @return com.objectmentors.state.IState
     */
    public final IState getOutcomeState() {
        return outcomeState;
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 9:34:41 AM)
     * @return com.objectmentors.state.Event
     */
    public final Event getTriggerEvent() {
        return triggerEvent;
    }

    /**
     * Insert the method's description here.
     * Creation date: (15/02/01 2:14:58 PM)
     * @return java.lang.String
     */
    @Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Causes this Transition to fire.
     * Creation date: (14/02/01 6:21:09 PM)
     * @author Don Stewart
     * @since 0.1
     * @param pCurrentState the current State
     * @param pEvent the Event to trigger
     * @return com.objectmentors.state.State
     */
    public final IState trigger(
            final IState pCurrentState,
            final Event pEvent) {
        IState retVal = pCurrentState;
        boolean passesGuards = false;
        if ((initialState.equals(pCurrentState))
                && (triggerEvent.equals(pEvent))) {
            // Check if there are any guard conditions
            if (guardConditions.isEmpty()) {
                // No guards so just trigger
                passesGuards = true;
            } else {
                for (IGuardCondition ig : guardConditions) {
                    passesGuards = ig.evaluate(pEvent);
                }
            }
            if (passesGuards) {
                retVal = outcomeState;
            }
        }
        return retVal;
    }

    /**
     * Check to evaluate if this Transition will trigger
     * given the supplied current State of the State Machine
     * and the supplied Event.
     * Creation date: (14/02/01 6:21:09 PM)
     * @param pCurrentState current State of the State Machine
     * @param pEvent the supplied Event
     * @return com.objectmentors.state.State
     */
    public final boolean willTrigger(
            final IState pCurrentState,
            final Event pEvent) {
        boolean retVal = false;
        boolean passesGuards = false;
        if ((initialState.equals(pCurrentState))
                && (triggerEvent.equals(pEvent))) {
            for (IGuardCondition ig : guardConditions) {
                passesGuards = ig.evaluate(pEvent);
            }
            if (passesGuards) {
                retVal = true;
            }
        }
        return retVal;
    }
}