package controlmodule;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.Floor;
import model.state.ElevatorGoingDown;
import model.state.ElevatorGoingUp;
import model.state.ElevatorStill;
import model.state.State;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.FloorsStopElevatorPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.serialization.SerializationList;

public class Controler 
{
	
	private final Client client;
	
	public Controler(int tcpPort, String IPAdress)
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
	        		FloorsStopElevatorPacket packet = handleElevator((ElevatorStatePacket)object);
	        		connection.sendTCP(packet);
	        	}
	        } 
	     });
	}
	
	private FloorsStopElevatorPacket handleElevator(ElevatorStatePacket packet)
	{
		State elevatorState = packet.getElevatorState();
		int[] upButtons = packet.getUpButtons();
		int[] downButtons = packet.getDownButtons();
		List<Integer> passengersDestinations = packet.getDestinations();
		
		List<Integer> stops = new LinkedList<Integer>();
		if(elevatorState instanceof ElevatorStill)
		{
			
		}
		if(elevatorState instanceof ElevatorGoingDown)
		{
			
		}
		if(elevatorState instanceof ElevatorGoingUp)
		{
			
		}
		return new FloorsStopElevatorPacket(1, stops);
	}
}
