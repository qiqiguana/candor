// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MenuItemList.java

package ch.bluepenguin.tapestry.components.menu.model.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import java.util.Iterator;
import java.util.Vector;

public class MenuItemList
{

    public MenuItemList(IMenuItem parent)
    {
        parentMenuItem = parent;
        children = new Vector();
    }

    public void addChild(MenuItemList item)
    {
        if(parentMenuItem == null)
            item.getMenuItem().setDepth(0);
        else
            item.getMenuItem().setDepth(parentMenuItem.getDepth() + 1);
        children.add(item);
    }

    public IMenuItem getMenuItem()
    {
        return parentMenuItem;
    }

    public int getChildrenSize()
    {
        return children.size();
    }

    public boolean checkConsistency()
    {
        boolean result = true;
        for(int i = 0; i < children.size() && result; i++)
        {
            if(!(children.get(i) instanceof MenuItemList))
                result = false;
            MenuItemList childList = (MenuItemList)children.get(i);
            if(!childList.getMenuItem().equals(parentMenuItem))
                result = false;
            result = childList.checkConsistency();
        }

        return result;
    }

    public Iterator getChildrenIterator()
    {
        return children.iterator();
    }

    public String toString()
    {
        if(parentMenuItem == null)
            return "(0) null";
        else
            return "(" + parentMenuItem.getDepth() + ") " + parentMenuItem.getName();
    }

    private static String lineseparator = System.getProperty("line.separator");
    private Vector children;
    private IMenuItem parentMenuItem;

}