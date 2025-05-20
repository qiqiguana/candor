/*
 * Resources.java
 *
 *  Copyright 2010 Yann D'Isanto.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.mytdev.resources4j.interfaces;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Date;

/**
 * A resources set with methods to get resources values.
 * Each resource is identified by a string key.
 * @author Yann D'Isanto
 */
public interface Resources {

    /**
     * Returns the resource identified by the specified key as a boolean.
     * @param key the resource key.
     * @return the resource identified by the specified key as a boolean.
     */
    public boolean getBoolean(String key);

    /**
     * Returns the resource identified by the specified key as a boolean or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a boolean or
     * the specified defaultValue if the resource is not found.
     */
    public boolean getBoolean(String key, boolean defaultValue);

    /**
     * Returns the resource identified by the specified key as a boolean array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a boolean array.
     */
    public boolean[] getBooleanArray(String key);

    /**
     * Returns the resource identified by the specified key as a Boolean array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Boolean array.
     */
    public Boolean[] getBooleanObjectArray(String key);

    /**
     * Returns the resource identified by the specified key as a byte.
     * @param key the resource key.
     * @return the resource identified by the specified key as a byte.
     */
    public byte getByte(String key);

    /**
     * Returns the resource identified by the specified key as a byte or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a byte or
     * the specified defaultValue if the resource is not found.
     */
    public byte getByte(String key, byte defaultValue);

    /**
     * Returns the resource identified by the specified key as a byte or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a byte or
     * the specified defaultValue if the resource is not found.
     */
    public byte getByte(String key, byte defaultValue, int radix);

    /**
     * Returns the resource identified by the specified key as a byte array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a byte array.
     */
    public byte[] getByteArray(String key);

    /**
     * Returns the resource identified by the specified key as a byte array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a byte array.
     */
    public byte[] getByteArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a Byte array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Byte array.
     */
    public Byte[] getByteObjectArray(String key);

    /**
     * Returns the resource identified by the specified key as a Byte array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a Byte array.
     */
    public Byte[] getByteObjectArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a char.
     * @param key the resource key.
     * @return the resource identified by the specified key as a char.
     */
    public char getChar(String key);

    /**
     * Returns the resource identified by the specified key as a char or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a char or
     * the specified defaultValue if the resource is not found.
     */
    public char getChar(String key, char defaultValue);

    /**
     * Returns the resource identified by the specified key as a char array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a char array.
     */
    public char[] getCharArray(String key);

    /**
     * Returns the resource identified by the specified key as a Character array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Character array.
     */
    public Character[] getCharacterArray(String key);

    /**
     * Returns the resource identified by the specified key as a Color.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Color.
     */
    public Color getColor(String key);

    /**
     * Returns the resource identified by the specified key as a Color or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a Color or
     * the specified defaultValue if the resource is not found.
     */
    public Color getColor(String key, Color defaultValue);

    /**
     * Returns the resource identified by the specified key as a Color array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Color array.
     */
    public Color[] getColorArray(String key);

    /**
     * Returns the resource identified by the specified key as a Date.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Date.
     */
    public Date getDate(String key);

    /**
     * Returns the resource identified by the specified key as a Date or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a Date or
     * the specified defaultValue if the resource is not found.
     */
    public Date getDate(String key, Date defaultValue);

    /**
     * Returns the resource identified by the specified key as a double.
     * @param key the resource key.
     * @return the resource identified by the specified key as a double.
     */
    public double getDouble(String key);

    /**
     * Returns the resource identified by the specified key as a double or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a double or
     * the specified defaultValue if the resource is not found.
     */
    public double getDouble(String key, double defaultValue);

    /**
     * Returns the resource identified by the specified key as a double array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a double array.
     */
    public double[] getDoubleArray(String key);

    /**
     * Returns the resource identified by the specified key as a Double array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Double array.
     */
    public Double[] getDoubleObjectArray(String key);

    /**
     * Returns the resource identified by the specified key as a float.
     * @param key the resource key.
     * @return the resource identified by the specified key as a float.
     */
    public float getFloat(String key);

    /**
     * Returns the resource identified by the specified key as a float or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a float or
     * the specified defaultValue if the resource is not found.
     */
    public float getFloat(String key, float defaultValue);

    /**
     * Returns the resource identified by the specified key as a float array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a float array.
     */
    public float[] getFloatArray(String key);

    /**
     * Returns the resource identified by the specified key as a Float array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Float array.
     */
    public Float[] getFloatObjectArray(String key);

    public String getFormattedString(String key, Object... parameters);

    /**
     * Returns the resource identified by the specified key as an image.
     * @param key the resource key.
     * @return the resource identified by the specified key as an image.
     */
    public Image getImage(String key) throws IOException;

    /**
     * Returns the resource identified by the specified key as an int.
     * @param key the resource key.
     * @return the resource identified by the specified key as an int.
     */
    public int getInt(String key);

    /**
     * Returns the resource identified by the specified key as an int or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as an int or
     * the specified defaultValue if the resource is not found.
     */
    public int getInt(String key, int defaultValue);

    /**
     * Returns the resource identified by the specified key as an int or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as an int or
     * the specified defaultValue if the resource is not found.
     */
    public int getInt(String key, int defaultValue, int radix);

    /**
     * Returns the resource identified by the specified key as an int array.
     * @param key the resource key.
     * @return the resource identified by the specified key as an int array.
     */
    public int[] getIntArray(String key);

    /**
     * Returns the resource identified by the specified key as an int array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as an int array.
     */
    public int[] getIntArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as an Integer array.
     * @param key the resource key.
     * @return the resource identified by the specified key as an Integer array.
     */
    public Integer[] getIntegerArray(String key);

    /**
     * Returns the resource identified by the specified key as an Integer array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as an Integer array.
     */
    public Integer[] getIntegerArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a long.
     * @param key the resource key.
     * @return the resource identified by the specified key as a long.
     */
    public long getLong(String key);

    /**
     * Returns the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     */
    public long getLong(String key, long defaultValue);

    /**
     * Returns the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     */
    public long getLong(String key, long defaultValue, int radix);

    /**
     * Returns the resource identified by the specified key as a long array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a long array.
     */
    public long[] getLongArray(String key);

    /**
     * Returns the resource identified by the specified key as a long array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a long array.
     */
    public long[] getLongArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a Long array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Long array.
     */
    public Long[] getLongObjectArray(String key);

    /**
     * Returns the resource identified by the specified key as a Long array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a Long array.
     */
    public Long[] getLongObjectArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a short.
     * @param key the resource key.
     * @return the resource identified by the specified key as a short.
     */
    public short getShort(String key);

    /**
     * Returns the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     */
    public short getShort(String key, short defaultValue);

    /**
     * Returns the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a long or
     * the specified defaultValue if the resource is not found.
     */
    public short getShort(String key, short defaultValue, int radix);

    /**
     * Returns the resource identified by the specified key as a short array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a short array.
     */
    public short[] getShortArray(String key);

    /**
     * Returns the resource identified by the specified key as a short array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a short array.
     */
    public short[] getShortArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a Short array.
     * @param key the resource key.
     * @return the resource identified by the specified key as a Short array.
     */
    public Short[] getShortObjectArray(String key);

    /**
     * Returns the resource identified by the specified key as a Short array.
     * @param key the resource key.
     * @param radix the radix used to load the byte resource.
     * @return the resource identified by the specified key as a Short array.
     */
    public Short[] getShortObjectArray(String key, int radix);

    /**
     * Returns the resource identified by the specified key as a String.
     * @param key the resource key.
     * @return the resource identified by the specified key as a String.
     */
    public String getString(String key);

    /**
     * Returns the resource identified by the specified key as a String or
     * the specified defaultValue if the resource is not found.
     * @param key the resource key.
     * @param defaultValue the default value.
     * @return the resource identified by the specified key as a String or
     * the specified defaultValue if the resource is not found.
     */
    public String getString(String key, String defaultValue);

}
