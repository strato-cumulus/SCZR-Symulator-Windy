package model;

import java.io.IOException;

<<<<<<< HEAD
import model.elevator.ElevatorCar;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Model{

 private final Server server;
 
 public static final int STOREY_NUM = 5;	//parter to pietro zero; liczba 5 oznacza ze jest parter i 4 pietra
 private final ElevatorCar elevatorCar = new ElevatorCar(10, 90);
 private final Floor[] floors = new Floor[STOREY_NUM];

 
 
	
	public Model(int tcpPort) throws IOException{
		
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		
		this.server.addListener(new Listener(){
			@Override
			public void received (Connection connection, Object object) {
			}
		});
		
	}
	
	
	public static void main(String[] args) throws IOException{
		new Model(1234);
	}

	
=======
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;

public class Model{

 private final Server server;
	
	public Model(int tcpPort) throws IOException{
		
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		
		this.server.addListener(new Listener() {
			
			@Override
			public void received(Connection c, Object o) {
				if(o instanceof ElevatorCallPacket) {
					System.out.println("ElevatorCallPacket received");
					ElevatorCallPacket p = (ElevatorCallPacket)o;
				}
			}
		});
	}
	
	public static void main(String[] args){
		System.out.println("model");
		while(true) System.out.println("model");
	}

}
