package model;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import model.elevator.ElevatorCar;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.GUIpackets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.serialization.SerializationList;





public class Model{
	public static final int NUMBER_OF_FLOORS = 10;
	public static final int FLOOR_HEIGHT = 90;
	public static final int GUI_REFRESH_RATE = 100;

	private final Server server;
	private final ElevatorCar elevatorCar = new ElevatorCar(NUMBER_OF_FLOORS, FLOOR_HEIGHT);
	private Floor[] floors = new Floor[NUMBER_OF_FLOORS];
	
	private int GUIConnectionId = -1;
	
	
	public Model(int tcpPort) throws IOException{
		
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
				}				
				//modul sterowania
				
				//modul gui
				else if(o instanceof GUIRegisterPacket){
					GUIConnectionId = c.getID();
					System.out.println("rejestracja gui");
					c.sendTCP(new InitializeGUIPacket(NUMBER_OF_FLOORS, FLOOR_HEIGHT, elevatorCar.ELEVATOR_WIDTH));
				}
				//else if(o instanceof ElevatorCa)

			}
		});
		
		
		
		SerializationList.register(server.getKryo());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				server.sendToTCP(GUIConnectionId, 
						new ElevatorStateInfoPacket(elevatorCar.getCurrentDoorWidth(), elevatorCar.getCurrentHeight()));
			}
		}, 0, GUI_REFRESH_RATE);
		
	}
	
	public static void main(String[] args){
		System.out.println("...GÓRAL...");
		Model model; 
		
		
		try{
			model = new Model(1234);
		}
		catch(Exception e){
			e.printStackTrace(); 
			System.exit(1);
		}
		
	}

}
