package org.character.parse;

import org.character.net.packet.ClientConnect;
import org.javathena.core.data.IParse;
import org.javathena.core.data.Socket_data;
import org.javathena.core.net.IPacket;
import org.javathena.core.net.SessionPacket;

public class FromClient implements IParse
{
    
    private static IPacket[] packets;
    static
    {
	packets = new IPacket[0x7530 + 1];
	packets[0x65] = new ClientConnect();
	packets[0x66] = null;
	packets[0x67] = null;
	packets[0x68] = null;
	packets[0x187] = null;
	packets[0x1fb] = null;
	packets[0x28d] = null;
	packets[0x7e5] = null;
	packets[0x7e7] = null;
	packets[0x2af8] = null;
	packets[0x7530] = null;
    }
    @Override
    public int parse(Socket_data session, byte[] packet)
    {
	session.func_send(packets[packet[0] + (packet[1] * 256)].execute(new SessionPacket(packet)));
	return 0;
    }

}
