package Interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import IO.Drawpanel;
import IO.Loadfile;
import IO.Savefile;
import Program.ACO;
import Program.Main;
import Value.parameter;



public class View extends JFrame {

	public static boolean checkloading = false;
	public static double  totaldistance = 0;//執行次數總和
	public static double  totaltime = 0;//執行時間總和
	public static int runtime = 0;
	public static int runcount;
	public static int[] bestpath;
	public static double bestdistance;
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告介面*****//
	JLabel antamount = new JLabel("Ant Amount:"); //螞蟻數量
	JLabel iteration = new JLabel("Time:");//執行時間
	JLabel computerrun = new JLabel("執行次數：");//Computer Run
	JLabel starttemperature = new JLabel("起始溫度：");//起始溫度
	JLabel a = new JLabel("α：");//降溫係數
	JLabel avgcost = new JLabel("平均距離：");//平均距離
	JLabel avgtime = new JLabel("平均時間：");//平均時間
	public static JLabel time_output = new JLabel("花費時間：");
	public static JLabel cost_output = new JLabel("最佳距離：");
	public static JTextField antamount_input = new JTextField("10");
	JTextField iteration_input = new JTextField("100");
	JTextField starttemperature_input = new JTextField("100");
	JTextField a_input = new JTextField("0.95");
	JTextField computerrun_input = new JTextField("100");
	JButton loadfile = new JButton("Load File");
	JButton drawpanel = new JButton("Draw Panel");
	JButton start = new JButton("Starts");
	JButton savebest = new JButton("Save Best Data");
	public static JPanel show = new JPanel();
	public static JFileChooser open = new JFileChooser();
	public static JCheckBox iscomputerrun = new JCheckBox("Computer Run");
	
	
	View()
	{
		//*****設定介面*****//
		this.setSize(1000, 800);
		this.setLayout(null);
		this.setTitle("SA_ACO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//*****設定物件位置*****//
		antamount.setBounds(730, 30, 100, 30);
		antamount_input.setBounds(810, 30, 150, 30);
		iteration.setBounds(750, 70, 100, 30);
		iteration_input.setBounds(810, 70, 150, 30);
		starttemperature.setBounds(750, 110, 150, 30);
		starttemperature_input.setBounds(810, 110, 150, 30);
		a.setBounds(760, 150, 150, 30);
		a_input.setBounds(810, 150, 150, 30);
		loadfile.setBounds(750, 190, 200, 30);
		drawpanel.setBounds(750, 230, 200, 30);
		start.setBounds(750, 270, 200, 30);
		show.setBounds(20, 20, 700, 700);
		time_output.setBounds(750, 310, 250, 30);
		cost_output.setBounds(750, 350, 250, 30);
		iscomputerrun.setBounds(740, 390, 200, 30);
		computerrun.setBounds(750, 430, 100, 30);
		computerrun_input.setBounds(810, 430, 100, 30);
		avgcost.setBounds(750, 470, 250, 30);
		avgtime.setBounds(750, 510, 250, 30);
		savebest.setBounds(750, 550, 200, 30);
		
		
		//*****設定JPanel框線*****//
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		//*****物件放入介面*****//
		cp.add(antamount);
		cp.add(antamount_input);
		cp.add(iteration);
		cp.add(iteration_input);
		cp.add(loadfile);
		cp.add(drawpanel);
		cp.add(start);
		cp.add(show);
		cp.add(time_output);
		cp.add(cost_output);
		cp.add(computerrun);
		cp.add(computerrun_input);
		cp.add(avgcost);
		cp.add(iscomputerrun);
		cp.add(avgtime);
		cp.add(starttemperature);
		cp.add(starttemperature_input);
		cp.add(a);
		cp.add(a_input);
		cp.add(savebest);
		
		//*****讀取檔案按鈕事件*****//
		loadfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Loadfile.loadfile();
			}
		});
		
		//*****畫點按鈕事件*****//
		drawpanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkloading)
				{
					Drawpanel.drawpanel();
				}
			}
		});
		
		//*****執行演算法按鈕事件*****//
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkloading)
				{
					parameter.antcount = Integer.parseInt(antamount_input.getText());
					parameter.iteration = Integer.parseInt(iteration_input.getText());
					parameter.starttemperature = Double.parseDouble(starttemperature_input.getText());
					parameter.a = Double.parseDouble(a_input.getText());
					if(iscomputerrun.isSelected())
					{
						Drawpanel.drawpanel();
						bestdistance = 0;
						totaldistance = 0;
						totaltime = 0;
						runcount = 1;
						bestpath = new int[Main.pointdata.length+1];
						runtime = Integer.parseInt(computerrun_input.getText());
						for(int t=0;t<runtime;t++)
						{
							System.out.println("t:"+t);
							Main.main();
							totaltime += Main.costtime;
							totaldistance += Main.distance;
							runcount++;
							System.out.println(bestdistance);
						}
						cost_output.setText("花費距離："+bestdistance);
						avgcost.setText("平均距離："+String.valueOf(totaldistance/(double)runtime));
						avgtime.setText("平均時間："+String.valueOf(totaltime/(double)runtime));
					}
					else
					{
						Drawpanel.drawpanel();
						Main.main();
						System.out.println("View.bestdistance:" + bestdistance);
					}
				}
			}
		});
		
		//*****存檔按鈕事件*****//
		savebest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(bestdistance != 0 && bestpath != null)
				{
					Savefile.savefile();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new View();
	}
		

}
