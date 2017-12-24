package Program;

import IO.Drawpanel;
import Interface.View;
import Value.data;
import Value.parameter;

public class Main {

	//*****資料點陣列宣告*****//
	public static data pointdata[];
	
	static long time_start;//運行開始時間
	public static double costtime;
	public static double distance;
	public static int[] path;
	
	public static void main()
	{
		//*****儲存演算法開始時間*****//
		time_start = System.currentTimeMillis();
		
		//*****模擬退火演算法產生初始路徑*****//
		parameter.SAiteration = parameter.iteration;
		SA.simulatedannealing();
		
		//*****螞蟻演算法*****//
		parameter.ACOiteration = parameter.iteration/2;
		ACO.antcolonyoptimization();
		
		//*****選擇解*****//
		if(SA.bestdistance < ACO.bestdistance)
		{
			distance = SA.bestdistance;
			path = SA.besttrip;
			System.out.println("SA");
		}
		else
		{
			distance = ACO.bestdistance;
			path = ACO.bestpath;
			System.out.println("ACO");
		}
		
		//*****儲存演算法執行時間*****//
		costtime = ((double)System.currentTimeMillis()-(double)time_start)/1000;
		
		//*****輸出路徑*****//
		if(View.iscomputerrun.isSelected())
		{
			if(View.runtime == View.runcount)
			{
				for(int i=0;i<path.length-1;i++)
				{
					Drawpanel.drawline(path[i], path[i+1]);
				}
			}
			
			if(View.runcount == 1)
			{
				View.bestpath = path;
				View.bestdistance = distance;
			}
			else
			{
				if(View.bestdistance > distance)
				{
					View.bestpath = path;
					View.bestdistance = distance;
				}
			}
		}
		else
		{
			for(int i=0;i<path.length-1;i++)
			{
				Drawpanel.drawline(path[i], path[i+1]);
			}
			View.time_output.setText("執行時間："+String.valueOf(costtime));
			View.cost_output.setText("花費距離："+distance);
			View.bestpath = path;
			View.bestdistance = distance;
		}
	}
}
