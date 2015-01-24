package com.sczr.symulator_windy.ui.elevator;

import java.util.LinkedList;
import java.util.Queue;

import com.sczr.symulator_windy.ui.passengers.Passenger;

public class Floor 
{
	final int storeyNumber;
	final Queue<Passenger> waitingPassengers;
	final Queue<Passenger> arrivedPassengers;
	
	Floor(final int storeyNumber)
	{
		this.storeyNumber = storeyNumber;
		this.waitingPassengers = new LinkedList<Passenger>();
		this.arrivedPassengers = new LinkedList<Passenger>();
	}
	
	public void addArrivedPassenger(Passenger passenger)
	{
		this.arrivedPassengers.add(passenger);
	}
	
	public void addWaitingPassenger(Passenger passenger)
	{
		this.waitingPassengers.add(passenger);
	}
	
	public Passenger getInPassenger()
	{
		Passenger temp;
		temp = waitingPassengers.poll();
		return temp;
	}
}
