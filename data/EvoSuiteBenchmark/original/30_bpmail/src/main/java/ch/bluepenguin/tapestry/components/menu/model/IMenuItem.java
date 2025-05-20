//Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
//Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
//Decompiler options: packimports(3) 
//Source File Name:   IMenuItem.java

package ch.bluepenguin.tapestry.components.menu.model;

import ch.bluepenguin.tapestry.components.menu.renderer.IMenuItemRenderer;

public interface IMenuItem
{

 public abstract void setRenderer(IMenuItemRenderer imenuitemrenderer);

 public abstract IMenuItemRenderer getRenderer();

 public abstract void setLeaf(boolean flag);

 public abstract boolean isLeaf();

 public abstract void setName(String s);

 public abstract String getName();

 public abstract int getIndex();

 public abstract void setIndex(int i);

 public abstract int getDepth();

 public abstract void setDepth(int i);

 public abstract void setContained(Object obj);

 public abstract Object getContained();

 public abstract void setParent(Object obj);

 public abstract Object getParent();
}