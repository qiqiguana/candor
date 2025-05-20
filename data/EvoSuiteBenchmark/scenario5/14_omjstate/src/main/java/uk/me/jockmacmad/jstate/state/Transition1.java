package uk.me.jockmacmad.jstate.state;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * An event that takes place upon which a statemachine has a dependance.
 * Creation date: (2/14/01 6:27:09 PM)
 *
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 */
public class Event extends java.util.EventObject {

    /**
     * Method to compare two Event objects.
     *
     * Returns true if both Event objects are instances are identical,
     * otherwise returns false.
     * <p>
     * Uses the Apache Commons Lang
     *  <code>EqualsBuilder.reflectionEquals(this, pEvent);</code> function.
     *
     * @since 0.1
     * @return boolean
     * @param pEvent the Event to compare against
     * the State to compare against
     */
    @Override
    public final boolean equals(final Object pEvent);
}

/**
 * An interface that is used to mark an implementing class as
 * a predicate.
 *
 * Creation date: (2/20/01 12:38:18 PM)
 *
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 * @stereotype predicate
 */
public interface IPredicate {

    /**
     * All predicate implementors have to implement the evaluate method.
     * This takes in a java.lang.Object and evaluates the object against
     * the predicate.
     * Creation date: (2/20/01 12:53:25 PM)
     *
     * @return boolean
     * @param o java.lang.Object
     * @since 0.1
     */
    boolean evaluate(Object object);
}

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
 *
 * @author:
 */
public class Transition {

    /**
     * Check to evaluate if this Transition will trigger
     * given the supplied current State of the State Machine
     * and the supplied Event.
     * Creation date: (14/02/01 6:21:09 PM)
     *
     * @param pCurrentState current State of the State Machine
     * @param pEvent the supplied Event
     * @return com.objectmentors.state.State
     */
    public final boolean willTrigger(final IState pCurrentState, final Event pEvent) {
        boolean retVal = false;
        boolean passesGuards = false;
        if ((initialState.equals(pCurrentState)) && (triggerEvent.equals(pEvent))) {
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

/**
 * Interface representing a state that is contained within a state machine.
 * Creation date: (2/14/01 6:18:59 PM)
 *
 * @author:
 */
public interface IState {

    /**
     * Compares two states for type equality.
     * Creation date: (15/02/01 9:31:52 AM)
     *
     * @return boolean
     * @param pIState com.objectmentors.state.IState
     */
    @Override
    boolean equals(Object pIState);
}
