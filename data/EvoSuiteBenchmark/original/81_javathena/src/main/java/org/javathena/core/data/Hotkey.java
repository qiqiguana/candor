package org.javathena.core.data;

public class Hotkey
{
	/*public enum HOTKEY_TYPE{
		ITEM,
		SKILL}*/
	private int id;
	private int lv;
	private int type;
	
	public Hotkey(int id, int lv,int type)
	{
		this.id = id;
		this.lv = lv;
		this.type = type;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getLv()
	{
		return lv;
	}
	public void setLv(int lv)
	{
		this.lv = lv;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
}
