package controlmodule;

import java.io.IOException;

import javax.tools.Diagnostic;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.serialization.SerializationList;

public class ControlerClient 
{
	
	private final Client client;
	
	public ControlerClient(int tcpPort, String IPAdress)
	{
		this.client = new Client();
		this.client.start();
		SerializationList.register(this.client.getKryo());
		try 
		{
			this.client.connect(5000, IPAdress, tcpPort);
		} 
		catch (IOException e) 
		{
			System.err.println("Nie uda³o sie po³aczyc");
		}
	}
	
	public void runControler()
	{
	    client.addListener(new Listener() 
	    {
	        public void received (Connection connection, Object object) 
	        {
	        	if (object instanceof ElevatorStatePacket) 
	        	{
	        		DispatchElevatorPacket packet = new DispatchElevatorPacket(1, 1);
	        		connection.sendTCP(packet);
	        	}
	        } 
	     });
	}
}
