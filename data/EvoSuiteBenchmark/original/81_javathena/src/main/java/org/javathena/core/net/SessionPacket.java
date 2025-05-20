package org.javathena.core.net;

public class SessionPacket
{
    protected byte[] packet = null;
    protected int index = 0;

    public SessionPacket(byte[] packet)
    {
	this.packet = packet;
    }

    public SessionPacket(int length)
    {
	this.packet = new byte[length];
    }

    protected void writeInteger(int nInt)
    {
	packet[index] = (byte) (nInt % 256);
	nInt /= 256;
	packet[index + 1] = (byte) (nInt % 256);

	index += 2;
    }

    protected void writeString(String nString, int end)
    {
	for (int i = index, j = 0; i < end && j < nString.length(); i++, j++)
	{
	    packet[i] = (byte) nString.charAt(j);
	}
	index += nString.length();
    }

    protected void writeByte(byte nByte)
    {
	packet[index] = nByte;
	index++;
    }

    protected String readString(int start, int end, byte[] buf)
    {
	return readString(start, end, buf, false);
    }

    protected String readString(int start, int end, byte[] buf, boolean stopToZero)
    {
	String str = "";
	for (int i = start; i < end && (buf[i] != 0 || !stopToZero); i++)
	{
	    if (buf[i] != 0)
	    {
		str += (char) buf[i];
	    }
	}
	return str;
    }

    public static int readInt()
    {
	return 0;
    }

    public byte[] getPacketTab()
    {
	return packet;
    }
}
