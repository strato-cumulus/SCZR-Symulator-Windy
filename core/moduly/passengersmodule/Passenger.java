package passengersmodule;

public class Passenger {
	final int ID;
	final int destination;
	final int floor;
	
	Passenger(final int ID, final int destination, final int floor)
	{
		this.ID = ID;
		this.destination = destination;
		this.floor = floor;
	}
	
	public int getDestination()
	{
		return this.destination;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public int getFloor()
	{
		return this.floor;
	}
}
