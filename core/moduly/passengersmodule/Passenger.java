package passengersmodule;

public class Passenger {
	private final int ID;
	private final int destination;
	private final int floor;
	
	Passenger()
	{
		this.ID = 0;
		this.destination = 0;
		this.floor = 0;
	}
	
	public Passenger(final int ID, final int destination, final int floor)
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
