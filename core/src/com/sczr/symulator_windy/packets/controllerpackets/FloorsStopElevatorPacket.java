package com.sczr.symulator_windy.packets.controllerpackets;

import java.util.List;



public class FloorsStopElevatorPacket 
{
	private final int destination;
	private final List<Integer> indirectStops;
	
	public FloorsStopElevatorPacket(final int destination, List<Integer> indirectStops)
	{
		this.destination = destination;
		this.indirectStops = indirectStops;
	}
	
	public int getDestination()
	{
		return destination;
	}
	
	public List<Integer> getIndirectStops()
	{
		return indirectStops;
	}
}
