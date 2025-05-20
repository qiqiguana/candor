package net.sf.xbus.base.xml;

import net.sf.xbus.base.core.trace.Trace;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * <code>XParserErrorHandler</code> handles messages from a xml parser when
 * validating the xml code.
 * 
 * @see org.xml.sax.ErrorHandler If the application does not register an error
 *      handler, all error events reported by the SAX parser will be silently
 *      ignored; however, normal processing may not continue. It is highly
 *      recommended that all SAX applications implement an error handler to
 *      avoid unexpected bugs. <br>
 *      The only thing in <code>XParserErrorHandler</code> is tracing the
 *      <code>SAXParseExceptions</code>.
 * 
 * @author Stephan Düwel
 */
public class XParserErrorHandler implements ErrorHandler
{

	/**
	 * @see org.xml.sax.ErrorHandler#warning(SAXParseException)
	 */
	public void warning(SAXParseException e)
	{
		Trace.warn(e.getMessage());
		Trace.warn("Line: " + e.getLineNumber() + ", Column: "
				+ e.getColumnNumber());
	} // warning(SAXParseException e)

	/**
	 * @see org.xml.sax.ErrorHandler#error(SAXParseException)
	 */
	public void error(SAXParseException e) throws SAXException
	{
		// Trace.error(e);
		// Trace.error("Line: " + e.getLineNumber() + ", Column: "
		// + e.getColumnNumber());
		throw e;
	} // error(SAXParseException e)

	/**
	 * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
	 */
	public void fatalError(SAXParseException e) throws SAXException
	{
		// Trace.error(e);
		// Trace.error("Line: " + e.getLineNumber() + ", Column: "
		// + e.getColumnNumber());
		throw e;
	} // fatalError(SAXParseException e)

} // XParserErrorHandler
