package ghm.follow.search;

/**
 * Container for positions of found terms.
 * 
 * @author Carl Hall (carl.hall@gmail.com)
 */
public class WordResult
{
	// term described by these positions
	public String term;

	// absolute starting position of term in document
	public int start;

	// absolute ending position of term in document
	public int end;

	// start position of term on line
	public int termStartLine;

	// end position of term on line
	public int termEndLine;

	// parent line result for back referencing
	public LineResult parent;

	public WordResult(int start, int end, String term)
	{
		this.start = start;
		this.end = end;
		this.term = term;
		setLineOffset(-1);
	}

	public WordResult(int start, int end, String term, int lineOffset)
	{
		this(start, end, term);
		setLineOffset(lineOffset);
	}

	public void setLineOffset(int lineOffset)
	{
		if (lineOffset >= 0)
		{
			termStartLine = start - lineOffset + 1;
			termEndLine = end - lineOffset;
		}
		else
		{
			termStartLine = 0;
			termEndLine = 0;
		}
	}

	public String toString()
	{
		String retval = "";
		if (termStartLine > 0 && termEndLine > 0)
		{
			if (termStartLine == termEndLine)
			{
				retval += termStartLine;
			}
			else
			{
				retval += termStartLine + "-" + termEndLine;
			}
		}
		return retval;
	}
}