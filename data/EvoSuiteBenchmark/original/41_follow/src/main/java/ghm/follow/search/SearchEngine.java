package ghm.follow.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches through text based on flags passed at time of search.
 * 
 * @author Carl Hall (carl.hall@gmail.com)
 */
public class SearchEngine
{
	public static final int CASE_SENSITIVE = 1;
	public static final int REGEX = 2;
	private int flags;

	/**
	 * Constructor for search text and tracking display elements for results list
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param textPane
	 */
	public SearchEngine(int flags)
	{
		this.flags = flags;
	}

	/**
	 * Search for <code>term</code>. Use the constants of this class for flags.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param term
	 * @return An array of found positions of term
	 */
	public List<WordResult> search(String term, String text)
	{
		List<WordResult> retval = null;
		// search using a case sensitive regular expression
		if (((flags & CASE_SENSITIVE) != 0) && ((flags & REGEX) != 0))
		{
			Pattern p = Pattern.compile(term, Pattern.MULTILINE);
			retval = regexSearch(p, text);
		}
		// search using a case insensitive regular expression
		else if ((flags & REGEX) != 0)
		{
			Pattern p = Pattern.compile(term, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			retval = regexSearch(p, text);
		}
		// search by case sensitive text
		else if ((flags & CASE_SENSITIVE) != 0)
		{
			retval = textSearch(term, text);
		}
		// search by case insensitive text
		else if (flags == 0)
		{
			retval = textSearch(term.toLowerCase(), text.toLowerCase());
		}
		// fail due to unknown flags
		else
		{
			throw new IllegalArgumentException("Unknown search strategy requested [flags=" + flags);
		}
		return retval;
	}

	/**
	 * Searches <code>text</code> for <code>term</code> by performing a simple text search.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param term
	 * @param text
	 * @return
	 */
	protected List<WordResult> textSearch(String term, String text)
	{
		ArrayList<WordResult> results = new ArrayList<WordResult>();
		if (term != null && term.length() > 0 && text != null && text.length() > 0)
		{
			int pos = 0;
			while ((pos = text.indexOf(term, pos)) > -1)
			{
				results.add(new WordResult(pos, pos + term.length(), term));
				pos += term.length();
				// allow other things to happen in case the search takes a while
				Thread.yield();
			}
		}
		return results;
	}

	/**
	 * Searches <code>text</code> using the provided <code>Pattern</code><br>
	 * <br>
	 * Thanks to prec in #regex for correcting the use of Matcher.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param p
	 * @param text
	 * @return
	 */
	protected List<WordResult> regexSearch(Pattern p, String text)
	{
		Matcher m = p.matcher(text);
		ArrayList<WordResult> results = new ArrayList<WordResult>();
		while (m.find())
		{
			results.add(new WordResult(m.start(), m.end(), m.group()));
			Thread.yield();
		}
		return results;
	}
}