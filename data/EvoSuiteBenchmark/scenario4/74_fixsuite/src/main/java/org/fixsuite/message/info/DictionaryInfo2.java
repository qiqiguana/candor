package org.fixsuite.message.info;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a FIX version specification. DictionaryInfo acts as a central
 * storage for all items defined in a version.
 *
 * @author jramoyo
 */
public class DictionaryInfo {

    private String version;

    private int loadCount;

    // Default collection
    private TreeMap<String, MessageInfo> messagesByName;

    private TreeMap<Integer, MessageInfo> messagesById;

    // Default collection
    private TreeMap<Integer, FieldInfo> fieldsByTagNumber;

    private TreeMap<String, FieldInfo> fieldsByName;

    // Default collection
    private TreeMap<String, ComponentInfo> componentsByName;

    private TreeMap<Integer, ComponentInfo> componentsById;

    /**
     * Creates a new DictionaryInfo
     *
     * @param version - a version
     */
    public DictionaryInfo(String version) {
    }

    /**
     * Returns the fields
     *
     * @return the fields
     */
    public List<FieldInfo> getFields();

    /**
     * Returns a field given a tagNumber
     *
     * @param tagNumber - a tagNumber
     * @return a field given a tagNumber
     */
    public FieldInfo getField(int tagNumber);

    /**
     * Returns a field given a name
     *
     * @param name - a name
     * @return a field given a name
     */
    public FieldInfo getField(String name);

    /**
     * Adds a field
     *
     * @param field - a field
     */
    public void addField(FieldInfo field);

    /**
     * Returns the components
     *
     * @return the components
     */
    public List<ComponentInfo> getComponents();

    /**
     * Returns a component given an id
     *
     * @param id - an id
     * @return a component given an id
     */
    public ComponentInfo getComponent(int id);

    /**
     * Returns a component given a name
     *
     * @param name - a name
     * @return a component given a name
     */
    public ComponentInfo getComponent(String name);

    /**
     * Adds a component
     *
     * @param component
     */
    public void addComponent(ComponentInfo component);

    /**
     * Replace the field by a group. This is used by FPL parsers which cannot
     * tell if a field is a group just from Fields.xml
     *
     * @param field - a field
     * @param group - a group
     */
    public void replaceAsGroup(FieldInfo field, GroupInfo group);

    /**
     * Returns a message given an id
     *
     * @param id - an id
     * @return a message given an id
     */
    public MessageInfo getMessage(int id);

    /**
     * Returns a message given a name
     *
     * @param name - a name
     * @return a message given a name
     */
    public MessageInfo getMessage(String name);

    public List<MessageInfo> getMessages();

    /**
     * Adds a message
     *
     * @param message - a message
     */
    public void addMessage(MessageInfo message);

    /**
     * Returns the version
     *
     * @return the version
     */
    public String getVersion();

    /**
     * Modifies the version
     *
     * @param version - the version to set
     */
    public void setVersion(String version);

    /**
     * Increments the loadCount
     */
    public void incrementLoadCount();

    /**
     * Returns whether the dictionary is loaded
     *
     * @return whether the dictionary is loaded
     */
    public boolean isLoaded();
}
