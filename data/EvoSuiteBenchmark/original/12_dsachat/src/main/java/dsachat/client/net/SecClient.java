package dsachat.client.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
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
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import dsachat.share.ConnectionEndpoint;
import dsachat.share.InputEvent;
import dsachat.share.InputListener;
import dsachat.share.KeyExc;


/**
 * This class represents the network connection of the client.
 * It contains a socket but does more than a basic one
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
public class SecClient extends Thread implements ConnectionEndpoint{
	
	private Socket s;
	private DHParameterSpec dhSkipParamSpec;
	private KeyPair clientKpair;
	private KeyAgreement clientKeyAgree;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	byte[] aliceSharedSecret;
	private boolean run = true;
	private Vector<InputListener> inpList = new Vector<InputListener>();
	private boolean connected = false;


	public SecClient() {}
	
	/**
	 * Create new key specifications or use static ones
	 * @param create true: create new specifications
	 * @throws GeneralSecurityException when an error during generation of spec occurs
	 */
	public void initialize(boolean create)
		throws GeneralSecurityException
	{
		if(create){
			AlgorithmParameterGenerator paramGen
	        	= AlgorithmParameterGenerator.getInstance("DH");
			paramGen.init(512);
			AlgorithmParameters params = paramGen.generateParameters();
			dhSkipParamSpec = (DHParameterSpec)params.getParameterSpec
	        	(DHParameterSpec.class);
		}else{
			dhSkipParamSpec = new DHParameterSpec(skip1024Modulus,
	                skip1024Base);
		}
		/*
		 * This client creates its own DH key pair, using the DH parameters from
		 * above
		 */
		KeyPairGenerator aliceKpairGen = KeyPairGenerator.getInstance("DH");
		aliceKpairGen.initialize(dhSkipParamSpec);
		clientKpair = aliceKpairGen.generateKeyPair();
		// Alice creates and initializes her DH KeyAgreement object
		clientKeyAgree = KeyAgreement.getInstance("DH");
		clientKeyAgree.init(clientKpair.getPrivate());
	}
	
	/**
	 * set up the connection
	 * @param host the host
	 * @param port the port
	 * @throws UnknownHostException if host can't be resolved
	 * @throws IOException error while connecting
	 */
	public void connect(String host, int port)
		throws UnknownHostException, IOException
	{
		s = new Socket(host,port);
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
	}
	
	/**
	 * send public DH key
	 * @throws GeneralSecurityException encoding of key failed
	 * @throws IOException connection error
	 */
	public void exchange()
		throws GeneralSecurityException, IOException
	{
        // Client encodes his public key, and sends it over to Server.
        byte[] alicePubKeyEnc = clientKpair.getPublic().getEncoded();
        out.writeObject(new KeyExc(alicePubKeyEnc));
	}

	/**
	 * this is done in a thread.
	 * wait for objects to arrive (readObject blocks)
	 * and pass the object to the listener if it is not for key exchange
	 */
	public void run()  {
	    	
	    	while(run){
	    		try{
    				Object o = in.readObject();
    				if(o==null)
    					throw new IOException("received object was null");
    				if(o.getClass().equals(KeyExc.class)){
    					/*
    					 * Alice uses Bob's public key for the first (and only) phase
    					 * of her version of the DH
    					 * protocol.
    					 * Before she can do so, she has to instantiate a DH public key
    					 * from Bob's encoded key material.
    					 */
    					KeyFactory aliceKeyFac = KeyFactory.getInstance("DH");
    					X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
    							((KeyExc)o).getKey());
    					PublicKey bobPubKey = aliceKeyFac.generatePublic(x509KeySpec);
    					clientKeyAgree.doPhase(bobPubKey, true);
    					/*
    					 * At this stage, both Alice and Bob have completed the DH key
    					 * agreement protocol.
    					 * Both generate the (same) shared secret.
    					 */
    					aliceSharedSecret = clientKeyAgree.generateSecret();
    					byte[] cltSecKey = new byte[16];
    					for(int i=0;i<16;i++){
    						cltSecKey[i] = aliceSharedSecret[i];
    					}
    					SecretKeySpec aliceDesKey =
				        	new SecretKeySpec(cltSecKey, "RC4");
    			        Cipher clientEncCipher =
				        	Cipher.getInstance("RC4");
				        clientEncCipher.init(Cipher.ENCRYPT_MODE, aliceDesKey);
				        out = new ObjectOutputStream(
				        		new CipherOutputStream(s.getOutputStream(),clientEncCipher));
				        out.flush();
				        Cipher clientDecCipher =
				        	Cipher.getInstance("RC4");
				        clientDecCipher.init(Cipher.DECRYPT_MODE, aliceDesKey);
				        in = new ObjectInputStream(
				        		new CipherInputStream(s.getInputStream(),clientDecCipher));
				        System.out.println("cipher established");
				        connected = true;
				        
    				}//end key exchange
    				else{
    					fireEvent(o);
    				}
	    		}
	    		catch(IOException e){
					System.out.println("disconnected");
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
	 * @param o the object to send
	 */
    public void send(Object o) throws IOException{
    	out.writeObject(o);
    	out.flush();
    }
	
    /**
     * disconnect this client
     * @throws IOException error occurs
     */
    public void disconnect() throws IOException{
    	run = false;
    	s.close();
    }

	    // The 1024 bit Diffie-Hellman modulus values used by SKIP
	    private static final byte skip1024ModulusBytes[] = {
	        (byte)0xF4, (byte)0x88, (byte)0xFD, (byte)0x58,
	        (byte)0x4E, (byte)0x49, (byte)0xDB, (byte)0xCD,
	        (byte)0x20, (byte)0xB4, (byte)0x9D, (byte)0xE4,
	        (byte)0x91, (byte)0x07, (byte)0x36, (byte)0x6B,
	        (byte)0x33, (byte)0x6C, (byte)0x38, (byte)0x0D,
	        (byte)0x45, (byte)0x1D, (byte)0x0F, (byte)0x7C,
	        (byte)0x88, (byte)0xB3, (byte)0x1C, (byte)0x7C,
	        (byte)0x5B, (byte)0x2D, (byte)0x8E, (byte)0xF6,
	        (byte)0xF3, (byte)0xC9, (byte)0x23, (byte)0xC0,
	        (byte)0x43, (byte)0xF0, (byte)0xA5, (byte)0x5B,
	        (byte)0x18, (byte)0x8D, (byte)0x8E, (byte)0xBB,
	        (byte)0x55, (byte)0x8C, (byte)0xB8, (byte)0x5D,
	        (byte)0x38, (byte)0xD3, (byte)0x34, (byte)0xFD,
	        (byte)0x7C, (byte)0x17, (byte)0x57, (byte)0x43,
	        (byte)0xA3, (byte)0x1D, (byte)0x18, (byte)0x6C,
	        (byte)0xDE, (byte)0x33, (byte)0x21, (byte)0x2C,
	        (byte)0xB5, (byte)0x2A, (byte)0xFF, (byte)0x3C,
	        (byte)0xE1, (byte)0xB1, (byte)0x29, (byte)0x40,
	        (byte)0x18, (byte)0x11, (byte)0x8D, (byte)0x7C,
	        (byte)0x84, (byte)0xA7, (byte)0x0A, (byte)0x72,
	        (byte)0xD6, (byte)0x86, (byte)0xC4, (byte)0x03,
	        (byte)0x19, (byte)0xC8, (byte)0x07, (byte)0x29,
	        (byte)0x7A, (byte)0xCA, (byte)0x95, (byte)0x0C,
	        (byte)0xD9, (byte)0x96, (byte)0x9F, (byte)0xAB,
	        (byte)0xD0, (byte)0x0A, (byte)0x50, (byte)0x9B,
	        (byte)0x02, (byte)0x46, (byte)0xD3, (byte)0x08,
	        (byte)0x3D, (byte)0x66, (byte)0xA4, (byte)0x5D,
	        (byte)0x41, (byte)0x9F, (byte)0x9C, (byte)0x7C,
	        (byte)0xBD, (byte)0x89, (byte)0x4B, (byte)0x22,
	        (byte)0x19, (byte)0x26, (byte)0xBA, (byte)0xAB,
	        (byte)0xA2, (byte)0x5E, (byte)0xC3, (byte)0x55,
	        (byte)0xE9, (byte)0x2F, (byte)0x78, (byte)0xC7
	    };

	    // The SKIP 1024 bit modulus
	    private static final BigInteger skip1024Modulus
	    = new BigInteger(1, skip1024ModulusBytes);

	    // The base used with the SKIP 1024 bit modulus
	    private static final BigInteger skip1024Base = BigInteger.valueOf(2);
	    
	    private void fireEvent(Object o){
			InputEvent e = new InputEvent(this,o);
			for(int i=0;i<inpList.size(); i++){
				inpList.elementAt(i).handleInput(e);
			}
		}

		public void addInputListener(InputListener l) {
			inpList.addElement(l);
		}

		public void removeInputListener(InputListener l) {
			inpList.removeElement(l);
		}

		public int getClientPort() {
			return s.getLocalPort();
		}

		public boolean isConnected() {
			return connected ;
		}
}
