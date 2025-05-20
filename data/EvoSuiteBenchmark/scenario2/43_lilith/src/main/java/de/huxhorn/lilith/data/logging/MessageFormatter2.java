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

    /**
     * <p>This method returns a MessageFormatter.ArgumentResult which contains the arguments converted to String
     * as well as an optional Throwable.</p>
     * <p/>
     * <p>If the last argument is a Throwable and is NOT used up by a placeholder in the message pattern it is returned
     * in MessageFormatter.ArgumentResult.getThrowable() and won't be contained in the created String[].<br/>
     * If it is used up getThrowable will return null even if the last argument was a Throwable!</p>
     *
     * @param messagePattern the message pattern that to be checked for placeholders.
     * @param arguments the argument array to be converted.
     * @return a MessageFormatter.ArgumentResult containing the converted arformatted message and optionally a Throwable.
     */
    public static ArgumentResult evaluateArguments(String messagePattern, Object[] arguments) {
        if (arguments == null) {
            return null;
        }
        int argsCount = countArgumentPlaceholders(messagePattern);
        int resultArgCount = arguments.length;
        Throwable throwable = null;
        if (argsCount < arguments.length) {
            if (arguments[arguments.length - 1] instanceof Throwable) {
                throwable = (Throwable) arguments[arguments.length - 1];
                resultArgCount--;
            }
        }
        String[] stringArgs;
        if (argsCount == 1 && throwable == null && arguments.length > 1) {
            // special case
            stringArgs = new String[1];
            stringArgs[0] = deepToString(arguments);
        } else {
            stringArgs = new String[resultArgCount];
            for (int i = 0; i < stringArgs.length; i++) {
                stringArgs[i] = deepToString(arguments[i]);
            }
        }
        return new ArgumentResult(stringArgs, throwable);
    }
}
