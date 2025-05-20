/*
 * Created on 17.03.2005
 *
 */
package ch.bluepenguin.tapestry.components.menu;

import java.util.Iterator;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IEngine;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.engine.IScriptSource;
import org.apache.tapestry.html.Body;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import ch.bluepenguin.tapestry.components.menu.model.IMenuModel;
import ch.bluepenguin.tapestry.components.menu.model.impl.MenuItemList;

/**
 * @author Christian
 *  
 */
public abstract class BluepenguinMenu extends BaseComponent {
	public abstract String getMenuTag();
	public abstract void setMenuTag(String menuClass);
	public abstract String getMenuClass();
	public abstract void setMenuClass(String menuClass);
	public abstract String getSubMenuClass();
	public abstract void setSubMenuClass(String leafClass);
	public abstract IMenuModel getModel();
	public abstract void setModel(IMenuModel model);

	/**
     * A cached copy of the script used with the component.
     */
    private IScript _script;
	
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if (getModel() != null) {
			IMarkupWriter myWriter = writer.getNestedWriter();
			myWriter.begin(getMenuTag());
			myWriter.attribute("class", getMenuClass());
			//TODO:configurable
			Iterator menuItemList = getModel().getRootList().getChildrenIterator();
			renderMenuRecursively(menuItemList, myWriter);
			myWriter.end();
			myWriter.flush();
			myWriter.close();
		}
		super.renderComponent(writer, cycle);
		if (!cycle.isRewinding()) {
// no javascript atm
//            runScript(cycle);
        }
	}
    /**
     * Executes the associated script, which generates all the JavaScript to support this Palette.
     */
	
    private void runScript(IRequestCycle cycle) {
        // Get the script, if not already gotten. Scripts are re-entrant, so it is
        // safe to share this between instances of Palette.
        if (_script == null) {
            IEngine engine = getPage().getEngine();
            IScriptSource source = engine.getScriptSource();
            _script = source.getScript(getSpecification().
            		getSpecificationLocation().
					getRelativeLocation("BluepenguinMenu.script"));
        }
        _script.execute(cycle, Body.get(cycle), null);
    }

	
	/**
	 * @param menuItems
	 * @param myWriter
	 */
	private void renderMenuRecursively(Iterator menuItems, IMarkupWriter myWriter) {
		while (menuItems.hasNext()) {
			MenuItemList itemList = (MenuItemList) menuItems.next();
			IMenuItem item = itemList.getMenuItem();
			IMarkupWriter workingWriter = myWriter.getNestedWriter();
			//check wether next item is node or leaf 
			if (!item.isLeaf()) {
				//node: create new menu tag (might be submenu tag later)
				//start myself
				item.getRenderer().setStyleClass(getSubMenuClass());
				item.getRenderer().startRender(workingWriter, item);
				//submenu tag
					IMarkupWriter nested = workingWriter.getNestedWriter();
					nested.begin(getMenuTag());
					//write all children
					    Iterator childrenList = getModel().getChildList(item).getChildrenIterator();
						renderMenuRecursively(childrenList, nested);
				    nested.end();
					nested.flush();
					nested.close();
				//stop myself --> this writer won't be valid anymore	
				item.getRenderer().stopRender(workingWriter, item);
			} else { 
				//leaf: write myself & be happy with it :)
				item.getRenderer().render(workingWriter, item);
			}
		}
	}
}