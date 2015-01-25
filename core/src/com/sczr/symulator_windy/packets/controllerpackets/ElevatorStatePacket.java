package com.sczr.symulator_windy.packets.controllerpackets;

import java.util.ArrayList;
import java.util.List;

import model.elevator.state.State;

import com.sczr.symulator_windy.packets.Packet;



public class ElevatorStatePacket extends Packet
{
	private ArrayList<Integer> upButtons;
	private ArrayList<Integer> downButtons;
	private  List<Integer> passangersDestinations;
	private State elevatorState;
	private int currentFloor;
	
	public ElevatorStatePacket(){}
	
	public ElevatorStatePacket(ArrayList<Integer>upButtonsClicked, ArrayList<Integer >downButtonsClicked, final int currentFloor,
								List<Integer> passengersDestinations, State elevatorState)
	{
		this.upButtons = upButtonsClicked;
		this.downButtons = downButtonsClicked;
		this.passangersDestinations = passengersDestinations;
		this.elevatorState = elevatorState;
		this.currentFloor = currentFloor;
	}
	
	public ArrayList<Integer> getUpButtons()
	{
		
		return upButtons;
	}
	
	public ArrayList<Integer> getDownButtons()
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
