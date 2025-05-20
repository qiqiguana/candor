package net.virtualinfinity.atrobots.compiler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of compiler errors.
 *
 * @author Daniel Pitts
 */
public class Errors {
    List<String> messages = new ArrayList<String>();

    /**
     * Add an error message on the given line number.
     *
     * @param error      the message.
     * @param lineNumber the line number.
     */
    public void add(String error, int lineNumber) {
        messages.add("Line " + lineNumber + ": " + error);
    }

    public boolean hasErrors() {
        return !messages.isEmpty();
    }

    /**
     * Add an error message on the given line number and column.
     *
     * @param error      the message.
     * @param lineNumber the line number.
     * @param column     the column
     */
    public void add(String error, int lineNumber, int column) {
        messages.add("Line " + lineNumber + "," + column + ": " + error);
    }

    /**
     * Display the errors in a JDialog, if there are any.
     *
     * @param name   the dialog name.
     * @param parent the parent frame or dialog.
     */
    public void showErrorDialog(String name, Object parent) {
        if (!hasErrors()) {
            return;
        }
        final JDialog dialog = parent instanceof Frame ? new JDialog((Frame) parent, name, true) : new JDialog((Dialog) parent, name, true);
        final JList jList = new JList();
        dialog.add(new JScrollPane(jList));
        final DefaultListModel listModel = new DefaultListModel();
        for (String st : messages) {
            listModel.addElement(st);
        }
        jList.setModel(listModel);
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Add information not associated with a line.
     *
     * @param info the information.
     */
    public void info(String info) {
        messages.add(info);
    }

    /**
     * Add a copy of the messages in another errors object into this one.
     *
     * @param errors the errors to add from.
     */
    public void addAll(Errors errors) {
        messages.addAll(errors.messages);
    }

    /**
     * Dump the errors to System.out.
     */
    public void dumpErrors() {
        if (hasErrors()) {
            for (String m : messages) {
                System.out.println(m);
            }
        }
    }

    /**
     * Get a copy of the messages.
     *
     * @return a copy of the messages.
     */
    public List<String> getMessages() {
        return new ArrayList<String>(messages);
    }
}
