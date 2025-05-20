package net.sf.xbus.base.core;

/**
 * A StringTokenizer, that conveniently tokenizes a ASCII-file The main
 * difference to its base class is, that it doesn´t count the delimiters as
 * tokens in method countTokens() and that its methods nextElement() and
 * nextToken() don´t return a delimiter, but an empty Object/String if there are
 * two delimiters next to each other.
 * 
 * @see java.util.StringTokenizer
 * @author Martin Sturzenhecker
 */
public class ASCIITokenizer extends java.util.StringTokenizer
{

	/** The string to be tokenized */
	private String string;

	/** The delimiters */
	private String delims;
	/**
	 * Creates a new instance of <code>ASCIIOfferTokenizer</code>
	 * 
	 * @param str String to be tokenized
	 * @param delim String separator
	 */
	public ASCIITokenizer(String str, String delim)
	{
		super(str, delim, true);
		this.string = str;
		this.delims = delim;
	}
	/**
	 * Creates a new instance of <code>ASCIIOfferTokenizer</code>
	 * 
	 * @param str String to be tokenized
	 * @param delim String separator
	 * @param returnTokens boolean: shall the delimiters be returned?
	 */
	private ASCIITokenizer(String str, String delim, boolean returnTokens)
	{
		super(str, delim, returnTokens);
		this.string = str;
		this.delims = delim;
	}
	/**
	 * Counts the number of tokens in the string to be tokenized
	 * <p>
	 * The delimiters are <b>not</b> counted unlike in class StringTokenizer
	 * 
	 * @return int - the number of tokens without the delimiters
	 */
	public int countTokens()
	{

		// toky exists for counting purposes only. It´s the same as the current
		// Tokenizer
		ASCIITokenizer toky = new ASCIITokenizer(this.string, this.delims, true);
		int count = 0;
		String last = "";
		while (toky.hasMoreTokens())
		{
			last = toky.nextToken();
			count++;
		}
		return (this.getDelimiters().indexOf(last) >= 0 ? count + 1 : count);
	}
	/**
	 * Returns the delimiters of this tokenizer
	 * 
	 * @return String -the delimiters of this tokenizer
	 */
	public java.lang.String getDelimiters()
	{
		return delims;
	}
	/**
	 * Returns the string to be tokenized
	 * 
	 * @return String to be tokenized
	 */
	public java.lang.String getStr()
	{
		return string;
	}
	/**
	 * Returns the next element of the string to be tokenized
	 * <p>
	 * It does quite the same as the method <code>nextToken()</code> but it
	 * returns an <code>Object</code> instead of a String
	 * 
	 * @return Object - the next element of the string
	 * @see #nextToken()
	 */
	public Object nextElement()
	{
		return this.nextToken();
	}
	/**
	 * Returns the next token of the string to be tokenized
	 * 
	 * @return String - the next token of the string
	 */
	public String nextToken()
	{

		String tmp;

		/* if there are more tokens get next one, else the last token is empty */
		tmp = (this.hasMoreTokens() ? super.nextToken() : "");

		/* tmp is a delimiter -> the currentToken is empty */
		if (this.getDelimiters().indexOf(tmp) >= 0)
		{
			tmp = "";
		}

		/*
		 * tmp is not a delimiter and the String has more tokens -> the
		 * currentToken is not empty
		 */
		else if (this.hasMoreTokens())
		{
			super.nextToken(); // This is a delimiter for sure.
		}

		/*
		 * tmp is not a delimiter and the token is the last one -> the
		 * currentToken is not empty
		 */
		else
		{
			/*
			 *  do nothing
			 */
		}
		return tmp;
	}
}
