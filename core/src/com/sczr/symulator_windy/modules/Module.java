package com.sczr.symulator_windy.modules;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.serialization.SerializationList;

abstract public class Module 
{
	public static int CONTROLLER_TCP = -1;
	
	public final int tcpPort;
	
	protected final Server server;
	protected final Client client;
	
	public Module(int tcpPort) throws IOException
	{
		if(CONTROLLER_TCP == -1) {
			throw new IOException("Controller instance should be created before modules");
		}
		this.tcpPort = tcpPort;
		
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		SerializationList.register(this.server.getKryo());
		
		this.client = new Client();
		this.client.start();
		this.client.connect(5000, "127.0.0.1", CONTROLLER_TCP);
		SerializationList.register(this.client.getKryo());
	}
}
