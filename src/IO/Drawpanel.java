package IO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Interface.View;
import Program.ACO;
import Program.Main;

public class Drawpanel {
			//*****宣告放大率*****//
			public static double rate = 0;
			
			//*****宣告畫布*****//
			static Graphics graphics;
			
			public static void drawpanel()
			{
				//*****JPanel轉成畫布*****//
				graphics = View.show.getGraphics();
						
				//*****設定點顏色*****//
				graphics.setColor(Color.white);
										
				//*****清空畫布*****//
				graphics.fillRect(0, 0, View.show.getWidth(), View.show.getHeight());
						
				//*****設定點顏色*****//
				graphics.setColor(Color.black);
						
				//*****打點*****//
				for(int i = 0; i < Main.pointdata.length; i++)
				{
					int x = (int)((Main.pointdata[i].x)*rate);
					int y = (int)((Main.pointdata[i].y)*rate);
					graphics.fillRect(x, y, 3, 3);
				}
			}
			
			//*****畫線*****//
			public static void drawline(int A,int B)
			{
				int x1 = (int)((Main.pointdata[A].x)*rate);
				int y1 = (int)((Main.pointdata[A].y)*rate);
				int x2 = (int)((Main.pointdata[B].x)*rate);
				int y2 = (int)((Main.pointdata[B].y)*rate);
				
				//*****畫布轉為2D*****//
				Graphics2D graphics2d = (Graphics2D)graphics;
				
				//*****設定顏色*****//
				graphics2d.setColor(Color.RED);
				
				//*****設定粗細*****//
				graphics2d.setStroke(new BasicStroke(1.0f));
				
				//*****畫線*****//
			    graphics2d.drawLine(x1, y1, x2, y2);
			}
}
