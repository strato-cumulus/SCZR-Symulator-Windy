package model;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class Model{

 private final Server server;
	
	public Model(int tcpPort) throws IOException{
		
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		
		this.server.addListener(new Listener() {
			
			@Override
			public void received(Connection c, Object o) {

			}
		});
	}
	
	public static void main(String[] args){
		System.out.println("model");
		while(true) System.out.println("model");
	}

}
