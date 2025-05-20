
package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import net.java.games.joal.AL;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import net.java.games.joal.OpenALException;
import java.util.Hashtable;



public class AudioManager {

    private boolean ready = false;
    private boolean initialized;
    private static AL al;
    private Hashtable handles;
    private int nSamples;
    // Buffers hold sound data.
    private static int[] buffers = new int[128];

    // Sources are points emitting sound.
    private static int[] source = new int[128];

    // Position of the source sound.
    

    // Velocity of the source sound.
    private static float[] sourceVel = { 0.0f, 0.0f, 0.0f };

    private float[] listenerPos = { .0f, 0.0f, 0.0f };
    // Velocity of the listener.
    private static float[] listenerVel = { 0.0f, 0.0f, 0.0f };

    // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
    private static float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };
    
    public boolean loadSample(String filename, String handle, boolean loops) {
	if (ready) {
	if (handles.containsKey(handle)) {

	    System.out.println("AudioManager: handle exists");
	    return false;
	}

	// variables to load into

        int[] format = new int[1];
        int[] size = new int[1];
        ByteBuffer[] data = new ByteBuffer[1];
        int[] freq = new int[1];
        int[] loop = new int[1];

        // Load wav data into a buffer.
        ALut.alutLoadWAVFile(
            filename,
            format,
            data,
            size,
            freq,
            loop);
        al.alBufferData(buffers[nSamples], format[0], data[0], size[0], freq[0]);
        ALut.alutUnloadWAV(format[0], data[0], size[0], freq[0]);

        // Bind buffer with a source.
        
        if (al.alGetError() != AL.AL_NO_ERROR) {
	    System.out.println("AudioManager: Couldn't create sources");
	    return false;
	}
         
	float [] sourcePos = {((float)Math.random()%4.0f - 2.0f),
			      ((float)Math.random()%4.0f - 2.0f),
			      ((float)Math.random()%4.0f - 2.0f)};
        al.alSourcei(source[nSamples], AL.AL_BUFFER, buffers[nSamples]);
        al.alSourcef(source[nSamples], AL.AL_PITCH, 1.0f);
        al.alSourcef(source[nSamples], AL.AL_GAIN, 1.0f);
        al.alSourcefv(source[nSamples], AL.AL_POSITION, sourcePos);
        al.alSourcefv(source[nSamples], AL.AL_VELOCITY, sourceVel);
	if (loops) {
	    al.alSourcei(source[nSamples], AL.AL_LOOPING, AL.AL_TRUE);
	} else {
	    al.alSourcei(source[nSamples], AL.AL_LOOPING, AL.AL_FALSE);
	}

        // Do another error check and return.
	int i = al.alGetError();
        if (i == AL.AL_NO_ERROR){
	    System.out.println("AudioManager: All ok");
	    handles.put(handle, new Integer(nSamples));
	    nSamples++;
	    ready = true;
	    return true;
	}
	System.out.println("LoadSample, error: " + i);
	System.out.println(AL.AL_INVALID_OPERATION  );
	System.out.println(AL.AL_OUT_OF_MEMORY  );
	System.out.println(AL.AL_INVALID_VALUE  );
	System.out.println(al.AL_INVALID_NAME  );

	
	}
        return false;
    }

    
    public void setEnabled(boolean b) {
	ready = b;
   }

    private void setListenerValues() {
	if (ready) {
        al.alListenerfv(AL.AL_POSITION, listenerPos);
        al.alListenerfv(AL.AL_VELOCITY, listenerVel);
        al.alListenerfv(AL.AL_ORIENTATION, listenerOri);
	}
    }

    public void killAllData() {
	if (initialized) {
	    al.alDeleteBuffers(128, buffers);
	    al.alDeleteSources(128, source);
	    ALut.alutExit();
	}
    }

    public AudioManager() {

	this(true);

    }	
    public AudioManager(boolean b) {
	if (b) {
	    initialized = false;
	    try {
		al = ALFactory.getAL();
		ALut.alutInit();
		al.alGetError();
		initialized = true;
	    } catch (OpenALException e) {
		e.printStackTrace();
		//System.exit(1);
	    }
	    al.alGenBuffers(128, buffers);
	    al.alGenSources(32, source);
	    handles = new Hashtable();
	    nSamples = 0;
	    setListenerValues();
	    ready = true;
	}
	
    }

    public void play(String handle) {
	
	if (ready && handles.containsKey(handle))  {
	    Integer i = (Integer)handles.get(handle);
	    System.out.println("playing: " + i);
	    al.alSourcePlay(source[i.intValue()]);
	}
    }

    public void stop(String handle) {
	if (ready && handles.containsKey(handle))  {
	    Integer i = (Integer)handles.get(handle);
	    al.alSourceStop(source[i.intValue()]);
	}
    }

    public void moveListener(float x, float y, float z) {

	if (ready) {
	    al.alListener3f(al.AL_POSITION, x, y, z);
	    al.alListener3f(AL.AL_VELOCITY, 0,0,0);
	    al.alListenerfv(AL.AL_ORIENTATION, listenerOri);
	}
    }

    public boolean moveSource(String handle, float x, float y, float z) {
	
	if (ready && handles.containsKey(handle)) {
	     Integer i = (Integer)handles.get(handle);
	     
	     al.alSource3f(source[i.intValue()], AL.AL_POSITION, x,y,z);
	     return true;
	}
	return false;

    }

/*    public static void main(String argv[]) {
	BufferedReader input = 
	    new BufferedReader(new InputStreamReader(System.in));

	AudioManager a = new AudioManager();
	if(!a.loadSample("test.wav", "test", false)) {

	    System.out.println("error");
	    a.killAllData();
	    System.exit(0);
	    
	}
	if(!a.loadSample("hit.wav", "test2", false)) {

	    System.out.println("error2");
	    a.killAllData();
	    System.exit(0);
	    
	}
	if(!a.loadSample("test2.wav", "ambient", true)) {

	    System.out.println("error3");
	    a.killAllData();
	    System.exit(0);
	    
	}
	if(!a.loadSample("test3.wav", "test3", false)) {

	    System.out.println("error4");
	    a.killAllData();
	    System.exit(0);
	    
	}
	if(!a.loadSample("test4.wav", "test4", false)) {

	    System.out.println("error5");
	    a.killAllData();
	    System.exit(0);
	    
	}
	a.play("ambient");
	a.play("test");
	String s = "";
	float t = 0;
	float u = 0;
	t += .01;
	u += .01;
	try {
	    while(!s.equals("q")) {
		
		Character c = new Character((char)input.read());
		s = c.toString();
		if (s.equals("w")) {
		    t += .5;
		    a.moveListener(u,t,0);
		}
		if (s.equals("s")) {
		    t -= .5;
		    a.moveListener(u,t,0);
		}
		if (s.equals("a")) {
		    u -= .5;
		    a.moveListener(u,t,0);
		}
		if (s.equals("d")) {
		    u += .5;
		    a.moveListener(u,t,0);
		}
		if (s.equals("u")) {
		    a.play("test");
		}
		if (s.equals("i")) {
		    a.play("test2");
		}
		if (s.equals("o")) {
		    a.play("test3");
		}
		if (s.equals("p")) {
		    a.play("test4");
		}
		//System.out.println("S: " + s + ";");
	    }
	} catch (Exception e) { 
	    a.killAllData(); 
	    System.exit(0);
	    
	}
	
	a.killAllData();
	System.exit(0);
    }*/

}
