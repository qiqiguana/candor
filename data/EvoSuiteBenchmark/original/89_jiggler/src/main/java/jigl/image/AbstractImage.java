package jigl.image;


public abstract class AbstractImage<T> implements Image<T> {

	/** Cartesian width */
	protected final int X;
	
	/** Cartesian height */
	protected final int Y;

	public AbstractImage(int x, int y) {
		X = x;
		Y = y;
	}
	
	public AbstractImage(java.awt.Image img) {
		this(jimgWidth(img), jimgHeight(img));
	}

	protected static int jimgWidth(java.awt.Image img) {
		return img.getWidth(jigl.internal.DummyObserver.dummy);
	}
	
	protected static int jimgHeight(java.awt.Image img) {
		return img.getHeight(jigl.internal.DummyObserver.dummy);
	}
	
	/**
	 * Returns the width (maximum X value)
	 * 
	 * @param none
	 */
	public int X() {
		return X;
	}

	/**
	 * Returns the height (maximum Y value)
	 * 
	 * @param none
	 */
	public int Y() {
		return Y;
	}

}
