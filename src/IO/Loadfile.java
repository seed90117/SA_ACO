package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import Interface.View;
import Program.Main;
import Value.data;

public class Loadfile {
static BufferedReader br;
	
	public static void loadfile()
	{
		String tmp =null;
		FileReader fr = null;
		
		//*****設定chooser預設開啟位置*****//
		View.open.setCurrentDirectory(new java.io.File("//Users//kevin//Documents//Algorithm_Data//Ant Algorithm_Data"));
		
		//*****設定chooserTitle*****//
		View.open.setDialogTitle("選擇檔案");
		
		//*****判斷是否按下確定*****//
		if(View.open.showDialog(View.antamount_input, "確定") == JFileChooser.APPROVE_OPTION)
		{
			//*****取得選取檔案*****//
			File filepath = View.open.getSelectedFile();
			
			//*****取得選取檔案路徑*****//
			tmp = filepath.getPath().toString();
			
			//*****讀取檔案*****//
			try
			{
				fr = new FileReader(tmp);
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(Loadfile.class.getName()).log(Level.SEVERE, null , ex);
			}
			
			br = new BufferedReader(fr);//檔案放入讀取器
			View.checkloading = true;//確認檔案讀取
			getdata();//讀取檔案
		}
	}
	public static void getdata()
	{
		String tmp;
		String[] tmparray;
		int i =0;
		double max=0;
		
		try
		{
			//*****讀取第一行*****//
			tmp = br.readLine();
			tmparray = tmp.split(" ");
			
			//*****新增data物件*****//
			Main.pointdata = new data[Integer.parseInt(tmparray[0])];
			
			
			//*****讀取資料*****//
			while((tmp = br.readLine()) != null)
			{
				tmparray = tmp.split(" ");
				Main.pointdata[i] = new data();
				Main.pointdata[i].x = Double.valueOf(tmparray[0]);
				Main.pointdata[i].y = Double.valueOf(tmparray[1]);
				
				//*****找出最大數值*****//
				if(max < Double.valueOf(tmparray[0]))
				{
					max = Double.valueOf(tmparray[0]);
				}
				if(max < Double.valueOf(tmparray[1]))
				{
					max = Double.valueOf(tmparray[1]);
				}
				i++;
			}
			
			//*****計算放大率*****//
			Drawpanel.rate = View.show.getHeight()/max;
			
			
		}catch(Exception ex){
			Logger.getLogger(Loadfile.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
