package brain.ga;
import java.lang.*;
import java.util.*;
public class RankSelector implements Selector
{
	private int pos;




	public Genome select (Population population)
	{
		pos = GAUtilities.nextPos(population.getSize());
		Genome genome = population.get(pos);
		return genome;
	}
/*	public static void main(String[] args)
	{
		RankSelector rs = new RankSelector(new Population());
		int n=3;
		for (int i=0;i<20;i++)
			System.out.println(rs.nextPos(n));
	}*/
}