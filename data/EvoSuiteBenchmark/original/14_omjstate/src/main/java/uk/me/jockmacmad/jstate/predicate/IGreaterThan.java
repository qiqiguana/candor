package uk.me.jockmacmad.jstate.predicate;

/**
 * A predicate that evaluates the supplied object and tests
 * to see if it is greater than the predicate.
 * Creation date: (2/20/01 12:46:50 PM)
 * @stereotype predicate
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 * @see IPredicate
 */
public interface IGreaterThan extends IPredicate {
    /**
     * A predicate that evaluates the supplied object and tests
     * to see if it is greater than the predicate.
     *
     * @return boolean
     * @param o java.lang.Object
     * @exception uk.me.jockmacmad.jstate.predicate.EvaluationNotPossibleException The exception description.
     * @see EvaluationNotPossibleException
     * @since 0.1
     */
    boolean greaterThan(Object object) throws EvaluationNotPossibleException;
}