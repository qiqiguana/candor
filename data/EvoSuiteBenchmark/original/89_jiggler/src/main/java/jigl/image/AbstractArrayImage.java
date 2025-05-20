package jigl.image;

public abstract class AbstractArrayImage<T> extends AbstractImage<T> implements ArrayImage<T> {
	/** Two dimensional integer array to store image content. */
	protected final T[][] data;
	
	public AbstractArrayImage(final int x, final int y, final T[][] data) {
		super(x, y);
		if(!verifyArray(data)) throw new IllegalArgumentException("Array is invalid");
        this.data = data;
	}

	public AbstractArrayImage(java.awt.Image img, final T[][] data) {
		super(img);
		if(!verifyArray(data)) throw new IllegalArgumentException("Array is invalid");
        this.data = data;
	}

	/** Return a deep copy of <i>data</i>. */
	public T[][] getData() {
		return data.clone();
	}

    private static <T> boolean verifyArray(T[][] arr) {
        if(arr.length == 0) return true;
        final int width = arr[0].length;

        for(T[] sub : arr) {
            if(sub.length != width) return false;
        }
        return true;
    }
}
