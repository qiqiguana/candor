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
 *
 * @author:
 */
public class Transition {

    public final IState trigger(final IState pCurrentState, final Event pEvent) {
        IState retVal = pCurrentState;
        boolean passesGuards = false;
        if ((initialState.equals(pCurrentState)) && (triggerEvent.equals(pEvent))) {
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
}
