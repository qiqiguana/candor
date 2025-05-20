/* $Id: MessageTimeoutException.java,v 1.2 2004/03/31 21:12:03 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.2 $
 *
 */

package module;

class MessageTimeoutException extends Exception {
    MessageTimeoutException(String str) {
	super(str);
    }
}
