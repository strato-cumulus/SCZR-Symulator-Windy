package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.elevator.ElevatorCarModel;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.GUIpackets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.serialization.SerializationList;




public class Model{
	public static final int NUMBER_OF_FLOORS = 10;
	public static final int FLOOR_HEIGHT = 90;
	public static final int GUI_REFRESH_RATE = 100;
	public static final int CONTROLLER_REFRESH_RATE = 25;
	public static final int MODEL_REFRESH_RATE = 25;

	private final Server server;
	private final ElevatorCarModel elevatorCar = new ElevatorCarModel();
	private Floor[] floors = new Floor[NUMBER_OF_FLOORS];
	
	private int guiConnectionId = -1;
	private int controllerConnectionId = -1;
	
	private boolean[] upButtons = new boolean[NUMBER_OF_FLOORS-1];
	private boolean[] downButtons = new boolean[NUMBER_OF_FLOORS-1];
	
	
	public Model(int tcpPort) throws IOException{
		for (int i=0; i<NUMBER_OF_FLOORS; i++) {
			floors[i] = new Floor(i);
		}
		
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
					floors[p.floor].addWaitingPassenger(new Passenger(p.ID, p.destination));
					server.sendToAllTCP(new NewPassengerPacket(p.ID, p.destination, p.floor));
					System.out.println("dodano nowego pasazera");
				}				
				//modul sterowania
				else if(o instanceof ControllerRegisterPacket){
					controllerConnectionId = c.getID();
					System.out.println("rejestracja kontrolera");
				}
				//modul gui
				else if(o instanceof GUIRegisterPacket){
					guiConnectionId = c.getID();
					System.out.println("rejestracja gui");
					c.sendTCP(new InitializeGUIPacket(NUMBER_OF_FLOORS, FLOOR_HEIGHT, ElevatorCarModel.ELEVATOR_WIDTH));
				}
				//else if(o instanceof ElevatorCa)

			}
		});
		
		
		
		SerializationList.register(server.getKryo());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				server.sendToTCP(guiConnectionId, 
						new ElevatorStateInfoPacket(elevatorCar.getCurrentDoorWidth(), elevatorCar.getCurrentVericalPosition()));
			}
		}, 0, GUI_REFRESH_RATE);
        
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				server.sendToTCP(controllerConnectionId, 
						new ElevatorStatePacket(getUpButtonsClicked(), 
								getDownButtonsClicked(), 
								elevatorCar.checkFloor(), 
								elevatorCar.getPassangerDestinations(), 
								elevatorCar.elevatorState));
			}
		}, 0, CONTROLLER_REFRESH_RATE);
		
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
	
	public static void main(String[] args){
		System.out.println("...GÓRAL...");
		
		try{
			new Model(1234);
		}
		catch(Exception e){
			e.printStackTrace(); 
			System.exit(1);
		}
		
	}
	
	private ArrayList<Integer> getUpButtonsClicked(){
		ArrayList<Integer> clicked = new ArrayList<>();
		for(int i=0; i<upButtons.length; i++){
			if(upButtons[i]==true)
				clicked.add(i);
		}
		return clicked;
	}
	
	private ArrayList<Integer> getDownButtonsClicked(){
		ArrayList<Integer> clicked = new ArrayList<>();
		for(int i=0; i<downButtons.length; i++){
			if(downButtons[i]==true)
				clicked.add(i);
		}
		return clicked;
	}

}
