package controlmodule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.ElevatorStill;
import model.elevator.state.State;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.controllerpackets.ChangeDestinationFloorPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.serialization.SerializationList;

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
	
	
	public void runControler(){

	}
	
	private ChangeDestinationFloorPacket handleElevator(ElevatorStatePacket packet)
	{
		int nextFloorToStop = -1;
		State elevatorState = packet.getElevatorState();
		ArrayList<Integer> upButtons = packet.getUpButtons();
		ArrayList<Integer> downButtons = packet.getDownButtons();
		List<Integer> passengersDestinations = packet.getDestinations();
		int currentFloor = packet.getCurrentFloor();
		
		if(downButtons.size()+passengersDestinations.size()==0)
			return new ChangeDestinationFloorPacket(currentFloor);
		
		if(elevatorState instanceof ElevatorStill)
		{
	
		}
		else if(elevatorState instanceof ElevatorGoingDown){
			int currentMaximum =-1;
			for (Integer integer : downButtons) {
				if(integer>currentMaximum && integer <= currentFloor){
					currentMaximum = integer;
				}
			}
			for (Integer integer : passengersDestinations) {
				if(integer>currentMaximum && integer <= currentFloor){
					currentMaximum = integer;
				}
			}
			nextFloorToStop = currentMaximum;
		}
		else if(elevatorState instanceof ElevatorGoingUp){
			int currentMinimum = Integer.MAX_VALUE;
			for (Integer integer : upButtons) {
				if(integer<currentMinimum && integer > currentFloor){
					currentMinimum = integer;
				}
			}
			for (Integer integer : passengersDestinations) {
				if(integer<currentMinimum && integer > currentFloor){
					currentMinimum = integer;
				}
			}
			nextFloorToStop = currentMinimum;
		}
		
		return new ChangeDestinationFloorPacket(nextFloorToStop);
	}
}
