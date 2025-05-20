package edu.mscd.cs.jclo;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class JCLO {

    private Field[] fields;

    private Object object;

    private boolean doubleDashes;

    private boolean hasEquals;

    private String prefix = "";

    private String[][] aliases;

    /**
     * 	A constructor that takes the Object that contains the variables
     * 	acceptable on a command line.  Call parse (String) to do the actual
     * 	parsing.
     *
     * 	@param	object	where the variables/arguments are
     */
    public JCLO(Object object) {
    }

    public JCLO(Object object, String[][] aliases) {
    }

    public JCLO(String prefix, Object object) {
    }

    /**
     * 	A constructor that takes an Object, a prefix, and a boolean that
     * 	specifies whether to accept single or double dashes; call parse
     * 	(String) to do the actual parsing.
     *
     * 	@param	object	where the variables/arguments are
     * 	@param	prefix	the String CLO's start with, if any
     */
    public JCLO(String prefix, Object object, String[][] aliases) {
    }

    /**
     * 	Just a simple method to put the try/catch in one place.
     *
     * 	@param	f	the Field to get the value of
     * 	@return		the Object with the value
     */
    private Object getObject(Field f);

    private Field getField(String key);

    /**
     * 	Get the current value of the variable in the object
     *
     * 	@param	key	the variable name
     * 	@return		an Object with the value
     */
    public Object getValue(String key);

    /**
     * 	Just a simple method to put the try/catch in one place.
     *
     * 	@param	f	the Field to set the value of
     * 	@param	o	the Object with the value
     */
    private void setObject(Field f, Object o);

    private String getArrayType(Class type);

    /**
     * 	An external representation of the object
     *
     * 	@return		a formatted version of this object
     */
    public String toString();

    private String getUsageType(Class type);

    /**
     *  Create and usage message for the acceptable command line variables.
     *
     * 	@return		a String that specifies acceptable options
     */
    public String usage();

    private void parseAdditional(String[] args, int i);

    /**
     * 	Add to object o to the end of the array contained in field and
     * 	return the resulting array.
     *
     * 	@param	field	the field in the object
     * 	@param	o	the new object to be placed at the end
     * 	@return		a formatted version of this object
     */
    private Object addToArray(Field field, Object o);

    private String getKey(String arg);

    private String getBooleanValue(String arg);

    /**
     * 	Make an Object of the correct type for the field, using a String
     * 	version of the value to create it.
     *
     * 	@param	type	a String representing the base (or String) type
     * 	@param	val	the value
     * 	@return		an Object of the correct type and value
     */
    private Object makeObject(String type, String val);

    private String getEqualsValue(String arg);

    /**
     * 	Parse a command line.
     *
     * 	@param	args	the arguments to be parsed
     */
    public void parse(String[] args);

    /**
     * Get a boolean value from the object after parsing.
     */
    public boolean getBoolean(String key);

    /**
     * Get a byte value from the object after parsing.
     */
    public byte getByte(String key);

    /**
     * Get a character value from the object after parsing.
     */
    public char getChar(String key);

    /**
     * Get a short value from the object after parsing.
     */
    public short getShort(String key);

    /**
     * Get an integer value from the object after parsing.
     */
    public int getInt(String key);

    /**
     * Get a float value from the object after parsing.
     */
    public float getFloat(String key);

    /**
     * Get a double value from the object after parsing.
     */
    public double getDouble(String key);

    /**
     * Get a long value from the object after parsing.
     */
    public long getLong(String key);

    /**
     * Get a String from the object after parsing.
     */
    public String getString(String key);

    /**
     * Get an array of bytes from the object after parsing.
     */
    public byte[] getBytes(String key);

    /**
     * Get an array of characaters from the object after parsing.
     */
    public char[] getChars(String key);

    /**
     * Get an array of shorts from the object after parsing.
     */
    public short[] getShorts(String key);

    /**
     * Get an array of integers from the object after parsing.
     */
    public int[] getInts(String key);

    /**
     * Get an array of floats from the object after parsing.
     */
    public float[] getFloats(String key);

    /**
     * Get an array of doubles from the object after parsing.
     */
    public double[] getDoubles(String key);

    /**
     * Get an array of longs from the object after parsing.
     */
    public long[] getLongs(String key);

    /**
     * Get an array of Strings from the object after parsing.
     */
    public String[] getStrings(String key);

    public static void main(String[] args);
}
