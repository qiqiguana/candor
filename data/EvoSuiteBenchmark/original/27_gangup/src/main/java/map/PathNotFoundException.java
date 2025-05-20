/* PathNotFoundException.java
 *
 * Joel Andersson <bja@kth.se>
 * Mon Jan 28 19:52:58 CET 2002
 */

package map;

public class PathNotFoundException extends Exception {

    public PathNotFoundException() {
	super();
    }

    public PathNotFoundException(String s) {
	super(s);
    }
}
