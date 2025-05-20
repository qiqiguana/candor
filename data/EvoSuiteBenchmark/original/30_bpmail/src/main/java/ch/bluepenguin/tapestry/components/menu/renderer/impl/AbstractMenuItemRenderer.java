// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AbstractMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import ch.bluepenguin.tapestry.components.menu.renderer.IMenuItemRenderer;
import org.apache.tapestry.IMarkupWriter;

public abstract class AbstractMenuItemRenderer
    implements IMenuItemRenderer
{

    public AbstractMenuItemRenderer()
    {
    }

    public void setStyleClass(String styleClass)
    {
        this.styleClass = styleClass;
    }

    public void render(IMarkupWriter writer, IMenuItem item)
    {
        startRender(writer, item);
        stopRender(writer, item);
    }

    public void stopRender(IMarkupWriter writer, IMenuItem item)
    {
        writer.end();
        writer.flush();
        writer.close();
    }

    protected String styleClass;
}