// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DefaultMenuModel.java

package ch.bluepenguin.tapestry.components.menu.model.impl;

import ch.bluepenguin.tapestry.components.menu.model.*;
import ch.bluepenguin.tapestry.components.menu.renderer.IMenuItemRenderer;
import ch.bluepenguin.tapestry.components.menu.renderer.impl.HyperlinkMenuItemRenderer;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package ch.bluepenguin.tapestry.components.menu.model.impl:
//            MenuItemList

public class DefaultMenuModel
    implements IMenuModel
{

    public DefaultMenuModel()
    {
        rootList = new MenuItemList(null);
        orphans = new ArrayList();
    }

    public Iterator getMenuItemsDepthFirst()
        throws MenuStructureException
    {
        ArrayList all = new ArrayList();
        getMenuItemsRecursion(rootList, all);
        return all.iterator();
    }

    private void getMenuItemsRecursion(MenuItemList list, ArrayList all)
    {
        if(list.getMenuItem() != null)
            all.add(list.getMenuItem());
        for(Iterator children = list.getChildrenIterator(); children.hasNext(); getMenuItemsRecursion((MenuItemList)children.next(), all));
    }

    public void addMenuItem(IMenuItem item)
    {
        HyperlinkMenuItemRenderer renderer = new HyperlinkMenuItemRenderer();
        renderer.setHyperlink("#");
        addMenuItem(item, ((IMenuItemRenderer) (renderer)));
    }

    private void addMenuItemToList(IMenuItem item, MenuItemList parentlist)
    {
        item.setIndex(parentlist.getChildrenSize());
        MenuItemList menulist = new MenuItemList(item);
        attachOrphans(menulist);
        parentlist.addChild(menulist);
    }

    private void attachOrphans(MenuItemList menulist)
    {
        for(int i = 0; i < orphans.size(); i++)
        {
            IMenuItem current = (IMenuItem)orphans.get(i);
            MenuItemList list = new MenuItemList(current);
            orphans.remove(i);
            if(current.getParent() != null && current.getParent().equals(menulist.getMenuItem().getContained()))
                menulist.addChild(list);
            else
                rootList.addChild(list);
            attachOrphans(list);
        }

    }

    private MenuItemList findParentList(IMenuItem item, MenuItemList parentList)
    {
        if(item.getParent() == null)
            return null;
        if(parentList.getMenuItem() != null && parentList.getMenuItem().getContained().equals(item.getParent()))
        {
            parentList.getMenuItem().setLeaf(false);
            return parentList;
        }
        for(Iterator children = parentList.getChildrenIterator(); children.hasNext();)
        {
            MenuItemList childList = (MenuItemList)children.next();
            MenuItemList found = findParentList(item, childList);
            if(found != null)
                return found;
        }

        return null;
    }

    private MenuItemList findChildList(IMenuItem item, MenuItemList currentList)
    {
        if(currentList.getMenuItem() != null && currentList.getMenuItem().equals(item))
            return currentList;
        for(Iterator children = currentList.getChildrenIterator(); children.hasNext();)
        {
            MenuItemList childList = (MenuItemList)children.next();
            MenuItemList found = findChildList(item, childList);
            if(found != null)
                return found;
        }

        return null;
    }

    public void checkMenuModelHealth()
        throws MenuStructureException
    {
        if(orphans.size() > 0)
            throw new MenuStructureException("Too many orphans still hanging around! ");
        if(!rootList.checkConsistency())
            throw new MenuStructureException("Tree structure not consistent ");
        else
            return;
    }

    public String displayMenuStructure()
    {
        return displayMenuList(rootList, new StringBuffer());
    }

    private String displayMenuList(MenuItemList list, StringBuffer spacer)
    {
        spacer.append("   ");
        StringBuffer buffer = new StringBuffer();
        buffer.append(list.toString());
        for(Iterator children = list.getChildrenIterator(); children.hasNext(); spacer.delete(spacer.length() - 4, spacer.length() - 1))
            buffer.append("\n").append(spacer.toString()).append(displayMenuList((MenuItemList)children.next(), spacer));

        return buffer.toString();
    }

    public void addMenuItem(IMenuItem item, IMenuItemRenderer renderer)
    {
        if(item == null)
            return;
        item.setRenderer(renderer);
        if(item.getParent() == null)
        {
            addMenuItemToList(item, rootList);
        } else
        {
            MenuItemList parentlist = findParentList(item, rootList);
            if(parentlist == null)
                orphans.add(item);
            else
                addMenuItemToList(item, parentlist);
        }
    }

    public MenuItemList getRootList()
    {
        return rootList;
    }

    public MenuItemList getChildList(IMenuItem item)
    {
        return findChildList(item, rootList);
    }

    private MenuItemList rootList;
    private ArrayList orphans;
}