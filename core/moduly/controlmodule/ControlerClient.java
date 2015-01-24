package controlmodule;

import java.io.IOException;

import javax.tools.Diagnostic;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket_;
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
		try 
		{
			this.client.connect(5000, IPAdress, tcpPort);
		} 
		catch (IOException e) 
		{
			System.err.println("Nie uda�o sie po�aczyc");
		}
		SerializationList.register(this.client.getKryo());
	}
	
	public void runControler()
	{
	    client.addListener(new Listener() 
	    {
	        public void received (Connection connection, Object object) 
	        {
	        	if (object instanceof ElevatorStatePacket) 
	        	{
	        		DispatchElevatorPacket_ packet = new DispatchElevatorPacket_(1, 1);
	        		connection.sendTCP(packet);
	        	}
	        } 
	     });
	}
}
