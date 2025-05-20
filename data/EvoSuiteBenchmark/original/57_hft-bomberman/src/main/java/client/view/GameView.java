/**
 * 
 */
package client.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Andi
 * 
 */
public class GameView {

	private Vector<GameObjectView> views;

	private GameCanvas gameCanvas;

	private OverlayView overlayView;

	private static boolean displayNames = true;

	private static final Logger logger = Logger.getLogger(GameView.class);

	private static final AffineTransform NEUTRAL_TF = AffineTransform
			.getTranslateInstance(0., 0.);

	/**
	 * @param gameCanvas
	 */
	public GameView(GameCanvas gameCanvas) {
		super();
		this.gameCanvas = gameCanvas;
		this.views = new Vector<GameObjectView>();
		this.overlayView = new OverlayView();
	}

	public void paint() {
		Vector<GameObjectView> removedViews = new Vector<GameObjectView>();

		for (GameObjectView view : views) {
			if (PlayerView.class.isInstance(view)) {
				if (!view.isVisible()) {
					removedViews.add(view);
				}
			}
			if (PowerUpView.class.isInstance(view)) {
				if (!view.isVisible()) {
					removedViews.add(view);
				}
			}
			if (TileView.class.isInstance(view)) {
				if (!view.isVisible()) {
					removedViews.add(view);
				}
			}
			if (BombView.class.isInstance(view)) {
				if (!view.isVisible()) {
					removedViews.add(view);
				}
			}
		}

		for (GameObjectView view : removedViews) {
			views.remove(view);
		}

		Graphics2D gfx = gameCanvas.getCanvas();
		// AffineTransform preTf = gfx.getTransform();
		int size = views.size();
		for (int i = 0; i < size; i++) {
			View view = views.get(i);
			view.paint(gfx);
		}
		overlayView.paint(gfx);
		gameCanvas.update();

		gfx.setTransform(NEUTRAL_TF);
	}

	/**
	 * @param gameObjectView
	 * @return
	 * @see java.util.Vector#add(java.lang.Object)
	 */
	public boolean addGameObjectView(GameObjectView gameObjectView) {
		return views.add(gameObjectView);
	}

	/**
	 * @param string
	 */
	public void setOverlayText(String string) {
		overlayView.setText(string);
	}

	public void toggleDisplayNames() {
		if (displayNames) {
			displayNames = false;
		} else {
			displayNames = true;
		}
	}

	public static boolean displayNames() {
		return displayNames;
	}

	public void resetGfx() {
		Graphics2D gfx = gameCanvas.getCanvas();
		gfx.setTransform(AffineTransform.getRotateInstance(0.));
		gfx.clearRect(0, 0, 800, 600);
	}

}
