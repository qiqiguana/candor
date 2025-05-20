package framework.util;

import java.sql.Timestamp;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.BigInteger;

import framework.ApplicationParameters;

/**
 * Convertions utility class
 * <li>Convert String => Object</li>
 * <li>Convert Object => String</li>
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public final class ConvertUtils
{

  // Logger
  private static Log log = LogFactory.getLog(ConvertUtils.class);

  // Accept "1" as true and "0" as false
  public static final String STRING_BOOLEAN_FALSE = "0";
  public static final String STRING_BOOLEAN_TRUE  = "1";

  /**
   * Protected constructor
   */
  protected ConvertUtils()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public static String getDefaultDatePattern()
  {
    return ApplicationParameters.getAsString("framework.util.convert.defaultDatePattern", "yyyy-MM-dd");
  }

  public static String getDefaultTimePattern()
  {
    return ApplicationParameters.getAsString("framework.util.convert.defaultTimePattern", "hh:mm:ss");
  }

  public static String getDefaultTimestampPattern()
  {
    return getDefaultDatePattern()+" "+getDefaultTimePattern();
  }

  public static Locale getDefaultLocale()
  {
    return ApplicationParameters.getDefaultLocale();
  }


////////////////////////////////////////////////////////////////////////////////
//        CONVERSION METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Convert a int into a string
   * @param obj int to convert
   * @return The object in string format
   */
  public static String convertString(int obj) 
  {
    return String.valueOf(obj);
  }

  /**
   * Convert a boolean into a string
   * @param obj int to convert
   * @return The object in string format
   */
  public static String convertString(boolean obj) 
  {
    return String.valueOf(obj);
  }

  /**
   * Convert a object into a string with the default date format and locale
   * @param obj Object to convert
   * @return The object in string format
   */
  public static String convertString(Object obj) 
  {
    return convertString(obj, null, null);
  }
  
  /**
   * Convert a object into a string 
   * If obj is Date, the specified datePattern and locale are applied
   * If obj is Timestamp, the specified datePattern is applied
   * @param obj Object to convert
   * @param datePattern Date format
   * @param locale Locale
   * @return The object in string format
   */
  public static String convertString(Object obj, String datePattern, Locale locale) 
  {
    String result = null;
    
    if (obj instanceof java.sql.Timestamp) 
    {
      result = convertString((java.sql.Timestamp)obj, datePattern, locale);
    }
    else if (obj instanceof java.sql.Date) 
    {
      result = convertString(new java.util.Date(((java.sql.Date)obj).getTime()), datePattern, locale);
    }
    else if (obj instanceof java.util.Date) 
    {
      result = convertString((java.util.Date)obj, datePattern, locale);
    }
    else result = String.valueOf(obj);

    return result;
  }
 
  /**
   * Convert a java util date into a string with the specified datePattern
   * If datePattern is null, default date format is applied
   * @param date Date to convert
   * @param datePattern Date format
   * @param locale Locale
   * @return The date in string format
   */
  public static String convertString(java.util.Date date, String datePattern, Locale locale) 
  {
    if (date==null) return null;
    if (datePattern==null) datePattern = getDefaultDatePattern();
    if (locale==null) locale = getDefaultLocale();

    try
    {
      SimpleDateFormat stringFormat = new SimpleDateFormat(datePattern, locale);
      return stringFormat.format(date);
    }
    catch(Exception e)
    {
      log.warn("convertString: Error converting '"+date+"' returning null", e);
      return null;
    }
  } 

  /**
   * Convert a java sql timestamp into a string with the specified datePattern
   * If datePattern is null, default date format is applied
   * @param timestamp Timestamp to convert
   * @param datePattern Date format
   * @param locale Locale
   * @return The date in string format
   */
  public static String convertString(Timestamp timestamp, String datePattern, Locale locale) 
  {
    if (timestamp==null) return null;
    if (datePattern==null) datePattern = getDefaultTimestampPattern();
    if (locale==null) locale = getDefaultLocale();

    try
    {
      SimpleDateFormat stringFormat = new SimpleDateFormat(datePattern, locale);
      return stringFormat.format(timestamp);
    }
    catch(Exception e)
    {
      log.warn("convertString: Error converting '"+timestamp+"' returning null", e);
      return null;
    }
  } 

  /**
   * Convert a String into the target Class 
   * If Class is Date, default date format and locale are applied
   * If Class is Timestamp, the specified datePattern is applied
   * @param  value String to convert
   * @param  clazz Class for the String to be converted in
   * @return The object
   */
  public static Object convert(String value, Class clazz)
  {
    return convert(value, clazz, null, null);
  }

  /**
   * Convert a String into the target Class 
   * If datePattern is null, default date format is applied
   * If locale is null, default locale is applied
   * @param  value String to convert
   * @param  clazz Class for the String to be converted in
   * @param datePattern Date format
   * @param locale Locale
   * @return The object
   */
  public static Object convert(String value, Class clazz, String datePattern, Locale locale)
  {
  
    if (value == null) return null;
    else if (clazz == String.class) return (value);
    else if (clazz == Integer.TYPE) return (convertInteger(value));
    else if (clazz == Boolean.TYPE) return (convertBoolean(value));
    else if (clazz == Long.TYPE) return (convertLong(value));
    else if (clazz == Double.TYPE) return (convertDouble(value));
    else if (clazz == Character.TYPE) return (convertCharacter(value));
    else if (clazz == Byte.TYPE) return (convertByte(value));
    else if (clazz == Float.TYPE) return (convertFloat(value));
    else if (clazz == Short.TYPE) return (convertShort(value));
    else if (clazz == BigInteger.class) return (convertBigInteger(value));
    else if (clazz == BigDecimal.class) return (convertBigDecimal(value));
    else if (clazz == Integer.class) return (convertInteger(value));
    else if (clazz == Boolean.class) return (convertBoolean(value));
    else if (clazz == Long.class) return (convertLong(value));
    else if (clazz == Double.class) return (convertDouble(value));
    else if (clazz == Character.class) return (convertCharacter(value));
    else if (clazz == Byte.class) return (convertByte(value));
    else if (clazz == Float.class) return (convertFloat(value));
    else if (clazz == Short.class) return (convertShort(value));
    else if (clazz == java.sql.Timestamp.class) return (convertTimestamp(value));
    else if (clazz == java.sql.Date.class) return (convertSqlDate(value, datePattern, locale));
    else if (clazz == java.util.Date.class) return (convertUtilDate(value, datePattern, locale));
    else return (value.toString());
  }

  /**
   * Convert a array of String into a array of target Class 
   * eventually using the specified datePattern
   * Default date format is applied
   * Default locale is applied
   * @param  values String[] to convert
   * @param  clazz Class for the Strings to be converted in
   * @return The array of object
   */
  public static Object convert(String values[], Class clazz)
  {
    return convert(values, clazz, null, null);
  }

  /**
   * Convert a array of String into a array of target Class 
   * eventually using the specified datePattern
   * if datePattern is null, default date format is applied
   * @param  values String[] to convert
   * @param  clazz Class for the Strings to be converted in
   * @param datePattern Date format
   * @return The array of object
   */
  public static Object convert(String values[], Class clazz, String datePattern, Locale locale)
  {
    if (values == null) return (null);
    int len = values.length;

    if (clazz == String.class) 
    {
      return (values);
    }
    else if (clazz == Integer.TYPE)
    {
      int array[] = new int[len];
      for (int i = 0; i < len; i++) array[i] = convertInteger(values[i]).intValue();
      return (array);
    } 
    else if (clazz == Boolean.TYPE) 
    {
      boolean array[] = new boolean[len];
      for (int i = 0; i < len; i++) array[i] = convertBoolean(values[i]).booleanValue();
      return (array);
    } 
    else if (clazz == Long.TYPE) 
    {
      long array[] = new long[len];
      for (int i = 0; i < len; i++) array[i] = convertLong(values[i]).longValue();
      return (array);
    } 
     else if (clazz == Double.TYPE) 
    {
      double array[] = new double[len];
      for (int i = 0; i < len; i++) array[i] = convertDouble(values[i]).doubleValue();
      return (array);
    } 
    else if (clazz == Character.TYPE) 
    {
      char array[] = new char[len];
      for (int i = 0; i < len; i++) array[i] = convertCharacter(values[i]).charValue();
      return (array);
    } 
    else if (clazz == Byte.TYPE) 
    {
      byte array[] = new byte[len];
      for (int i = 0; i < len; i++) array[i] = convertByte(values[i]).byteValue();
      return (array);
    } 
    else if (clazz == Float.TYPE) 
    {
      float array[] = new float[len];
      for (int i = 0; i < len; i++) array[i] = convertFloat(values[i]).floatValue();
      return (array);
    } 
    else if (clazz == Short.TYPE) 
    {
      short array[] = new short[len];
      for (int i = 0; i < len; i++) array[i] = convertShort(values[i]).shortValue();
      return (array);
    } 
    else if (clazz == BigInteger.class) 
    {
      BigInteger array[] = new BigInteger[len];
      for (int i = 0; i < len; i++) array[i] = convertBigInteger(values[i]);
      return (array);
    } 
    else if (clazz == BigDecimal.class) 
    {
      BigDecimal array[] = new BigDecimal[len];
      for (int i = 0; i < len; i++) array[i] = convertBigDecimal(values[i]);
      return (array);
    } 
    else if (clazz == Integer.class) 
    {
      Integer array[] = new Integer[len];
      for (int i = 0; i < len; i++) array[i] = convertInteger(values[i]);
      return (array);
    } 
    else if (clazz == Boolean.class) 
    {
      Boolean array[] = new Boolean[len];
      for (int i = 0; i < len; i++) array[i] = convertBoolean(values[i]);
      return (array);
    } 
    else if (clazz == Long.class) 
    {
      Long array[] = new Long[len];
      for (int i = 0; i < len; i++) array[i] = convertLong(values[i]);
      return (array);
    } 
    else if (clazz == Double.class) 
    {
      Double array[] = new Double[len];
      for (int i = 0; i < len; i++) array[i] = convertDouble(values[i]);
      return (array);
    } 
    else if (clazz == Character.class) 
    {
      Character array[] = new Character[len];
      for (int i = 0; i < len; i++) array[i] = convertCharacter(values[i]);
      return (array);
    } 
    else if (clazz == Byte.class) 
    {
      Byte array[] = new Byte[len];
      for (int i = 0; i < len; i++) array[i] = convertByte(values[i]);
      return (array);
    } 
    else if (clazz == Float.class) 
    {
      Float array[] = new Float[len];
      for (int i = 0; i < len; i++) array[i] = convertFloat(values[i]);
      return (array);
    } 
    else if (clazz == Short.class) 
    {
      Short array[] = new Short[len];
      for (int i = 0; i < len; i++) array[i] = convertShort(values[i]);
      return (array);
    }
    else if (clazz == java.sql.Timestamp.class)
    {
      java.sql.Timestamp array[] = new java.sql.Timestamp[len];
      for (int i = 0; i < len; i++) array[i] = convertTimestamp(values[i], datePattern, locale);
      return (array);
    }
    else if (clazz == java.sql.Date.class)
    {
      java.sql.Date array[] = new java.sql.Date[len];
      for (int i = 0; i < len; i++) array[i] = convertSqlDate(values[i], datePattern, locale);
      return (array);
    }
    else if (clazz == java.util.Date.class)
    {
      java.util.Date array[] = new java.util.Date[len];
      for (int i = 0; i < len; i++) array[i] = convertUtilDate(values[i], datePattern, locale);
      return (array);
    }
    else 
    {
      String array[] = new String[len];
      for (int i = 0; i < len; i++) array[i] = values[i].toString();
      return (array);
    }
  }

  /**
   * Convert a String into a Byte
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Byte object
   */
  public static Byte convertByte(String value)
  {
    return convertByte(value, null);
  }

  /**
   * Convert a String into a Byte
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Byte object
   */
  private static Byte convertByte(String value, Byte defaultValue)
  {
    try
    {
      return (new Byte(value));
    }
    catch(Exception e)
    {
      log.warn("convertByte: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Float
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Float object
   */
  public static Float convertFloat(String value)
  {
    return convertFloat(value, null);
  }

  /**
   * Convert a String into a Float
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Float object
   */
  private static Float convertFloat(String value, Float defaultValue)
  {
    try
    {
      return (new Float(value));
    }
    catch(Exception e)
    {
      log.warn("convertFloat: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Short
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Short object
   */
  public static Short convertShort(String value)
  {
    return convertShort(value, null);
  }

  /**
   * Convert a String into a Short
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Short object
   */
  private static Short convertShort(String value, Short defaultValue)
  {
    try
    {
      return (new Short(value));
    }
    catch(Exception e)
    {
      log.warn("convertShort: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Character
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Character object
   */
  public static Character convertCharacter(String value)
  {
    return convertCharacter(value, null);
  }

  /**
   * Convert a String into a Character
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Character object
   */
  private static Character convertCharacter(String value, Character defaultValue)
  {
    try
    {
      return (new Character(value.charAt(0)));
    }
    catch(Exception e)
    {
      log.warn("convertCharacter: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a boolean
   * @param  value String to convert
   * @return The boolean type
   */
  public static boolean convertBooleanType(String value, boolean defaultValue)
  {
    Boolean result = convertBoolean(value);
    return result==null?defaultValue:result.booleanValue();
  }

  /**
   * Convert a String into a Boolean
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Boolean object
   */
  public static Boolean convertBoolean(String value)
  {
    return convertBoolean(value, null);
  }

  /**
   * Convert a String into a Boolean
   * If value equals "1" or "true", return true
   * If value equals "0" or "false", return false
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Boolean object
   */
  public static Boolean convertBoolean(String value, Boolean defaultValue)
  {
    if (STRING_BOOLEAN_TRUE.equalsIgnoreCase(value)
      || "true".equalsIgnoreCase(value))
    {
      return new Boolean(true);
    }
    else if (STRING_BOOLEAN_FALSE.equalsIgnoreCase(value)
      || "false".equalsIgnoreCase(value))
    {
      return new Boolean(false);
    }
    else return defaultValue;
  }

  /**
   * Convert a String into a Double
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Double object
   */
  public static Double convertDouble(String value)
  {
    return convertDouble(value, null);
  }

  /**
   * Convert a String into a Double
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Double object
   */
  public static Double convertDouble(String value, Double defaultValue)
  {
    try
    {
      return (new Double(value));
    } 
    catch (Exception e)
    {
      log.warn("convertDouble: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Long
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Long object
   */
   public static Long convertLong(String value)
  {
    return convertLong(value, null);
  }

  /**
   * Convert a String into a Long
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Long object
   */
  public static Long convertLong(String value, Long defaultValue)
  {
    try
    {
      return (new Long(value));
    } 
    catch (Exception e)
    {
      log.warn("convertLong: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a BigInteger
   * If conversion fails, return null
   * @param  value String to convert
   * @return The BigInteger object
   */
  public static BigInteger convertBigInteger(String value)
  {
    return convertBigInteger(value, null);
  }

  /**
   * Convert a String into a BigInteger
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The BigInteger object
   */
  public static BigInteger convertBigInteger(String value, BigInteger defaultValue)
  {
    try
    {
      return (new BigInteger(value));
    } 
    catch (Exception e)
    {
      log.warn("convertBigInteger: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a BigDecimal
   * If conversion fails, return null
   * @param  value String to convert
   * @return The BigDecimal object
   */
  public static BigDecimal convertBigDecimal(String value)
  {
    return convertBigDecimal(value, null);
  }

  /**
   * Convert a String into a BigDecimal
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The BigDecimal object
   */
  public static BigDecimal convertBigDecimal(String value, BigDecimal defaultValue)
  {
    try
    {
      return (new BigDecimal(value));
    } 
    catch (Exception e)
    {
      log.warn("convertBigDecimal: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Integer
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Integer object
   */
  public static Integer convertInteger(String value)
  {
    return convertInteger(value, null);
  }

  /**
   * Convert a String into a Integer
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Integer object
   */
  public static Integer convertInteger(String value, Integer defaultValue)
  {
    try
    {
      return (new Integer(value));
    } 
    catch (Exception e)
    {
      log.warn("convertInteger: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a int
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Integer object
   */
  public static int convertInt(String value, int defaultValue)
  {
    Integer result = convertInteger(value, null);
    return (result==null?defaultValue:result.intValue());
  }

  /**
   * Convert a String into a Date with the default date format and locale
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Date object
   */
  public static java.sql.Date convertSqlDate(String value)
  {
    return convertSqlDate(value, null, null, null);
  }

  /**
   * Convert a String into a Date with the specified datePattern and locale
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Date object
   */
  public static java.sql.Date convertSqlDate(String value, String datePattern, Locale locale)
  {
    return convertSqlDate(value, datePattern, locale, null);
  }

  /**
   * Convert a String into a Date with the specified datePattern and locale
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Date object
   */
  public static java.sql.Date convertSqlDate(String value, 
                                          String datePattern, 
                                          Locale locale,
                                          java.sql.Date defaultValue)
  {
    java.util.Date date = convertUtilDate(value, datePattern, locale);
    if (date!=null) return new java.sql.Date(date.getTime());
    else return defaultValue;
  }

  /**
   * Convert a String into a Date with the default date format and locale
   * If conversion fails, return null
   * Default date format is applied
   * Default locale is applied
   * @param  value String to convert
   * @return The Date object
   */
  public static java.util.Date convertUtilDate(String value)
  {
    return convertUtilDate(value, null, null, null);
  }

  /**
   * Convert a String into a Date with the specified datePattern and locale
   * If conversion fails, return null
   * @param  value String to convert
   * @return The Date object
   */
  public static java.util.Date convertUtilDate(String value, String datePattern, Locale locale)
  {
    return convertUtilDate(value, datePattern, locale, null);
  }

  /**
   * Convert a String into a Date with the specified datePattern and locale
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @return The Date object
   */
  public static java.util.Date convertUtilDate(String value, 
                                          String datePattern, 
                                          Locale locale,
                                          java.util.Date defaultValue)
  {
    if (datePattern==null) datePattern = getDefaultDatePattern();
    if (locale==null) locale = getDefaultLocale();

    try
    {
      SimpleDateFormat df = new SimpleDateFormat(datePattern, locale);
      df.setLenient(false);
      java.util.Date date = df.parse(value);
      return date;
    } 
    catch (Exception e)
    {
      log.warn("convertUtilDate: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

  /**
   * Convert a String into a Timestamp
   * If conversion fails, return null
   * Default date format is applied
   * Default locale is applied
   * @param  value String to convert
   * @return The Time object
   */
  public static java.sql.Timestamp convertTimestamp(String value)
  {
    return convertTimestamp(value, null, null, null);
  }

  /**
   * Convert a String into a Timestamp
   * If conversion fails, return null
   * @param  value String to convert
   * @param  datePattern Date format
   * @param  locale Locale
   * @return The Time object
   */
  public static java.sql.Timestamp convertTimestamp(String value, String datePattern, Locale locale)
  {
    return convertTimestamp(value, datePattern, locale, null);
  }

  /**
   * Convert a String into a Timestamp
   * If conversion fails, return defaultValue
   * @param  value String to convert
   * @param  datePattern Date format
   * @param  locale Locale
   * @param  defaultValue Default value
   * @return The Time object
   */
  public static java.sql.Timestamp convertTimestamp(String value, 
                                                    String datePattern, 
                                                    Locale locale, 
                                                    java.sql.Timestamp defaultValue)
  {
    try
    {
      java.util.Date date = convertUtilDate(value, datePattern, locale);
      return new java.sql.Timestamp(date.getTime());    
    }
    catch(Exception e)
    {
      log.warn("convertTimestamp: Error converting '"+value+"' returning default value '"+defaultValue+"'", e);
      return (defaultValue);
    }
  }

}
