package com.sczr.symulator_windy.packets.passengerpackets;

import com.sczr.symulator_windy.packets.Packet;

public class NewPassengerPacket extends Packet {
	public final int ID;
	public final int destination;
	public final int floor;
	
	public NewPassengerPacket()
	{
		this.ID = -1;
		this.destination = -1;
		this.floor = -1;
	}
	
	
	public NewPassengerPacket(int ID, int destination, int floor)
	{
		this.ID = ID;
		this.destination = destination;
		this.floor = floor;
	}
}
