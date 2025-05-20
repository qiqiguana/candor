package org.quickserver.util.io;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * This class prompts the user for a password and attempts to mask
 * input.
 *
 * @since 1.4
 */
public class PasswordField {

    /**
     * @param prompt The prompt to display to the user.
     * @return The password as entered by the user.
     */
    public static final char[] getPassword(String prompt) throws IOException;
}
