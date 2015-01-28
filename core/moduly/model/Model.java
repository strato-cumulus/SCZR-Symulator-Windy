package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.elevator.ElevatorCarModel;
import model.elevator.SendPacketInterface;
import model.elevator.state.ElevatorStill;
import passengersmodule.Passenger;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.packets.GUIpackets.ModelStateInfoPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ChangeDestinationFloorPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.serialization.SerializationList;





public class Model implements SendPacketInterface 
{
	public static final int NUMBER_OF_FLOORS = 10;
	public static final int FLOOR_HEIGHT = 60;
	public static final int GUI_REFRESH_RATE = 30;
	public static final int CONTROLLER_REFRESH_INTERVAL = 15;
	public static final int MODEL_REFRESH_RATE = 25;

	private final Server server;
	private final ElevatorCarModel elevatorCar;
	public static Floor[] floors = new Floor[NUMBER_OF_FLOORS];
	
	private int guiConnectionId = -1;
	private int controllerConnectionId = -1;
	public static boolean isControllerConnected = false;
	
	private int prevCounter = 0;
	private static int counter = 0;
	
	
	public Model(int tcpPort) throws IOException{
		for (int i=0; i<NUMBER_OF_FLOORS; i++) {
			floors[i] = new Floor(i);
		}
		this.elevatorCar = new ElevatorCarModel((SendPacketInterface)this);
		this.server = new Server();
		this.server.start();
		this.server.bind(tcpPort);
		this.server.addListener(new Listener() {
			
			@Override
			public void received(Connection c, Object o) {
				//modul pasazerow

				if(o instanceof ElevatorCallPacket) {
					ElevatorCallPacket p = (ElevatorCallPacket)o;
				}
				else if(o instanceof NewPassengerPacket){
					NewPassengerPacket p = (NewPassengerPacket)o;
						floors[p.floor].addWaitingPassenger(new Passenger(p.ID, p.destination, p.floor));
						server.sendToAllTCP(new NewPassengerPacket(p.ID, p.destination, p.floor));
						counter++;
					System.out.println("dodano nowego pasazera");
				}				
				//modul sterowania
				else if(o instanceof ControllerRegisterPacket){
					controllerConnectionId = c.getID();
					isControllerConnected = true;
					System.out.println("rejestracja kontrolera");
				}
				else if(o instanceof ChangeDestinationFloorPacket){
					ChangeDestinationFloorPacket p = (ChangeDestinationFloorPacket)o;
					elevatorCar.setDestinationFloor(p.getDestination());
					System.out.println("p.getdest: " + p.getDestination());
				}
				//modul gui
				else if(o instanceof GUIRegisterPacket){
					guiConnectionId = c.getID();
					System.out.println("rejestracja gui");
					c.sendTCP(new InitializeGUIPacket(NUMBER_OF_FLOORS, FLOOR_HEIGHT, ElevatorCarModel.ELEVATOR_WIDTH));
				}

			}
			
			@Override
			public void disconnected (Connection connection) {
				if(connection.getID() == controllerConnectionId){
					isControllerConnected = false;
					System.out.println("rozlaczono kontroler");
				}
				else if(connection.getID() == guiConnectionId){
					System.out.println("rozlaczono gui");
				}
			}
		});
		
		
		
		SerializationList.register(server.getKryo());
		
		
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				int[] peopleWating = new int[Model.NUMBER_OF_FLOORS];
				for (int i=0; i<floors.length; i++) {
					peopleWating[i] = floors[i].numberOfWaitingPassengers();
				}
				if(elevatorCar.elevatorState instanceof ElevatorStill)
				{
					if(isControllerConnected == true && number() > 0){
						try 
						{
							Thread.sleep(200);
						} catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
					if(isControllerConnected == false)
					{
						try 
						{
							Thread.sleep(200);
						} catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
				}
				//System.out.flush();

				server.sendToTCP(guiConnectionId, 
						new ModelStateInfoPacket(elevatorCar.getCurrentDoorWidth(), elevatorCar.getCurrentVericalPosition(),
								peopleWating, elevatorCar.getNumberOfPeopleInside(), elevatorCar.elevatorState));

			}
		}, 0, GUI_REFRESH_RATE);
        
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() 
			{
				if(prevCounter != counter)
				{
					prevCounter = counter;
					server.sendToTCP(controllerConnectionId, 
							new ElevatorStatePacket(getUpButtonsClicked(), 
									elevatorCar.checkFloor(), 
									elevatorCar.getPassangerDestinations(), 
									elevatorCar.elevatorState,
									elevatorCar.previousElevatorState));
				}
				
				
			}
		}, 0, CONTROLLER_REFRESH_INTERVAL);
		
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					elevatorCar.actElevator(MODEL_REFRESH_RATE);
				} catch (ElevatorStateException e) {
					e.printStackTrace();
				}
			}
		}, 0, MODEL_REFRESH_RATE);
	}
	
	private ArrayList<Integer> getUpButtonsClicked(){
		ArrayList<Integer> clicked = new ArrayList<>();
		for (Floor floor : floors) {
			for (Passenger passenger : floor.waitingPassengers) {
				clicked.add(passenger.getFloor());
			}
		}
		return clicked;
	}

	
	public Floor[] getFloors(){
		return floors;
	}
	
	
	
	
	public static void main(String[] args){	
		try{
			new Model(1234);
		}
		catch(Exception e){
			e.printStackTrace(); 
			System.exit(1);
		}
		System.out.println("Winda uruchomiona!");
		
	}

	@Override
	public void sendPacket(Packet packet) {
		this.server.sendToAllTCP(packet);
	}
	
	public static void incrementCounter()
	{
		counter++;
	}
	
	private int number()
	{
		int number = 0;
		for (Floor floor : floors) 
		{
			number += floor.numberOfWaitingPassengers();
		}
		number+= elevatorCar.getNumberOfPeopleInside();
		return number;
	}

}
