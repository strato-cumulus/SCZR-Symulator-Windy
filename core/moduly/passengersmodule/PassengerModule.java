package passengersmodule;

import java.util.Scanner;

public class PassengerModule {

	public static void main(String[] args)
	{
		String adress;
		int port;
		Scanner input = new Scanner(System.in);
		//System.out.println("Podaj adres:");
		//adress = input.nextLine();
		//System.out.println("Podaj port:");
		//port = input.nextInt();
		adress = "127.0.0.1";
		port = 1234;
		PassengerClient passengerClient = new PassengerClient(port, adress);
		passengerClient.startPassengerClient();

	}
	
}
