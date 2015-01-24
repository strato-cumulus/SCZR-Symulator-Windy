package com.sczr.symulator_windy.ui;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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
		this.update(o);
	}
	
	void update(Object o) {};
	//void update(ElevatorCoordinatesPacket packet)
	//{
	//	elevator.update(packet.verticalPosition, packet.doorWidth);
	//}
}