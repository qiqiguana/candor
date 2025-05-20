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
 * Complex Number Operations. For descriptions of these operations, see
 * {@link <a href="../../others/complex_functions.html">Complex Functions</a>}.
 */
public class Complex extends Number {
	private static final long serialVersionUID = 1L;

	/** the real part of the Complex number */
	protected float real;

	/** imaginary part of the Complex number */
	protected float imaginary;

	/** Sets the real and imaginary part of the complex number to zero */
	public Complex() {
		real = (float) 0.0;
		imaginary = (float) 0.0;
	}

	/**
	 * Initializes the real and imaginary part to a and b respectively
	 * 
	 * @param real Real part of the complex number
	 * @param b the imaginary part of the complex number
	 */
	public Complex(double real, double imaginary) {
		this.real = (float) real;
		this.imaginary = (float) imaginary;
	}

	/** Initializes the complex number to z */
	public Complex(Complex z) {
		real = (float) z.real();
		imaginary = (float) z.imaginary();
	}

	/** Returns the real part of the complex number */
	public double real() {
		return real;
	}

    /** Set the real part of the complex number and returns old value */
    @Deprecated
	public double real(double a) {
        return setReal(a);
    }

	/** Set the real part of the complex number and returns old value */
	public double setReal(double a) {
		double retval = real;
		real = (float) a;
		return retval;
	}

	/** Return the imaginary part of the imaginary number */
    @Deprecated
	public double imag() {
		return imaginary();
	}

    /** Return the imaginary part of the imaginary number */
	public double imaginary() {
		return imaginary;
	}

	/** Sets the imaginary part of the complex number and returns old value */
    @Deprecated
    public double imag(double a) {
        return setImaginary(a);
    }

    /** Sets the imaginary part of the complex number and returns old value */
	public double setImaginary(double a) {
		double retval = imaginary;
		imaginary = (float) a;
		return retval;
	}



	/** Returns the magnitude of the complex number */
	public double magnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns the phase of the complex number.Converts rectangular coordinates (x, y) to polar (r,
	 * theta).
	 */
	public double arg() {
		return (Math.atan2(imaginary, real));
	}

	/** Normalizes the complex number */
	public Complex unit() {
		double r = magnitude();
		return new Complex(real / r, imaginary / r);
	}

	/** Performs the conjunction function on the complex number */
	public Complex conj() {
		return new Complex(real, -imaginary);
	}

	/** Converts polarrectangular coordinates (r,t) to rectangular (x, y). */
	public void polar(double r, double t) {
		real = (float) (r * Math.cos(t));
		imaginary = (float) (r * Math.sin(t));
	}

	/** Compares this complex number to another Complex number */
	public boolean equals(Complex z) {
		return ((real == z.real) && (imaginary == z.imaginary));
	}

	/** Return the string representation of this complex number */
	public String toString() {
		return "(" + real + ", " + imaginary + ")";
	}

	/**
	 * Adds this complex number to another complex number. <code>this</code> is modified.
	 * 
	 * @param z Complex number to add
	 */
	public Complex add(Complex z) {
		real += z.real;
		imaginary += z.imaginary;
		return this;
	}

	/**
	 * Subtracts another complex number from this complex number. <code>this</code> is modified.
	 * 
	 * @param z Complex number to subtract
	 */
	public Complex sub(Complex z) {
		real -= z.real;
		imaginary -= z.imaginary;
		return this;
	}

	/**
	 * Multiplies this complex number by another complex number. <code>this</code> is modified.
	 * 
	 * @param z Complex number to multiply
	 */
	public Complex mult(Complex z) {
		float a = real * z.real - imaginary * z.imaginary;
		float b = real * z.imaginary + imaginary * z.real;
		real = a;
		imaginary = b;
		return this;
	}

	/**
	 * Divides this complex number by <code>z</code> (<code>z shouldn't be 0<code>). <code>this</code> is modified.
	 * 
	 * @param z Complex number to divide
	 */
	public Complex div(Complex z) {
		float r = z.real * z.real + z.imaginary * z.imaginary;
		float a = (real * z.real + imaginary * z.imaginary) / r;
		float b = (imaginary * z.real - real * z.imaginary) / r;
		real = a;
		imaginary = b;
		return this;
	}

	/**
	 * Adds a constant to the real part of this complex number. <code>this</code> is modified.
	 * 
	 * @param a constant to add
	 */
	public Complex add(double a) {
		real += (float) a;
		return this;
	}

	/**
	 * Subtracts a constant from the real part of this complex number. <code>this</code> is
	 * modified.
	 * 
	 * @param a constant to subtract
	 */
	public Complex sub(double a) {
		real -= (float) a;
		return this;
	}

	/**
	 * Multiplies this complex number by a constant. <code>this</code> is modified.
	 * 
	 * @param a constant to multiply
	 */
	public Complex mult(double a) {
		real *= (float) a;
		imaginary *= (float) a;
		return this;
	}

	/**
	 * Divides this complex number by a constant <code>a</code>(<code>a shouldn't be 0</code>). <code>this</code>
	 * is modified.
	 * 
	 * @param a constant to divide by
	 */
	public Complex div(double a) {
		real /= (float) a;
		imaginary /= (float) a;
		return this;
	}

	/** Returns the square root of this complex number */
	public Complex sqrt() {
		double r = magnitude();
		double t = Math.atan2(imaginary, real);
		return new Complex(Math.sqrt(r) * Math.cos(t / 2.0), Math.sqrt(r) * Math.sin(t / 2.0));
	}

	/** Returns the result of <code>e^(x+y*i) = e^x * e^(i*y) = e^x*(cos(y) + sin(y))</code>. */
	public Complex exp() {
		return new Complex(Math.exp(real) * Math.cos(imaginary), Math.exp(real) * Math.sin(imaginary));
	}

	/** Returns the result of <code>ln(x+y*i) = ln(r*e^(i*theta)) = ln(r) + i*theta</code>. */
	public Complex log() {
		return new Complex(Math.log(magnitude()), Math.atan2(imaginary, real));
	}

	/**
	 * Returns this complex number raised to the power <i>a</i> complex number. That is, calculates
	 * (x+y*i)^a
	 */
	public Complex pow(double a) {
		double r = magnitude();
		double t = Math.atan2(imaginary, real);
		return new Complex(Math.pow(r, a) * Math.cos(a * t), Math.pow(r, a) * Math.sin(a * t));
	}

	/**
	 * Returns the hyperbolic cosine of this complex number.
	 * <code>cosh(k) = 1/2 * (e^k + e^k)</code>.
	 */
	public Complex cosh() {
		Complex z1 = this.exp();
		Complex z2 = this.mult(-1.0).exp();
		z1.add(z2);
		z1.div(2.0);
		return z1;
	}

	/**
	 * Returns the hyperbolic sine of this complex number. <code>sinh(k) = 1/2 * (e^k - e^k)</code>.
	 */
	public Complex sinh() {
		Complex z1 = this.exp();
		Complex z2 = this.mult(-1.0).exp();
		z1.sub(z2);
		z1.div(2.0);
		return z1;
	}

	/** Returns a complex number with the negative of this complex number's real part */
	Complex j1mult() {
		return new Complex(-real, imaginary);
	}

	/** Returns a complex number with the negative of this complex number's real and imaginary part */
	Complex j2mult() {
		return new Complex(-real, -imaginary);
	}

	/** Returns a complex number with the negative of this complex number's imaginary part */
	Complex j3mult() {
		return new Complex(real, -imaginary);
	}

	/**
	 * Returns the cosine of this complex number.
	 * <code>cos(x+y*i) = cos(x)*cosh(y) - i*(sin(x)*sinh(y))</code>.
	 */
	public Complex cos() {
		Complex z1 = this.j1mult().exp();
		Complex z2 = this.j3mult().exp();
		z1.add(z2);
		z1.div(2.0);
		return z1;
	}

	/**
	 * Returns the sine of this complex number
	 * <code>sin(x+y*i) = sin(x)*cosh(y), i*(cos(x)*sinh(y))</code>
	 */
	public Complex sin() {
		Complex z1 = this.j1mult().exp();
		Complex z2 = this.j3mult().exp();
		z1.sub(z2);
		z1 = z1.j3mult();//FIXME: ??????
		z1.div(2.0);
		
		return z1;
	}

	/** Returns the double value of this complex number */
	public double doubleValue() {
		return magnitude();
	}

	/** Returns the float value of this complex number */
	public float floatValue() {
		return (float) magnitude();
	}

	/** Returns the integer value of this complex number */
	public int intValue() {
		return (int) magnitude();
	}

	/** Returns the long value of this complex number */
	public long longValue() {
		return (long) magnitude();
	}

	/**
	 * Adds two complex numbers
	 * 
	 * @param z1 first complex number
	 * @param z2 second complex number
	 */
	public static Complex add(Complex z1, Complex z2) {
		Complex z = new Complex();
		z.real = z1.real + z2.real;
		z.imaginary = z1.imaginary + z2.imaginary;
		return z;
	}

	/**
	 * Subtracts two complex numbers
	 * 
	 * @param z1 first complex number
	 * @param z2 second complex number (subtracted from the first)
	 */
	public static Complex sub(Complex z1, Complex z2) {
		Complex z = new Complex();
		z.real = z1.real - z2.real;
		z.imaginary = z1.imaginary - z2.imaginary;
		return z;
	}

	/**
	 * Multiplies two complex numbers
	 * 
	 * @param z1 first complex number
	 * @param z2 second complex number
	 */
	public static Complex mult(Complex z1, Complex z2) {
		Complex z = new Complex();
		z.real = z1.real * z2.real - z1.imaginary * z2.imaginary;
		z.imaginary = z1.real * z2.imaginary + z1.imaginary * z2.real;
		return z;
	}

	/**
	 * Divides two complex numbers
	 * 
	 * @param z1 first complex number
	 * @param z2 second complex number
	 */
	public static Complex div(Complex z1, Complex z2) {
		float r = z2.real * z2.real + z2.imaginary * z2.imaginary;
		Complex z = new Complex();
		z.real = (z1.real * z2.real + z1.imaginary * z2.imaginary) / r;
		z.imaginary = (z1.imaginary * z2.real - z1.real * z2.imaginary) / r;
		return z;
	}

	/** test the Complex class */
	/*
	public static void main(String[] args) {
	Complex z = new Complex(1.11111111, 1.152525);

	ComplexImage c = new ComplexImage(5,5);
	ComplexImage c2 = new ComplexImage(5,5);

	c.add(z);
	c2.add(z);

	z.add(new Complex(2.3, 3.2));

	c.divide(z);

	c.set(4, 4, new Complex(1.0,1.0));

	//c2.add(c);

	System.out.println(c.toString());
	System.out.println(c2.toString());

	System.out.println(z);
	System.out.println("Real Part:         " + z.real());
	System.out.println("Imaginary Part:    " + z.imag());
	System.out.println("Modulus:           " + z.mod());
	System.out.println("Argument:          " + z.arg());
	System.out.println("Sign:              " + z.sign());
	System.out.println("Conjugate:         " + z.conj());

	System.out.println("Square Root:       " + z.sqrt());
	System.out.println("Exponent:          " + z.exp());
	System.out.println("Logarithm:         " + z.log());
	System.out.println("Sine:              " + z.sin());
	System.out.println("Cosine:            " + z.cos());
	System.out.println("Hyperbolic Sine:   " + z.sinh());
	System.out.println("Hyperbolic Cosine: " + z.cosh());
	}
	*/
}
