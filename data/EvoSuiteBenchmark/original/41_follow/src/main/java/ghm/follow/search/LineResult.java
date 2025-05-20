package ghm.follow.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Keeps results information about a line in the searchable text.
 * 
 * @author Carl Hall (carl.hall@gmail.com)
 */
public class LineResult
{
	// line position of the found term
	public int lineNumber;

	// starting caret position
	public int start;

	// ending caret position
	public int end;

	// word results found on this line
	private List<WordResult> wordResults;

	// keeps track of lower caret position for display scrolling
	private int firstPosition;

	/**
	 * Constructor
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param lineNumber
	 */
	public LineResult(int lineNumber, int start, int end)
	{
		wordResults = new ArrayList<WordResult>();
		this.lineNumber = lineNumber;
		this.start = start;
		this.end = end;
		firstPosition = Integer.MAX_VALUE;
	}

	/**
	 * Get the word results associated with this line.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @return
	 */
	public List<WordResult> getWordResults()
	{
		return wordResults;
	}

	/**
	 * Get a specific word result associated with this line.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param index
	 * @return
	 */
	public WordResult getWordResult(int index)
	{
		return (WordResult) wordResults.get(index);
	}

	/**
	 * Get the caret position of the first occurring word result on this line.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @return
	 */
	public int getFirstWordPosition()
	{
		return firstPosition;
	}

	/**
	 * Add a <code>WordResult</code> to this line
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param wordResult
	 */
	public void addWord(WordResult wordResult)
	{
		wordResult.parent = this;

		if (wordResult.start < firstPosition)
		{
			firstPosition = wordResult.start;
		}
		wordResults.add(wordResult);
	}

	/**
	 * Builds a string that is conducive for showing as a result list entry
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 */
	public String toString()
	{
		StringBuffer retval = new StringBuffer("Line " + lineNumber);

		if (wordResults.size() > 0)
		{
			retval.append(" (");
			Iterator<WordResult> words = wordResults.iterator();

			while (words.hasNext())
			{
				WordResult word = words.next();
				String wordString = word.toString();

				if (wordString.length() > 0)
				{
					retval.append(wordString);

					if (words.hasNext())
					{
						retval.append(",");
					}
				}
			}
			retval.append(")");
		}
		return retval.toString();
	}
}