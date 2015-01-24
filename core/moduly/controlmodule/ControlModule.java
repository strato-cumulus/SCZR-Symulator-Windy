package controlmodule;

import java.util.Scanner;


public class ControlModule  
{
	public static void main(String[] args) throws InterruptedException
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
		Controler controler = new Controler(port, adress);
		//controler.runControler();
		System.out.println("ControlModule elo");
		while(true);
		//Thread.currentThread().join();
	}
}
