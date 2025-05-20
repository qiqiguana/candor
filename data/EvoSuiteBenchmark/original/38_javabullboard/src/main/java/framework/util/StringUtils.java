package framework.util;

import java.io.File;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.regexp.RE;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.ApplicationParameters;

/**
 * String utility class
 * For info:
 * t u0009 HORIZONTAL TABULATION 
 * n u000A NEW LINE 
 * f u000C FORM FEED 
 * r u000D CARRIAGE RETURN 
 * '  ' \u0020 SPACE 
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public final class StringUtils
{
  // Logger
  private static Log log = LogFactory.getLog(StringUtils.class);

  // Useful patterns
  private static final String PATTERN_WHITESPACE = "^\\s*$";
  private static final String PATTERN_POSITIVE_INTEGER = "^\\d+$";
  private static final String PATTERN_POSITIVE_FLOAT = "^\\d+\\.?\\d*$";
  private static final String PATTERN_INTEGER = "^-?\\d+$";
  private static final String PATTERN_FLOAT = "^-?\\d+\\.?\\d*$";
  private static final String PATTERN_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
  private static final String PATTERN_BOOLEAN = "true|false|0|1";

  /**
   * Protected constructor
   */
  protected StringUtils()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public static String getKeywordPath()
  throws Exception
  {
    return ApplicationParameters.getAsMandatoryString("framework.util.string.keyword.path");
  }

  public static String getKeywordDirectory()
  throws Exception
  {
    return ApplicationParameters.FRAMEWORK_APPLICATION_BASE_DIRECTORY+getKeywordPath();
  }

  public static String getKeywordFileExtension()
  {
    return ApplicationParameters.getAsString("framework.util.string.keyword.fileExtension", "kw");
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get keywords from a file
   * @param fileName The fileName
   * @return The map of keywords
   */
  public static Map getKeywords(String fileName)
  throws Exception
  {
    String fullPath = getKeywordDirectory()+File.separator+fileName+"."+getKeywordFileExtension();
    File wordFile = new File(fullPath);
    String wordString = FileUtils.toString(wordFile);
    List wordList = stringToWordList(wordString);
    Map wordMap = new HashMap();
    Iterator it = wordList.iterator();
    while (it.hasNext()) 
    {
      String word = ((String)it.next()).trim().toUpperCase();
      wordMap.put(word, word);
    }
    return wordMap;    
  }

  /**
   * Reformat an string
   * @param keywords The Map containing the keywords
   * @param string The string to format
   * @param preffix The preffix to add
   * @param suffix The suffix to add
   * @param caseMethod The case method to apply ("toUpperCase", "toLowerCase" or null for unchange)
   * @return The formatted string
   */ 
  public static String prettyPrint( Map keywords, 
                                    String string, 
                                    String preffix, 
                                    String suffix, 
                                    String caseMethod)
  throws Exception 
  {
    if (!exists(string)) return string;
    preffix = preffix==null?"":preffix;
    suffix = suffix==null?"":suffix;
    boolean isUpperCase = "toUpperCase".equalsIgnoreCase(caseMethod);
    boolean isLowerCase = "toLowerCase".equalsIgnoreCase(caseMethod);
    
    List words = StringUtils.stringToWordList(string);
    Iterator it = words.iterator();
    while (it.hasNext()) 
    {
      String word = (String)it.next();
      boolean isKeyword = keywords.get(word.toUpperCase())!=null;
      if (isKeyword) 
      {
        String newWord = word;
        if (isUpperCase) newWord = newWord.toUpperCase();
        else if (isLowerCase)  newWord = newWord.toLowerCase();
        string = StringUtils.replace(string, word, preffix+newWord+suffix);
      }
    }
    return string;
  }

  /**
   * Test if a String is null or only contains whitespaces
   * Note: The pattern always validate empty string, so set acceptNull to false
   * to reject null or empty values
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is null or whitespace
   */
  public static boolean isWhitespace(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_WHITESPACE, value, acceptNull);
  }

  /**
   * Test if a String is a positive integer
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is a positive integer
   */
  public static boolean isPositiveInteger(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_POSITIVE_INTEGER, value, acceptNull);
  }

  /**
   * Test if a String is a positive float
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is a positive float
   */
  public static boolean isPositiveFloat(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_POSITIVE_FLOAT, value, acceptNull);
  }

  /**
   * Test if a String is an integer (can be signed)
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is an integer (can be signed)
   */
  public static boolean isInteger(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_INTEGER, value, acceptNull);
  }

  /**
   * Test if a String is an float (can be signed)
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is an float (can be signed)
   */
  public static boolean isFloat(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_FLOAT, value, acceptNull);
  }

  /**
   * Test if a String is a valid Email
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is a valid Email
   */
  public static boolean isEmail(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_EMAIL, value, acceptNull);
  }

  /**
   * Test if a String is a valid boolean
   * Valid values are "0", "1", "true", "false"
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value is a valid boolean
   */
  public static boolean isBoolean(String value, boolean acceptNull)
  {
    return matchPattern(PATTERN_BOOLEAN, value, acceptNull);
  }

  /**
   * Test if a String math a pattern (regular expression)
   * @param pattern The regular expression
   * @param value The value to test
   * @param acceptNull true if null or empty value is accepted
   * @return true if value match the pattern
   */
  public static boolean matchPattern(String pattern, String value, boolean acceptNull)
  {
    boolean matched = false;

    // Handle null value
    if (!StringUtils.exists(value)) return acceptNull;

    // Run RegExp    
    try 
    {
      RE r = new RE(pattern, RE.MATCH_CASEINDEPENDENT);
      matched = r.match(value);
      if (log.isDebugEnabled()) log.debug("pattern="+pattern+" value="+value+" matched="+matched);
    } 
    catch (Exception e) 
    {
      log.error("matchPattern:", e);
    }
    return matched;
  }

  /**
   * Converts an array of String into a list of String
   * @param stringArray The String[] to convert
   * @return a List of String
   */
  public static List stringArrayToList (String[] stringArray)
  {
    return Arrays.asList(stringArray); 
  }

  /** 
   * Converts a list of String into an array of String
   * @param list The list of String to convert
   * @return a String[] with all the seperate elements
   */
  public static String[] listToStringArray(List list)
  {
    return (String[])list.toArray(new String[list.size()]);
  }
    
  /**
   * Splits a string with arguments seperated by a delmimiter into its parts
   * @param string The string to be split, for example "abc,def,xyz"
   * @param delimiter The delimiter string
   * @return The list with all the elements as String
   */
  public static List stringToList(String string, String delimiter)
  {
    StringTokenizer st = new StringTokenizer(string, delimiter);
    List elements = new ArrayList(st.countTokens());

    String element = null;
    while(st.hasMoreTokens())
    {
      element = ((String)st.nextToken()).trim();
      elements.add(element);
    }
    return elements;
  }

  /**
   * Splits a string with arguments seperated by a delmimiter into its parts
   * @param string The string to be split, for example "abc,def,xyz"
   * @param delimiter The delimiter char
   * @return The list with all the elements as String
   */
  public static List stringToList(String string, char delimiter)
  {
    List result = new ArrayList();
    int begin = 0;

    int end = string.indexOf(delimiter);
    while (true)
    {
      String token = null;
      if (end == -1) token = string.substring(begin);
      else token = string.substring(begin, end);

      if (token!=null && token.length()>0) result.add(token.trim());
      // End if there are no more delimiters
      if (end == -1) break;
      begin = end + 1;
      end = string.indexOf(delimiter, begin);
    }
    return result;
  }

  /** 
   * Splits a string with arguments seperated by a delimiter into its parts
   * @param string The string to be split, for example "abc,def,xyz"
   * @param delimiter The delimiter string
   * @return a String[] with all the seperate elements
   */
  public static String[] stringToStringArray (String string, String delimiter)
  {
    List list = stringToList(string, delimiter);
    return listToStringArray(list);
  }

  /** 
   * Trims a string and if the end result is "", it returns null.
   * This method is especially useful if you run into the following
   * browser and/or servlet engine bug:
   * when a checkbox is changed from checked to unchecked, the returned
   * value is not null but " " (netscape) or "" (internet explorer).
   * @param string The String to be trimmed
   * @return The trimmed String or null if the trimmed result is ""
   */
  public static String trimEmptyToNull(String string)
  {
    if (string == null || "".equals(string.trim())) return null;
    else return string;
  }

  /**
   * Replace pattern by replacement
   * @param inString Search pattern in this String
   * @param patternToReplace Pattern to search
   * @param replacement The replacement of the pattern
   */
  public static String replacePattern(String inString, String patternToReplace, String replacement)
  {
    Map patternReplacements = new HashMap();
    patternReplacements.put(patternToReplace, replacement);
    return replacePattern(inString, patternReplacements);
  }

  /**
   * Replace patterns by replacements (DOTALL mode)
   * In dotall mode, the expression . matches any character, including a line terminator
   * @param inString Search patterns in this String
   * @param patternReplacements Map containing patterns and their replacements
   */
  public static String replacePattern(String inString, Map patternReplacements)
  {
    String result = inString;
    Iterator it = patternReplacements.keySet().iterator();
    while (it.hasNext()) 
    {
      String patternToReplace = (String)it.next();
      String replacement = (String)patternReplacements.get(patternToReplace);
      Pattern pattern = Pattern.compile(patternToReplace, Pattern.DOTALL);
      Matcher matcher = pattern.matcher(result);
      result = matcher.replaceAll(replacement);
    }    
    return result;
  }

  /**
   * Replace a part of a String by another
   * @param s String to processed
   * @param sub String to be substitute
   * @param with String to substitue for sub
   * @return The result String
   */
  public static String replace(String s, String sub, String with)
  {
    if (s==null) return null;
    if (sub==null || with==null) throw new IllegalArgumentException("Cannot accept null argument");
    
    int c = 0;
    int i = s.indexOf(sub,c);
    if (i == -1) return s;
    
    StringBuffer buf = new StringBuffer(s.length()+with.length());

    synchronized(buf)
    {
      do
      {
        buf.append(s.substring(c,i));
        buf.append(with);
        c = i+sub.length();
      } 
      while ((i=s.indexOf(sub,c))!=-1);
            
      if (c<s.length()) buf.append(s.substring(c,s.length()));
            
      return buf.toString();
    }
  }

  /**
   * Capitalize first letters of each word of a String
   * @param text The string to be processed
   * @return The result String
   */
  public static String initCap(String text)
  {
    if (text==null) return null;

    String result = "";
    StringTokenizer st = new StringTokenizer(text, " \t\n\r\f", true);
    while (st.hasMoreElements())
    {
      String token = st.nextToken();
      result += token.substring(0, 1).toUpperCase()+token.substring(1).toLowerCase();
    }
    return result;
  }

  /**
   * Check a string existence
   * @param value The string value
   * @return false if string is empty or null, else true
   */
  public static boolean exists(String value)
  {
    return (value!=null && value.length()>0);
  }

  /**
   * Return a empty String if value is null, else return value
   * @param value The string value
   * @return The result String
   */
  public static String valueOf(Object value)
  {
    return value==null?"":String.valueOf(value);
  }

  /**
   * Return the String with the first character in uppercase
   *
   * @param value The string value
   * @return The result String
   */
  public static String toUpperFirstChar(String value)
  {
    if (!StringUtils.exists(value)) return value;

    StringBuffer stringbuffer = new StringBuffer(value);
    stringbuffer.setCharAt(0, Character.toUpperCase(stringbuffer.charAt(0)));
    return stringbuffer.toString();
  }

  /**
   * Return a Java style name from the string 
   * @param value The string value
   * @return The result String
   */
  public static String getJavaName(String value)
  {
    if (!StringUtils.exists(value)) return value;

    StringBuffer stringbuffer = new StringBuffer(value.length());
    StringTokenizer st = new StringTokenizer(value, "_#$ \".\\/-+*=(),", false);
    while (st.hasMoreTokens())
    {
      String s2 = st.nextToken();
      s2 = s2.toLowerCase();
      s2 = toUpperFirstChar(s2);
      stringbuffer.append(s2);
    }
      
    String s1 = stringbuffer.toString();
    return s1;
  }

  /**
   * Return true if value is a valid Java name
   * @param value The string value
   * @return true if value is a valid Java name, else false
   */
  public static boolean isJavaName(String value)
  {
    return matchPattern("^[a-zA-Z_]\\w*$", value, false);
  }

  /**
   * Return true if value is a valid Java package name
   * @param value The string value
   * @return true if value is a valid Java name, else false
   */
  public static boolean isJavaPackageName(String value)
  {
    return matchPattern("^[a-zA-Z_]\\w*(\\.[a-zA-Z_]\\w*)*$", value, true);
  }

  /**
   * Replace tokens in a String
   * A token is a array of two Objects: new Object[]  {tokenName, tokenValue}
   * Constants tokenStart and tokenEnd are the token delimiters
   * @param in The String to process
   * @param tokenValues Collection of tokens
   * @return The result String
   */
  public static String replaceTokens(String in, Collection tokenValues, String tokenStart, String tokenEnd)
  {
    String out = in;
    if (tokenValues!=null)
    {
      Iterator it = tokenValues.iterator();
      while (it.hasNext()) 
      {
        Object[] tokenValue = (Object[])it.next();
        out = replace(in, tokenStart+tokenValue[0]+tokenEnd, String.valueOf(tokenValue[1]));
      }
    }
    return out;
  }

  /**
   * Remove space characters from a string
   * @param s String to process
   * @return The result String
   */
  public static String removeWhitespaces(String s)
  {
    if (s==null) return null;
    char[] in = s.toCharArray();
    StringBuffer out = new StringBuffer();
    
    for (int i=0; i<in.length; i++) if (!Character.isWhitespace(in[i])) out.append(in[i]);
    return out.toString();
  }

  /**
   * Reformat a String
   * @param s String to process
   * @return The result String
   */
  public static String reformat(String s)
  {
    if (s==null) return null;
    StringBuffer out = new StringBuffer();
    List list = stringToWordList(s);
    Iterator it = list.iterator();
    while (it.hasNext()) out.append(it.next()).append(" ");   
    return out.toString();
  }

  /**
   * Get the list of words in the string
   * @param s String to process
   * @return The list of words in the string
   */
  public static List stringToWordList(String s)
  {
    return stringToList(s, " \t\n\r\f");
  }

}
