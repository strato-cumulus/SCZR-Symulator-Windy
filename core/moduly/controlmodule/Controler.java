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
	
	
	public void runControler()
	{

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
		ArrayList<Integer> allCalls = new ArrayList<Integer>();
		
		if(upButtons.size()+passengersDestinations.size()==0)
			return new ChangeDestinationFloorPacket(0);
		


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
		
		
		//int maxOfStoreysBelowFloor =-10;
		//int minOfStoreysAboveCar = Integer.MAX_VALUE;
		
		//allCalls.addAll(passengersDestinations
		//allCalls.addAll(upButtons);
		//allCalls.addAll(downButtons);
		
		for (Integer integer : passengersDestinations) {
			allCalls.add(integer);
		}
		
		for (Integer integer : upButtons) {
			allCalls.add(integer);
		}
		

		int maxLess = -1;
		for(int floor: allCalls) {
			if(floor <= packet.getCurrentFloor() && floor >= maxLess) {
				maxLess = floor;
			}
		}
		
		int minBigger = 10;
		for(int floor: allCalls) {
			if(floor >= packet.getCurrentFloor() && floor <= minBigger) {
				minBigger = floor;
			}
		}
		
		/*for (Integer integer : downButtons) {
			allCalls.add(integer);
		}*/
		
		if(allCalls.size() == 0)
			System.out.println("dupa");
		
		
		
		
		
		
		
		
		
		
		
		
		
		//System.out.println(currentFloor);
/*
		for (Integer integer : allCalls) {
			if(integer>maxOfStoreysBelowFloor && integer <= currentFloor){
				maxOfStoreysBelowFloor = integer;
			}
		}
			

		for (Integer integer : allCalls) {
			if(integer<minOfStoreysAboveCar && integer > currentFloor){
				minOfStoreysAboveCar = integer;
			}
		}
			*/
		if(prevElevatorState instanceof ElevatorGoingUp){
			nextFloorToStop = minBigger/*minOfStoreysAboveCar*/;
		}
		else if(prevElevatorState instanceof ElevatorGoingDown){
			nextFloorToStop = maxLess/*maxOfStoreysBelowFloor*/;
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
				//throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*for (Integer integer : allCalls) {
			System.out.println(integer);
		}*/
		//System.out.println("dest:" + nextFloorToStop);
	//	System.out.println("AllCalls: " +allCalls.size());
	//	System.out.println("Dest: " + nextFloorToStop);
		
		if(nextFloorToStop <= -1) 
		{
			nextFloorToStop = minBigger;
		}
		else if(nextFloorToStop >= 10)
		{
			nextFloorToStop = maxLess;
		}
		return new ChangeDestinationFloorPacket(nextFloorToStop);
	}
}
