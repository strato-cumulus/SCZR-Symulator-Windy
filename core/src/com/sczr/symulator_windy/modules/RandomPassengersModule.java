package com.sczr.symulator_windy.modules;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;

public class RandomPassengersModule extends Module
{
	
	public RandomPassengersModule(int tcpPort) throws IOException
	{
		super(tcpPort);
		this.server.addListener(new Listener() {
			public void received(Connection c, Object o) {
				System.out.println("Object of class " + o.getClass().getSimpleName() + " received.");
			}
		});
	}
}
