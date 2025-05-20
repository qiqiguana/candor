package org.character.net.packet;

import org.javathena.core.net.IPacket;
import org.javathena.core.net.SessionPacket;

public class ClientConnect implements IPacket
{

    @Override
    public byte[] execute(SessionPacket requeste)
    {
	SessionPacket awnser = null;// new SessionPacket(10);
	
	return awnser.getPacketTab();
    }

}
