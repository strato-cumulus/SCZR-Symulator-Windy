package passengersmodule;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
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
		Passenger newPassenger;
		int LAST_ID = 0;
		while(true)
		{
			RandomCreatingOfPassengers create = new RandomCreatingOfPassengers();
			//Passenger newPassenger = RandomCreatingOfPassengers.createRandomPassenger(LAST_ID);
			NewPassengerPacket passengerPacket = new NewPassengerPacket(newPassenger.ID, newPassenger.destination, newPassenger.floor);
			client.sendTCP(passengerPacket);
			TimeUnit.SECONDS.sleep(2);
			//delete newPassenger;
			
		}
	}
}
