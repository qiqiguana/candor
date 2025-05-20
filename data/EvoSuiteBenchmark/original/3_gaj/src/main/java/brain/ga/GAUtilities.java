package brain.ga;

import java.util.*;
public class GAUtilities
{
	private static Random rnd = new Random();
	//returns a boolean value with the prob probability
	//NOT IMPLEMENTED YET correctly
	public static boolean flipCoin(double prob)
	{
		return rnd.nextBoolean();
	}
	//return an integer value in the range [1..n] with
	//a sigmoid probability distribution.
	//Thanx to Pintilie Radu, 02.04.2001
	public static int nextPos(int n)
	{
		int nn = rnd.nextInt(n*(n+1)/2) + 1;
		//System.out.print(nn + ",");
		int i;
		for (i=1;(i<=n) && (i*(i-1)/2<nn); i++) { }
		return i-1;
	}
	
}

