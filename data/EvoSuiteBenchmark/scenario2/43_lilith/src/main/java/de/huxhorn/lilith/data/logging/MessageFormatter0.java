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
     * Replace placeholders in the given messagePattern with arguments.
     *
     * @param messagePattern the message pattern containing placeholders.
     * @param arguments the arguments to be used to replace placeholders.
     * @return the formatted message.
     */
    public static String format(String messagePattern, String[] arguments) {
        if (messagePattern == null || arguments == null || arguments.length == 0) {
            return messagePattern;
        }
        StringBuilder result = new StringBuilder();
        int escapeCounter = 0;
        int currentArgument = 0;
        for (int i = 0; i < messagePattern.length(); i++) {
            char curChar = messagePattern.charAt(i);
            if (curChar == ESCAPE_CHAR) {
                escapeCounter++;
            } else {
                if (curChar == DELIM_START) {
                    if (i < messagePattern.length() - 1) {
                        if (messagePattern.charAt(i + 1) == DELIM_STOP) {
                            // write escaped escape chars
                            int escapedEscapes = escapeCounter / 2;
                            for (int j = 0; j < escapedEscapes; j++) {
                                result.append(ESCAPE_CHAR);
                            }
                            if (escapeCounter % 2 == 1) {
                                // i.e. escaped
                                // write escaped escape chars
                                result.append(DELIM_START);
                                result.append(DELIM_STOP);
                            } else {
                                // unescaped
                                if (currentArgument < arguments.length) {
                                    result.append(arguments[currentArgument]);
                                } else {
                                    result.append(DELIM_START).append(DELIM_STOP);
                                }
                                currentArgument++;
                            }
                            i++;
                            escapeCounter = 0;
                            continue;
                        }
                    }
                }
                // any other char beside ESCAPE or DELIM_START/STOP-combo
                // write unescaped escape chars
                if (escapeCounter > 0) {
                    for (int j = 0; j < escapeCounter; j++) {
                        result.append(ESCAPE_CHAR);
                    }
                    escapeCounter = 0;
                }
                result.append(curChar);
            }
        }
        return result.toString();
    }
}
