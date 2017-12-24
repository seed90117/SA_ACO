package Value;

public class parameter {
	public static int antcount = 10;//螞蟻數量
	public static double pheromone = 0.00001;//初始線段費洛蒙
	public static int iteration = 100;//演算法代數
	public static int ACOiteration = 100;//螞蟻演算法代數
	public static int SAiteration = 100;//模擬退火演算法代數
	public static double q = 0.9;//決定轉換規則/機率參數
	public static double p = 0.1;//費洛蒙衰退參數
	public static double A = 1;
	public static double B = 2;
	public static double Q = 0.5;
	public static double starttemperature = 100;//起始溫度
	public static double endtemperature = 10;//目標溫度
	public static double a = 0.95;//降溫參數
}
