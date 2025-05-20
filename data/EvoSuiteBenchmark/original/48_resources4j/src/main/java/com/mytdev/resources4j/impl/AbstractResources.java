/*
 * AbstractResources.java
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

package com.mytdev.resources4j.impl;

import com.mytdev.resources4j.interfaces.Resources;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract Resources based on String resources.
 * @author Yann D'Isanto
 */
public abstract class AbstractResources implements Resources {

    private static final String INT_REGEX = "\\w+";
    private static final String BOOLEAN_REGEX = "(?i)(true)|(false)";
    private static final String CHAR_REGEX = "'(.|(\\\\[tbnrf]))'";
    private static final String DECIMAL_REGEX = "\\d+(\\.\\d+)?";
    private static final String RAW_COLOR_REGEX = "#([0-9a-fA-F]{2}){3,4}";
    private static final String COLOR_REGEX = String.format("(%s)|([a-zA-Z _]+)", RAW_COLOR_REGEX);
    private static final String GENERIC_ARRAY_REGEX = "\\s*\\{\\s*(%1$s)(\\s*,\\s*(%1$s))*\\s*\\}";

    private static final Pattern INT_PATTERN = Pattern.compile(INT_REGEX);
    private static final Pattern BOOLEAN_PATTERN = Pattern.compile(BOOLEAN_REGEX);
    private static final Pattern CHAR_PATTERN = Pattern.compile(CHAR_REGEX);
    private static final Pattern DECIMAL_PATTERN = Pattern.compile(DECIMAL_REGEX);
    private static final Pattern COLOR_PATTERN = Pattern.compile(COLOR_REGEX);
    private static final Pattern RAW_COLOR_PATTERN = Pattern.compile(RAW_COLOR_REGEX);

    private static final Pattern INT_ARRAY_PATTERN = Pattern.compile(String.format(GENERIC_ARRAY_REGEX, INT_REGEX));
    private static final Pattern BOOLEAN_ARRAY_PATTERN = Pattern.compile(String.format(GENERIC_ARRAY_REGEX, BOOLEAN_REGEX));
    private static final Pattern DECIMAL_ARRAY_PATTERN = Pattern.compile(String.format(GENERIC_ARRAY_REGEX, DECIMAL_REGEX));
    private static final Pattern CHAR_ARRAY_PATTERN = Pattern.compile(String.format(GENERIC_ARRAY_REGEX, CHAR_REGEX));
    private static final Pattern COLOR_ARRAY_PATTERN = Pattern.compile(String.format(GENERIC_ARRAY_REGEX, COLOR_REGEX));

    private Logger logger = Logger.getLogger(AbstractResources.class.getName());

    private Map<String, String> resources;
    private Map<String, SoftReference<Image>> imagesRef = new HashMap<String, SoftReference<Image>>();
    private DateFormat dateFormat;

    public AbstractResources(Map<String, String> resources, DateFormat dateFormat) {
        if(resources == null) {
            throw new NullPointerException("resources is null");
        }
        this.resources = resources;
    }



    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = loadString(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }

    @Override
    public boolean[] getBooleanArray(String key) {
        String text = loadString(key);
        if (!BOOLEAN_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = BOOLEAN_PATTERN.matcher(text);
        List<Boolean> booleans = new ArrayList<Boolean>();
        while (matcher.find()) {
            booleans.add(Boolean.parseBoolean(matcher.group()));
        }
        boolean[] array = new boolean[booleans.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = booleans.get(i);
        }
        return array;
    }

    @Override
    public Boolean[] getBooleanObjectArray(String key) {
        String text = loadString(key);
        if (!BOOLEAN_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = BOOLEAN_PATTERN.matcher(text);
        List<Boolean> booleans = new ArrayList<Boolean>();
        while (matcher.find()) {
            booleans.add(Boolean.parseBoolean(matcher.group()));
        }
        return booleans.toArray(new Boolean[0]);
    }

    @Override
    public byte getByte(String key) {
        byte defaultValue = 0;
        return getByte(key, defaultValue);
    }

    @Override
    public byte getByte(String key, byte defaultValue) {
        return getByte(key, defaultValue, 10);
    }

    @Override
    public byte getByte(String key, byte defaultValue, int radix) {
        String value = loadString(key);
        return value == null ? defaultValue : Byte.parseByte(value, radix);
    }

    @Override
    public byte[] getByteArray(String key) {
        return getByteArray(key, 10);
    }

    @Override
    public byte[] getByteArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Byte> bytes = new ArrayList<Byte>();
        while (matcher.find()) {
            bytes.add(Byte.parseByte(matcher.group(), radix));
        }
        byte[] array = new byte[bytes.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = bytes.get(i);
        }
        return array;
    }

    @Override
    public Byte[] getByteObjectArray(String key) {
        return getByteObjectArray(key, 10);
    }

    @Override
    public Byte[] getByteObjectArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Byte> bytes = new ArrayList<Byte>();
        while (matcher.find()) {
            bytes.add(Byte.parseByte(matcher.group(), radix));
        }
        return bytes.toArray(new Byte[0]);
    }

    @Override
    public char getChar(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public char getChar(String key, char defaultValue) {
        String value = loadString(key);
        return value == null ? defaultValue : value.charAt(0);
    }

    @Override
    public char[] getCharArray(String key) {
        String text = loadString(key);
        if (!CHAR_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = CHAR_PATTERN.matcher(text);
        List<Character> chars = new ArrayList<Character>();
        while (matcher.find()) {
            String charStr = matcher.group(1);
            if (charStr.length() == 2) {
                switch (charStr.charAt(1)) {
                    case 't':
                        chars.add('\t');
                        break;
                    case 'b':
                        chars.add('\b');
                        break;
                    case 'n':
                        chars.add('\n');
                        break;
                    case 'r':
                        chars.add('\r');
                        break;
                    case 'f':
                        chars.add('\f');
                        break;
                }
            } else {
                chars.add(charStr.charAt(0));
            }
        }
        char[] array = new char[chars.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = chars.get(i);
        }
        return array;
    }

    @Override
    public Character[] getCharacterArray(String key) {
        String text = loadString(key);
        if (!CHAR_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = CHAR_PATTERN.matcher(text);
        List<Character> chars = new ArrayList<Character>();
        while (matcher.find()) {
            String charStr = matcher.group(1);
            if (charStr.length() == 2) {
                switch (charStr.charAt(1)) {
                    case 't':
                        chars.add('\t');
                        break;
                    case 'b':
                        chars.add('\b');
                        break;
                    case 'n':
                        chars.add('\n');
                        break;
                    case 'r':
                        chars.add('\r');
                        break;
                    case 'f':
                        chars.add('\f');
                        break;
                }
            } else {
                chars.add(charStr.charAt(0));
            }
        }
        return chars.toArray(new Character[0]);
    }

    @Override
    public Color getColor(String key) {
        return getColor(key, null);
    }

    @Override
    public Color getColor(String key, Color defaultValue) {
        String value = loadString(key);
        Color color = defaultValue;
        if (value != null) {
            if (!COLOR_PATTERN.matcher(value).matches()) {
                String message = String.format("invalid color value (%s) : %s",
                        key, value);
                logger.log(Level.WARNING, message);
                throw new IllegalArgumentException(message);
            }
            if(RAW_COLOR_PATTERN.matcher(value).matches()) {
                int r = Integer.parseInt(value.substring(1, 3), 16);
                int g = Integer.parseInt(value.substring(3, 5), 16);
                int b = Integer.parseInt(value.substring(5, 7), 16);
                if(value.length() == 9) {
                    int a = Integer.parseInt(value.substring(7, 9), 16);
                    color = new Color(r, g, b, a);
                } else {
                    color = new Color(r, g, b);
                }
            } else {
                color = colorsCache.get(value.toUpperCase());
            }
        }
        return color;
    }

    @Override
    public Color[] getColorArray(String key) {
        String text = loadString(key);
        if(!COLOR_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = COLOR_PATTERN.matcher(text);
        List<Color> colors = new ArrayList<Color>();
        while (matcher.find()) {
            String colorStr = matcher.group();
            Color color = null;
            if(RAW_COLOR_PATTERN.matcher(colorStr).matches()) {
                int r = Integer.parseInt(colorStr.substring(1, 3), 16);
                int g = Integer.parseInt(colorStr.substring(3, 5), 16);
                int b = Integer.parseInt(colorStr.substring(5, 7), 16);
                if(colorStr.length() == 9) {
                    int a = Integer.parseInt(colorStr.substring(7, 9), 16);
                    color = new Color(r, g, b, a);
                } else {
                    color = new Color(r, g, b);
                }
            } else {
                color = colorsCache.get(colorStr.toUpperCase());
            }
            colors.add(color);
        }
        return colors.toArray(new Color[0]);
    }

    @Override
    public Date getDate(String key) {
        return getDate(key, null);
    }

    @Override
    public Date getDate(String key, Date defaultValue) {
        Date date = null;
        String value = loadString(key);
        try {
            date = value == null ? defaultValue : dateFormat.parse(value);
        } catch (ParseException ex) {
            Logger.getLogger(AbstractResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        String value = loadString(key);
        return value == null ? defaultValue : Double.parseDouble(value);
    }

    @Override
    public double[] getDoubleArray(String key) {
        String text = loadString(key);
        if (!DECIMAL_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = DECIMAL_PATTERN.matcher(text);
        List<Double> doubles = new ArrayList<Double>();
        while (matcher.find()) {
            doubles.add(Double.parseDouble(matcher.group()));
        }
        double[] array = new double[doubles.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = doubles.get(i);
        }
        return array;
    }

    @Override
    public Double[] getDoubleObjectArray(String key) {
        String text = loadString(key);
        if (!DECIMAL_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = DECIMAL_PATTERN.matcher(text);
        List<Double> doubles = new ArrayList<Double>();
        while (matcher.find()) {
            doubles.add(Double.parseDouble(matcher.group()));
        }
        return doubles.toArray(new Double[0]);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0.0F);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        String value = loadString(key);
        return value == null ? defaultValue : Float.parseFloat(value);
    }

    @Override
    public float[] getFloatArray(String key) {
        String text = loadString(key);
        if (!DECIMAL_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = DECIMAL_PATTERN.matcher(text);
        List<Float> floats = new ArrayList<Float>();
        while (matcher.find()) {
            floats.add(Float.parseFloat(matcher.group()));
        }
        float[] array = new float[floats.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = floats.get(i);
        }
        return array;
    }

    @Override
    public Float[] getFloatObjectArray(String key) {
        String text = loadString(key);
        if (!DECIMAL_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = DECIMAL_PATTERN.matcher(text);
        List<Float> floats = new ArrayList<Float>();
        while (matcher.find()) {
            floats.add(Float.parseFloat(matcher.group()));
        }
        return floats.toArray(new Float[0]);
    }

    @Override
    public String getFormattedString(String key, Object... arguments) {
        String value = getString(key);
        return value == null ? null : MessageFormat.format(value, arguments);
    }


    @Override
    public Image getImage(String key) throws IOException {
        Image image = null;
        SoftReference<Image> ref = imagesRef.get(key);
        if (ref != null) {
            image = ref.get();
        }
        if (image == null) {
            image = loadImage(key);
            imagesRef.put(key, new SoftReference<Image>(image));
        }
        return image;
    }

    @Override
    public int getInt(String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return getInt(key, defaultValue, 10);
    }

    @Override
    public int getInt(String key, int defaultValue, int radix) {
        String value = loadString(key);
        return value == null ? defaultValue : Integer.parseInt(value, radix);
    }

    @Override
    public int[] getIntArray(String key) {
        return getIntArray(key, 10);
    }

    @Override
    public int[] getIntArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Integer> ints = new ArrayList<Integer>();
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group(), radix));
        }
        int[] array = new int[ints.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ints.get(i);
        }
        return array;
    }

    @Override
    public Integer[] getIntegerArray(String key) {
        return getIntegerArray(key, 10);
    }

    @Override
    public Integer[] getIntegerArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Integer> ints = new ArrayList<Integer>();
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group(), radix));
        }
        return ints.toArray(new Integer[0]);
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return getLong(key, defaultValue, 10);
    }

    @Override
    public long getLong(String key, long defaultValue, int radix) {
        String value = loadString(key);
        return value == null ? defaultValue : Long.parseLong(value, radix);
    }

    @Override
    public long[] getLongArray(String key) {
        return getLongArray(key, 10);
    }

    @Override
    public long[] getLongArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Long> longs = new ArrayList<Long>();
        while (matcher.find()) {
            longs.add(Long.parseLong(matcher.group(), radix));
        }
        long[] array = new long[longs.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = longs.get(i);
        }
        return array;
    }

    @Override
    public Long[] getLongObjectArray(String key) {
        return getLongObjectArray(key, 10);
    }

    @Override
    public Long[] getLongObjectArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Long> longs = new ArrayList<Long>();
        while (matcher.find()) {
            longs.add(Long.parseLong(matcher.group(), radix));
        }
        return longs.toArray(new Long[0]);
    }

    @Override
    public short getShort(String key) {
        short defaultValue = 0;
        return getShort(key, defaultValue);
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return getShort(key, defaultValue, 10);
    }

    @Override
    public short getShort(String key, short defaultValue, int radix) {
        String value = loadString(key);
        return value == null ? defaultValue : Short.parseShort(value, radix);
    }

    @Override
    public short[] getShortArray(String key) {
        return getShortArray(key, 10);
    }

    @Override
    public short[] getShortArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Short> shorts = new ArrayList<Short>();
        while (matcher.find()) {
            shorts.add(Short.parseShort(matcher.group(), radix));
        }
        short[] array = new short[shorts.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = shorts.get(i);
        }
        return array;
    }

    @Override
    public Short[] getShortObjectArray(String key) {
        return getShortObjectArray(key, 10);
    }

    @Override
    public Short[] getShortObjectArray(String key, int radix) {
        String text = loadString(key);
        if (!INT_ARRAY_PATTERN.matcher(text).matches()) {
            throw new IllegalArgumentException("invalid array format");
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        List<Short> shorts = new ArrayList<Short>();
        while (matcher.find()) {
            shorts.add(Short.parseShort(matcher.group(), radix));
        }
        return shorts.toArray(new Short[0]);
    }

    @Override
    public String getString(String key) {
        return loadString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = loadString(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Loads and returns the image resource identified by the specified key.
     * @param key the resource key
     * @return the image resource identified by the specified key.
     * @throws IOException if an I/O error occurs when loading the image.
     */
    protected abstract Image loadImage(String key) throws IOException;

    /**
     * Loads and returns the resource identified by the specified key.
     * @param key the resource key.
     * @return the resource identified by the specified key.
     */
    protected String loadString(String key) {
        return resources.get(key);
    }

    private static Map<String, Color> colorsCache = new HashMap<String, Color>();
    static {
        colorsCache.put("BLACK", Color.BLACK);
        colorsCache.put("BLUE", Color.BLUE);
        colorsCache.put("CYAN", Color.CYAN);
        colorsCache.put("DARK GRAY", Color.DARK_GRAY);
        colorsCache.put("GRAY", Color.GRAY);
        colorsCache.put("GREEN", Color.GREEN);
        colorsCache.put("LIGHT GRAY", Color.LIGHT_GRAY);
        colorsCache.put("MAGENTA", Color.MAGENTA);
        colorsCache.put("ORANGE", Color.ORANGE);
        colorsCache.put("PINK", Color.PINK);
        colorsCache.put("RED", Color.RED);
        colorsCache.put("WHITE", Color.WHITE);
    }

}
