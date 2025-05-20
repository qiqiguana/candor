package ch.bluepenguin.email.client.tapestry.tests;

import junit.framework.TestCase;

/**
 * @author Christian
 *
 */
public class TreeModelBuilderTestCase extends TestCase {
/*
    
   public void NOtestTreeModelWithNoRoot() {
	 Folder[] result = new Folder[3];
	 TreeModelHelper helper = new TreeModelHelper();
	 for(int i=0;i<result.length;i++) {
	 	result[i]= new Folder(new Integer(i));
	 }
	 result[0].setName("INBOX");
	 result[1].setName("IMPORTANT");
	 result[2].setName("DUMMY");
	 ITreeModel model = helper.buildFolderTreeModel(result);
	 Object root =  model.getTreeDataModel().getRoot();
	 assertNotNull(root);
	 System.out.println("root " + model.getTreeDataModel().getRoot().toString());
	 assertEquals(3,model.getTreeDataModel().getChildCount(root));
	 
   }

   public void NOtestTreeModelWithRoot() {
	 Folder[] result = new Folder[6];
	 TreeModelHelper helper = new TreeModelHelper();
	 for(int i=0;i<result.length;i++) {
	 	result[i]= new Folder(new Integer(i));
	 }
	 result[0].setName("PARENT1");
	 result[1].setName("CHILD11");
	 result[1].setParent(result[0]);
	 result[2].setName("CHILD12");
	 result[2].setParent(result[0]);
	 result[3].setName("CHILD121");
	 result[3].setParent(result[2]);
	 result[4].setName("PARENT2");
	 result[5].setName("CHILD21");
	 result[5].setParent(result[4]);
	 
	 ITreeModel model = helper.buildFolderTreeModel(result);
	 Object root =  model.getTreeDataModel().getRoot();
	 assertNotNull(root);
	 System.out.println("root " + model.getTreeDataModel().getRoot().toString());
	 assertEquals(2,model.getTreeDataModel().getChildCount(root));
	 
  }

   public void testDepthFirst() {
	 Folder[] result = new Folder[6];
	 TreeModelHelper helper = new TreeModelHelper();
	 for(int i=0;i<result.length;i++) {
	 	result[i]= new Folder(new Integer(i));
	 }
	 result[0].setName("PARENT1");
	 result[1].setName("CHILD11");
	 result[1].setParent(result[0]);
	 result[2].setName("CHILD12");
	 result[2].setParent(result[0]);
	 result[3].setName("CHILD121");
	 result[3].setParent(result[2]);
	 result[4].setName("PARENT2");
	 result[5].setName("CHILD21");
	 result[5].setParent(result[4]);
	 
	 ITreeModel model = helper.buildFolderTreeModel(result);
   	 
	 ArrayList list = helper.getMenuListDepthFirst(model.getTreeDataModel());
	 for(int i=0; i< result.length; i++) {
	 	System.out.println("input list entry:       " + result[i].getName()  + " / parent: " + result[i].getParent() );
	 }
	 for(int i=0; i< list.size(); i++) {
	 	System.out.println("depth first list entry: " + list.get(i) );
	 }
//again, mixed up
	 
	 result[3].setName("PARENT1");
	 result[1].setName("CHILD11");
	 result[1].setParent(result[0]);
	 result[0].setName("CHILD12");
	 result[0].setParent(result[0]);
	 result[4].setName("CHILD121");
	 result[4].setParent(result[2]);
	 result[5].setName("PARENT2");
	 result[2].setName("CHILD21");
	 result[2].setParent(result[4]);
	 
	 model = helper.buildFolderTreeModel(result);
   	 
	 list = helper.getMenuListDepthFirst(model.getTreeDataModel());
	 for(int i=0; i< result.length; i++) {
	 	System.out.println("input list entry:       " + result[i]  + " / parent: " + result[i].getParent() );
	 }
	 for(int i=0; i< list.size(); i++) {
	 	System.out.println("depth first list entry: " + list.get(i) );
	 }
   }
*/
}
