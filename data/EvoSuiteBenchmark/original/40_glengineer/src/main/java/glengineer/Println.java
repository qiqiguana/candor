package glengineer;


public class Println
{
	public Println()
	{
		System.out.println();
	}
	public Println(Object o)
	{
		System.out.println( o == null ? "null" : o.toString() );
	}
}
