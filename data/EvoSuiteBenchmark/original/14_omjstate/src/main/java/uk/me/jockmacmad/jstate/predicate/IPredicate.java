package uk.me.jockmacmad.jstate.predicate;

/**
 * An interface that is used to mark an implementing class as
 * a predicate.
 *
 * Creation date: (2/20/01 12:38:18 PM)
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 * @stereotype predicate*/
public interface IPredicate {
    /**
     * All predicate implementors have to implement the evaluate method.
     * This takes in a java.lang.Object and evaluates the object against
     * the predicate.
     * Creation date: (2/20/01 12:53:25 PM)
     * @return boolean
     * @param o java.lang.Object
     * @since 0.1*/
    boolean evaluate(Object object);
}