package client.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import common.ResourceService;
 /**
  * 
  * @author Andreas Glauner
  * @autor Adam Kozielski
  *
  */
public class GfxFactory {

	private HashMap<Integer, Image> wallImageCache;
	
	private HashMap<String, Image> tileImageCache;
	
	private HashMap<String, Image> powerUpImageCache;
	
	private HashMap<String, Image> o;
	
	private HashMap<Integer, Image>[] playerImageCache;
	
	private HashMap<Integer, Image>[] bombImageCache;
	
	private HashMap<Integer, Integer> idTranslator;
	
	private HashMap<Integer, Image>[] explosionImageCache;
	
	private ExplosionGfxFactory explosionGfxFactory;
	
	private int currentId = 1;
	
	private static String basePath = "/gfx";
	
	private String tileSet = "ice";
	
	private Logger logger = Logger.getLogger(GfxFactory.class);
	
	
	public GfxFactory() {
		wallImageCache = new HashMap<Integer, Image>();
		playerImageCache = new HashMap[9];
		for(int i = 0; i < 9; i++){
			playerImageCache[i] = new HashMap<Integer, Image>();
		}
		bombImageCache = new HashMap[9];
		for(int i = 0; i < 9; i++){
			bombImageCache[i] = new HashMap<Integer, Image>();
		}
		explosionImageCache = new HashMap[9];
		for(int i = 0; i < 9; i++){
			explosionImageCache[i] = new HashMap<Integer, Image>();
		}
		idTranslator = new HashMap<Integer, Integer>();
		explosionGfxFactory = new ExplosionGfxFactory();
		tileImageCache = new HashMap<String, Image>();
		powerUpImageCache = new HashMap <String, Image>();
	}

	/**
	 * @param id The player id.
	 * @param state
	 * @return
	 */
	public Image getPlayerImage(int id, int state) {
		int key = translateId(id);
		if(!playerImageCache[key].containsKey(state)){
			playerImageCache[key].put(state, loadImage((basePath + "/player/" + key + "/" + state + ".png")));
		}
		return playerImageCache[key].get(state);
	}

	public Image getTileImage(String type) {
		if(!tileImageCache.keySet().contains(type)){
			String path = "/gfx/map/" + tileSet + "/" + type + ".png";
			tileImageCache.put(type, loadImage(path));
		}
 		return tileImageCache.get(type);
		
	}
	
	private int translateId(int id){
		if(!idTranslator.containsKey(id)){
			idTranslator.put(id, currentId);
			currentId = currentId + 1;
		}
		return idTranslator.get(id);
	}

	private Image loadImage(String path) {
		return ResourceService.getImage(path);
	}

	/**
	 * @param id The planter's id.
	 * @return
	 */
	public Image getBombImage(int id) {
		int key = translateId(id);
		if(!bombImageCache[key].containsKey(0)){
			bombImageCache[key].put(0, loadImage(basePath + "/player/" + key + "/bomb.png"));
		}
		return bombImageCache[key].get(0);
	}
	
	/**
	 * @param id The planter's id.
	 * @return
	 */
	public Image getBombImage(int id, int state) {
		int key = translateId(id);
		if(!bombImageCache[key].containsKey(state)){
			
			bombImageCache[key].put(state, loadImage(basePath + "/player/" + key + "/bomb" + state + ".png"));
		}
		return bombImageCache[key].get(state);
	}
	

	public Image getExplosionImage(int diameter, int id) {
		int key = translateId(id);
		if(!explosionImageCache[key].containsKey(diameter)){
			explosionImageCache[key].put(diameter, explosionGfxFactory.getExplosionImage(diameter, translateId(id)));
		}
		return explosionImageCache[key].get(diameter);
	}

	public void setTileSet(String tileSet) {
		this.tileSet = tileSet;
	}

	public Image getWallImage(int state) {
		if(!wallImageCache.containsKey(state)){
			wallImageCache.put(state, loadImage(basePath + "/map/" + tileSet + "/wall" + state + ".png"));
		}
		return wallImageCache.get(state);
	}
	/**
	 * Power up image loading
	 * @param type A String value describing the type of powerUp
	 *  *
	 */
	public Image getPowerUpImage(String type) {
		if (!powerUpImageCache.keySet().contains(type)) {
			String path = "/gfx/powerUps/" + type + ".png";
			powerUpImageCache.put(type, loadImage(path));
		}
		return powerUpImageCache.get(type);

	}
	
}
