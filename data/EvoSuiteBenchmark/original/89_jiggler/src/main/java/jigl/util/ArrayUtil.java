package jigl.util;

public final class ArrayUtil {
	private ArrayUtil() {}
	
	public static <N extends Comparable<N>> N arrayMax(N[] arr) {
		if(arr == null || arr.length==0) return null;
		N greatest = null;
		for(N n : arr) {
			if(greatest == null || n.compareTo(greatest) > 0) greatest = n;
		}
		return greatest;
	}

	public static <N extends Comparable<N>> N arrayMin(N[] arr) {
		if(arr == null || arr.length==0) return null;
		N least = null;
		for(N n : arr) {
			if(least == null || n.compareTo(least) < 0) least = n;
		}
		return least;
	}
	
	public static <N extends Comparable<N>> N arrayMin(N[][] arr) {
		if(arr == null || arr.length==0) return null;
		N least = null;
		for(N[] n : arr) {
			N candidate = arrayMin(n);
			if(least == null || candidate.compareTo(least) < 0) least = candidate;
		}
		return least;
	}
	
	public static <N extends Comparable<N>> N arrayMax(N[][] arr) {
		if(arr == null || arr.length==0) return null;
		N greatest = null;
		for(N[] n : arr) {
			N candidate = arrayMax(n);
			if(greatest == null || candidate.compareTo(greatest) > 0) greatest = candidate;
		}
		return greatest;
	}
	
	public static <T> boolean arrayEquals(T[][] arr1, T[][] arr2) {
		if(arr1.length != arr2.length) return false;
		for(int i = 0; i < arr1.length; i++) {
			T[] subArr1 = arr1[i];
			T[] subArr2 = arr2[i];
			if(!arrayEquals(subArr1,subArr2)) return false;
		}
		return true;
	}
	
	public static <T> boolean arrayEquals(T[] arr1, T[] arr2) {
		if(arr1.length != arr2.length) return false;
		for(int i = 0; i < arr1.length; i++) {
			if(!arr1[i].equals(arr2[i])) return false;
		}
		return true;
	}

    public static Integer[][] box(int[][] data) {
        final int length = data.length;
        final int width = width(data);
        Integer[][] boxed = new Integer[length][width];
        for(int x = 0; x < length; x++) {
            for(int y = 0; y < width; y++) {
                boxed[x][y] = data[x][y];
            }
        }
        return boxed;
    }

    public static int[][] rowMajorToColumnMajor(final int[][] rowMajor) {
        final int rowCount = rowMajor.length;
        final int columnCount = width(rowMajor);

        final int[][] colMajor = new int[columnCount][rowCount];
        for(int row = 0; row < rowCount; row++) {
            for(int col = 0; col < columnCount; col++) {
                colMajor[col][row] = rowMajor[row][col];
            }
        }
        return colMajor;
    }

    public static int width(int[][] data) {
		int max = 0;

		//find the max length of data
		for(int[] row : data) {
			if(row.length > max)
				max = row.length;
		}

		return max;
	}

    public static double[] unbox(Double[] boxed) {
        double[] unboxed = new double[boxed.length];
        for(int i = 0; i < boxed.length; i++) {
            unboxed[i] = boxed[i];
        }
        return unboxed;
    }
    
    public static float[] unbox(Float[] boxed) {
        float[] unboxed = new float[boxed.length];
        for(int i = 0; i < boxed.length; i++) {
            unboxed[i] = boxed[i];
        }
        return unboxed;
    }

}
