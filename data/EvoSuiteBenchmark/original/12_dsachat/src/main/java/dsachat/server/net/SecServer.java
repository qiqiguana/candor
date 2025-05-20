package dsachat.server.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import dsachat.server.Handler;
import dsachat.share.ConnectionEndpoint;
import dsachat.share.InputEvent;
import dsachat.share.InputListener;
import dsachat.share.KeyExc;

/**
 * This class represents the network connection fo the server.
 * It contains a socket but does mor than a basic one
 * When a connection begins the client sends a Diffie-Hellman (DH) key spec.
 * From that key spec this server creates a new Dh keypair and sends the public part to
 * the client.<br>
 * He also receives the public key from the client. after that both create a shared secret
 * and they are setting up a RC-4 cipher with a 128 bit key
 * then the Streams are set. The basic stream is the in- and outputstream from the socket
 * first wrapped by cipher streams and finnaly wrapped in Object streams to send serializable
 * objects.
 * <br>
 * if the server receives an object it passes it to the listener, but only after the 
 * exchange happened
 * 
 * @author bernshausen
 *
 */
public class SecServer extends Thread implements ConnectionEndpoint{

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	KeyAgreement serverKeyAgree;
	KeyPair serverKpair;
	private Vector<InputListener> inpList = new Vector<InputListener>();
	private boolean connected;
	
	/**
	 * create a new server
	 * @param con the socket of that connection
	 */
	public SecServer(Socket con){
		s=con;
		connected = false;
	}
	
	/**
	 * this is done in a thread.
	 * wait for objects to arrive (readObject blocks)
	 * and pass the object to the listener if it is not for ey exchange
	 */
	public void run(){
		System.out.println("connection from " +s+" accepted: "+s.isConnected());
		try {
			out=new ObjectOutputStream(s.getOutputStream());
			in=new ObjectInputStream(s.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(s.isConnected()){
			try {
				Object o;
				o = in.readObject();
				if(o==null)//that means input stream was closed
					throw new IOException("received object is null");
				if(o.getClass().equals(KeyExc.class)){
					/*
					 * Server has received client public key
					 * in encoded format.
					 * He instantiates a DH public key from the encoded key material.
					 */
					KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
					
					X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
							((KeyExc)o).getKey());
					PublicKey alicePubKey = serverKeyFac.generatePublic(x509KeySpec);
					/*
			         * Server gets the DH parameters associated with Alice's public key.
			         * He must use the same parameters when he generates his own key
			         * pair.
			         */
			        DHParameterSpec dhParamSpec = ((DHPublicKey)alicePubKey).getParams();

			        // Bob creates his own DH key pair
			        KeyPairGenerator bobKpairGen = KeyPairGenerator.getInstance("DH");
			        bobKpairGen.initialize(dhParamSpec);
			        serverKpair = bobKpairGen.generateKeyPair();

			        // Bob creates and initializes his DH KeyAgreement object
			        serverKeyAgree = KeyAgreement.getInstance("DH");
			        serverKeyAgree.init(serverKpair.getPrivate());
			        
			        // Bob encodes his public key, and sends it over to Alice.
			        byte[] bobPubKeyEnc = serverKpair.getPublic().getEncoded();
			        out.writeObject(new KeyExc(bobPubKeyEnc));
			        /*
			         * Bob uses Alice's public key for the first (and only) phase
			         * of his version of the DH
			         * protocol.
			         */
			        serverKeyAgree.doPhase(alicePubKey, true);
			        /*
					 * At this stage, both Alice and Bob have completed the DH key
					 * agreement protocol.
					 * Both generate the (same) shared secret.
					 */
			        byte[] bobSharedSecret  = serverKeyAgree.generateSecret();
			        
			        byte[] srvSecKey = new byte[16];
			        for(int i=0;i<16;i++){
						srvSecKey[i] = bobSharedSecret[i];
					}
			        SecretKeySpec skeySpec =
			        	new SecretKeySpec(srvSecKey, "RC4");
			        //set up ciphers and streams
			        Cipher serverDecCipher =
			        	Cipher.getInstance("RC4");
			        serverDecCipher.init(Cipher.DECRYPT_MODE, skeySpec);
			        in = new ObjectInputStream(
			        		new CipherInputStream(s.getInputStream(),serverDecCipher));
			        Cipher serverEncCipher =
			        	Cipher.getInstance("RC4");
			        serverEncCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			        out = new ObjectOutputStream(
			        		new CipherOutputStream(s.getOutputStream(),serverEncCipher));
			        out.flush();
			        System.out.println("Cipher established");
			        connected =true;
				}//finished key exchange
				else{
					fireEvent(o);
				}
			} catch (IOException e) {
				//if that exception is thrown the connection is closed
				System.out.println("+++ end connection to "+s);
				for(int i=0; i<inpList.size(); i++){//JUST for this chat
					((Handler)inpList.elementAt(i)).removeConnection(this);
				}
				return;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * send an object
	 */
    public void send(Object o) throws IOException{
    	out.writeObject(o);
    	out.flush();
    }

	public void addInputListener(InputListener l) {
		inpList.addElement(l);
	}

	public void removeInputListener(InputListener l) {
		inpList.removeElement(l);
	}
	
	//send the object to listeners
	private void fireEvent(Object o){
		InputEvent e = new InputEvent(this,o);
		for(int i=0;i<inpList.size(); i++){
			inpList.elementAt(i).handleInput(e);
		}
	}

	public int getClientPort() {
		return s.getPort();
	}

	public boolean isConnected() {
		return connected;
	}
}
