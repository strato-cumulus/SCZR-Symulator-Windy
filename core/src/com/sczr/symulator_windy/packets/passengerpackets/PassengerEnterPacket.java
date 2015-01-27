package com.sczr.symulator_windy.packets.passengerpackets;

import com.sczr.symulator_windy.packets.Packet;

public class PassengerEnterPacket extends Packet
{
	public final int id;
	public final int floor;
	public final int destination;
	
	public PassengerEnterPacket()
	{
		this.id = -1;
		this.floor = -1;
		this.destination = -1;
	}
	
	public PassengerEnterPacket(int id, int floor, int destination)
	{
		this.id = id;
		this.floor = floor;
		this.destination = destination;
	}
}
