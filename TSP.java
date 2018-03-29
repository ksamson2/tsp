import java.util.*;
import java.io.*;

public class TSP
{
	public TSP()
	{

	}
	//KIM: 
	//- ask about states expanded? 
	ArrayList<Node> cities = new ArrayList<Node>();
	ArrayList<Node> cities_temp = new ArrayList<Node>();

	public class Node{
		public Node next;
		public double value;
		public double xc; 
		public double yc;
		public Node parent; 
		public double heuristic=0.0; 
		public boolean leaf=true; 
		public boolean visited=false; 
		public double distance;   
		int depth=0;
		ArrayList<Node> keys= new ArrayList<Node>(); 
		ArrayList<Double> tracePath= new ArrayList<Double>(); 
		protected Node(double v, double x, double y)
		{
			value=v;
			xc=x; 
			yc=y;
			distance=0;
		}
		protected Node(Node x)
		{
			value=x.value;
			xc=x.xc;
			yc=x.yc;
			distance=0;
		}
		public boolean containsPath(double value)
		{
			for(int i=0;i<tracePath.size();i++)
			{
				if(tracePath.get(i)==value)
				{
					return true;
				}
			}
			return false; 
		}
		public void sort() 
		{
			int size= keys.size();
	        for(int i=0;i<size;i++)
	        {
	            int minx = i;
	            for(int j=minx+1;j<size;j++)
	                if (keys.get(j).heuristic<keys.get(minx).heuristic)
	                    minx = j;
	 
	           
	            Node temp = keys.get(minx);
	            keys.set(minx, keys.get(i));
	            keys.set(i, temp);
	        }
	   	}
	   	public String getPath()
	   	{
	   		String a = " ";
	   		for(int i=0; i<tracePath.size();i++)
	   		{
	   			a = a + tracePath.get(i) + " ";
	   		}
	   		return a;
	   	}
	}

	public void buildfromFile(String filename)
	{
		try
		{
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			double v,x,y;
			while (sc.hasNextLine())
			{
				String temp=sc.nextLine();
	 			
	 			if(Character.isDigit(temp.charAt(0)))
	 			{
	 				String[] split=temp.split("\\s+");
	 				v=Double.parseDouble(split[0]);
	 				x=Double.parseDouble(split[1]);
	 				y=Double.parseDouble(split[2]);	 				
	 				Node newN=new Node(v,x,y);
	 				cities.add(newN);
	 				cities_temp.add(newN);
	 			}
	 		}
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("File not found");
		}
	}

	public void dfs(String problem)
	{
		long startTime=System.nanoTime();
		LinkedList<Node> open = new LinkedList<Node>(); 
		LinkedList<Node> closed = new LinkedList<Node>();
		boolean state=false;
		open.push(cities.get(0));
		//cities.remove(0);
		System.out.println("How many seconds would you like the algorithm to run for?");
		Scanner S=new Scanner(System.in);
		Integer index=S.nextInt();
		long endVal=index.longValue();
		long start = System.currentTimeMillis();
		long end = start + endVal*1000; 
		
		while(open.size()>0 && !state)
		{
			while(System.currentTimeMillis()<end)
			{
				if(open.size()>0) 
				{
					Node x= open.remove(0);
					if(x.tracePath.size() >= cities.size())
					{
						state = true;
						System.out.println("Solution: "+state);
						System.out.println("Node: "+x.value);
						System.out.println("TracePath: "+x.getPath());
						x.heuristic = x.heuristic+calcDistance(x,cities.get(0));
						System.out.println("Distance: "+x.heuristic);
						closed.add(x);
						
						break;
					}
					if(x.tracePath.size()==0)
					{
						x.tracePath.add(x.value);
					}
					
					Node Tree= buildTree(x);
					for(int i=0;i<Tree.keys.size();i++)
					{
						Node curr=Tree.keys.get(i);
						double check=calcDistance(x,curr);
						x.keys.get(i).distance=check;
						System.out.println("Node visited: "+curr.value);
						if(open.contains(curr)|| closed.contains(curr))
						{

						}
						else 
						{
							open.addFirst(curr);
						}
						//}
					}
					closed.add(x);
					System.out.println("Path to x currently: "+x.getPath());
					System.out.println("------------------------------------------");
				}
			}
			break;
		}
		
		int statesExpanded=closed.size()+open.size();
		System.out.println("States expanded: "+statesExpanded);
		System.out.println("Closed:");
		for(int l=0;l<closed.size();l++)
		{
			System.out.print(closed.get(l).value+", ");
		}
		int closedSize=closed.size();
		Node x=closed.get(closedSize-1);
		System.out.println("TracePath: "+x.getPath());
		long endTime=System.nanoTime();
		long timePassed=endTime - startTime;
		timePassed/=1000000;
		System.out.println("Time: "+timePassed+"ms");
		writeToFile(x.heuristic,closed,timePassed," Breadth First Search",problem);
	}

	public int dfsID(LinkedList<Node> open, int index,boolean state)
	{
		if(open.size()>0&& !state)
		{
			index++;
		}
		return index;
	}
	
	public void bfs(String problem)
	{
		long startTime=System.nanoTime();
		LinkedList<Node> open = new LinkedList<Node>(); 
		LinkedList<Node> closed = new LinkedList<Node>();
		boolean state=false;
		open.push(cities.get(0));
		//cities.remove(0);
		System.out.println("How many seconds would you like the algorithm to run for?");
		Scanner S=new Scanner(System.in);
		Integer index=S.nextInt();
		long endVal=index.longValue();
		long start = System.currentTimeMillis();
		long end = start + endVal*1000; 
		
		while(open.size()>0 && !state)
		{
			while(System.currentTimeMillis()<end)
			{
				if(open.size()>0) 
				{
					Node x= open.remove(0);
					if(x.tracePath.size() >= cities.size())
					{
						state = true;
						System.out.println("Solution: "+state);
						System.out.println("Node: "+x.value);
						System.out.println("TracePath: "+x.getPath());
						x.heuristic = x.heuristic+calcDistance(x,cities.get(0));
						System.out.println("Distance: "+x.heuristic);
						closed.add(x);
						
						break;
					}
					if(x.tracePath.size()==0)
					{
						x.tracePath.add(x.value);
					}
					
					Node Tree= buildTree(x);
					for(int i=0;i<Tree.keys.size();i++)
					{
						Node curr=Tree.keys.get(i);
						// if((!open.contains(curr)&& !closed.contains(curr)))
						// {
							//logic here is flawed
							double check=calcDistance(x,curr);
							x.keys.get(i).distance=check;
							System.out.println("Node visited: "+curr.value);
							if(open.contains(curr)|| closed.contains(curr))
							{

							}
							else 
							{
								open.add(curr);
							}
						//}
					}
					closed.add(x);
					System.out.println("Path to x currently: "+x.getPath());
					System.out.println("------------------------------------------");
				}
			}
			break;
		}
		
		int statesExpanded=closed.size()+open.size();
		System.out.println("States expanded: "+statesExpanded);
		System.out.println("Closed:");
		for(int l=0;l<closed.size();l++)
		{
			System.out.print(closed.get(l).value+", ");
		}
		int closedSize=closed.size();
		Node x=closed.get(closedSize-1);
		System.out.println("TracePath: "+x.getPath());
		long endTime=System.nanoTime();
		long timePassed=endTime - startTime;
		timePassed/=1000000;
		System.out.println("Time: "+timePassed+"ms");
		writeToFile(x.heuristic,closed,timePassed," Breadth First Search",problem);
		
	}

	public void astar(String problem)
	{
		long startTime=System.nanoTime();
		LinkedList<Node> open = new LinkedList<Node>(); 
		LinkedList<Node> closed = new LinkedList<Node>();
		boolean state=false;
		open.push(cities.get(0));
		//cities.remove(0);
		// cities.remove(index);

		while(open.size()>0 && state!=true)
		{
			if(open.size()>0) 
			{
				Node x= open.remove(0);
				if(x.tracePath.size() >= cities.size())
				{
					state = true;
					//System.out.println("Solution: "+state);
					System.out.println("Node: "+x.value);
					System.out.println("TracePath: "+x.getPath());
					x.heuristic = x.heuristic+calcDistance(x,cities.get(0));
					System.out.println("Distance: "+x.heuristic);
					closed.add(x);
					System.out.println("Closed:");
					int statesExpanded=closed.size()+open.size();
					System.out.println("States expanded: "+statesExpanded);
					for(int l=0;l<closed.size();l++)
					{
						System.out.print(closed.get(l).value+", ");
					}
					long endTime=System.nanoTime();
					long timePassed=endTime - startTime;
					timePassed/=1000000;
					System.out.println("Time: "+timePassed+"ms");
					writeToFile(x.heuristic,closed,timePassed," A* algorithm",problem);
					break;
				}
				if(x.tracePath.size()==0)
				{
					x.tracePath.add(x.value);
				}
				// boolean remCities=cities.contains(x);
				// if(remCities==true)
				// {
				// 	for(int k=0;k<cities.size();k++)
				// 	{
				// 		if(cities.get(k).value==x.value)
				// 		{
				// 			cities.remove(k);
				// 		}
				// 	}
				// }
				x= buildTree(x);
				for(int i=0;i<x.keys.size();i++)
				{
					Node curr=x.keys.get(i);
					// if((!open.contains(curr)&& !closed.contains(curr)))
					// {
						//logic here is flawed
						double check=calcDistance(x,curr);
						System.out.println("Node visited: "+curr.value);
						x.keys.get(i).heuristic=check+x.heuristic;
						x.keys.get(i).distance=check;
						//System.out.println("heuristic: "+x.keys.get(i).heuristic);
				}
				System.out.println("Path to x currently: "+x.getPath());
				x.sort();
				open.addAll(0,x.keys);
				// System.out.println("HERE ----------------------------------------------");
				//open=listSort(open);
				//System.out.print("open currently:");
				// for(int i=0;i<open.size();i++)
				// {
				// 	System.out.print(open.get(i).value+", ");
				// }
				// System.out.println("x-heuristic: "+x.heuristic);
				closed.add(x);
				System.out.println("--------------------------------------:");
				// for(int i=0; i<open.size();i++)
				// {
				// 	System.out.println("node: "+open.get(i).value+" heuristic: "+open.get(i).heuristic);
				// }
				// System.out.println("--------------------------------------:");
				// System.out.println("node: "+x.value+" heuristic: "+x.heuristic);
			}
		}
	}

	public Node buildTree(Node x)
	{
		// System.out.println("Print node------------------");
		// System.out.println("Print x: "+x.value+ "_heuristic: "+x.heuristic);
		for(int i=0;i<cities.size(); i++)
		{
			if(cities.get(i).value!=x.value && !x.containsPath(cities.get(i).value))
			{
				Node newNode = new Node(cities.get(i));
				newNode.tracePath.addAll(x.tracePath);
				newNode.tracePath.add(x.value);
				x.keys.add(newNode);
				//x.keys.get(i).parent=x;
				//System.out.println("x_parent: "+x.keys.get(i).parent.value);
			}
		}
		//System.out.println("---------------------");
		return x;
	}
	public double calcDistance(Node a, Node b)
	{
		double xd=a.xc-b.xc;
		double yd=a.yc-b.yc;
		double distance=Math.sqrt(xd*xd+ yd*yd);
		return distance;
	}
	public void writeToFile(double path, LinkedList<Node> closed, long time, String algorithm, String problem)
	{
		BufferedWriter buff=null;
		try
		{
			buff=new BufferedWriter(new FileWriter("result.txt",true));
			buff.write("-------------------------------");
			buff.newLine();
			buff.write("ALGORITHM DATA"+algorithm);
			buff.newLine();
			buff.write("Problem: "+problem);
			buff.newLine();
			buff.write("Time: "+time+ " ms");
			buff.newLine();
			buff.write("Path taken: ");
			for(int l=0;l<closed.size();l++)
			{	
				buff.write(closed.get(l).value+", ");
			}
			buff.newLine();
			if(path>0)
			{
				buff.write("Path distance: "+path);
				buff.newLine();
			}
			buff.write("-------------------------------");
			buff.newLine();
			buff.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			if(buff!=null)
			{
				try
				{
					buff.close();
				}
				catch (IOException ioe2)
				{

				}
			}
		}
	}
}
