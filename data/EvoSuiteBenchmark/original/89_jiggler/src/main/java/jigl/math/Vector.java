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
package jigl.math;

/**
 * Written By: Michael Reese Bastian
 * <P>
 * Last Modified: 23 October 1997
 * <P>
 * 
 * This class allows a programmer to create real vectors of an arbitrary dimension.
 * <P>
 */
public class Vector implements Cloneable {
	/**
	 * This member is the reference to the array where the elements of the vector are stored.
	 * <P>
	 */

	private double[] mem;

	//public double [] mem;

	/**
	 * This method copies the elements of <CODE>x</CODE> to <CODE>mem</CODE>.
	 * <P>
	 */

	public Vector assign(double[] x) {
		final int n = x.length;

		mem = new double[n];

		for (int k = 0; k < n; ++k)
			mem[k] = x[k];

		return this;
	}

	/**
	 * This method assigns the member values of <CODE>x</CODE> to the member values of
	 * <CODE>this</CODE>.
	 * <P>
	 */

	public Vector assign(Vector x) {
		final int n = x.mem.length;

		mem = new double[n];

		for (int k = 0; k < n; ++k)
			mem[k] = x.mem[k];

		return this;
	}

	/**
	 * This method creates a new vector and stores the sum of <CODE>this</CODE> and <CODE>x</CODE>
	 * in it.
	 * <P>
	 * The vectors must be of the same dimension.
	 * <P>
	 */

	public Vector add(Vector x) throws ArithmeticException {
		Vector r = new Vector(this);

		if (mem.length != x.mem.length)
			throw new ArithmeticException("Vectors not of the same dimension");

		for (int k = 0; k < mem.length; ++k)
			r.mem[k] += x.mem[k];

		return r;
	}

	/**
	 * This method creates a new vector stores the difference between <CODE>this</CODE> and
	 * <CODE>x</CODE> in it.
	 * <P>
	 * The vectors must be of the same dimension.
	 * <P>
	 */

	public Vector sub(Vector x) throws ArithmeticException {
		Vector r = new Vector(this);

		if (mem.length != x.mem.length)
			throw new ArithmeticException("Vectors not of the same dimension");

		for (int k = 0; k < mem.length; ++k)
			r.mem[k] -= x.mem[k];

		return r;
	}

	/**
	 * This method computes the dot product of <CODE>this</CODE> with <CODE>x</CODE>.
	 * <P>
	 * The vectors must be of the same dimension.
	 * <P>
	 */
	public double dot(Vector x) throws ArithmeticException {
		double sum = 0.0;

		if (mem.length != x.mem.length)
			throw new ArithmeticException("Vectors not of the same dimension: " + mem.length + " "
					+ x.mem.length);

		for (int k = 0; k < mem.length; ++k)
			sum += mem[k] * x.mem[k];

		return sum;
	}

	/**
	 * This method computes the dot product of <CODE>this</CODE> with <CODE>x</CODE>.
	 * <P>
	 * They must be of the same dimension.
	 * <P>
	 */

	public double dot(double[] x) throws ArithmeticException {
		double sum = 0.0;

		if (mem.length != x.length)
			throw new ArithmeticException("Vectors not of the same dimension" + mem.length + " "
					+ x.length);

		for (int k = 0; k < mem.length; ++k)
			sum += mem[k] * x[k];

		return sum;
	}

	/**
	 * This method computes the cross product between two 3-vectors. A 3-vector is returned.
	 * <P>
	 */

	public Vector cross(Vector x) throws ArithmeticException {
		if (mem.length != 3 || x.mem.length != 3)
			throw new ArithmeticException("Vectors must be 3-dimensional");

		Vector z = new Vector(3);

		z.mem[0] = mem[1] * x.mem[2] - mem[2] * x.mem[1];
		z.mem[1] = -(mem[0] * x.mem[2] - mem[2] * x.mem[0]);
		z.mem[2] = mem[0] * x.mem[1] - mem[1] * x.mem[0];

		return z;
	}

	/**
	 * This method returns a new vector with each element multiplied by <CODE>a</CODE>.
	 * <P>
	 */

	public Vector mult(double a) {
		Vector r = new Vector(this);

		for (int k = 0; k < mem.length; ++k)
			r.mem[k] = mem[k] * a;

		return r;
	}

	/**
	 * This method creates a new vector with each element equal to the corresponding element of
	 * <CODE>this</CODE> divided by the last element of <CODE>this</CODE>. The last element of the
	 * new vector is <CODE>1.0</CODE>.
	 * <P>
	 */

	public Vector project() {
		int n = mem.length;

		if (mem[n - 1] == 1.0)
			return new Vector(this);
		else {
			Vector x = new Vector(n);

			for (int i = 0; i < n - 1; ++i)
				x.mem[i] = mem[i] / mem[n - 1];

			x.mem[n - 1] = 1.0;

			return x;
		}
	}

	/**
	 * Reduces the dimension of a vector by one and returns the new vector. If the value of the last
	 * dimension is neither 0.0 nor 1.0, <code>null</code> is returned.
	 */
	public Vector decrDim() {
		int n = mem.length;

		if (mem[n - 1] == 0.0 || mem[n - 1] == 1.0) {
			Vector x = new Vector(n - 1);

			for (int i = 0; i < n - 1; ++i)
				x.mem[i] = mem[i];

			return x;
		} else {
			return null;
		}
	}

	/**
	 * This method computes the <VAR>L<SUB>p</SUB></VAR> norm of the vector.
	 * <P>
	 */

	public double norm(int p) {
		final int n = mem.length;

		if (p == 0) {
			double max = 0.0;

			for (int i = 0; i < n; ++i)
				max = Math.max(max, Math.abs(mem[i]));

			return max;
		} else {
			double sum = 0.0, x = (double) p;

			for (int i = 0; i < n; ++i)
				sum += Math.pow(Math.abs(mem[i]), x);

			return Math.pow(sum, 1.0 / x);
		}
	}

	/**
	 * This method computes the euclidean norm of the vector.
	 * <P>
	 */

	public double norm() {
		return norm(2);
	}

	/**
	 * This is the default constructor.
	 * <P>
	 * It assigns <CODE>mem</CODE> to <CODE>null</CODE>.
	 * <P>
	 */

	public Vector() {
		mem = null;
	}

	/**
	 * This constructor creates of zero vector of dimension <CODE>n</CODE>.
	 * <P>
	 */

	public Vector(int n) {
		mem = new double[n];
	}

	/**
	 * This method makes a vector from an array of type <CODE>double</CODE>.
	 * <P>
	 */

	public Vector(double[] x) {
		assign(x);
	}

	/**
	 * This constructor makes a vector from a vector.
	 * <P>
	 */

	public Vector(Vector x) {
		assign(x);
	}

	/**
	 * This method returns element <CODE>i</CODE> of the vector.
	 * <P>
	 */

	public double get(int i) throws ArrayIndexOutOfBoundsException, NullPointerException {
		return mem[i];
	}

	/**
	 * This method sets element <CODE>i</CODE> of the vector to <CODE>a</CODE>.
	 * <P>
	 */

	public void set(int i, double a) throws ArrayIndexOutOfBoundsException, NullPointerException {
		mem[i] = a;
	}

	/**
	 * This method returns the dimension of the vector.
	 * <P>
	 */

	public int dim() {
		if (mem == null)
			return 0;
		else
			return mem.length;
	}

	/**
	 * This method creates a new vector that is the unit vector pointing in the same direction as
	 * <CODE>this</CODE>.
	 * <P>
	 */

	public Vector unit() throws ArithmeticException {
		final double l = norm(2);
		Vector u = new Vector(this);

		for (int i = 0; i < mem.length; ++i)
			u.mem[i] = mem[i] / l;

		return u;
	}

	/** Prints the vector to stdout. */
	public void print() {
		for (int x = 0; x < mem.length; x++)
			System.out.print(mem[x] + " ");
	}

	/**
	 * This method compares to vectors. They are equal if and only if every element of
	 * <CODE>this</CODE> is equal to every element of <CODE>x</CODE>.
	 * <P>
	 */
	public boolean equals(Vector x) throws ArithmeticException {
		if (mem.length != x.mem.length)
			throw new ArithmeticException("Vectors not of the same dimension");

		for (int i = 0; i < mem.length; ++i)
			if (mem[i] != x.mem[i])
				return false;

		return true;
	}

	/**
	 * This method creates a string from the vector.
	 * <P>
	 */

	public Object clone() {
		return new Vector(this);
	}

	/** Converts to string. */
	public String toString() {
		final int n = mem.length;
		String s = new String();

		s += "{ ";

		for (int i = 0; i < n; ++i) {
			s += Double.toString(mem[i]);

			if (i < n - 1)
				s += ", ";
		}

		s += " }";

		return s;
	}
}
