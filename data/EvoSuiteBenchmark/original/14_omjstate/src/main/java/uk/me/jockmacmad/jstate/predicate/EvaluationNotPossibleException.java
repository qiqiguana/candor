package uk.me.jockmacmad.jstate.predicate;

/**
 * An exception that is thrown if a predicate cannot evaluate
 * due to an incorrect type of object being supplied for
 * evaluation.
 *
 * Creation date: (2/20/01 12:45:18 PM)
 * @stereotype exception
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 */
public class EvaluationNotPossibleException extends Exception {
    /**
     * Compiler generated serialVersionUID.
     */
    private static final long serialVersionUID = -2604194592749081532L;

    /**
     * EvaluationNotPossibleException default constructor, simply calls
     * it's superclasses constructor.
     * @since 0.1
     */
    public EvaluationNotPossibleException() {
        super();
    }

    /**
     * EvaluationNotPossibleException default constructor, simply calls
     * it's superclasses constructor and passes it the supplied parameter.
     * @param s java.lang.String
     * @since 0.1
     */
    public EvaluationNotPossibleException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}