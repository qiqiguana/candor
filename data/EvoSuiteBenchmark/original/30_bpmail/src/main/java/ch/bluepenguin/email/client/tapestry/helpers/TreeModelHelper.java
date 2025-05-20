/*
 * Created on 28.01.2005
 *
 * Helper class for manipulating tree hierarchies
 */
package ch.bluepenguin.email.client.tapestry.helpers;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.contrib.tree.model.ITreeDataModel;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import ch.bluepenguin.tapestry.components.menu.model.IMenuModel;
import ch.bluepenguin.tapestry.components.menu.model.impl.DefaultMenuItem;
import ch.bluepenguin.tapestry.components.menu.model.impl.DefaultMenuModel;
import ch.bluepenguin.tapestry.components.menu.renderer.impl.DirectLinkMenuItemRenderer;

/**
 * @author Christian
 *
 */
public  class TreeModelHelper {


	public  IMenuModel buildFolderModel(Folder[] folders, 
			IComponent component, IRequestCycle cycle) {
		IMenuModel folderModel = new DefaultMenuModel();
		ArrayList menuItems = new ArrayList();
		for(int i=0;i<folders.length;i++) {
			Folder currentFolder = folders[i];
			//renderer
			DirectLinkMenuItemRenderer renderer = new DirectLinkMenuItemRenderer();
			renderer.setComponent(component);
			renderer.setCycle(cycle);
			Object[] parameters = {currentFolder};
			renderer.setParameters(parameters);
			IMenuItem item = new DefaultMenuItem(currentFolder);
			item.setParent(currentFolder.getParent());
			item.setName(currentFolder.getName());
			menuItems.add(item);
			folderModel.addMenuItem(item,renderer);
		}  
		return folderModel;
	}
	
    /**
     * returns depth first ordering of the data model
	 * @param menuDataModel
	 * @return
	 */
	public ArrayList getMenuListDepthFirst(ITreeDataModel menuDataModel) {
		Object root = menuDataModel.getRoot();
		ArrayList result = new ArrayList();
		traverseMenu(root, menuDataModel, result);
		return result;
	}
	/**
	 * @param root
	 */
	private void traverseMenu(Object parent, ITreeDataModel menuDataModel, ArrayList result) {
		Iterator children = menuDataModel.getChildren(parent);
		result.add(parent);
		while(children.hasNext()) {
			Object child = children.next();
			traverseMenu(child, menuDataModel, result);
		}
	}


}
