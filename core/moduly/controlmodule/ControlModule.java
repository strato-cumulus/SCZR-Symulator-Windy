package controlmodule;

import java.util.Scanner;


public class ControlModule  
{
	public static void main(String[] args) throws InterruptedException
	{
		String adress;
		int port;
		Scanner input = new Scanner(System.in);
		adress = "127.0.0.1";
		port = 1234;
		Controler controler = new Controler(port, adress);
		System.out.println("Kontroler windy dziala");
		while(true);
	}
}
