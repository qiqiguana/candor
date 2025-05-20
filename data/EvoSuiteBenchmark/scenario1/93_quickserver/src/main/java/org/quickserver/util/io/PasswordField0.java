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

    public static final char[] getPassword(String prompt) throws IOException {
        return getPassword(System.in, prompt);
    }
}
