/**
 * Copyright (c) 2007, Aberystwyth University
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above
 *    copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 *  - Neither the name of the Centre for Advanced Software and
 *    Intelligent Systems (CASIS) nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package edu.uiuc.ndiipp.hubandspoke.workflow;

import java.util.Iterator;
import javax.swing.JEditorPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.purl.sword.base.Collection;
import org.purl.sword.base.SWORDEntry;
import org.purl.sword.base.Service;
import org.purl.sword.base.ServiceDocument;
import org.purl.sword.base.Workspace;
import org.purl.sword.atom.Author;
import org.purl.sword.atom.Content;
import org.purl.sword.atom.Contributor;
import org.purl.sword.atom.Generator;
import org.purl.sword.atom.Link;
import org.purl.sword.atom.Source;
import org.purl.sword.atom.TextConstruct;


/**
    * Holds the data for a tree node. It specifies the name that will be displayed
    * in the node, and stores associated data.
    *
    * @author Neil Taylor
    */
   class TreeNodeWrapper
   {
      /**
       * The node name.
       */
      private String name;

      /**
       * The user data.
       */
      private Object userObject;

      /**
       * Create a new instance.
       *
       * @param name The name of the node.
       * @param data The data in the node.
       */
      public TreeNodeWrapper(String name, Object data)
      {
         this.name = name;
         this.userObject = data;
      }

      /**
       * Retrieve the data that is stored in this node.
       *
       * @return The data.
       */
      public Object getData()
      {
         return userObject;
      }

      /**
       * Get a string description for this node.
       */
      public String toString()
      {
         if( name == null || name.trim().equals("") )
         {
            return "Unspecified";
         }

         return name;
      }
   }

/**
 *
 */
public class SWORDUtils {

    public void processServiceDocument(String url, ServiceDocument doc,
	    DefaultTreeModel treeModel, DefaultMutableTreeNode top,
	    JTree services) {
	TreeNodeWrapper wrapper = null;

	Service service = doc.getService();
	wrapper = new TreeNodeWrapper(url, service);
	DefaultMutableTreeNode serviceNode = new DefaultMutableTreeNode(wrapper);
	treeModel.insertNodeInto(serviceNode, top, top.getChildCount());
	services.scrollPathToVisible(new TreePath(serviceNode.getPath()));

	// process the workspaces
	DefaultMutableTreeNode workspaceNode = null;

	Iterator<Workspace> workspaces = service.getWorkspaces();
	for (; workspaces.hasNext();) {
	    Workspace workspace = workspaces.next();
	    wrapper = new TreeNodeWrapper(workspace.getTitle(), workspace);
	    workspaceNode = new DefaultMutableTreeNode(wrapper);
	    treeModel.insertNodeInto(workspaceNode, serviceNode, serviceNode.
		    getChildCount());
	    services.scrollPathToVisible(new TreePath(workspaceNode.getPath()));

	    DefaultMutableTreeNode collectionNode = null;
	    Iterator<Collection> collections = workspace.collectionIterator();
	    for (; collections.hasNext();) {
		Collection collection = collections.next();
		wrapper = new TreeNodeWrapper(collection.getTitle(), collection);
		collectionNode = new DefaultMutableTreeNode(wrapper);
		treeModel.insertNodeInto(collectionNode, workspaceNode,
			workspaceNode.getChildCount());
		services.scrollPathToVisible(new TreePath(
			collectionNode.getPath()));
	    }
	} // for
    }

   /**
    * Add a new HTML table row to the specified StringBuffer. The label is displayed in
    * the left column and the value is displayed in the right column.
    *
    * @param buffer The destination string buffer.
    * @param label The label to add.
    * @param value The corresponding value to add.
    */
   public void addTableRow(StringBuffer buffer, String label, Object value)
   {
      buffer.append("<tr bgcolor=\"#ffffff;\"><td>");
      buffer.append(label);
      buffer.append("</td><td>");
      buffer.append(value);
      buffer.append("</td></tr>");
   }
   /**
    * Show the specified service data in the details panel.
    *
    * @param service The service node to display.
    */
   public void showService(Service service, JEditorPane details)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("<html>");
      buffer.append("<body>");

      buffer.append("<table border=\"1\" width=\"100%\">");
      buffer.append("<tr bgcolor=\"#69a5c8;\"><td colspan=\"2\"><font size=\"+2\">Service Summary</font></td></tr>");
      addTableRow(buffer, "SWORD Version", service.getVersion());
      addTableRow(buffer, "NoOp Support ", service.isNoOp());
      addTableRow(buffer, "Verbose Support ", service.isVerbose());

      buffer.append("</table>");

      buffer.append("</body>");
      buffer.append("</html>");
      details.setText(buffer.toString());
   }

   /**
    * Display the workspace data in the details panel.
    *
    * @param workspace The workspace.
    */
   public void showWorkspace(Workspace workspace, JEditorPane details)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("<html>");
      buffer.append("<body>");

      buffer.append("<table border=\"1\" width=\"100%\">");
      buffer.append("<tr bgcolor=\"#69a5c8;\"><td colspan=\"2\"><font size=\"+2\">Workspace Summary</font></td></tr>");
      addTableRow(buffer, "Workspace Title", workspace.getTitle());
      buffer.append("</table>");

      buffer.append("</body>");
      buffer.append("</html>");
      details.setText(buffer.toString());
   }


   /**
    * Add a string within paragraph tags.
    *
    * @param buffer The buffer to add the message to.
    * @param message The message to add.
    */
   private void addPara(StringBuffer buffer, String message)
   {
      buffer.append("<p>" + message + "</p>");
   }

   /**
    * Show the specified collection data in the details panel.
    *
    * @param collection The collection data.
    */
   public void showCollection(Collection collection, JEditorPane details)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("<html>");
      buffer.append("<body>");

      if( collection == null )
      {
         addPara(buffer, "Invalid Collection object. Unable to display details.");
      }
      else
      {
         buffer.append("<table border=\"1\" width=\"100%\">");
         buffer.append("<tr bgcolor=\"#69a5c8;\"><td colspan=\"2\"><font size=\"+2\">Collection Summary</font></td></tr>");
         addTableRow(buffer, "Collection location", collection.getLocation());
         addTableRow(buffer, "Collection title", collection.getTitle());
         addTableRow(buffer, "Abstract", collection.getAbstract());
         addTableRow(buffer, "Collection Policy", collection.getCollectionPolicy());
         addTableRow(buffer, "Treatment", collection.getTreatment());
         addTableRow(buffer, "Mediation", collection.getMediation());
         addTableRow(buffer, "Nested Service Document", collection.getService());


         String[] accepts = collection.getAccepts();
         String acceptList = "";
         if( accepts != null && accepts.length == 0 )
         {
            acceptList = "None specified";
         }
         else
         {
            for (String s : accepts)
            {
               acceptList +=  s + "<br>";
            }
         }
         addTableRow(buffer, "Accepts", acceptList);

         buffer.append("</table>");
      }

      buffer.append("</body>");
      buffer.append("</html>");
      details.setText(buffer.toString());
   }

   /**
    * Display the contents of a Post entry in the display panel.
    *
    * @param entry The entry to display.
    */
   public void showEntry(SWORDEntry entry, JEditorPane details)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("<html>");
      buffer.append("<body>");

      if( entry == null )
      {
         addPara(buffer, "Invalid Entry object. Unable to display details.");
      }
      else
      {
         buffer.append("<table border=\"1\" width=\"100%\">");
         buffer.append("<tr bgcolor=\"#69a5c8;\"><td colspan=\"2\"><font size=\"+2\">Entry Summary</font></td></tr>");

         // process atom:title
         String titleString = getTextConstructDetails(entry.getSummary());
         addTableRow(buffer, "Title", titleString);

         // process id
         addTableRow(buffer, "ID", entry.getId());

         // process updated
         addTableRow(buffer, "Date Updated", entry.getUpdated());

         String authorString = getAuthorDetails(entry.getAuthors());
         addTableRow(buffer, "Authors", authorString.toString());

         // process summary
         String summaryString = getTextConstructDetails(entry.getSummary());
         addTableRow(buffer, "Summary", summaryString);

         // process content
         Content content = entry.getContent();
         String contentString = "";
         if( content == null )
         {
            contentString = "Not defined.";
         }
         else
         {
            contentString += "Source: '" + content.getSource() + "', Type: '" +
            content.getType() + "'";
         }
         addTableRow(buffer, "Content", contentString);

         // process links
         Iterator<Link> links = entry.getLinks();
         StringBuffer linkBuffer = new StringBuffer();
         for( ; links.hasNext(); )
         {
            Link link = links.next();
            linkBuffer.append("href: '");
            linkBuffer.append(link.getHref());
            linkBuffer.append("', href lang: '");
            linkBuffer.append(link.getHreflang());
            linkBuffer.append("', rel: '");
            linkBuffer.append(link.getRel());
            linkBuffer.append("')<br>");
         }
         if( linkBuffer.length() == 0 )
         {
            linkBuffer.append("Not defined");
         }
         addTableRow(buffer, "Links", linkBuffer.toString());

         // process contributors
         String contributorString = getContributorDetails(entry.getContributors());
         addTableRow(buffer, "Contributors", contributorString.toString());

         // process source
         String sourceString="";
         Generator generator = entry.getGenerator();
         if( generator != null )
         {
            sourceString += "Content: '" + generator.getContent() + "' <br>'";
            sourceString += "Version: '" + generator.getVersion() + "' <br>'";
            sourceString += "Uri: '" + generator.getUri() + "'";
         }
         else
         {
            sourceString += "No generator defined.";
         }

         addTableRow(buffer, "Generator", sourceString);

         // process treatment
         addTableRow(buffer, "Treatment", entry.getTreatment());

         // process verboseDescription
         addTableRow(buffer, "Verbose Description", entry.getVerboseDescription());

         // process noOp
         addTableRow(buffer, "NoOp", entry.isNoOp());

         // process formatNamespace
         addTableRow(buffer, "Packaging", entry.getPackaging());

         // process userAgent
         addTableRow(buffer, "User Agent", entry.getUserAgent());


         buffer.append("</table>");
      }

      buffer.append("</body>");
      buffer.append("</html>");
      details.setText(buffer.toString());
   }

   /**
    * Retrieve the details for a TextConstruct object.
    *
    * @param data The text construct object to display.
    *
    * @return Either 'Not defined' if the data is <code>null</code>, or
    *         details of the text content element.
    */
   private String getTextConstructDetails(TextConstruct data)
   {
      String summaryStr = "";
      if( data == null )
      {
         summaryStr = "Not defined";
      }
      else
      {
         summaryStr = "Content: '" + data.getContent() + "', Type: ";
         if( data.getType() != null )
         {
            summaryStr += "'" + data.getType().toString() + "'";
         }
         else
         {
            summaryStr += "undefined.";
         }
      }

      return summaryStr;
   }

   /**
    * Get the author details and insert them into a string.
    *
    * @param authors the list of authors to process.
    *
    * @return A string containing the list of authors.
    */
   private String getAuthorDetails(Iterator<Author> authors)
   {
      // process author
      StringBuffer authorBuffer = new StringBuffer();
      for( ; authors.hasNext(); )
      {
         Author a = authors.next();
         authorBuffer.append(getAuthorDetails(a));
      }

      if( authorBuffer.length() == 0 )
      {
         authorBuffer.append("Not defined");
      }

      return authorBuffer.toString();
   }

   /**
    * Get the contributor details and insert them into a string.
    *
    * @param contributors The contributors.
    *
    * @return The string that lists the details of the contributors.
    */
   private String getContributorDetails(Iterator<Contributor> contributors)
   {
      // process author
      StringBuffer authorBuffer = new StringBuffer();
      for( ; contributors.hasNext(); )
      {
         Contributor c = contributors.next();
         authorBuffer.append(getAuthorDetails(c));
      }

      if( authorBuffer.length() == 0 )
      {
         authorBuffer.append("Not defined");
      }

      return authorBuffer.toString();
   }

   /**
    * Build a string that describes the specified author.
    *
    * @param author The author.
    *
    * @return The string description.
    */
   private String getAuthorDetails(Author author)
   {
      // process author
      StringBuffer authorBuffer = new StringBuffer();
      authorBuffer.append(author.getName());
      authorBuffer.append(" (email: '");
      authorBuffer.append(author.getEmail());
      authorBuffer.append("', uri: '");
      authorBuffer.append(author.getUri());
      authorBuffer.append("')<br>");

      return authorBuffer.toString();
   }
}
