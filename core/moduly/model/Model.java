package model;

import java.io.IOException;

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
