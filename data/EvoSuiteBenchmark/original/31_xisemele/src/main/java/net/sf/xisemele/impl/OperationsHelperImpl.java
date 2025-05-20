package net.sf.xisemele.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementação de {@link OperationsHelper}.
 * 
 * @author Carlos Eduardo Coral.
 */
class OperationsHelperImpl implements OperationsHelper {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * {@inheritDoc}
    */
   public List<Node> children(Node node) {
      ArrayList<Node> children = new ArrayList<Node>();
      NodeList nodeList = node.getChildNodes();
      for (int index = 0; index < nodeList.getLength(); index++) {
         Node item = nodeList.item(index);
         if (item.getNodeType() == Node.ELEMENT_NODE) {
            children.add(item);
         }
      }
      return children;
   }

   /**
    * {@inheritDoc}
    */
   public Node find(Document document, String path) {
      String[] names = path.split("/");
      Node found = null;

      if (names.length > 0 && names[0].equals(document.getDocumentElement().getNodeName())) {
         found = document.getDocumentElement();
         NodeList nodeList = found.getChildNodes();
         for (int nameIndex = 1; nameIndex < names.length; nameIndex++) {
            for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
               if (nodeList.item(nodeIndex).getNodeName().equals(names[nameIndex])) {
                  found = nodeList.item(nodeIndex);
                  nodeList = found.getChildNodes();
                  break;
               }
            }
         }
      }

      if (found != null && found.getNodeName().equals(names[names.length - 1])) {
         return found;
      } else {
         return null;
      }
   }

   /**
    * {@inheritDoc}
    */
   public Node nodeWithName(List<Node> nodes, String name) {
      for (Node node : nodes) {
         if (node.getNodeName().equals(name)) {
            return node;
         }
      }
      return null;
   }

   /**
    * {@inheritDoc}
    */
   public List<Node> sublistWithName(List<Node> nodes, String name) {
      ArrayList<Node> sublist = new ArrayList<Node>();
      for (Node node : nodes) {
         if (node.getNodeName().equals(name)) {
            sublist.add(node);
         }
      }
      return sublist;
   }
}
