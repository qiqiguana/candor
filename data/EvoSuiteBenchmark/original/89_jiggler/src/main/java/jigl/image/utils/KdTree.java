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
package jigl.image.utils;

import java.util.Vector;

import jigl.image.types.RealColorImage;

/**
 * This class does kdTree operations. A KD Tree is a data structure that is used in computer science
 * during orthogonal range searching. For instance, when you want to find the set of points that
 * fall into a given rectangle in a plane, using KD Tree it is possible to find the resulting points
 * in O(sqrt(n)+k) time where n is the number of points and k is the number of points in the result.
 */
public class KdTree {
	/***/
	private int determine;
	/** left branch of the tree */
	private KdTree left;
	/** Right branch of the tree */
	private KdTree right;
	/** value at the node */
	private Float[] value;
	/** The number of dimensions */
	private int dimension;
	/** the root node of the tree */
	private KdTree root;
	/** store the value of nearest neighbors */
	private Vector values;

	/** Construct a KdTree object with dimensions <code>dimensions</code>. */
	public KdTree(int dimensions) {
		root = null;
		dimension = dimensions;
		values = new Vector();

	}

	/** Returns the left child. */
	public KdTree getLeft() {
		return left;
	}

	/** Returns the right child. */
	public KdTree getRight() {
		return right;
	}

	/** Returns the value of the kdtree. */
	public Float[] getValue() {
		return value;
	}

	/**
	 * Finds the nearest neighbor of <i>node</i>. Threshold is necessary because if the threshold is
	 * too low, a value is not always returned. If threshold is too high, then too many values are
	 * returned.
	 */
	public void findNearest(Float[] node, KdTree tree, int threshold) {

		//		int tempDist=0;
		if (node[tree.determine] <= tree.value[tree.determine] - threshold) {
			if (tree.left != null)
				findNearest(node, tree.left, threshold);
		} else if (node[tree.determine] >= tree.value[tree.determine] + threshold) {
			if (tree.right != null)
				findNearest(node, tree.right, threshold);
		} else {
			if ((node[0] >= tree.value[0] - threshold) && (node[0] <= tree.value[0] + threshold)
					&& (node[1] >= tree.value[1] - threshold)
					&& (node[1] <= tree.value[1] + threshold)
					&& (node[2] >= tree.value[2] - threshold)
					&& (node[2] <= tree.value[2] + threshold)) {
				values.addElement(tree.value);
			}
			if (tree.left != null)
				findNearest(node, tree.left, threshold);
			if (tree.right != null)
				findNearest(node, tree.right, threshold);
		}

	}

	/** Builds a KdTree from the colors in the image */
	public void buildTreePalette(RealColorImage image) {
		//simple approach; optimizations to get a better tree, coming soon
		Float[] color = new Float[3];
		for (int x = 0; x < image.X(); x++)
			for (int y = 0; y < image.Y(); y++) {
				color = image.get(x, y);
				addNode(color, root);
			}
	}

	/** Adds a node to the correct place in the KdTree */
	public void addNode(Float[] node, KdTree tree) {
		if (root == null) {
			root = new KdTree(3);
			root.determine = 0;
			root.value = node;
		} else {
			if (node[tree.determine] < tree.value[tree.determine]) {
				if (tree.left == null) {
					KdTree newNode = new KdTree(3);
					tree.left = newNode;
					newNode.value = node;
					newNode.determine = (tree.determine + 1) % dimension;
				} else
					addNode(node, tree.left);
			} else if (node[tree.determine] > tree.value[tree.determine]) {
				if (tree.right == null) {
					KdTree newNode = new KdTree(3);
					tree.right = newNode;
					newNode.value = node;
					newNode.determine = (tree.determine + 1) % dimension;
				} else
					addNode(node, tree.right);
			} else if (node[0] != tree.value[0] || node[1] != tree.value[1]
					|| node[2] != tree.value[2]) {
				if (tree.left == null) {
					KdTree newNode = new KdTree(3);
					tree.left = newNode;
					newNode.value = node;
					newNode.determine = (tree.determine + 1) % dimension;
				} else
					addNode(node, tree.left);
			}
		}//else (root != null)
	}//addNode

	public KdTree getRoot() {
		return root;
	}

	//FIXME: make this read-only
	public Vector getValues() {
		return values;
	}
}
