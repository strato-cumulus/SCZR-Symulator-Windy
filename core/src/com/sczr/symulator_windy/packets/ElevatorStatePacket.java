package com.sczr.symulator_windy.packets;

import java.util.List;
import model.state.State;

public class ElevatorStatePacket extends Packet
{
	private final int[] upButtons;
	private final int[] downButtons;
	private final List<Integer> passangersDestinations;
	private State elevatorState;
	
	public ElevatorStatePacket(final int[] upButtons, final int[] downButtons, List<Integer> passengersDestinations, State elevatorState)
	{
		this.upButtons = upButtons;
		this.downButtons = downButtons;
		this.passangersDestinations = passengersDestinations;
		this.elevatorState = elevatorState;
	}
	
	public int[] getUpButtons()
	{
		return upButtons;
	}
	
	public int[] getDownButtons()
	{
		return downButtons;
	}
	
	public List<Integer> getDestinations()
	{
		return passangersDestinations;
	}
	
	public State getElevatorState()
	{
		return elevatorState;
	}
}
