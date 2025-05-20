package geo.google.utils;

import org.w3c.dom.Node;

/**
 * Process function of a node
 * 
 * The tree traversal in TreeUtils will call this function
 * to process a node when it visit a node
 * 
 * (think function pointer)
 * 
 * @author jliang
 *
 */
public interface NodeProcessor{
  public void preProcess(Node node);
  public void postProcess(Node node);
}