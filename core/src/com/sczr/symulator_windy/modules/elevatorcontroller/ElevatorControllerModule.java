package com.sczr.symulator_windy.modules.elevatorcontroller;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.modules.Module;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;

public class ElevatorControllerModule extends Module
{	
	public ElevatorControllerModule(int tcpPort) throws IOException
	{
		super(tcpPort);
		this.server.addListener(new Listener() {
			@Override
			public void received(Connection c, Object o) {
				if(o instanceof ElevatorCallPacket) {
					System.out.println("ElevatorCallPacket received");
					ElevatorCallPacket p = (ElevatorCallPacket)o;
					sendPacket(new DispatchElevatorPacket(p.floor, p.destination));
				}
			}
		});
	}
	
	void sendPacket(Packet outputPacket)
	{
		this.client.sendTCP(outputPacket);
	}
}
