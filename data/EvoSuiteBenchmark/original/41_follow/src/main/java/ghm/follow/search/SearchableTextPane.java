package ghm.follow.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class SearchableTextPane extends JTextArea
{
	private Logger log = Logger.getLogger(SearchableTextPane.class.getName());
	private int lastSearchPos = -1;
	private String lastSearchTerm;
	private final DefaultHighlightPainter wordPainter = new DefaultHighlightPainter(Color.YELLOW);

	public SearchableTextPane(Font font, int tabSize)
	{
		// set the display font
		setFont(font);
		setTabSize(tabSize);
	}

	/**
	 * Override this to keep the text from wrapping and to make the viewable area as wide as the
	 * tabbed pane
	 */
	public boolean getScrollableTracksViewportWidth()
	{
		Component parent = getParent();
		ComponentUI ui = getUI();

		return parent != null ? (ui.getPreferredSize(this).width <= parent.getSize().width) : true;
	}

	/**
	 * Highlight <code>term</code> wherever it is found in the view. Also highlights the entire
	 * line on which the term is found.
	 * 
	 * @param term
	 * @param caseSensitive
	 * @param useRegularExpression
	 * @return
	 */
	public List<LineResult> highlight(String term, int flags)
	{
		List<LineResult> lineResults = new ArrayList<LineResult>();
		// Remove all old highlights
		removeHighlights();
		// Search for pattern
		if ((term != null) && (term.length() > 0))
		{
			// look for instances of the term in the text

			try
			{
				Document doc = getDocument();
				String text = doc.getText(0, doc.getLength());
				List<WordResult> searchResults = new SearchEngine(flags).search(term, text);
				lineResults = convertWords2Lines(searchResults);
				for (LineResult lineResult : lineResults)
				{
					List<WordResult> wordResults = lineResult.getWordResults();
					for (WordResult wordResult : wordResults)
					{
						// highlight the searched term
						int wordStart = wordResult.start;
						int wordEnd = wordResult.end;
						addHighlight(wordStart, wordEnd - wordStart);
						Thread.yield();
					}
				}
			}
			catch (BadLocationException e)
			{
				log.log(Level.SEVERE, "BadLocationException in SearchableTextPane", e);
				lineResults = new ArrayList<LineResult>();
			}
		}
		return lineResults;
	}

	/**
	 * Highlight a piece of text in the document
	 * 
	 * @param start
	 * @param wordEnd
	 * @param highlighter
	 */
	private void addHighlight(int start, int length) throws BadLocationException
	{
		getHighlighter().addHighlight(start, start + length, wordPainter);
	}

	/**
	 * Removes highlights from text area
	 */
	public void removeHighlights()
	{
		getHighlighter().removeAllHighlights();
	}

	/**
	 * Searches for a term. If the term provided matches the last searched term, the last found
	 * position is used as a starting point.<br>
	 * <br>
	 * Developer note: this method isn't currently used.
	 * 
	 * @param term
	 *            The string for which to search.
	 * @return The position where the term was found.<br>
	 *         If the term is null, empty or not found, -1 is returned.
	 */
	public int search(String term)
	{
		if (term != null && term.length() > 0)
		{
			if (term.equals(lastSearchTerm))
			{
				// assume to start at the beginning
				int pos = 0;
				// if there is a previous search position, start there plus the
				// length
				// of the last term so that last term again isn't found again
				if (lastSearchPos != -1)
				{
					pos = lastSearchPos + lastSearchTerm.length();
				}
				lastSearchPos = search(lastSearchTerm, pos);
			}
			else
			{
				lastSearchPos = search(term, 0);
			}
		}
		// remember the term if it was found
		if (lastSearchPos == -1)
		{
			lastSearchTerm = null;
		}
		else
		{
			lastSearchTerm = term;
		}
		return lastSearchPos;
	}

	/**
	 * Searches for a term at the given starting position.<br>
	 * <br>
	 * Developer note: this method isn't currently used.
	 * 
	 * @param term
	 *            The string for which to search.
	 * @param startPos
	 *            Where to start.
	 * @return The position where the term was found.<br>
	 *         If the term is null, empty or not found, -1 is returned.
	 */
	public int search(String term, int startPos)
	{
		int pos = 0;
		try
		{
			Document doc = getDocument();
			String text = doc.getText(0, doc.getLength());

			// Search for pattern
			pos = text.indexOf(term, startPos);
		}
		catch (BadLocationException e)
		{
			// just return -1;
			log.log(Level.WARNING, "BadLocationException in SearchableTextPane", e);
			pos = -1;
		}
		return pos;
	}

	/**
	 * Converts word results from search into line results
	 * 
	 * @param words
	 * @return
	 */
	private List<LineResult> convertWords2Lines(List<WordResult> words) throws BadLocationException
	{
		ArrayList<LineResult> lines = new ArrayList<LineResult>();
		LineResult tempLine = null;
		int lastLine = -1;
		for (WordResult word : words)
		{
			int line = getLineOfOffset(word.start);
			if (line != lastLine)
			{
				if (tempLine != null)
				{
					lines.add(tempLine);
				}
				Element elem = Utilities.getParagraphElement(this, word.start);
				int lineStart = elem.getStartOffset();
				int lineEnd = elem.getEndOffset();
				tempLine = new LineResult(line, lineStart, lineEnd);
			}
			updateWordResult(word, tempLine);
			lastLine = line;
			// allow other things to happen in case the search takes a while
			Thread.yield();
		}
		if (tempLine != null)
		{
			lines.add(tempLine);
		}
		return lines;
	}

	/**
	 * Adds word result to line result and updates line information
	 * 
	 * @param wordResult
	 * @param lineResult
	 */
	private void updateWordResult(WordResult wordResult, LineResult lineResult)
			throws BadLocationException
	{
		lineResult.addWord(wordResult);
		// increase by 1 because offset starts at 0.
		// 1 is clearer to the user since most people don't start counting
		// at 0
		int line = getLineOfOffset(wordResult.start);
		wordResult.parent.lineNumber = line + 1;
		int lineOffset = getLineStartOffset(line);
		wordResult.setLineOffset(lineOffset);
	}
}