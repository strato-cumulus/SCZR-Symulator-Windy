package com.sczr.symulator_windy.packets;

public class ElevatorCoordinatesPacket
{
	public final Float verticalPosition;
	public final Float doorWidth;
	
	public ElevatorCoordinatesPacket()
	{
		this.verticalPosition = null;
		this.doorWidth = null;
	}
	
	public ElevatorCoordinatesPacket(float verticalPosition, float doorWidth)
	{
		this.verticalPosition = verticalPosition;
		this.doorWidth = doorWidth;
	}
}
