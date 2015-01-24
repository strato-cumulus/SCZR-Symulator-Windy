package passengersmodule;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.serialization.SerializationList;

public class PassengerClient {

	protected final Client client;
	
	public PassengerClient(int tcpPort, String ipAddress )
	{	
		this.client = new Client();
		this.client.start();
		try
		{
		this.client.connect(5000, ipAddress, tcpPort);
		}
		catch(IOException e)
		{
			System.out.println("Nie nawizano po³aczenia");
		}
		SerializationList.register(this.client.getKryo());
	}
	
	void startPassengerClient()
	{
		
	
		client.addListener(new Listener()
		{
		    public void received(Connection connection, Object object)
		    {
		        if(object instanceof ElevatorStatePacket)
		        {
		           
		        } 
		    }
		});
	}
}
