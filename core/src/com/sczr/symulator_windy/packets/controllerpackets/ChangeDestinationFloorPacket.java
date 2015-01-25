package com.sczr.symulator_windy.packets.controllerpackets;




public class ChangeDestinationFloorPacket 
{
	public ChangeDestinationFloorPacket() {

	}

	private int destination;
	
	public ChangeDestinationFloorPacket(final int destination)
	{
		this.destination = destination;
	}
	
	public int getDestination()
	{
		return destination;
	}

}
