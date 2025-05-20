package de.huxhorn.lilith.data.logging;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Replacement for org.slf4j.helpers.MessageFormatter.</p>
 * <p>
 * In contrast to the mentioned class, the formatting of message pattern and arguments into the actual message
 * is split into three parts:
 * </p>
 * <ol>
 * <li>Counting of placeholders in the message pattern (cheap)</li>
 * <li>Conversion of argument array into an ArgumentResult, containing the arguments converted to String as well as
 * an optional Throwable if available (relatively cheap)</li>
 * <li>Replacement of placeholders in a message pattern with arguments given as String[]. (most expensive)</li>
 * </ol>
 * <p>
 * That way only the first two steps have to be done during event creation while the most expensive part, i.e. the
 * actual construction of the message, is only done on demand.
 * </p>
 */
public class MessageFormatter {

    private static final char DELIM_START = '{';

    private static final char DELIM_STOP = '}';

    private static final char ESCAPE_CHAR = '\\';

    public static final String RECURSION_PREFIX = "[...";

    public static final String RECURSION_SUFFIX = "...]";

    public static final String ERROR_PREFIX = "[!!!";

    public static final String ERROR_SEPARATOR = "=>";

    public static final String ERROR_MSG_SEPARATOR = ":";

    public static final String ERROR_SUFFIX = "!!!]";

    /**
     * Replace placeholders in the given messagePattern with arguments.
     *
     * @param messagePattern the message pattern containing placeholders.
     * @param arguments      the arguments to be used to replace placeholders.
     * @return the formatted message.
     */
    public static String format(String messagePattern, String[] arguments);

    /**
     * Counts the number of unescaped placeholders in the given messagePattern.
     *
     * @param messagePattern the message pattern to be analyzed.
     * @return the number of unescaped placeholders.
     */
    public static int countArgumentPlaceholders(String messagePattern);

    /**
     * <p>This method returns a MessageFormatter.ArgumentResult which contains the arguments converted to String
     * as well as an optional Throwable.</p>
     * <p/>
     * <p>If the last argument is a Throwable and is NOT used up by a placeholder in the message pattern it is returned
     * in MessageFormatter.ArgumentResult.getThrowable() and won't be contained in the created String[].<br/>
     * If it is used up getThrowable will return null even if the last argument was a Throwable!</p>
     *
     * @param messagePattern the message pattern that to be checked for placeholders.
     * @param arguments      the argument array to be converted.
     * @return a MessageFormatter.ArgumentResult containing the converted arformatted message and optionally a Throwable.
     */
    public static ArgumentResult evaluateArguments(String messagePattern, Object[] arguments);

    public static String deepToString(Object o);

    /**
     * This method performs a deep toString of the given Object.
     * Primitive arrays are converted using their respective Arrays.toString methods while
     * special handling is implemented for "container types", i.e. Object[], Map and Collection because those could
     * contain themselves.
     * <p/>
     * dejaVu is used in case of those container types to prevent an endless recursion.
     * <p/>
     * It should be noted that neither AbstractMap.toString() nor AbstractCollection.toString() implement such a behavior.
     * They only check if the container is directly contained in itself, but not if a contained container contains the
     * original one. Because of that, Arrays.toString(Object[]) isn't safe either.
     * Confusing? Just read the last paragraph again and check the respective toString() implementation.
     * <p/>
     * This means, in effect, that logging would produce a usable output even if an ordinary System.out.println(o)
     * would produce a relatively hard-to-debug StackOverflowError.
     *
     * @param o      the Object to convert into a String
     * @param str    the StringBuilder that o will be appended to
     * @param dejaVu a list of container identities that were already used.
     */
    private static void recursiveDeepToString(Object o, StringBuilder str, Set<String> dejaVu);

    /**
     * This method returns the same as if Object.toString() would not have been
     * overridden in obj.
     * <p/>
     * Note that this isn't 100% secure as collisions can always happen with hash codes.
     * <p/>
     * Copied from Object.hashCode():
     * As much as is reasonably practical, the hashCode method defined by
     * class <tt>Object</tt> does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java<font size="-2"><sup>TM</sup></font> programming language.)
     *
     * @param obj the Object that is to be converted into an identity string.
     * @return the identity string as also defined in Object.toString()
     */
    public static String identityToString(Object obj);

    /**
     * <p>This is just a simple class containing the result of an evaluateArgument call. It's necessary because we need to
     * return two results, i.e. the resulting String[] and the optional Throwable.</p>
     * <p/>
     * <p>This class is not Serializable because serializing a Throwable is generally a bad idea if the data is supposed
     * to leave the current VM since it may result in ClassNotFoundExceptions if the given Throwable is not
     * available/different in the deserializing VM.</p>
     */
    public static class ArgumentResult {

        private Throwable throwable;

        private String[] arguments;

        public ArgumentResult(String[] arguments, Throwable throwable) {
            this.throwable = throwable;
            this.arguments = arguments;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public String[] getArguments() {
            return arguments;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("ArgumentResult[throwable=").append(throwable);
            result.append(", arguments=");
            if (arguments != null) {
                result.append("[");
                boolean isFirst = true;
                for (String current : arguments) {
                    if (!isFirst) {
                        result.append(", ");
                    } else {
                        isFirst = false;
                    }
                    if (current != null) {
                        result.append("'").append(current).append("'");
                    } else {
                        result.append("null");
                    }
                }
                result.append("]");
            }
            return result.toString();
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ArgumentResult result = (ArgumentResult) o;
            if (!Arrays.equals(arguments, result.arguments))
                return false;
            if (throwable != null ? !throwable.equals(result.throwable) : result.throwable != null)
                return false;
            return true;
        }

        public int hashCode() {
            int result;
            result = (throwable != null ? throwable.hashCode() : 0);
            result = 31 * result + (arguments != null ? Arrays.hashCode(arguments) : 0);
            return result;
        }
    }
}
