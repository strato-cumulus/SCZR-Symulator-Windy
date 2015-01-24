package com.sczr.symulator_windy.packets;

import model.elevator.state.State;

public class ElevatorStateInfoPacket extends Packet
{
	public final State doorState;
	public final State elevatorState;
	public final Float verticalPosition;
	public final Float doorWidth;
	
	
	public ElevatorStateInfoPacket(State doorState, State elevatorState, Float doorWidth, Float verticalPosition)
	{
		this.doorState = doorState;
		this.elevatorState = elevatorState;
		this.verticalPosition = verticalPosition;
		this.doorWidth = doorWidth;
	}
}
