package sound;

/**
 * Sound Interface
 * plays sounds
 * @author Ghazwan, Tobi
 *
 */
public interface ISound {
		public final String INTRO 	 	="/SoundFiles/intro.mp3";
		public final String BUTTON	 	="/SoundFiles/buttongedrueckt.mp3";
		public final String HINTERGRUND ="/SoundFiles/Hintergrundmusik.mp3";
		public final String EXPLOSION1 	="/SoundFiles/BombeExplodiert.mp3";
		public final String EXPLOSION2 	="/SoundFiles/BombeExplodiert2.mp3";
		public final String KASSE 		="/SoundFiles/Kasse.mp3";
		public final String BUTTON2 	="/SoundFiles/von_button_zu_button_springen.mp3";
		public final String POWER_UP 	="/SoundFiles/Power Up.mp3";
		public final String VERLIERT   	="/SoundFiles/SpielerVerliert.mp3";
		public final String GEWINNT  	="/SoundFiles/SpielerGewinnt.mp3";
		public final String OUTRO  		="/SoundFiles/Outro.mp3";
		//public final String GAME_OVER ="/SoundFiles/game_over.mp3";
		public final String B_WERFEN 	="/SoundFiles/b_werfen.mp3";
		public final String STIRBT 	="/SoundFiles/MaennchenStirbt.mp3";
		public final String READY = "/SoundFiles/AreYouReady.mp3";
		
		public void intro();
		public void button();
		public void button2();
		public void hintergrund();
		public void bWerfen();
		public void explosion1();
		public void explosion2();
		public void kasse();
		public void powerUp();
		public void verliert();
		public void gewinnt();
		public void outro();
		public void stirbt();
}
