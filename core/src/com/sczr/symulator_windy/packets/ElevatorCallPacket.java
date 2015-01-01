package com.sczr.symulator_windy.packets;

import com.sczr.symulator_windy.state.Direction;

public class ElevatorCallPacket extends Packet
{
	private final Integer floor;
	private final Direction direction;
	
	public ElevatorCallPacket() 
	{
		this.floor = null;
		this.direction = null;
	}
	
	public ElevatorCallPacket(int floor, Direction direction)
	{
		this.floor = floor;
		this.direction = direction;
	}

	public Integer getFloor() {
		return floor;
	}

	public Direction getDirection() {
		return direction;
	}
}
