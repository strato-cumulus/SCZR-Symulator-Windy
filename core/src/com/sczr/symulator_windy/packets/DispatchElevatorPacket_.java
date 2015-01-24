package com.sczr.symulator_windy.packets;

public class DispatchElevatorPacket_ extends Packet
{
	public final int callFloor;
	public final int destinationFloor;
	
	public DispatchElevatorPacket_()
	{
		this.callFloor = -1;
		this.destinationFloor = -1;
	}
	
	
	public DispatchElevatorPacket_(int callFloor, int destinationFloor)
	{
		this.callFloor = callFloor;
		this.destinationFloor = destinationFloor;
	}
}
