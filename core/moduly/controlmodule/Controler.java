package controlmodule;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.ElevatorStill;
import model.elevator.state.State;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.controllerpackets.FloorsStopElevatorPacket;
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
	        @Override
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
		int currentFloor = packet.getCurrentFloor();
		
		int prevdestination;
		int destination;
		List<Integer> stops = new LinkedList<Integer>();
		
		if(elevatorState instanceof ElevatorStill)
		{
			if(currentFloor != 0)
			{
				for(int i = 0; i<10; i++)
				{
					if(upButtons[i] == 1)
					{
						destination = i;
					}
				}
			}
			if(currentFloor == 0)
			{
				
			}
				
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
