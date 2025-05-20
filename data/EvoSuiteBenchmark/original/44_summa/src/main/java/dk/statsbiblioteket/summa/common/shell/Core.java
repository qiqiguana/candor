/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.shell;

import dk.statsbiblioteket.summa.common.shell.commands.*;
import dk.statsbiblioteket.summa.common.shell.notifications.*;
import dk.statsbiblioteket.util.Strings;
import dk.statsbiblioteket.util.qa.QAInfo;
import jline.ConsoleReader;
import jline.SimpleCompletor;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.*;

/**
 * A generic shell driver.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "mke")
public class Core {
    // Commands usable in this shell
    private HashMap<String, Command> commands;
    // Alises availeble in this shell
    private HashMap<String, String> aliases;
    // The last trace
    private String lastTrace;

    private String header;
    private String prompt;
    // The {@link ShellContext} implementation used.
    private ShellContext shellCtx;
    // The {@link CommandLineParser} used.
    private CommandLineParser cliParser;
    // Command comprator.
    private static SimpleCompletor cmdComparator;

    /**
     * Create a new shell {@code Core} outputting to a default
     * {@link ShellContext}.
     *
     * @param withDefaultCommands if {@code true} install the default commands
     *                            found in the
     *                            {@link dk.statsbiblioteket.summa.common.shell.commands}
     *                            package.
     */
    public Core(boolean withDefaultCommands) {
        this(null, withDefaultCommands, false);
    }

    /**
     * Create a new shell {@code Core} with the default command set found in
     * {@link dk.statsbiblioteket.summa.common.shell.commands}.
     */
    public Core() {
        this(true);
    }

    /**
     * Create a new shell {@code Core} outputting to a custom
     * {@link ShellContext}.
     *
     * @param shellCtx            the {@link ShellContext} to write output to. If this
     *                            parameter is {@code null} a default shell context will
     *                            be used.
     * @param withDefaultCommands If {@code true} install the default commands
     *                            found in the
     *                            {@link dk.statsbiblioteket.summa.common.shell.commands}
     *                            package.
     * @param debug               If {@code true} debug messages will be printed to the
     *                            created shell context if {@code shellCtx} is {@code null}.
     */
    public Core(ShellContext shellCtx, final boolean withDefaultCommands, final boolean debug) {
        cliParser = new PosixParser();
        commands = new HashMap<String, Command>();
        aliases = new HashMap<String, String>();
        lastTrace = null;
        header = "Summa Generic Shell v@summa.api.version@";
        prompt = "summa-shell> ";
        cmdComparator = new SimpleCompletor(new String[]{});

        if (shellCtx != null) {
            this.shellCtx = shellCtx;
        } else {
            this.shellCtx = new ShellContextImpl(createConsoleReader(), System.err, System.out, debug);
        }
        // Install default commands.
        if (withDefaultCommands) {
            installCommand(new Help());
            installCommand(new Quit());
            installCommand(new Trace());
            installCommand(new Exec());
            installCommand(new Clear());
        }
    }

    /**
     * Create a console reader.
     *
     * @return The console reader, which should be used.
     */
    public static ConsoleReader createConsoleReader() {
        try {
            ConsoleReader reader = new ConsoleReader();
            reader.addCompletor(cmdComparator);
            return reader;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create ConsoleReader: " + e.getMessage(), e);
        }
    }

    /**
     * Set the header of this core.
     *
     * @param header The new header for this core.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Return this core's header.
     *
     * @return The header for this Core
     */
    public String getHeader() {
        return header;
    }

    /**
     * Install a command into this core.
     *
     * @param cmd The command to install.
     */
    public void installCommand(Command cmd) {
        commands.put(cmd.getName(), cmd);
        aliases.put(cmd.getName(), cmd.getName());
        for (String alias : cmd.getAliases()) {
            aliases.put(alias, cmd.getName());
        }
        cmdComparator.addCandidateString(cmd.getName());
    }

    /**
     * Return the shell context used.
     *
     * @return The used shell context.
     */
    public ShellContext getShellContext() {
        return shellCtx;
    }

    /**
     * Accessor for the prompt.
     *
     * @return The prompt.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Mutator for the prompt.
     *
     * @param prompt The new prompt.
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * A main iteration of the core. The caller is responsible for handling
     * any exceptions from the underlying subsystems.
     *
     * @throws Exception      Uf an error happens inside the running {@link Command}.
     * @throws ParseException If there is an error parsing the command line
     *                        as entered by the user.
     */
    private void doMainIteration() throws Exception {
        String cmdString;

        shellCtx.prompt(getPrompt());

        /* This call will block until we have input */
        cmdString = shellCtx.readLine();

        /* Check if the input stream has been closed an exit the shell if so */
        if (cmdString == null) {
            throw new AbortNotification("Input steam closed", 0);
        }

        /* Ignore empty commands, and re-print the prompt */
        if ("".equals(cmdString)) {
            return;
        }

        invoke(cmdString);
    }

    /**
     * Parse and run a command.
     *
     * @param cmdString The command line to run.
     * @return True if and only if {@code cmdString} was executed successfully.
     * @throws Exception Any exception from the invoked {@link Command} will
     *                   cascade upwards from this method.
     */
    boolean invoke(String cmdString) throws Exception {
        String[] tokens;
        Command cmd;

        tokens = tokenize(cmdString);
        cmd = commands.get(aliases.get(tokens[0]));

        if (cmd == null) {
            shellCtx.error("No such command '" + cmdString + "'");
            return false;
        }
        String[] args = new String[tokens.length - 1];
        // Copy arguments from command array to args array
        System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

        CommandLine cli = cliParser.parse(cmd.getOptions(), args);

        int commandEnd = tokens.length > 1 ? cmd.getName().length() + 1 : cmd.getName().length();
        cmd.prepare(cli, cmdString.substring(commandEnd));

        cmd.invoke(shellCtx);

        return true;
    }

    /**
     * Return the string tokized by white space respecting phrases enclosed
     * by double-quotes.
     *
     * @param in The string to tokenize.
     * @return The string tokens as separated by white space with double-quoted
     *         substrings treated as one token.
     * @throws SyntaxErrorNotification If there are improperly formatted
     *                                 double-quoted substrings.
     */
    public String[] tokenize(String in) {
        StringTokenizer tok = new StringTokenizer(in, "\"", true);
        List<String> result = new ArrayList<String>(tok.countTokens());

        while (tok.hasMoreElements()) {
            String s = tok.nextToken();

            /* If this is the start of a phrase read the whole phrase in
             * one go */
            if ("\"".equals(s)) {
                if (tok.hasMoreElements()) {
                    s = tok.nextToken().trim();
                    result.add(s);
                    if (tok.hasMoreElements()) {
                        s = tok.nextToken();
                        if (!"\"".equals(s)) {
                            throw new SyntaxErrorNotification(
                                    "Unexpected token '" + s + "' when tokenizing phrase. Expected '\"'");
                        }
                        continue;
                    } else {
                        throw new SyntaxErrorNotification("Unclosed phrase near token '" + s + "'");
                    }
                } else {
                    throw new SyntaxErrorNotification("Stray '\"' at line end");
                }

            }

            // A normal token, split by spaces
            for (String ss : s.split(" ")) {
                if (!"".equals(ss)) {
                    result.add(ss.trim());
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * Helper function to handle {@link HelpNotification}s.
     *
     * @param help The help notification.
     */
    private void handleHelpNotification(HelpNotification help) {
        String target = help.getTargetCommand();
        if (target == null) {
            printCommands();
        } else {
            if (commands.containsKey(target)) {
                Command cmd = commands.get(target);
                printHelp(cmd, cmd.getDescription());
            } else {
                shellCtx.error("No such command: '" + target + "'.\nThe available commands are:\n");
                printCommands();
            }
        }
    }

    /**
     * Helper function to handle {@link TraceNotification}s.
     *
     * @param help The trace notificatino.
     */
    private void handleTraceNotification(TraceNotification help) {
        if (lastTrace == null) {
            shellCtx.info("No stack trace recorded");
            return;
        }

        shellCtx.info("Last recorded stack trace:\n\n\t" + lastTrace + "\n");
    }

    /**
     * Helper function to handle {@link ClearNotification}s.
     */
    private void handleClearNotification() {
        shellCtx.clear();
    }

    /**
     * Helper function to handle {@link SyntaxErrorNotification}s.
     *
     * @param syntaxErrorNotification The syntax notification.
     */
    private void handleSyntaxErrorNotification(SyntaxErrorNotification syntaxErrorNotification) {
        shellCtx.error("Syntax error: " + syntaxErrorNotification.getMessage());
    }

    /**
     * Print a help message for a command
     *
     * @param cmd The command to print help for.
     * @param msg A message, possible empty or {@code null}, to print after the
     *            usage instructions.
     */
    private void printHelp(Command cmd, String msg) {
        HelpFormatter formatter = new HelpFormatter();

        if ("".equals(msg) || msg == null) {
            msg = cmd.getDescription();
        }

        // FIXME: We should really render this into a
        //        StringBuffer with formatter.renderOptions
        //        and print it via the ShellContext.
        formatter.printHelp(cmd.getUsage(), msg, cmd.getOptions(), "");
    }

    /**
     * Private helper function for Core help.
     *
     * @param msg The message.
     */
    private void printCoreHelp(String msg) {
        shellCtx.error("CORE HELP!");
    }

    /**
     * Helper function, for printing the commands.
     */
    private void printCommands() {
        SortedSet<String> sortedCommands = new TreeSet<String>(commands.keySet());
        for (String cmdName : sortedCommands) {
            Command cmd = commands.get(cmdName);
            shellCtx.info("\t" + cmd.getName() + "\t" + cmd.getDescription());
        }
    }

    /**
     * Run the shell's main loop. If {@code script} is non-null the script will
     * be executed and the shell core will exit afterwards. If {@code script}
     * is {@code null} it will enter interactive mode reading commands from
     * stdin.
     *
     * @param script A script to execute or {@code null} to enter interactive
     *               mode.
     * @return 0 on a clean exit which also indicates the any script passed to
     *         to the core returned without any errors. If this method returns
     *         non-zero some error occurred and the caller must understand the
     *         error code in its current context.
     */
    @SuppressWarnings("ObjectToString")
    public int run(Script script) {
        int returnVal = 0;
        Iterator<String> scriptIter = null;
        String scriptStatement = null;

        if (script != null) {
            scriptIter = script.iterator();
        }
        /* Print a greeting if running interactively */
        if (scriptIter == null) {
            getShellContext().info(getHeader());
        }

        while (true) {
            try {
                // If the shell is scripted feed the next script line to the
                // parser
                if (scriptIter != null) {
                    if (getShellContext().getLastError() != null) {
                        throw new AbortNotification(
                                "Error executing '" + scriptStatement + "'", returnVal == 0 ? -1 : returnVal);
                    }

                    if (scriptIter.hasNext()) {
                        scriptStatement = scriptIter.next();
                        getShellContext().pushLine(scriptStatement);
                    } else {
                        // Script is done. Exit the shell
                        break;
                    }
                }

                doMainIteration();
            } catch (Notification e) {
                if (e instanceof BadCommandLineNotification) {
                    if (scriptIter != null) {
                        getShellContext().error("Bad command line for '" + e.getCommand() + "': " + e.getMessage());
                        returnVal = -2;
                    } else {
                        printHelp(e.getCommand(), e.getMessage());
                    }
                } else if (e instanceof AbortNotification) {
                    // This is a clean exit
                    if (!"".equals(e.getMessage())) {
                        shellCtx.info(e.getMessage());
                    }
                    returnVal = ((AbortNotification) e).getReturnValue();
                    // Exit the shell
                    break;
                } else if (e instanceof HelpNotification) {
                    handleHelpNotification((HelpNotification) e);
                } else if (e instanceof TraceNotification) {
                    handleTraceNotification((TraceNotification) e);
                } else if (e instanceof ClearNotification) {
                    handleClearNotification();
                } else if (e instanceof SyntaxErrorNotification) {
                    if (scriptIter != null) {
                        getShellContext().error(
                                "Syntax error when parsing '" + scriptStatement + "': " + e.getMessage());
                        returnVal = -3;
                    } else {
                        handleSyntaxErrorNotification((SyntaxErrorNotification) e);
                    }
                } else {
                    // This is a bug in the shell core
                    shellCtx.error("Shell Core encountered an unknown notification: " + e.getClass().getName());
                }
            } catch (ParseException e) {
                returnVal = -4;
                getShellContext().error("Error parsing command line: " + e.getMessage());
            } catch (UnmarshalException e) {
                /* This is a specific hack to handle the case where an RMI
                 * service returns an unknown class */
                if (e.getCause() instanceof ClassNotFoundException) {
                    shellCtx.error("Caught exception of unknown class type: " + e.getCause().getMessage() + "\n\n"
                                   + "This usually happens if a remote service throws a custom exception");
                } else {
                    shellCtx.error("RMI protocol error:" + e.getMessage());
                }
                lastTrace = Strings.getStackTrace(e);
            } catch (Exception e) {
                shellCtx.error("Caught error: '" + e.getMessage() + "'");
                lastTrace = Strings.getStackTrace(e);
            }
        }

        getShellContext().info("Bye.");
        return returnVal;
    }
}