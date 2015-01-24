package passengersmodule;

import java.util.Random;

public class RandomCreatingOfPassengers {

	Random rand = new Random();
	
	private final int createID()
	{
		
		id += 1 % 1000;
		return id;
	}
	
	
}
