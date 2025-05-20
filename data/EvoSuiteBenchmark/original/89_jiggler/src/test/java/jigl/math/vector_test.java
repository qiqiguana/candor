package jigl.math;

/*JIGL--Java Imaging and Graphics Library
Copyright (C)1999 Brigham Young University

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

A copy of the GNU Lesser General Public Licence is contained in
/jigl/licence.txt */

//package jigl.math;


public class vector_test// implements Cloneable
{
  /**
	* This member is the reference to the array where the elements of the vector are stored. <P>
	*/

  protected double[] mem;
  //public double [] mem;

  /**
	* This method copies the elements of <CODE>x</CODE> to <CODE>mem</CODE>. <P>
	*/

  public vector_test assign(double[] x)
  {
    final int n = x.length;

    mem = new double[n];

    for (int k = 0; k < n; ++k)
      mem[k] = x[k];

    return this;
  }

  /**
	* This method assigns the member values of <CODE>x</CODE> to the member values of <CODE>this</CODE>. <P>
	*/

  public vector_test assign(vector_test x)
  {
    final int n = x.mem.length;

    mem = new double[n];

    for (int k = 0; k < n; ++k)
      mem[k] = x.mem[k];

    return this;
  }

  /**
	* This method creates a new vector and stores the sum of <CODE>this</CODE> and <CODE>x</CODE> in it. <P>
	* The vectors must be of the same dimension. <P>
	*/

  public vector_test add(vector_test x) throws ArithmeticException
  {
    vector_test r = new vector_test(this);

    if (mem.length != x.mem.length)
      throw new ArithmeticException("Vectors not of the same dimension");

    for (int k = 0; k < mem.length; ++k)
      r.mem[k] += x.mem[k];

    return r;
  }

  /**
	* This method creates a new vector stores the difference between <CODE>this</CODE> and <CODE>x</CODE> in it. <P>
	* The vectors must be of the same dimension. <P>
	*/

  public vector_test sub(vector_test x) throws ArithmeticException
  {
    vector_test r = new vector_test(this);

    if (mem.length != x.mem.length)
      throw new ArithmeticException("Vectors not of the same dimension");

    for (int k = 0; k < mem.length; ++k)
      r.mem[k] -= x.mem[k];

    return r;
  }

  /**
	* This method computes the dot product of <CODE>this</CODE> with <CODE>x</CODE>. <P>
	* The vectors must be of the same dimension. <P>
	*/
  public double dot(vector_test x) throws ArithmeticException
  {
    double sum = 0.0;

    if (mem.length != x.mem.length)
			throw new ArithmeticException("Vectors not of the same dimension: "+mem.length+" "+
				x.mem.length);

    for (int k = 0; k < mem.length; ++k)
      sum += mem[k] * x.mem[k];

    return sum;
  }

	/**
	 * This method computes the dot product of <CODE>this</CODE> with <CODE>x</CODE>. <P>
	 * They must be of the same dimension. <P>
	 */

  public double dot(double[] x) throws ArithmeticException
  {
    double sum = 0.0;

    if (mem.length != x.length)
      throw new ArithmeticException("Vectors not of the same dimension"+mem.length+" "+
                                                                        x.length);

    for (int k = 0; k < mem.length; ++k)
      sum += mem[k] * x[k];

    return sum;
  }
  /**
	* This method computes the cross product between two 3-vectors.
	* A 3-vector is returned. <P>
	*/

  public vector_test cross(vector_test x) throws ArithmeticException
  {
    if (mem.length != 3 || x.mem.length != 3)
      throw new ArithmeticException("Vectors must be 3-dimensional");

    vector_test z = new vector_test(3);

    z.mem[0] = mem[1] * x.mem[2] - mem[2] * x.mem[1];
    z.mem[1] = -(mem[0] * x.mem[2] - mem[2] * x.mem[0]);
    z.mem[2] = mem[0] * x.mem[1] - mem[1] * x.mem[0];

    return z;
  }

  /**
	* This method returns a new vector with each element multiplied by <CODE>a</CODE>. <P>
	*/

  public vector_test mult(double a)
  {
    vector_test r = new vector_test(this);

    for (int k = 0; k < mem.length; ++k)
      r.mem[k] = mem[k]*a;

    return r;
  }

  /**
   * This method creates a new vector with each element equal to the corresponding element of <CODE>this</CODE>
   * divided by the last element of <CODE>this</CODE>.  The last element of the new vector is <CODE>1.0</CODE>. <P>
  */

  public vector_test project()
  {
    int n = mem.length;

    if (mem[n - 1] == 1.0)
      return new vector_test(this);
    else
    {
        vector_test x = new vector_test(n);

        for (int i = 0; i < n - 1; ++i)
                x.mem[i] = mem[i] / mem[n - 1];

        x.mem[n - 1] = 1.0;

        return x;
    }
  }

  public vector_test decrDim()
  {
    int n = mem.length;

    if (mem[n - 1] == 0.0 || mem[n - 1] == 1.0)
		{
			vector_test x = new vector_test(n - 1);

			for (int i = 0; i < n - 1; ++i)
				x.mem[i] = mem[i];

			return x;
		}
    else
		{
			return null;
		}
  }

  /**
	* This method computes the <VAR>L<SUB>p</SUB></VAR> norm of the vector. <P>
	*/

  public double norm(int p)
  {
    final int n = mem.length;

    if (p == 0)
		{
			double max = 0.0;

			for (int i = 0; i < n; ++i)
				max = Math.max(max, Math.abs(mem[i]));

			return max;
		}
    else
		{
			double sum = 0.0, x = (double) p;

			for (int i = 0; i < n; ++i)
				sum += Math.pow(Math.abs(mem[i]), x);

			return Math.pow(sum, 1.0 / x);
		}
  }

  /**
	* This method computes the euclidean norm of the vector. <P>
	*/

  public double norm()
  {
    return norm(2);
  }

  /**
	* This is the default constructor. <P>
	* It assigns <CODE>mem</CODE> to <CODE>null</CODE>. <P>
	*/

  public vector_test()
  {
    mem = null;
  }

  /**
	* This constructor creates of zero vector of dimension <CODE>n</CODE>. <P>
	*/

  public vector_test(int n)
  {
    mem = new double[n];
  }

  /**
	* This method makes a vector from an array of type <CODE>double</CODE>. <P>
	*/

  public vector_test(double[] x)
  {
    assign(x);
  }

  /**
	* This constructor makes a vector from a vector. <P>
	*/

  public vector_test(vector_test x)
  {
    assign(x);
  }

  /**
	* This method returns element <CODE>i</CODE> of the vector. <P>
	*/

  public double get (int i) throws ArrayIndexOutOfBoundsException, NullPointerException
  {
    return mem[i];
  }

  /**
	* This method sets element <CODE>i</CODE> of the vector to <CODE>a</CODE>. <P>
	*/

  public void set (int i, double a) throws ArrayIndexOutOfBoundsException, NullPointerException
  {
    mem[i] = a;
  }

  /**
	* This method returns the dimension of the vector. <P>
	*/

  public int dim()
  {
    if (mem == null)
      return 0;
    else
      return mem.length;
  }

  /**
	* This method creates a new vector that is the unit vector pointing in the same direction as <CODE>this</CODE>. <P>
	*/

  public vector_test unit() throws ArithmeticException
  {
    final double l = norm(2);
    vector_test u = new vector_test(this);

    for (int i = 0; i < mem.length; ++i)
      u.mem[i] = mem[i] / l;

    return u;
  }

  /**
	* This method compares to vectors.  They are equal if and only if every element of <CODE>this</CODE> is equal to every
	* element of <CODE>x</CODE>. <P>
	*/

	public void print(){
		for (int x=0; x<mem.length; x++)
	    System.out.print(mem[x]+" ");
	}

  public boolean equals(vector_test x) throws ArithmeticException
  {
    if (mem.length != x.mem.length)
      throw new ArithmeticException("Vectors not of the same dimension");

    for (int i = 0; i < mem.length; ++i)
      if (mem[i] != x.mem[i])
				return false;

    return true;
  }

  /**
	* This method creates a string from the vector. <P>
	*/

  public Object clone()
  {
    return new vector_test(this);
  }

  public String toString()
  {
    final int n = mem.length;
    String s = new String();

    s += "{ ";

    for (int i = 0; i < n; ++i)
		{
			s += mem[i];

			if (i < n - 1)
				s += ", ";
		}

    s += " }";

    return s;
  }
}





