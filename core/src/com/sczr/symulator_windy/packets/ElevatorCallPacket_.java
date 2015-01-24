package com.sczr.symulator_windy.packets;


public class ElevatorCallPacket_ extends Packet
{
	public final Integer floor;
	public final Integer destination;
	
	public ElevatorCallPacket_() 
	{
		this.floor = null;
		this.destination = null;
	}
	
	public ElevatorCallPacket_(int floor, int destination)
	{
		this.floor = floor;
		this.destination = destination;
	}
}
