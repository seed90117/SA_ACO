package Program;

import java.util.Random;

import IO.Drawpanel;
import Value.parameter;

public class SA {

	static double temperature;
	static int[] trip;
	static double distance = 0;
	static boolean[] checkpoint;
	public static int[] besttrip;
	public static double bestdistance = 0;
	static int point = Main.pointdata.length;
	
	public static void simulatedannealing()
	{
		temperature = parameter.starttemperature;
		Initial(true);//全部陣列初始化
		
		for(int t=0;t<parameter.SAiteration;t++)
		{
			if(temperature <= parameter.endtemperature)
			{
				break;
			}
			Initial(false);//路徑陣列初始化
			route();//建立路徑
			twoopt();//2-OPT
			
			//*****計算路徑長度*****//
			for(int i=0;i<point;i++)
			{
				distance += distance(trip[i], trip[i+1]);
			}
			isbest(t);//判斷是否最佳路徑
			
			temperature *= parameter.a;//降溫
		}
		//System.out.println(bestdistance);
		
		//drawline();
		
	}
	
	//*****初始化*****//
	public static void Initial(boolean type)
	{
		if(type)//true
		{
			besttrip  = new int[point+1];
		}
		trip = new int[point+1];
		checkpoint = new boolean[point];
		distance = 0;
	}
	
	//*****建立路徑*****//
	public static void route()
	{
		Random random = new Random();
		for(int i=0;i<trip.length;i++)
		{
			int tmp;
			if(i == trip.length-1)
			{
				trip[i] = trip[0];
			}
			else
			{
				tmp = random.nextInt(point);
				while(checkpoint[tmp])
				{
					if(tmp == point-1)
					{
						tmp=0;
					}
					else 
					{
						tmp ++;
					}
				}
				trip[i] = tmp;
				checkpoint[tmp] = true;
			}
		}
	}
	
	//*****2-OPT*****//
	public static void twoopt()
	{
		boolean checkloop = true;
		int tmp;
		double ABCD,ACBD;
		for(int t=0;t<point*0.1;t++)
		{
			for(int i=0;i<point-2;i++)//Point
			{
				for(int j=i+2;j<point;j++)
				{
					ABCD = distance(trip[i],trip[i+1])+distance(trip[j],trip[j+1]);//線段AB+線段CD
					ACBD = distance(trip[i],trip[j])+distance(trip[i+1],trip[j+1]);//線段AC+線段BD
					if(ABCD > ACBD)
					{
						//A=i B=i+1
						//C=j D=j+1
						tmp = trip[i+1];
						trip[i+1] = trip[j];
						trip[j] = tmp;
						checkloop = true;
					}
				}
			}
		}
	}
	
	//*****判斷是否最佳路近*****//
	public static void isbest(int t)
	{
		if(t == 0)
		{
			bestdistance = distance;
			besttrip = trip;
		}
		else
		{
			if(bestdistance > distance)
			{
				bestdistance = distance;
				besttrip = trip;
			}
		}
	}
	
	//*****計算兩點間距離*****//
	public static double distance(int pointA,int pointB)
	{
		return Math.sqrt(((Main.pointdata[pointA].x-Main.pointdata[pointB].x)*(Main.pointdata[pointA].x-Main.pointdata[pointB].x))+((Main.pointdata[pointA].y-Main.pointdata[pointB].y)*(Main.pointdata[pointA].y-Main.pointdata[pointB].y)));
	}
	
	//*****畫線*****//
	public static void drawline()
	{
		for(int i=0;i<point;i++)
		{
			Drawpanel.drawline(besttrip[i], besttrip[i+1]);
		}
	}
}
