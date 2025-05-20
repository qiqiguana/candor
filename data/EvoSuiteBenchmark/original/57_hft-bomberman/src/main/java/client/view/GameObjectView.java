/**
 * 
 */
package client.view;


import common.GameObject;

/**
 * @author Andi
 *
 */
public abstract class GameObjectView extends View {
	
	protected GameObject gameObject;
	protected final GfxFactory gfxFactory;
	protected boolean active = true;
	protected boolean visible = true;
	
	/**
	 * @param gameObject
	 */
	public GameObjectView(GameObject gameObject, GfxFactory gfxFactory) {
		super();
		this.gameObject = gameObject;
		this.gfxFactory = gfxFactory;
	}

	
	public boolean isActive() {
		return active;
	}


	public GameObject getGameObject() {
		return gameObject;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}
	
	
	
}
