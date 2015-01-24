package model;


public class Passenger {
	public enum PassengerState {
		WAITING, INSIDE, ARRIVED
	}

	final int ID;
	final int destination;
	PassengerState state;
	
	
	Passenger(final int ID, final int destination){
		this.ID = ID;
		this.destination = destination;
		this.state = PassengerState.WAITING;
	}
	
	public void setState(PassengerState state){
		this.state = state;
	}
	
	public PassengerState getState(){
		return this.state;
	}
	
	public int getDestination(){
		return this.destination;
	}
	
	
}
