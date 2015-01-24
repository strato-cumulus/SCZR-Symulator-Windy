package com.sczr.symulator_windy.packets.GUIpackets;

import com.sczr.symulator_windy.packets.Packet;


public class ElevatorStateInfoPacket extends Packet
{

	public final Float verticalPosition;
	public final Float doorWidth;
	
	
	
	public ElevatorStateInfoPacket(Float doorWidth, Float verticalPosition)
	{
		this.verticalPosition = verticalPosition;
		this.doorWidth = doorWidth;
	}
}
