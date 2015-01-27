package controlmodule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		int nextFloorToStop = -10;
		State currentElevatorState = packet.getCurrentElevatorState();
		State prevElevatorState = packet.getPrevElevatorState();
		ArrayList<Integer> upButtons = packet.getUpButtons();
		ArrayList<Integer> downButtons = packet.getDownButtons();
		List<Integer> passengersDestinations = packet.getDestinations();
		int currentFloor = packet.getCurrentFloor();
		
		if(downButtons.size()+upButtons.size()+passengersDestinations.size()==0)
			return new ChangeDestinationFloorPacket(currentFloor);
		
		
		/*
		if(currentElevatorState instanceof ElevatorStill){
			if(currentFloor == Model.NUMBER_OF_FLOORS - 1){
				nextFloorToStop = Model.NUMBER_OF_FLOORS-2;
			}
			else if(currentFloor== 0){
				nextFloorToStop = 1;
				elevatorState = new ElevatorGoingUp();
				return new ElevatorGoingUp();
			}
			else{
				if(previousElevatorState instanceof ElevatorGoingUp){
					setDestinationFloor(getDestinationFloor()+1);
				}
				else if(previousElevatorState instanceof ElevatorGoingDown){
					setDestinationFloor(getDestinationFloor()-1);
				}
				elevatorState = previousElevatorState;
				return elevatorState;
			}
		
		}*/
		int maxOfStoreysBelowFloor =-1;
		int minOfStoreysAboveCar = Integer.MAX_VALUE;
		
		for (Integer integer : downButtons) {
			if(integer>maxOfStoreysBelowFloor && integer <= currentFloor){
				maxOfStoreysBelowFloor = integer;
			}
		}
		for (Integer integer : passengersDestinations) {
			if(integer>maxOfStoreysBelowFloor && integer <= currentFloor){
				maxOfStoreysBelowFloor = integer;
			}
		}
			
		for (Integer integer : upButtons) {
			if(integer<minOfStoreysAboveCar && integer > currentFloor){
				minOfStoreysAboveCar = integer;
			}
		}
		for (Integer integer : passengersDestinations) {
			if(integer<minOfStoreysAboveCar && integer > currentFloor){
				minOfStoreysAboveCar = integer;
			}
		}
			
		if(prevElevatorState instanceof ElevatorGoingUp){
			nextFloorToStop = minOfStoreysAboveCar;
		}
		else if(prevElevatorState instanceof ElevatorGoingDown){
			nextFloorToStop = maxOfStoreysBelowFloor;
		}
		else{
			try {
				throw new Exception("kod mial tu nie dojsc");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(nextFloorToStop == -1 || nextFloorToStop == Integer.MAX_VALUE){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("dest:" + nextFloorToStop);
		return new ChangeDestinationFloorPacket(nextFloorToStop);
	}
}
