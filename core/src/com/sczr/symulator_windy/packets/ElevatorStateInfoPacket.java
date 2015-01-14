package com.sczr.symulator_windy.packets;

import com.sczr.symulator_windy.ui.elevator.State;

public class ElevatorStateInfoPacket extends Packet
{
	public final State doorState;
	public final State elevatorState;
	
	public ElevatorStateInfoPacket()
	{
		this.doorState = null;
		this.elevatorState = null;
	}
	
	public ElevatorStateInfoPacket(State doorState, State elevatorState)
	{
		this.doorState = doorState;
		this.elevatorState = elevatorState;
	}
}
