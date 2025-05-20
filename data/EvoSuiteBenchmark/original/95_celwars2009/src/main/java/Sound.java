
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class Sound 
{
	private HashMap<String, java.applet.AudioClip> sounds;
	private boolean initialized = false;
	java.applet.AudioClip current = null;
	
	public void initialize()
	{
		
		sounds = new HashMap<String, java.applet.AudioClip>();
		File dir = new File("Sounds/");
		String[] fileList = dir.list();
		for(int i = 0; i < fileList.length; i++)
		{
			if(fileList[i].endsWith(".wav"))
			{
				try {
					java.applet.AudioClip clip = null;
					clip = java.applet.Applet.newAudioClip(new java.net.URL("file:Sounds/"+fileList[i]));
					sounds.put(fileList[i], clip);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					return;
				}
			}
		}
		
		initialized = true;
	}
	
	
	public void playSound(String file)
    {
		if(initialized)
		{
			if(current != null)
				current.stop();
			java.applet.AudioClip clip = sounds.get(file);
			if(clip != null)
			{
				clip.play();
				current = clip;
			}
		}
	}

}
