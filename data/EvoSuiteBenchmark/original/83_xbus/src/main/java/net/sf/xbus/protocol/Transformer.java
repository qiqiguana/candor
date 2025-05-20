package net.sf.xbus.protocol;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>Transformer</code> implementations are transforming the content of
 * one <code>Messages</code> to the format of another <code>Message</code>.
 * There are three possibilities where a transformer is used:
 * <ol>
 * <li><b>Request -> Request:</b> The request of a received message is
 * transformed to the request of a message that shall be send, either invoke or
 * distribute.</li>
 * <li><b>Response -> Request: </b>The response of a message after being sent
 * with invoke is transformed to the request of a message that shall be send,
 * either invoke or distribute.</li>
 * <li><b>Response -> Response:</b> When the response of a invocation shall be
 * returned to the initial sender, the response of the last message is
 * transformed to the response of the initial message.</li>
 * </ol>
 * Which transformer is used is determined by the xBus according to the message
 * types. This information is read out of the <code>Configuration</code>.
 */
public interface Transformer
{
	/**
	 * The <code>transform</code> method is automatically called by the xBus
	 * during the routing of messages.
	 * 
	 * @param inObject either the request or response that shall be transformed
	 *            to another format
	 * @param source the <code>XBUSSystem</code> of the message of the
	 *            <code>inObject</code>
	 * @param destination the <code>XBUSSystem</code> of the target message
	 * @param destinationMessage the target message eventually contains more
	 *            information necessary for transforming
	 * @return the transformed <code>Object</code>, written to either the
	 *         request or response of the target message
	 */
	Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, final Message destinationMessage)
			throws XException;
}
