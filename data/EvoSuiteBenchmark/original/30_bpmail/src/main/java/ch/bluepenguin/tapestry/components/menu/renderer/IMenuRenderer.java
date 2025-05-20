// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IMenuRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer;

import ch.bluepenguin.tapestry.components.menu.model.IMenuModel;
import ch.bluepenguin.tapestry.components.menu.model.MenuStructureException;
import org.apache.tapestry.IMarkupWriter;

public interface IMenuRenderer
{

    public abstract void render(IMarkupWriter imarkupwriter, IMenuModel imenumodel)
        throws MenuStructureException;

    public abstract void setMenuClass(String s);

    public abstract void setSubMenuClass(String s);

    public abstract void setSubmenuTag(String s);
}