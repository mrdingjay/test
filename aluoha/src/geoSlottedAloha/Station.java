package geoSlottedAloha;

import java.util.LinkedList;
import java.util.Queue;


public class Station {
	
	private int identifier;
	private boolean isSend = false;
	private int phase = 0;
	
	private Queue<Packet> buffer = new LinkedList<Packet>();
	
	public Station(int identifier){
		this.identifier = identifier;
		for (int i = 0; i < 20; i++)
		{
			Packet tmp = new Packet();
			tmp.setArriveTime(0);
			buffer.add(tmp);
		}
	}

	public void addPacket(){
		double r = Math.random();
		if (r < Channel.aggInputRate / (double)Channel.STATIONNUMBER)
		{
			Packet tmp = new Packet();
			tmp.setArriveTime(Channel.systemTime);
			buffer.add(tmp);
		}
		
		r = Math.random();
		if (buffer.size() > 0 && r < Math.pow(Channel.q, phase))
		{						
				isSend = true;
				Channel.NumberToSend++;
			
		}
		else
			isSend = false;
		
	}
	
	public void DepartorBack(){
		if (isSend == true){			
			Channel.totalAttempt++;
			if (Channel.NumberToSend == 1)
			{				
				removePacket();
		//		reserveChannel();
				phase = 0;
			}
			else
				phase = 1;
		}
	}
	
	public void removePacket(){
		Packet tmp = buffer.remove();
		tmp.setDepartTime(Channel.systemTime);
		Channel.totalDelay +=tmp.getDepartTime()-tmp.getArriveTime();
		Channel.totalSuccess++;
	}
	
	/*public void reserveChannel(){
		if (buffer.size()>0)
		{
			Channel.reserveNode = identifier;
			Channel.reserveNumber++;
		}
			
		else
		{
			Channel.reserveNode = -1;
			Channel.reserveNumber = 0;
		}
		
	}
	*/

}
