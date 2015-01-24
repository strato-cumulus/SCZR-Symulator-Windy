package passengersmodule;

import java.util.Random;

public class RandomCreatingOfPassengers {

	Random rand = new Random();
	Random rand2 = new Random();
	
	Passenger createRandomPassenger(int LAST_ID)
	{
		final int ID = LAST_ID + 1;
		int floor = rand.nextInt(10);
		int destination = rand2.nextInt(10);
		if (floor == destination) destination = destination + 1;
		
		Passenger newpassenger = new Passenger(ID,destination,floor);
		return newpassenger;
		
	}
	
	
}
