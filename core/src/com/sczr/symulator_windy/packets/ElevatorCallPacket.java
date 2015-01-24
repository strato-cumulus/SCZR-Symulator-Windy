package com.sczr.symulator_windy.packets;


public class ElevatorCallPacket extends Packet
{
	public final Integer floor;
	public final Integer destination;
	
	public ElevatorCallPacket() 
	{
		this.floor = null;
		this.destination = null;
	}
	
	public ElevatorCallPacket(int floor, int destination)
	{
		this.floor = floor;
		this.destination = destination;
	}
}
