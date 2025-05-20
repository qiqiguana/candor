/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.image.levelSetTool;

import jigl.image.types.ColorImage;
import jigl.image.types.RealColorImage;
import jigl.image.utils.ImageConverter;

/**
 * LevelSetTools is a class providing a common interface for three main kinds of level-set
 * operations: nudge, sharpen and smooth. Its methods call methods in those three classes.
 */
public class LevelSetTools {
	/** Original image */
	private RealColorImage image;
	/** The object */
	//RealGrayImage* want;
	/** Object for level-set sharpening operation. */
	private LevelSetSharpen sharpen;
	/** Object for level-set nudge operation. */
	private LevelSetNudge nudge;
	/** Object for level-set smoothing operation. */
	private LevelSetSmooth smooth;

	/** Default constructor */
	public LevelSetTools() {
		smooth = null;
		sharpen = null;
		nudge = null;
		type = END;
	}

	/**
	 * Constructs a LevelSetTools object from a ColorImage object. parameters; im - pointer to the
	 * source image op_type - type of level-set operation. <br>
	 * 
	 * <pre>
	 * 0: SMOOTH			1: NUDGE
	 * 			2: NUDGE_PERPENDICULAR		2: PINCH
	 * 			4: NORMAL_SHARPEN		5: SCRATCH
	 * 			6: END
	 * </pre>
	 */
	public LevelSetTools(ColorImage im, int op_type) {
		image = ImageConverter.toRealColor(im);
		type = op_type;
		init();
	}

	/** Flag for level-set smooth */
	public final static int SMOOTH = 0;
	/** Flag for level-set sharpen */
	public final static int SHARPEN = 1;
	/** Flag for level-set nudge */
	public final static int NUDGE = 2;
	/** Flag for level-set perpendicular nudge */
	public final static int NUDGE_PERPENDICULAR = 3;
	/** Flag for pinch */
	public final static int PINCH = 4;
	/** Flag for normal sharpen */
	public final static int NORMAL_SHARPEN = 5;
	/** Flag for scratch */
	public final static int SCRATCH = 6;
	/** Flag for end */
	public final static int END = 7;

	/** operation type. */
	public int type;

	/** Initializes current LevelSetTools object. */
	public void init() {
		NoiseDetectImage stats = new NoiseDetectImage();
		stats.init(image);
		smooth = new LevelSetSmooth(image, stats.getGlobal(), stats.std, stats.std2);
		nudge = new LevelSetNudge(image);
		sharpen = new LevelSetSharpen(image);
	}

	/**
	 * Changes the operation type. parameters; op_type - type of level-set operation. <br>
	 * 
	 * <pre>
	 * 0: SMOOTH					1: NUDGE
	 * 			2: NUDGE_PERPENDICULAR		2: PINCH
	 * 			4: NORMAL_SHARPEN			5: SCRATCH
	 * 			6: END
	 * </pre>
	 */
	public void setType(int op_type) {
		type = op_type;
	}

	/**
	 * Returns the pointer to the image. If it is called after applying level-set operation, the
	 * return pointer also points to the result image.
	 */
	public RealColorImage getImage() {
		return image;
	}

	/**
	 * Applies one type of level-set operations to current image according to internal type-value in
	 * the specified area.
	 * <p>
	 * <code>*image</code> is modified. parameters: x1 - left bound of the specified area y1 -
	 * bottom bound of the specified area x2 - right bound of the specified area y2 - top bound of
	 * the specified area
	 */
	public void apply(int x1, int y1, int x2, int y2) {
		if (type == SMOOTH)
			smooth.apply(x1, y1, x2, y2, true, true);
		else if (type == NUDGE)
			nudge.apply(x1, y1, x2, y2, false, false);
		else if (type == SHARPEN)
			sharpen.apply(x1, y1, x2, y2, true);
		else if (type == NORMAL_SHARPEN)
			sharpen.apply(x1, y1, x2, y2, false);
		else if (type == NUDGE_PERPENDICULAR)
			nudge.apply(x1, y1, x2, y2, true, false);
		else if (type == PINCH)
			nudge.apply(x1, y1, x2, y2, false, true);
	}
}
