package passengersmodule;

import java.util.Random;

public class RandomCreatingOfPassengers {

	
	Passenger createRandomPassenger(int LAST_ID)
	{
		final int ID = LAST_ID + 1;
		int floor = Random.nextInt(10);
		int destination = Random.nextInt(10);
		if (floor == destination) destination = destination + 3;
		
		Passenger newpassenger = new Passenger(ID,destination,floor);
		return newpassenger;
		
	}
	
	
}
