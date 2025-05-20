/*
 * Created on 09.03.2005
 *
 */
package ch.bluepenguin.tapestry.components.menu.model;

import java.util.Iterator;

import ch.bluepenguin.tapestry.components.menu.model.impl.MenuItemList;
import ch.bluepenguin.tapestry.components.menu.renderer.IMenuItemRenderer;

/**
 * @author Christian
 *
 *  model for menus. holds a hierarchy of entries, separators, ...
 */
public interface IMenuModel {
	/**
	 * 
	 * @return iterator with depth first menu items (IMenuItem)
	 */
  public Iterator getMenuItemsDepthFirst() throws MenuStructureException;
  
  /**
   * adds the item <br/>
   * If the item has got a parent, that parent is searched within 
   * the existing model. If the parent hasnt been found, this item is
   * added to the end of the list. When the parent has been added at 
   * a later date, this menu item is moved accordingly. <br/>
   * If the parent has been added, the index and depth of the item
   * is calculated by the model and set by it <br/>
   * If the parent has not been added at a later date before read-accessing
   * the menu model for the first time, a MenuStructureException is
   * thrown <br/>
   *  additionally, a DefaultMenuItemRenderer is associated with the element
   * @param item filled menu item
   */
  public void addMenuItem(IMenuItem item);

  public void addMenuItem(IMenuItem item, IMenuItemRenderer renderer);
  
  /**
   * checks, if the structure is ok:
   * <li>no circular references</li>
   * <li>no orphan items</li>
   * <li>depth index consistency</li>
   * <li>index consistency</li>
   * The later 2 points may be inaccurate if the depth and index
   * parameters have been set by hand instead of beeing calculated
   * by the model itself <br/>
   * This method may be called anytime. It is called internally 
   * the first time this model is tried to be read
   * @throws MenuStructureException
   */
  public void checkMenuModelHealth() throws MenuStructureException;
  
  public MenuItemList getRootList();
  public MenuItemList getChildList(IMenuItem item);
  
  
}
