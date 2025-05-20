package bible.util;

import java.io.*;
import java.util.*;

import bible.obj.*;

/**
 * Utility methods
 */
public class Util {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String ToString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    public static String ToString(Vector v) {
        return ToString(v, false, ", ");
    }

    public static String ToString(Vector v, boolean includeIndex, String separator) {
        Object element;
        if(v != null) {
            Object[] objs = new Object[v.size()];
            v.copyInto(objs);
            return ToString(objs, includeIndex, separator);
        } else {
            return "null";
        }
    }

    public static String ToString(int[] numbers) {
        if(numbers.length == 0) {
            return "";
        }
        StringBuffer s = new StringBuffer();
        for(int index = 0; index < numbers.length - 1; index++) {
            s.append(numbers[index]).append(", ");
        }
        return s.append(numbers[numbers.length - 1]).toString();
    }

    public static String ToString(Object[] objects) {
        return ToString(objects, false, ", ");
    }

    public static String ToString(Object[] objects, boolean includeIndex, String separator) {
        if(objects == null) {
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        for(int index = 0; index < objects.length; index++) {
            if(includeIndex) {
                sb.append(index).append(" - ");
            }
            if(objects[index] instanceof Vector) {
                sb.append(ToString((Vector)objects[index], includeIndex, separator));
            } else if(objects[index] == null){
                sb.append("null").append(separator);
            } else {
                sb.append(objects[index].toString()).append(separator);
            }
        }
        String s = sb.toString();
        if(s.endsWith(separator)) {
            s = s.substring(0, s.length() - separator.length());
        }// else objects.length == 0
        return s;
    }

    public static String ToString(String[] items, String separator) {
        StringBuffer s = new StringBuffer();
        for(int index = 0; index < items.length - 1; index++) {
            s.append(items[index]).append(separator);
        }
        if(items.length > 0) {
            s.append(items[items.length - 1]);
        }
        return s.toString();
    }

    public static String[] ToStringArray(Object[] objects) {
        String[] returnValues = new String[objects.length];
        for(int index = 0; index < objects.length; index++) {
            returnValues[index] = objects[index].toString();
        }
        return returnValues;
    }

    public static String[] ToStringArray(Vector objects) {
        Object element;
        if(objects != null) {
            String[] strings = new String[objects.size()];
            objects.copyInto(strings);
            return strings;
        } else {
            return new String[0];
        }
    }

    public static String[] ToStringArray(String items, String separator) {
        StringTokenizer st = new StringTokenizer(items, separator, false);
        int count = st.countTokens();
        String [] returnValue = new String[count];
        for(int index = 0; index < count; index++) {
            returnValue[index] = st.nextToken();
        }
        return returnValue;
    }

    public static int[] ToIntArray(String items, String separator) {
        StringTokenizer st = new StringTokenizer(items, separator, false);
        int count = st.countTokens();
        int [] returnValue = new int[count];
        for(int index = 0; index < count; index++) {
            returnValue[index] = Integer.parseInt(st.nextToken());
        }
        return returnValue;
    }

    public static int[] ToIntArray(String[] items) {
        int[] returnValue = new int[items.length];
        for(int index = 0; index < items.length; index++) {
            returnValue[index] = Integer.parseInt(items[index]);
        }
        return returnValue;
    }

    public static int[] ToIntArray(Identifible[] items) {
        int[] returnValue = new int[items.length];
        for(int index = 0; index < items.length; index++) {
            returnValue[index] = items[index].getId();
        }
        return returnValue;
    }

    public static Vector ToVector(Object[] objects) {
        Vector position = new Vector(objects.length);
        for (int index = 0; index < objects.length; index++) {
            position.addElement(objects[index]);
        }
        return position;
    }


    public static Vector ToVector(int[] numbers) {
        Vector position = new Vector(numbers.length);
        for (int index = 0; index < numbers.length; index++) {
            position.addElement(new Integer(numbers[index]));
        }
        return position;
    }

    public static String Replace(String original, String from, String to) {
        String replaced = original;
        String start;
                                                            // look for the next from after the last replacement
        for(int index = replaced.indexOf(from); index >= 0; index = replaced.indexOf(from, index + to.length())) {

            start = replaced.substring(0, index) + to;
            if(index + from.length() + 1 <= replaced.length()) {// There is something after replaced piece
                replaced = start + replaced.substring(index + from.length());
            } else {
                replaced = start;
            }
        }
        return replaced;
    }

    public static long GetSize(Object obj) throws IOException {
        long objSize = -1;
        if (obj instanceof Serializable){
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(obj);
                oos.flush();
                objSize = os.size();
                os.close();
            } catch(Exception e) {
                Logger.Log(e, "obj=" + obj);
            }
        }
        return objSize;
    }
}

