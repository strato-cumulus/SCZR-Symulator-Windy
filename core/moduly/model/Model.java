package model;

import java.io.IOException;

import model.elevator.ElevatorCar;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.serialization.SerializationList;



public class Model{
	public static final int NUMBER_OF_FLOORS = 10;
	public static final int FLOOR_HEIGHT = 90;

	private final Server server;
	private final ElevatorCar elevatorCar = new ElevatorCar(NUMBER_OF_FLOORS, FLOOR_HEIGHT);
	private Floor[] floors = new Floor[NUMBER_OF_FLOORS];
	
	
	public Model(int tcpPort) throws IOException{
		
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		this.server.addListener(new Listener() {
			
			@Override
			public void received(Connection c, Object o) {
				//modul pasazerow
				if(o instanceof NewPassengerPacket){
					NewPassengerPacket p = (NewPassengerPacket)o;
					floors[p.floor].addWaitingPassenger(new Passenger(p.ID, p.destination));
				}				
				//modul sterowania
				
				//modul gui
				//else if(o instanceof ElevatorCa)

			}
		});
		
		SerializationList.register(server.getKryo());
	}
	
	public static void main(String[] args){
		System.out.println("model");
		while(true) System.out.println("model");
	}

}
