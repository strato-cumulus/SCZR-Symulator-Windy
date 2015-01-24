package com.sczr.symulator_windy.ui;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.GUIpackets.ElevatorStateInfoPacket;

class PositionUpdateListener extends Listener
{
	ElevatorCar elevator;
	
	PositionUpdateListener(ElevatorCar e)
	{
		this.elevator = e;
	}
	
	@Override
	public void received(Connection c, Object o)
	{
		if(o instanceof ElevatorStateInfoPacket){
			System.out.println("asd");
			this.update((ElevatorStateInfoPacket) o);
		}
	}
	
	void update(ElevatorStateInfoPacket packet)
	{
		elevator.update(packet.verticalPosition, packet.doorWidth);
	}
}
