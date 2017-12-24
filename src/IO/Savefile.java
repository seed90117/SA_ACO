package IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import Interface.View;
import Program.Main;

public class Savefile {

	static BufferedWriter bw;
	static File file;
	
	public static void savefile()
	{
		try {
			file = new File("//Users//kevin//Documents//Algorithm_Data//Ant Algorithm_Data//EIL51_best.txt");
			//true續寫，false覆寫
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8")); // 指點編碼格式，以免讀取時中文字符異常
			bw.append(View.bestpath.length-1 + " 2");
			bw.newLine();
			for(int i=0;i<View.bestpath.length-1;i++)
			{
				bw.append((int)Main.pointdata[View.bestpath[i]].x + " " + (int)Main.pointdata[View.bestpath[i]].y);
				bw.newLine();
			}
			bw.append(String.valueOf(View.bestdistance));
			bw.flush();// 全部寫入緩存中的內容
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
