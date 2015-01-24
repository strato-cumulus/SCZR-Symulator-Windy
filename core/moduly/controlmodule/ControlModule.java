package controlmodule;

import java.util.Scanner;


public class ControlModule  
{
	public static void main(String[] args)
	{
		String adress;
		int port;
		Scanner input = new Scanner(System.in);
		System.out.println("Podaj adres:");
		adress = input.nextLine();
		System.out.println("Podaj port:");
		port = input.nextInt();
		Controler controler = new Controler(port, adress);
		controler.runControler();

		
		System.out.println("ControlModule elo");
	}
}
