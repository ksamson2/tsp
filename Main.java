import java.util.*;
public class Main
{
	/*Place your main and your test code in this class*/
	public static void main(String args[])
	{
		boolean quit=false;
		while(quit==false)
		{
			int index; 
			TSP tsp=new TSP();
			System.out.println("TSP algorithm solution");
			System.out.println("Select your file:");
			System.out.println("1.eil51.tsp");
			System.out.println("2.wi29.tsp");
			System.out.println("3.dj38.tsp");
			System.out.println("4.test.txt");
			Scanner S=new Scanner(System.in);
			index=S.nextInt();
			String filename="";
			switch (index)
			{
				case 1: filename="eil51.tsp";
						break;
				case 2: filename="wi29.tsp";
						break;
				case 3: filename="dj38.tsp";
						break;
				case 4: filename="test.txt";
						break;
			}
			tsp.buildfromFile(filename);
			System.out.println("Select your algorithm:");
			System.out.println("1.Depth First Search");
			System.out.println("2.Breath First Search");
			System.out.println("3.A* ");
			Scanner S2=new Scanner(System.in);
			index=S2.nextInt();
			switch (index)
			{
				case 1: index=1;
						tsp.dfs(filename);
						break;
				case 2: index=2;
						tsp.bfs(filename);
						break;
				case 3: index=3;
						tsp.astar(filename);
						break;
			}

			System.out.println();
		Scanner S3=new Scanner(System.in);
		System.out.println("Would you like to run another algorithm? (Y/N)");
		String input=S3.nextLine();
		if(input.equals("y")|| input.equals("yes"))
		{
			quit=false;
		}
		else 
		{
			quit=true;
		}

		}
		
	}
}