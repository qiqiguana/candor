package org.javathena.core.data;

import org.javathena.core.utiles.Functions;


public class Packet
{
	private byte data[];

	private int currentIndex;

	public Packet(int size)
	{
		data = new byte[size];
		currentIndex = 0;
	}

	public void writeString(String str)
	{

		assert currentIndex + str.length() < data.length;
		Functions.stringToByteTable(str, data, currentIndex,
				currentIndex += str.length());
	}

	public void writeString(String str, int size)
	{

		size = (size > data.length) ? size : data.length;
		assert currentIndex + size < data.length;
		Functions.stringToByteTable(str, data, currentIndex,
				currentIndex + str.length());
		currentIndex += size;
	}

	public void writeByte(byte b)
	{

		assert currentIndex + 1 < data.length;
		data[currentIndex++] = b;
	}
/*
	public void writeIndexable(Indexable1 ind)
	{
		assert currentIndex + ind.getSize() < data.length;
		ind.toByteTable(data, currentIndex);
		currentIndex += ind.getSize();
	}
*/
	public void writeBoolean(boolean b)
	{
		assert currentIndex + 1 < data.length;
		data[currentIndex++] = (byte) (b ? 1 : 0);
	}

	public void writeInteger(int i)
	{
		assert currentIndex + 4 < data.length;
		Functions.intToByteTab(i, currentIndex, currentIndex += 4, data);
	}

	public void writeShort(int i)
	{
		assert currentIndex + 2 < data.length;
		Functions.intToByteTab(i, currentIndex, currentIndex += 2, data);
	}

	public String readString(int length)
	{
		assert currentIndex + length < data.length;
		return Functions.byteTabToString(currentIndex, currentIndex += length,
				data);
	}

	public byte readByte()
	{

		assert currentIndex + 1 < data.length;
		return data[currentIndex++];
	}

	public boolean readBoolean()
	{

		assert currentIndex + 1 < data.length;
		return data[currentIndex++] >= 1;
	}

	public int readInteger()
	{
		assert currentIndex + 4 < data.length;
		return Functions.byteTabToInt(currentIndex, currentIndex += 4, data);
	}

	public short readShort()
	{
		assert currentIndex + 2 < data.length;
		return Functions.byteTabToShort(currentIndex, currentIndex += 2, data);
	}
	/*public void readIndexable(Indexable1 ind)
	{
		assert currentIndex + ind.getSize() < data.length;
		ind.fromByteTable(data,currentIndex);
		currentIndex += ind.getSize();
	}*/
	public int getCurrentIndex()
	{
		return currentIndex;
	}

	public boolean setCurrentIndex(int currentIndex)
	{
		if (currentIndex < data.length)
		{
			this.currentIndex = currentIndex;
			return true;
		} else
			return false;
	}

	public void resize(int newSize)
	{
		byte ndata[] = new byte[newSize];
		for (int i = 0; i < newSize && i < data.length; i++)
		{
			ndata[i] = data[i];
		}
		data = ndata;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

}
