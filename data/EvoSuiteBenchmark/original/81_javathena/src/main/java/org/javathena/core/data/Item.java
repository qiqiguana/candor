package org.javathena.core.data;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class Item
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4983465942644780755L;
	
	public static final int MAX_SLOT = 4;
	
	@XStreamOmitField
	private int id;
	private short nameid;
	private short amount;
	private short equip; // location(s) where item is equipped (using enum equip_pos for
					// bitmasking)
	private int identify;
	private int refine;
	private int attribute;
	private int cards[];
	private int expire_time;
	
	
	
	public Item()
	{
		cards = new int[MAX_SLOT];
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public short getNameid()
	{
		return nameid;
	}
	public void setNameid(short nameid)
	{
		this.nameid = nameid;
	}
	public short getAmount()
	{
		return amount;
	}
	public void setAmount(short amount)
	{
		this.amount = amount;
	}
	public short getEquip()
	{
		return equip;
	}
	public void setEquip(short equip)
	{
		this.equip = equip;
	}
	public int getIdentify()
	{
		return identify;
	}
	public void setIdentify(int identify)
	{
		this.identify = identify;
	}
	public int getRefine()
	{
		return refine;
	}
	public void setRefine(int refine)
	{
		this.refine = refine;
	}
	public int getAttribute()
	{
		return attribute;
	}
	public void setAttribute(int attribute)
	{
		this.attribute = attribute;
	}
	public int getCard(int ind)
	{
		return cards[ind];
	}
	public void setCard(int ind, int card)
	{
		this.cards[ind] = card;
	}
	public int getExpire_time()
	{
		return expire_time;
	}
	public void setExpire_time(int expire_time)
	{
		this.expire_time = expire_time;
	}
}
