package com.sczr.symulator_windy.packets.controllerpackets;

import java.util.List;



public class ChangeDestinationFloorPacket 
{
	private final int destination;
	//private final List<Integer> indirectStops;
	
	public ChangeDestinationFloorPacket(final int destination)
	{
		this.destination = destination;
		//this.indirectStops = indirectStops;
	}
	
	public int getDestination()
	{
		return destination;
	}
	
	public List<Integer> getIndirectStops()
	{
		return null;//return indirectStops; return
	}
}
