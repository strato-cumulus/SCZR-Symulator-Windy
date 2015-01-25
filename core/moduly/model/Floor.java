package model;

import java.util.LinkedList;
import java.util.Queue;

import passengersmodule.Passenger;

public class Floor 
{
	final int storeyNumber;
	final Queue<Passenger> waitingPassengers;
	
	Floor(final int storeyNumber)
	{
		this.storeyNumber = storeyNumber;
		this.waitingPassengers = new LinkedList<Passenger>();
	}
	
	
	public void addWaitingPassenger(Passenger passenger)
	{
		this.waitingPassengers.add(passenger);
	}
	
	public Passenger getInPassenger(){
		return waitingPassengers.poll();
	}
}
