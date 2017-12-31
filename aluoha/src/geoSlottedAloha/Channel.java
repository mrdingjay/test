package geoSlottedAloha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Channel {
	public static final int STATIONNUMBER = 50;
	public static final int SLOTNUMBER = 1000000;
	private static final long mod = (long) (Math.pow(2,35)-31);
	//public static final int L = 1; //连续预定信道的最大时隙数
	
	
	public static double q; //retransmission factor
	public static double aggInputRate = 0.8;
	public static int systemTime;
	public static int NumberToSend; //一个时隙中要发包的站点数
	//public static int reserveNode = -1; //预定信道的节点编号
	//public static int reserveNumber = 0; //连续预定信道的时隙数
	
	public static long seed;
	public static int totalAttempt;
	public static int totalSuccess;
	public static long totalDelay;
	public static double throughput;
	public static double totalAttRate;
	public static double aveDelay;
	
	public static Station[] Node = new Station[STATIONNUMBER];	
	public static PrintWriter out1,out2,out3;
	
	public static void main (String[] args){
		try {
			out1 = new PrintWriter(new File("Throughput.txt"));
			out2 = new PrintWriter(new File("AttemptRate.txt"));
			out3 = new PrintWriter(new File("Delay.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		seed = System.currentTimeMillis()%mod; 
		for (q = 0.01; q < 0.3; q +=0.01)
		{
			
			totalAttempt = 0;
			totalSuccess = 0;
			totalDelay = 0;
			for (int i = 0; i < STATIONNUMBER; i++)
			{
				Node[i] = new Station(i);							
			}
			
			for (int t = 1; t <= SLOTNUMBER; t++ )
			{
				NumberToSend = 0;
				systemTime = t;
				for (int i = 0; i < STATIONNUMBER; i++)
				{
					Node[i].addPacket();
				
				}
				/*
				if (reserveNode >= 0)
				{
					Node[reserveNode].removePacket();				
					totalAttempt++;
					
					if (reserveNumber < L )
					{
						Node[reserveNode].reserveChannel();
					}
					else
					{
						reserveNode = -1;
						reserveNumber = 0;
					}
					continue;
				}
				*/
				
					
				for (int i = 0; i < STATIONNUMBER; i++)
				{
					Node[i].DepartorBack();
					
				}
			}
			totalAttRate = 1.0*totalAttempt / SLOTNUMBER;
			throughput = 1.0*totalSuccess / SLOTNUMBER;
			aveDelay = 1.0*totalDelay / totalSuccess;
			System.out.println(throughput);
	//		System.out.println(aveDelay);
			out1.println(throughput);
			out2.println(totalAttRate);
			out3.println(aveDelay);
			
		}
						
		out1.close();
		out2.close();
		out3.close();
	}

}
