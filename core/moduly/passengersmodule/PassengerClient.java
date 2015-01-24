package passengersmodule;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.esotericsoftware.kryonet.Client;
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
			newPassenger = create.createRandomPassenger(LAST_ID);
			NewPassengerPacket passengerPacket = new NewPassengerPacket(newPassenger.getID(), newPassenger.getDestination(), newPassenger.getFloor());
			LAST_ID = newPassenger.getID();
			client.sendTCP(passengerPacket);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
