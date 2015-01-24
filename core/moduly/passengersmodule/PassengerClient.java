package passengersmodule;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.Model;

import com.esotericsoftware.kryonet.Client;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.serialization.SerializationList;

public class PassengerClient {

	protected final Client client;
	private final Random rand = new Random();
	private int lastId;
	public final int GENERATION_DELAY = 15000;
	
	public PassengerClient(int tcpPort, String ipAddress )
	{	
		this.client = new Client();
		this.client.start();
		SerializationList.register(client.getKryo());
		try
		{
			this.client.connect(5000, ipAddress, tcpPort);
	        Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {			
				@Override
				public void run() {
					
					client.sendTCP(generatePassanger());
				}
			}, 0, GENERATION_DELAY);
		}
		catch(IOException e)
		{
			System.out.println("Nie nawizano po³aczenia");
			System.exit(1);
		}
		
	}
	
	
	private NewPassengerPacket generatePassanger(){
		final int id = ++lastId;
		int floor = rand.nextInt(Model.NUMBER_OF_FLOORS);
		
		int destination;
		do{
			destination = rand.nextInt(Model.NUMBER_OF_FLOORS);
		}while(floor == destination);
		System.out.println("wygenrowano nowego pasazera id: "+id+" cel: "+destination + " pietro: "+floor);
		return new NewPassengerPacket(id, destination, floor);
		
	}
}
