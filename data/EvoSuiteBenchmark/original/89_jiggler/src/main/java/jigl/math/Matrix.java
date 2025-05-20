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
 * Last Modified: 6 November 1997
 * <P>
 * 
 * This class allows a programmer to create real matrices with an arbitrary number of rows and
 * columns.
 * <P>
 */
public class Matrix implements Cloneable {
	/**
	 * This member is the reference to the arrays where the entries of the matrix are stored.
	 * <P>
	 */
	public double[][] mem;

	/**
	 * This is the default constructor. It does not allocate any memory.
	 * <P>
	 */

	public Matrix() {
		mem = null;
	}

	/**
	 * This constructor builds a zero matrix of size <CODE>m</CODE>-by-<CODE>n</CODE>.
	 * <P>
	 */

	public Matrix(int m, int n) {
		int i, j;

		mem = new double[m][n];

		for (i = 0; i < m; ++i)
			for (j = 0; j < n; ++j)
				mem[i][j] = 0.0;
	}

	/**
	 * This constructor builds a matrix from the double array <CODE>b</CODE>.
	 * <P>
	 */

	public Matrix(double[][] b) {
		try {
			assign(b);
		} catch (ArrayStoreException ase) {
			mem = null;
		}
	}

	/**
	 * This constructor builds a matrix from the matrix <CODE>A</CODE>.
	 * <P>
	 */

	public Matrix(Matrix A) {
		assign(A);
	}

	/**
	 * This method returns the number of rows in the matrix.
	 * <P>
	 */

	public int nRows() {
		if (mem == null)
			return 0;
		else
			return mem.length;
	}

	/**
	 * This method returns the number of columns in the matrix.
	 * <P>
	 */

	public int nColumns() {
		if (mem == null || mem[0] == null)
			return 0;
		else
			return mem[0].length;
	}

	/**
	 * This method returns the <CODE>(i, j)</CODE> entry of the matrix.
	 * <P>
	 */

	public double get(int i, int j) throws ArrayIndexOutOfBoundsException, NullPointerException {
		return mem[i][j];
	}

	/**
	 * This method returns row <CODE>i</CODE> of the matrix.
	 * <P>
	 */

	public Vector getRow(int i) throws ArrayIndexOutOfBoundsException, NullPointerException {
		Vector x = new Vector(mem[i].length);

		for (int j = 0; j < mem[i].length; ++j)
			x.set(j, mem[i][j]);

		return x;
	}

	/**
	 * This method returns column <CODE>j</CODE> of the matrix.
	 * <P>
	 */

	public Vector getColumn(int j) throws ArrayIndexOutOfBoundsException, NullPointerException {
		Vector x = new Vector(mem.length);//length of a 2D array is length of rows

		for (int i = 0; i < mem.length; ++i)
			x.set(i, mem[i][j]);

		return x;
	}

	/**
	 * This method sets element <CODE>(i, j)</CODE> to <CODE>c</CODE>.
	 * <P>
	 */

	public void set(int i, int j, double c) throws ArrayIndexOutOfBoundsException,
			NullPointerException {
		mem[i][j] = c;
	}

	/**
	 * This method replaces the contents of the <CODE>mem</CODE> array with the data contained in
	 * <CODE>b</CODE>. Each sub-array of <CODE>b</CODE> must be of the same length.
	 */
	public Matrix assign(double[][] b) throws ArrayStoreException {
		final int m = b.length, n = b[0].length;

		mem = new double[m][n];

		for (int i = 0; i < m; ++i)
			if (b[i].length != n)
				throw new ArrayStoreException("Matrix sub-arrays not of the same length");
			else
				for (int j = 0; j < n; ++j)
					mem[i][j] = b[i][j];

		return this;
	}

	/**
	 * This method replaces the members of <CODE>this</CODE> with those of <CODE>A</CODE>.
	 * <P>
	 */

	public Matrix assign(Matrix A) {
		final int m = A.mem.length, n = A.mem[0].length;

		mem = new double[m][n];

		for (int i = 0; i < m; ++i)
			for (int j = 0; j < n; ++j)
				mem[i][j] = A.mem[i][j];

		return this;
	}

	/**
	 * This method compares if two matrices are equal. They are equal if and only if every element
	 * of <CODE>this</CODE> is equal to evey element of <CODE>A</CODE>.
	 * <P>
	 */

	public boolean equals(Matrix A) throws ArithmeticException {
		if (A == null)
			return false;

		if (mem[0].length != A.mem[0].length || mem.length != A.mem.length)
			throw new ArithmeticException("Matrices not of the same size");

		int i, j;

		for (i = 0; i < mem.length; ++i)
			for (j = 0; j < mem[i].length; ++j)
				if (mem[i][j] != A.mem[i][j])
					return false;

		return true;
	}

	/**
	 * This method creates a new matrix and stores the sum of <CODE>this</CODE> and <CODE>A</CODE>
	 * in it.
	 * <P>
	 * The matrices must be the same size.
	 * <P>
	 */

	public Matrix add(Matrix A) throws ArithmeticException {
		Matrix R = new Matrix(this);
		final int m = mem.length, n = mem[0].length;

		if (A.mem[0].length != m || A.mem.length != n)
			throw new ArithmeticException("Matrices not of the same size");

		for (int i = 0; i < m; ++i)
			for (int j = 0; i < n; ++j)
				R.mem[i][j] += A.mem[i][j];

		return R;
	}

	/**
	 * This method creates a new matrix and stores the difference of <CODE>this</CODE> and
	 * <CODE>A</CODE> in it.
	 * <P>
	 * The matrices must be the same size.
	 * <P>
	 */

	public Matrix sub(Matrix A) throws ArithmeticException {
		Matrix R = new Matrix(this);
		final int m = mem.length, n = mem[0].length;

		if (A.mem[0].length != m || A.mem.length != n)
			throw new ArithmeticException("Matrices not of the same size");

		for (int i = 0; i < m; ++i)
			for (int j = 0; i < n; ++j)
				R.mem[i][j] -= A.mem[i][j];

		return R;
	}

	/**
	 * This method creates a new matrix and stores the product of <CODE>this</CODE> and
	 * <CODE>A</CODE> in it.
	 * <P>
	 * The number of columns in <CODE>this</CODE> must equal the number of rows of <CODE>A</CODE>.
	 * <P>
	 */

	public Matrix mult(Matrix A) throws ArithmeticException {
		final int m = mem.length, n = mem[0].length, p = A.mem[0].length;
		Matrix R = new Matrix(m, p);

		if (A.mem.length != n)
			throw new ArithmeticException("Matrices not of the correct size");

		for (int i = 0; i < m; ++i)
			for (int j = 0; j < p; ++j)
				for (int k = 0; k < n; ++k)
					R.mem[i][j] += mem[i][k] * A.mem[k][j];

		return R;
	}

	/**
	 * This method returns of new matrix with each entry multiplied by <CODE>a</CODE>.
	 * <P>
	 */

	public Matrix mult(double a) {
		final int m = mem.length, n = mem[0].length;
		Matrix R = new Matrix(this);

		for (int i = 0; i < m; ++i)
			for (int j = 0; j < n; ++j)
				R.mem[i][j] *= a;

		return R;
	}

	/**
	 * This method returns a new vector that is the result of <CODE>this</CODE> multplied with
	 * <CODE>x</CODE>.
	 * <P>
	 * The number of columns of <CODE>this</CODE> must be equal to the dimension of <CODE>x</CODE>.
	 * <P>
	 */

	public Vector mult(Vector x) throws ArithmeticException {
		final int m = mem.length, n = mem[0].length;
		Vector r = new Vector(m);

		if (x.dim() != n)
			throw new ArithmeticException("Matrix rows and vector not of the same size");

		for (int i = 0; i < m; ++i) {
			double temp = x.get(i);
			for (int j = 0; j < n; ++j)
				temp += mem[i][j] * x.get(j);
			x.set(i, temp);
		}

		return r;
	}

	/**
	 * This method returns a new matrix containing the transpose of <CODE>this</CODE>.
	 * <P>
	 * The matrix must be a square matrix.
	 * <P>
	 */

	public Matrix t() throws ArithmeticException {
		if (mem[0].length != mem.length)
			throw new ArithmeticException("Matrix is not a square matrix");

		Matrix R = new Matrix(mem.length, mem.length);

		for (int i = 0; i < mem.length; ++i)
			for (int j = 0; j < mem.length; ++j)
				R.mem[j][i] = mem[i][j];

		return R;
	}

	/**
	 * This method exchanges entry <CODE>(i, j)</CODE> with entry <CODE>(k, l)</CODE>.
	 * <P>
	 * It is called by <CODE>gaussj</CODE>.
	 * <P>
	 */

	private void swap(int i, int j, int k, int l) {
		double t = mem[i][j];

		mem[i][j] = mem[k][l];
		mem[k][l] = t;
	}

	/**
	 * Linear equation solution by Gauss-Jordan elimination. <CODE>this</CODE> references the input
	 * matrix. <CODE>B</CODE> is an n-by-m matrix containing the <CODE>m</CODE> right-hand side
	 * vectors. On output, a matrix is returned that is the inverse of <CODE>this</CODE>, and the
	 * values of <CODE>B</CODE> are replaced by the corresponding set of solution vectors.
	 * 
	 * @see <a href="../../others/algorithm.html">Gauss-Jordan elimination Algorithm</a>
	 */

	public Matrix gaussj(Matrix B) throws ArithmeticException {
		final int m = B.mem[0].length;
		final int n = mem.length;
		Matrix A = new Matrix(this);

		// The integer arrays ipiv, indxr, and indxc are used for bookkeeping on the pivoting.

		int[] indxc = new int[n], indxr = new int[n], ipiv = new int[n];
		int i, j, k, l, ll, icol = 0, irow = 0;
		double big, dum, pivinv;

		if (mem[0].length != n)
			throw new ArithmeticException("Matrix is not a square matrix");

		if (B.mem.length != n)
			throw new ArithmeticException("Matrix not of the correct size");

		for (j = 0; j < n; ++j)
			ipiv[j] = 0;

		// This is the main loop over the columns to be reduced.

		for (i = 0; i < n; ++i) {
			big = 0.0;

			// This is the outer loop of the search for a pivot element.

			for (j = 0; j < n; ++j)
				if (ipiv[j] != 1)
					for (k = 0; k < n; ++k)
						if (ipiv[k] == 0)
							if (Math.abs(A.mem[j][k]) >= big) {
								big = Math.abs(A.mem[j][k]);
								irow = j;
								icol = k;
							} else if (ipiv[k] > 1)
								throw new ArithmeticException("gaussj:  Singular matrix-1");

			++ipiv[icol];

			/*
			 * We now have the pivot element, so we interchange rows, if needed, to put the pivot
			 * element on the diagonal.  The columns are not physically interchanged, only relabeled:
			 * indxc[i], the column of the ith pivot element, is the ith column that is reduced, while
			 * indxr[i] is the row in which that pivot element was originally located.  If indxr[i] !=
			 * indxc[i] there is an implied column interchange.  With this form of bookkeeping, the
			 * solution B's will end up in the correct order, and the inverse matrix will be scrambled
			 * by columns.
			 */

			if (irow != icol) {
				for (l = 0; l < n; ++l)
					A.swap(irow, l, icol, l);

				for (l = 0; l < m; ++l)
					B.swap(irow, l, icol, l);
			}

			indxr[i] = irow; // We are now ready to divide the pivot row by the
			indxc[i] = icol; //    pivot element, located at irow and icol.

			if (A.mem[icol][icol] == 0.0)
				throw new ArithmeticException("gaussj: Singular matrix-2");

			pivinv = 1.0 / A.mem[icol][icol];

			A.mem[icol][icol] = 1.0;

			for (l = 0; l < n; ++l)
				A.mem[icol][l] *= pivinv;

			for (l = 0; l < m; ++l)
				B.mem[icol][l] *= pivinv;

			for (ll = 0; ll < n; ++ll)
				// Next, we reduce the rows...
				if (ll != icol) // ...except for the pivot one, of course.
				{
					dum = A.mem[ll][icol];
					A.mem[ll][icol] = 0.0;

					for (l = 0; l < n; ++l)
						A.mem[ll][l] -= A.mem[icol][l] * dum;

					for (l = 0; l < m; ++l)
						B.mem[ll][l] -= B.mem[icol][l] * dum;
				}
		}

		/*
			* This is the end of the main loop over columns of the reduction.  It only remains to unscramble
			* the solution in view of the column interchanges.  We do this by interchanging pairs of columns
			* in the reverse order that the permutation was built up.
			*/

		for (l = n - 1; l >= 0; --l)
			if (indxr[l] != indxc[l])
				for (k = 0; k < n; ++k)
					A.swap(k, indxr[l], k, indxc[l]);

		// And we are done.

		return A;
	}

	/**
	 * The method performs a Jacobi rotation between entries <CODE>(i, j)</CODE> and
	 * <CODE>(k, l)</CODE>.
	 * <P>
	 */

	private void rotate(int i, int j, int k, int l, double s, double tau) {
		double g = mem[i][j], h = mem[k][l];

		mem[i][j] = g - s * (h + g * tau);
		mem[k][l] = h + s * (g - h * tau);
	}

	/**
	 * Given the eigenvalues <CODE>lambda[0..n-1]</CODE> and eigenvectors contained in the matrix
	 * <CODE>this</CODE> as output from <CODE>jacobi</CODE>, this routine sorts the eigenvalues into
	 * descending order, and rearranges the columns the columns of <CODE>this</CODE>
	 * correspondingly. The method is straight insertion.
	 * <P>
	 */

	public void eigsrt(double[] lambda) {
		final int n = lambda.length;

		int i, j, k;
		double p;

		for (i = 0; i < n; ++i) {
			p = lambda[i];
			k = i;

			for (j = i + 1; j < n; ++j)
				if (lambda[j] >= p) {
					p = lambda[j];
					k = j;
				}

			if (k != i) {
				lambda[k] = lambda[i];
				lambda[i] = p;

				for (j = 0; j < n; ++j)
					swap(j, i, j, k);
			}
		}
	}

	/**
	 * Computes all eigenvalues and eigenvectors of a real symmetric matrix <CODE>this</CODE>.
	 * <CODE>lamdba</CODE> contains the eigenvalues of <CODE>this</CODE>. <CODE>S</CODE> is a matrix
	 * whose columns contain, on output, the normalized eigenvectors of <CODE>this</CODE>.
	 */

	public int jacobi(double[] lambda, Matrix S) throws ArithmeticException, ArrayStoreException {
		final int n = mem.length;

		if (mem[0].length != n)
			throw new ArithmeticException("non-square matrices do not have eigenvalues!\ntry svd");

		if (lambda.length != n)
			throw new ArrayStoreException("array of eigenvalues must have length " + n);

		int i, j, ip, iq;
		double[] b = new double[n], z = new double[n];
		Matrix A = new Matrix(this);
		Matrix T = new Matrix(n, n);

		for (ip = 0; ip < n; ++ip) {
			T.mem[ip][ip] = 1.0;
			b[ip] = lambda[ip] = A.mem[ip][ip];
			z[ip] = 0.0;
		}

		int nrot = 0;

		for (i = 0; i < 50; ++i) {
			double thresh, sm = 0.0;

			for (ip = 0; ip < n - 1; ++ip)
				for (iq = ip + 1; iq < n; ++iq)
					sm += Math.abs(A.mem[ip][iq]);

			if (sm == 0.0) {
				T.eigsrt(lambda);
				S.assign(T);
				return nrot;
			}

			if (i < 4)
				thresh = 0.2 * sm / (n * n);
			else
				thresh = 0.0;

			for (ip = 0; ip < n - 1; ++ip)
				for (iq = ip + 1; iq < n; ++iq) {
					double g = 100.0 * Math.abs(A.mem[ip][iq]);

					if (i > 4 && (Math.abs(lambda[ip]) + g) == Math.abs(lambda[ip])
							&& (Math.abs(lambda[iq]) + g) == Math.abs(lambda[iq]))
						A.mem[ip][iq] = 0.0;
					else if (Math.abs(A.mem[ip][iq]) > thresh) {
						double t, h = lambda[iq] - lambda[ip];

						if ((Math.abs(h) + g) == Math.abs(h))
							t = A.mem[ip][iq] / h;
						else {
							double theta = 0.5 * h / A.mem[ip][iq];
							t = 1.0 / (Math.abs(theta) + Math.sqrt(1.0 + theta * theta));

							if (theta < 0.0)
								t = -t;
						}

						double c = 1.0 / Math.sqrt(1.0 + t * t);
						double s = t * c;
						double tau = s / (1.0 + c);

						h = t * A.mem[ip][iq];
						z[ip] -= h;
						z[iq] += h;
						lambda[ip] -= h;
						lambda[iq] += h;
						A.mem[ip][iq] = 0.0;

						for (j = 0; j < ip - 1; ++j)
							A.rotate(j, ip, j, iq, s, tau);

						for (j = ip + 1; j < iq - 1; ++j)
							A.rotate(ip, j, j, iq, s, tau);

						for (j = 0; j < ip - 1; ++j)
							A.rotate(ip, j, iq, j, s, tau);

						for (j = 0; j < ip - 1; ++j)
							T.rotate(j, ip, j, iq, s, tau);

						++nrot;
					}
				}

			for (ip = 0; ip < n; ++ip) {
				b[ip] += z[ip];
				lambda[ip] = b[ip];
				z[ip] = 0.0;
			}
		}

		throw new ArithmeticException("Too many iterations in routine jacobi");
	}

	/**
	 * Computes (<VAR>a</VAR>&sup2; + <VAR>b</VAR>&sup2;)<SUP>&frac12;</SUP> without destructive
	 * underflow or overflow.
	 * <P>
	 */

	public static double pythag(double a, double b) {
		double absa = Math.abs(a), absb = Math.abs(b);

		if (absa > absb)
			return absa * Math.sqrt(1.0 + (absb / absa) * (absb / absa));
		else if (absb == 0.0)
			return 0.0;
		else
			return absb * Math.sqrt(1.0 + (absa / absb) * (absa / absb));
	}

	public static double sign(double a, double b) {
		if (b > 0.0)
			return Math.abs(a);
		else
			return -Math.abs(a);
	}

	/**
	 * Solves <VAR>A</VAR>&middot;<VAR>X</VAR> = <VAR>B</VAR> for a vector <VAR>X</VAR>, where is
	 * specified by the matrices <CODE>U</CODE> and <CODE>V</CODE> and the array of singular values
	 * <CODE>w</CODE> as returned by <CODE>svdcmp</CODE>. <CODE>b</CODE> is the input right-hand
	 * side. No input quantities are destroyed, so the routine may be called sequentially with
	 * different <CODE>b</CODE>'s.
	 * <P>
	 */

	public static Vector svbksb(Matrix U, double[] w, Matrix V, Vector b)
			throws ArithmeticException {
		final int m = U.mem.length, n = V.mem.length;

		if (U.mem[0].length != m || V.mem[0].length != n || w.length != n || b.dim() != m)
			throw new ArithmeticException(
					"Arrays, matrrices, or vectors of incompatible dimensions");

		int i, j, jj;
		double s;
		Vector x = new Vector(n), tmp = new Vector(n);

		for (j = 0; j < n; ++j) {
			s = 0.0;

			if (w[j] != 0.0) {
				for (i = 0; i < m; ++i)
					s += U.mem[i][j] * b.get(i);

				s /= w[j];
			}

			tmp.set(j, s);
		}

		for (j = 0; j < n; ++j) {
			s = 0.0;

			for (jj = 0; jj < n; ++jj)
				s += V.mem[j][jj] * tmp.get(jj);

			x.set(j, s);
		}

		return x;
	}

	/**
	 * Given a matrix, this routine computes its singular value decomposition,
	 * <VAR>UWV<SUP><SMALL>T</SMALL></SUP></VAR>. The diagonal matrix of singular values
	 * <VAR>W</VAR> is output as the array <CODE>w</CODE>.
	 */

	public void svdcmp(Matrix U, double[] w, Matrix V) throws ArithmeticException,
			ArrayStoreException {
		boolean flag = true;
		int i, j, jj = 0, k, l = 0, m = mem.length, n = mem[0].length, nm = 0;
		double anorm = 0.0, c, f, g = 0.0, h, s, scale = 0.0, x, y, z = 0.0;
		double[] rv1 = new double[n];
		Matrix A = new Matrix(this);
		Matrix T = new Matrix(n, n);

		// Householder reduction to bidiagonal form.

		for (i = 0; i < n; ++i) {
			l = i + 1;
			s = 0.0;

			rv1[i] = scale * g;
			g = scale = 0.0;

			if (i < m) {
				for (k = i; k < m; ++k)
					scale += Math.abs(A.mem[k][i]);

				if (scale != 0.0) {
					for (k = i; k < m; ++k) {
						A.mem[k][i] /= scale;
						s += A.mem[k][i] * A.mem[k][i];
					}

					f = A.mem[i][i];
					g = -sign(Math.sqrt(s), f);
					h = f * g - s;
					A.mem[i][i] = f - g;

					for (j = l; j < n; ++j) {
						s = 0.0;

						for (k = i; k < m; ++k)
							s += A.mem[k][i] * A.mem[k][j];

						f = s / h;

						for (k = i; k < m; ++k)
							A.mem[k][j] += f * A.mem[k][i];
					}

					for (k = i; k < m; ++k)
						A.mem[k][i] *= scale;
				}
			}

			w[i] = scale * g;
			g = s = scale = 0.0;

			if (i < m && i != n) {
				for (k = l; k < n; ++k)
					scale += Math.abs(A.mem[i][k]);

				if (scale != 0.0) {
					for (k = l; k < n; ++k) {
						A.mem[i][k] /= scale;
						s += A.mem[i][k] * A.mem[i][k];
					}

					f = A.mem[i][l];
					g = -sign(Math.sqrt(s), f);
					h = f * g - s;
					A.mem[i][l] = f - g;

					for (k = l; k < n; ++k)
						rv1[k] = A.mem[i][k] / h;

					for (j = l; j < m; ++j) {
						s = 0.0;

						for (k = l; k < n; ++k)
							s += A.mem[j][k] * A.mem[i][k];

						for (k = l; k < n; ++k)
							A.mem[j][k] += s * rv1[k];
					}

					for (k = l; k < n; ++k)
						A.mem[i][k] *= scale;
				}
			}

			anorm = Math.max(anorm, Math.abs(w[i]) + Math.abs(rv1[i]));
		}

		// Accumulation of right-hand transformations.

		for (i = n - 1; i >= 0; --i) {
			if (i < n - 1) {
				if (g != 0.0) {
					for (j = l; j < n; ++j)
						T.mem[j][i] = (A.mem[i][j] / A.mem[i][l]) / g;

					for (j = l; j < n; ++j) {
						s = 0.0;

						for (k = l; k < n; ++k)
							s += A.mem[i][k] * T.mem[k][j];

						for (k = l; k < n; ++k)
							T.mem[k][j] += s * T.mem[k][i];
					}
				}

				for (j = l; j < n; ++j)
					T.mem[i][j] = T.mem[j][i] = 0.0;
			}

			T.mem[i][i] = 1.0;
			g = rv1[i];
			l = i;
		}

		// Accumulation of left-hand transformations.

		for (i = Math.min(m, n) - 1; i >= 0; --i) {
			l = i + 1;
			g = w[i];

			for (j = l; j < n; ++j)
				A.mem[i][j] = 0.0;

			if (g != 0.0) {
				g = 1.0 / g;

				for (j = l; j < n; ++j) {
					s = 0.0;

					for (k = l; k < m; ++k)
						s += A.mem[k][i] * A.mem[k][j];

					f = (s / A.mem[i][i]) * g;

					for (k = i; k < m; ++k)
						A.mem[k][j] += f * A.mem[k][i];
				}

				for (j = i; j < m; ++j)
					A.mem[j][i] *= g;
			} else
				for (j = i; j < m; ++j)
					A.mem[j][i] = 0.0;

			++A.mem[i][i];
		}

		// Diagonalization of the bidiagonal form: Loop over singular values, and over allowed iterations.

		for (k = n - 1; k >= 0; --k) {
			for (int its = 0; its < 30; ++its) {
				flag = true;

				// Test for splitting.  Note that rv1[0] is always zero.

				for (l = k; l >= 0; --l) {
					nm = l - 1;

					if ((Math.abs(rv1[l]) + anorm) == anorm) {
						flag = false;
						break;
					}

					if ((Math.abs(w[nm]) + anorm) == anorm)
						break;
				}

				if (flag) {
					c = 0.0; // Cancellation of rv1[l] if l > 1.
					s = 1.0;

					for (i = l; i <= k; ++i) {
						f = s * rv1[i];
						rv1[i] = c * rv1[i];

						if ((Math.abs(f) + anorm) == anorm)
							break;

						g = w[i];
						h = pythag(f, g);
						w[i] = h;
						h = 1.0 / h;
						c = g * h;
						s = -f * h;
						for (j = 0; j < m; ++j) {
							y = A.mem[j][nm];
							z = A.mem[j][i];
							A.mem[j][nm] = y * c + z * s;
							A.mem[j][i] = z * c - y * s;
						}
					}
				}

				z = w[k];

				if (l == k) // Convergence.
				{
					if (z < 0.0) {
						w[k] = -z; // Singular value is made nonnegative.

						for (j = 0; j < n; ++j)
							T.mem[j][k] = -T.mem[j][k];
					}

					break;
				}

				if (its == 30)
					throw new ArithmeticException("no convergence in 30 svdcom iterations.");

				x = w[l];
				nm = k - 1;
				y = w[nm];
				g = rv1[nm];
				h = rv1[k];
				f = ((y - z) * (y + z) + (g - h) * (g + h)) / (2.0 * h * y);
				g = pythag(f, 1.0);
				f = ((x - z) * (x + z) + h * ((y / (f + sign(g, f))) - h)) / x;
				c = s = 1.0; // Next QR transformation:

				for (j = 0; j < nm; ++j) {
					i = j + 1;
					g = rv1[i];
					y = w[i];
					h = s * g;
					g = c * g;
					z = pythag(f, h);
					rv1[j] = z;
					c = f / z;
					s = h / z;
					f = x * c + g * s;
					g = g * c - x * s;
					h = y * s;
					y *= c;

					for (jj = 0; jj < n; ++jj) {
						x = T.mem[jj][j];
						z = T.mem[jj][i];

						T.mem[jj][j] = x * c + z * s;
						T.mem[jj][i] = z * c - x * s;
					}

					z = pythag(f, h);
					w[j] = z; // Rotation can be arbitrary if z = 0.

					if (z != 0.0) {
						z = 1.0 / z;
						c = f * z;
						s = h * z;
					}

					f = c * g + s * y;
					x = c * y - s * g;

					for (jj = 0; jj < m; ++jj) {
						y = A.mem[jj][j];
						z = A.mem[jj][i];

						A.mem[jj][j] = y * c + z * s;
						A.mem[jj][i] = z * c - y * s;
					}
				}

				rv1[l] = 0.0;
				rv1[k] = f;
				w[k] = x;
			}
		}

		V.assign(T);
		U.assign(A);
	}

	/**
	 * This method creates a string from the matrix.
	 * <P>
	 */

	public String toString() {
		final int m = mem.length, n = mem[0].length;
		String s = new String();

		s += "{\n";

		for (int i = 0; i < m; ++i) {
			s += "\t{ ";

			for (int j = 0; j < n; ++j) {
				s += Double.toString(mem[i][j]);

				if (j < n - 1)
					s += ", ";
			}

			if (i < m - 1)
				s += " },\n";
			else
				s += " }\n";
		}

		s += "}";

		return s;
	}

	public Object clone() {
		return new Matrix(this);
	}

	/*public static void main(String[] args)
	{
	  Matrix M = new Matrix(3, 3), I = new Matrix(3, 3);

	  M.set(0, 0, 0.5);
	  M.set(1, 1, 1.0);
	  M.set(2, 2, 1.0);

	  System.out.println(M);

	  I.set(0, 0, 1.0);
	  I.set(1, 1, 1.0);
	  I.set(2, 2, 1.0);

	  System.out.println(M.gaussj(I));
	  System.out.println(I);
	}
	*/
}
