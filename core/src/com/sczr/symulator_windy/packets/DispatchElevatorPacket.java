package com.sczr.symulator_windy.packets;

public class DispatchElevatorPacket extends Packet
{
	public final int callFloor;
	public final int destinationFloor;
	
	public DispatchElevatorPacket()
	{
		this.callFloor = -1;
		this.destinationFloor = -1;
	}
	
	
	public DispatchElevatorPacket(int callFloor, int destinationFloor)
	{
		this.callFloor = callFloor;
		this.destinationFloor = destinationFloor;
	}
}
