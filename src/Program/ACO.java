package Program;

import java.util.Random;

import Value.line;
import Value.parameter;

public class ACO {
	
	//*****演算法所需變數宣告*****//
	public static line linephermone[][];//線段費洛蒙
	public static int ant[][];//螞蟻
	public static double antdistance[];//記錄螞蟻行走距離
	public static int pointcheck[][];
	public static int bestpath[];//最佳路徑
	public static double bestdistance;//最佳路徑距離
	public static Random random = new Random();
	public static boolean bestcheck = false;
	
	
	public static void antcolonyoptimization()
	{	
		//*****初始化所有線段費洛蒙*****//
		updatepheromone(0,0);
		
		//*****演算法*****//
		for(int t=0;t<parameter.ACOiteration;t++)
		{
			//*****產生螞蟻*****//
			ant = new int[parameter.antcount][Main.pointdata.length+1];
			antdistance = new double[parameter.antcount];
			pointcheck = new int [parameter.antcount][Main.pointdata.length];
			
			//*****螞蟻尋找路徑*****//
			for(int i=0;i<=Main.pointdata.length;i++)//點
			{
				for(int j=0;j<parameter.antcount;j++)//螞蟻
				{
					ant[j][i] = nextpoint(i, j);//取得下一個點
				}
				if(i!=0)
				{
					//*****局部更新費洛蒙*****//
					updatepheromone(1,i);
				}
			}
			//*****全域更新費洛蒙*****//
			updatepheromone(2,0);
			//System.out.println("Cost:"+bestdistance);//顯示每一代的解
		}
		
		//*****2-OPT*****//
		twoOPT();
		//System.out.println("BestCost:"+bestdistance);//顯示最佳解
		
	}
	
	//*****尋找下一點方法*****//
	public static int nextpoint(int point,int ants)
	{
		int next=-1;
		if(point==0)//第一個點
		{
			next = random.nextInt(Main.pointdata.length);
			pointcheck[ants][next] = 1;
		}
		else if(point == Main.pointdata.length)//最後一個點
		{
			next = ant[ants][0];
		}
		else
		{
			double tmp = random.nextDouble();
			if(tmp > parameter.q)//轉換機率
			{
				next = transformrate(ant[ants][point-1],ants);
			}
			else//轉換規則
			{
				next = transformroll(ant[ants][point-1],ants);
			}
			pointcheck[ants][next] = 1;
		}
		return next;
	}
	
	//*****費洛蒙更新方法*****//
	public static void updatepheromone(int type,int point)
	{
		
		//*****初始化所有線段費洛蒙*****//
		if(type == 0)
		{
			//*****建立陣列參數*****//
			linephermone = new line[Main.pointdata.length][Main.pointdata.length];//各線段費洛蒙
			bestpath = new int[Main.pointdata.length+1];//最佳路徑
			bestcheck = false;//判斷是否第一次執行
			
			for(int i=0;i<Main.pointdata.length-1;i++)
			{
				for(int j=i+1;j<Main.pointdata.length;j++)
				{
					linephermone[i][j] = new line();
					linephermone[i][j].pheromone = parameter.pheromone;//費洛蒙
					linephermone[i][j].distance = distance(i, j);//距離
				}
			}
		}
		
		//*****局部更新線段費洛蒙*****//
		if(type == 1)
		{
			for(int i=0;i<parameter.antcount;i++)
			{
				if(ant[i][point] < ant[i][point-1])
				{
					linephermone[ant[i][point]][ant[i][point-1]].pheromone = (1-parameter.p)*linephermone[ant[i][point]][ant[i][point-1]].pheromone+parameter.p*parameter.pheromone + (parameter.p*parameter.pheromone);
					antdistance[i] += linephermone[ant[i][point]][ant[i][point-1]].distance;
				}
				else
				{
					linephermone[ant[i][point-1]][ant[i][point]].pheromone = (1-parameter.p)*linephermone[ant[i][point-1]][ant[i][point]].pheromone+parameter.p*parameter.pheromone + (parameter.p*parameter.pheromone);
					antdistance[i] += linephermone[ant[i][point-1]][ant[i][point]].distance;
				}
			}
		}
		
		//*****全域更新線段費洛蒙*****//
		if(type == 2)
		{
			int best = 0;
			boolean bestupdate = false;
			double nowbestdistance = 0;
			int nowbest = 0;
			for(int j=0;j<parameter.antcount;j++)//螞蟻
			{	
				//*****紀錄全演算法的最佳解*****//
				if(bestcheck)
				{
					if(bestdistance > antdistance[j])
					{
						bestdistance = antdistance[j];
						best = j;
						bestupdate = true;
					}
				}
				else
				{
					bestdistance = antdistance[j];
					best = j;
					bestcheck = true;
				}
				
				//*****找出當代螞蟻的最好解*****//
				if(j==0)
				{
					nowbestdistance = antdistance[j];
					nowbest = j;
				}
				else
				{
					if(nowbestdistance > antdistance[j])
					{
						nowbestdistance = antdistance[j];
						nowbest = j;
					}
				}
				
			}
			
			//*****更新最佳解*****//
			if(bestupdate)
			{
				bestpath = ant[best];
			}
			
			if(bestdistance > SA.bestdistance)
			{
				updatepheromone(3, 0);
			}
			else
			{
				//*****更新線段費洛蒙*****//
				for(int i=0;i<Main.pointdata.length;i++)//點
				{
					
					if(ant[nowbest][i] < ant[nowbest][i+1])
					{
						linephermone[ant[nowbest][i]][ant[nowbest][i+1]].pheromone = (1-parameter.p)*linephermone[ant[nowbest][i]][ant[nowbest][i+1]].pheromone + (parameter.p*parameter.Q/nowbestdistance);
					}
					else
					{
						linephermone[ant[nowbest][i+1]][ant[nowbest][i]].pheromone = (1-parameter.p)*linephermone[ant[nowbest][i+1]][ant[nowbest][i]].pheromone + (parameter.p*parameter.Q/nowbestdistance);
					}
				}
			}
		}
		
		//*****更新SA最佳解費洛蒙*****//
		if(type == 3)
		{
			for(int i=0;i<Main.pointdata.length;i++)
			{
				if(SA.trip[i] < SA.trip[i+1])
				{
					linephermone[SA.trip[i]][SA.trip[i+1]].pheromone = (1-parameter.p)*linephermone[SA.trip[i]][SA.trip[i+1]].pheromone + (parameter.p*parameter.Q/SA.bestdistance);
				}
				else
				{
					linephermone[SA.trip[i+1]][SA.trip[i]].pheromone = (1-parameter.p)*linephermone[SA.trip[i+1]][SA.trip[i]].pheromone + (parameter.p*parameter.Q/SA.bestdistance);
				}
			}
		}
	}
	
	//*****2-OPT*****//
	public static void twoOPT()
	{
		boolean checkloop = true;
		while(checkloop)
		{
			checkloop = false;
			for(int i=0;i<Main.pointdata.length-2;i++)//Point
			{
				for(int j=i+2;j<Main.pointdata.length;j++)
				{
					double A = distance(bestpath[i],bestpath[i+1])+distance(bestpath[j],bestpath[j+1]);//線段AB+線段CD
					double B = distance(bestpath[i],bestpath[j])+distance(bestpath[i+1],bestpath[j+1]);//線段AC+線段BD
					if(A > B)
					{
						//A=i B=i+1
						//C=j D=j+1
						int tmp = bestpath[i+1];
						bestpath[i+1] = bestpath[j];
						bestpath[j] = tmp;
						checkloop = true;
					}
				}
			}
		}
		double tmp =0;
		for(int i=0;i<bestpath.length-1;i++)
		{
			tmp += distance(bestpath[i], bestpath[i+1]);
		}
		bestdistance = tmp;
	}
	
	//*****轉換機率*****//
	public static int transformrate(int nowpoint,int ants)
	{
		double denominator = 0;//分母
		double molecular[] = new double[Main.pointdata.length];//分子
		int re = 0;
		
		for(int i=0;i<Main.pointdata.length;i++)
		{
			if(pointcheck[ants][i] == 0 && i != nowpoint)
			{
				if(nowpoint < i)
				{
					//molecular[i] = Math.pow(linephermone[nowpoint][i].pheromone, parameter.A)*Math.pow(1/linephermone[nowpoint][i].distance, parameter.B);
					molecular[i] = linephermone[nowpoint][i].pheromone*(1/linephermone[nowpoint][i].distance)*(1/linephermone[nowpoint][i].distance);
				}
				else
				{
					//molecular[i] = Math.pow(linephermone[i][nowpoint].pheromone, parameter.A)*Math.pow(1/linephermone[i][nowpoint].distance, parameter.B);
					molecular[i] = linephermone[i][nowpoint].pheromone*(1/linephermone[i][nowpoint].distance)*(1/linephermone[i][nowpoint].distance);
				}
				denominator += molecular[i];
			}
		}
		
		//*****輪盤法*****//
		double tmp = random.nextDouble();
		for(int i=0;i<Main.pointdata.length;i++)
		{
			tmp = tmp - (molecular[i]/denominator);
			if(tmp<=0)
			{
				re = i;
				break;
			}
		}
		return re;
	}
	
	//*****轉換規則*****//
	public static int transformroll(int nowpoint,int ants)
	{
		double molecular;//分子
		double max=0;
		int re = 0;
		for(int i=0;i<Main.pointdata.length;i++)
		{
			if(pointcheck[ants][i] == 0 && i != nowpoint)
			{
				if(nowpoint < i)
				{
					//molecular = Math.pow(linephermone[nowpoint][i].pheromone, parameter.A)*Math.pow(1/linephermone[nowpoint][i].distance, parameter.B);
					molecular = linephermone[nowpoint][i].pheromone*(1/linephermone[nowpoint][i].distance)*(1/linephermone[nowpoint][i].distance);
				}
				else
				{
					//molecular = Math.pow(linephermone[i][nowpoint].pheromone, parameter.A)*Math.pow(1/linephermone[i][nowpoint].distance, parameter.B);
					molecular = linephermone[i][nowpoint].pheromone*(1/linephermone[i][nowpoint].distance)*(1/linephermone[i][nowpoint].distance);
				}
				if(molecular > max)
				{
					max = molecular;
					re = i;
				}
			}
		}
		return re;
	}
	
	//*****計算兩點間距離*****//
	public static double distance(int pointA,int pointB)
	{
		return Math.sqrt(((Main.pointdata[pointA].x-Main.pointdata[pointB].x)*(Main.pointdata[pointA].x-Main.pointdata[pointB].x))+((Main.pointdata[pointA].y-Main.pointdata[pointB].y)*(Main.pointdata[pointA].y-Main.pointdata[pointB].y)));
	}
}
