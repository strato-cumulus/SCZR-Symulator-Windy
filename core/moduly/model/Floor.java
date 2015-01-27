package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import passengersmodule.Passenger;

public class Floor 
{
	final int storeyNumber;
	final Queue<Passenger> waitingPassengersDown;
	final Queue<Passenger> waitingPassengersUp; 
	
	Floor(final int storeyNumber)
	{
		this.storeyNumber = storeyNumber;
		this.waitingPassengersDown = new LinkedList<Passenger>();
		this.waitingPassengersUp = new LinkedList<Passenger>();
	}
	
	
	public void addWaitingPassengerUp(Passenger passenger)
	{
		this.waitingPassengersUp.add(passenger);
	}
	
	public void addWaitingPassengerDown(Passenger passenger)
	{
		this.waitingPassengersDown.add(passenger);
	}
	
	public Passenger getInPassengerUp(){
		return waitingPassengersUp.poll();
	}
	
	public Passenger getInPassengerDown(){
		return waitingPassengersDown.poll();
	}
	
	public int numberOfWaitingPassengers(){
		return waitingPassengersUp.size() + waitingPassengersDown.size();
	}
}
