/*
 * Connection.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection
{
    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Connection(SocketChannel channel) {
		this.channel = channel;
	}

	public SocketChannel getChannel() {
		return channel;
	}
	
	public void handleRead(SelectionKey key) {
		try {
			int bytesRead = -1;
			do {
				inputBuffer.clear();
				bytesRead = channel.read(inputBuffer);
				if(bytesRead != 0 && bytesRead != -1) {
					inputBuffer.flip();
					outputBuffer.clear();
					outputBuffer.put(inputBuffer);
					setWrite(key, true);
					inputBuffer.rewind();
					while(inputBuffer.hasRemaining()) {
					    System.out.print("[" + toHex(inputBuffer.get()) + "]");
					}
					inputBuffer.rewind();
					CharBuffer cb = decoder.decode(inputBuffer);
				    System.out.print("Read: '" + cb + "'");
				}
			} while(bytesRead != 0 && bytesRead != -1);
		} catch(IOException e) {
			logger.warn("IOException: " + e.getMessage());
		}
	}
	
	public void handleWrite(SelectionKey key) {
		setWrite(key, false);
		try {
			outputBuffer.flip();
			channel.write(outputBuffer);
			if(outputBuffer.hasRemaining()) {
				setWrite(key, true);
			} else {
				outputBuffer.clear();
			}
		} catch(IOException e) {
			logger.warn("IOException: " + e.getMessage());
		}
	}
	
	private void setWrite(SelectionKey key, boolean newWriteState) {
		int interestOps = key.interestOps();
		if(newWriteState == true) {
			interestOps |= SelectionKey.OP_WRITE;
		} else {
			interestOps &= (~SelectionKey.OP_WRITE);
		}
		try {
			SelectionKey updateRegistration = channel.register(key.selector(), interestOps, key.attachment());
			if(updateRegistration != key) {
				logger.warn("SelectionKey mismatch after setWrite(" + newWriteState + ")");
			}
		} catch(ClosedChannelException e) {
			logger.warn("ClosedChannelException: " + e.getMessage());
		}
	}
	
	private String toHex(byte b) {
		final String HEX = "0123456789ABCDEF";
		int i = (int)b;
		i &= 0xFF;
		return "" + HEX.charAt((i & 0xF0) >> 4) + HEX.charAt(i & 0x0F);
	}
	
	private SocketChannel channel;
	
	private ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
	private ByteBuffer outputBuffer = ByteBuffer.allocate(1024);
}
