package com.sczr.symulator_windy.packets.controllerpackets;

import java.util.ArrayList;
import java.util.List;

import model.elevator.state.State;

import com.sczr.symulator_windy.packets.Packet;



public class ElevatorStatePacket extends Packet
{
	private ArrayList<Integer> upButtons;
	private ArrayList<Integer> downButtons;
	private final List<Integer> passangersDestinations;
	private final State elevatorState;
	private final int currentFloor;
	
	public ElevatorStatePacket(ArrayList<Integer>upButtonsClicked, ArrayList<Integer >downButtonsClicked, final int currentFloor,
								List<Integer> passengersDestinations, State elevatorState)
	{
		this.upButtons = upButtonsClicked;
		this.downButtons = downButtonsClicked;
		this.passangersDestinations = passengersDestinations;
		this.elevatorState = elevatorState;
		this.currentFloor = currentFloor;
	}
	
	public Integer[] getUpButtons()
	{
		return (Integer[])upButtons.toArray();
	}
	
	public Integer[] getDownButtons()
	{
		return(Integer[]) downButtons.toArray();
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
