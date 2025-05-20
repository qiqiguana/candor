// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import org.apache.tapestry.IMarkupWriter;

public interface IMenuItemRenderer
{

    public abstract void setStyleClass(String s);

    public abstract void render(IMarkupWriter imarkupwriter, IMenuItem imenuitem);

    public abstract void startRender(IMarkupWriter imarkupwriter, IMenuItem imenuitem);

    public abstract void stopRender(IMarkupWriter imarkupwriter, IMenuItem imenuitem);
}