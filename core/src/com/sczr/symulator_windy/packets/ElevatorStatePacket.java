package com.sczr.symulator_windy.packets;

import java.util.List;

import model.elevator.state.State;

import com.sun.istack.internal.FinalArrayList;



public class ElevatorStatePacket extends Packet
{
	private final int[] upButtons;
	private final int[] downButtons;
	private final List<Integer> passangersDestinations;
	private final State elevatorState;
	private final int currentFloor;
	
	public ElevatorStatePacket(final int[] upButtons, final int[] downButtons, final int currentFloor,
								List<Integer> passengersDestinations, State elevatorState)
	{
		this.upButtons = upButtons;
		this.downButtons = downButtons;
		this.passangersDestinations = passengersDestinations;
		this.elevatorState = elevatorState;
		this.currentFloor = currentFloor;
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
	
	public int getCurrentFloor()
	{
		return currentFloor;
	}
}
