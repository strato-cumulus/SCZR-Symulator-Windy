package controlmodule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Floor;
import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.State;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.controllerpackets.ChangeDestinationFloorPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.serialization.SerializationList;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Controler 
{
	private final Client client;
	
	public Controler(int tcpPort, String IPAdress)
	{
		this.client = new Client();
		this.client.start();
	    client.addListener(new Listener() 
	    {
	    	@Override
	    	public void connected(Connection c){
	    		c.sendTCP(new ControllerRegisterPacket());
	    		c.sendTCP(new ChangeDestinationFloorPacket(0));
	    	}
	    	
	        @Override
			public void received (Connection connection, Object object) 
	        {
	        	if (object instanceof ElevatorStatePacket) 
	        	{
	        		ChangeDestinationFloorPacket packet = handleElevator((ElevatorStatePacket)object);
	        		connection.sendTCP(packet);
	        	}
	        } 
	     });
		
		SerializationList.register(this.client.getKryo());
		try 
		{
			this.client.connect(5000, IPAdress, tcpPort);
		} 
		catch (IOException e) 
		{
			System.err.println("Nie uda³o sie po³aczyc");
			System.exit(1);
		}
	}
	
	
	public void runControler()
	{

	}
	
	private ChangeDestinationFloorPacket handleElevator(ElevatorStatePacket packet)
	{
		int nextFloorToStop = -10;
		State currentElevatorState = packet.getCurrentElevatorState();
		State prevElevatorState = packet.getPrevElevatorState();
		ArrayList<Integer> floorsCalls = packet.getUpButtons();
		List<Integer> passengersDestinations = packet.getDestinations();
		int currentFloor = packet.getCurrentFloor();
		ArrayList<Integer> allCalls = new ArrayList<Integer>();
		
		if(floorsCalls.size()+passengersDestinations.size()==0)
			return new ChangeDestinationFloorPacket(currentFloor);
		
		
		for (Integer integer : passengersDestinations) {
			allCalls.add(integer);
		}
		
		for (Integer integer : floorsCalls) {
			allCalls.add(integer);
		}
		

		int maxLess = -1;
		for(int floor: allCalls) {
			if(floor < packet.getCurrentFloor() && floor > maxLess) {
				maxLess = floor;
			}
		}
		
		int minBigger = 10;
		for(int floor: allCalls) {
			if(floor > packet.getCurrentFloor() && floor < minBigger) {
				minBigger = floor;
			}
		}
		
		
		if(prevElevatorState instanceof ElevatorGoingUp)
		{
			if(minBigger >= 10)
				nextFloorToStop = maxLess;
			else 
				nextFloorToStop = minBigger;
		}
		else if(prevElevatorState instanceof ElevatorGoingDown){
			
			if(maxLess <= -1)
				nextFloorToStop = minBigger;
			else 
				nextFloorToStop = maxLess;
		}

		return new ChangeDestinationFloorPacket(nextFloorToStop);
	}

}
