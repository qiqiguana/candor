package org.javathena.core.data;

public class Point
{
	private int mapId;
	private int x;
	private int y;

	public Point(int mapId, int x, int y)
	{
		this.mapId = mapId;
		this.x = x;
		this.y = y;
	}
	public int getMapId()
	{
		return mapId;
	}

	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
