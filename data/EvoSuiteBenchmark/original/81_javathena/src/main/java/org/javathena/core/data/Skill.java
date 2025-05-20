package org.javathena.core.data;

public class Skill
{
	private short id;
	private short lv;
	private short flag;
	
	public Skill(short id, short lv)
	{
		this.id = this.lv;
	}
	
	public short getId()
	{
		return id;
	}
	public void setId(short id)
	{
		this.id = id;
	}
	public short getLv()
	{
		return lv;
	}
	public void setLv(short lv)
	{
		this.lv = lv;
	}
	public short getFlag()
	{
		return flag;
	}
	public void setFlag(short flag)
	{
		this.flag = flag;
	}
}
