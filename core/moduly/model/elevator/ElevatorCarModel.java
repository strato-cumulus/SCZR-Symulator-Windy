package model.elevator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Model;
import model.elevator.state.DoorStill;
import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.State;
import model.elevator.state.StateMachine;
import passengersmodule.Passenger;

import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.packets.passengerpackets.PassengerEnterPacket;

public class ElevatorCarModel{

	public static final int ELEVATOR_WIDTH = 30;
	public final float DOOR_SPEED = 60;
	public final float ELEVATOR_SPEED = 0.02f;
	public final float MAX_PASSENGERS = 5;

	private int destinationFloor = 9;
	public float currentVerticalPosition = 361;
	private float currentDoorWidth = ELEVATOR_WIDTH;
	private SendPacketInterface sender;
	
	
	State doorState;
	public State elevatorState;
	public State previousElevatorState;
	
	final CopyOnWriteArrayList<Passenger> passengersInCar;
	private StateMachine stateMachine;
	
	public ElevatorCarModel(SendPacketInterface sender) {
		super();	
		this.stateMachine = new StateMachine(this);
		this.doorState = new DoorStill();
		this.elevatorState = new ElevatorGoingUp();
		this.passengersInCar = new CopyOnWriteArrayList<>();
		this.sender = sender;
	}
	
	public void actElevator(float delta) throws ElevatorStateException{	
		this.doorState.accept(this.stateMachine, delta);
		this.elevatorState.accept(this.stateMachine, delta);
	}

	
	public int getNumberOfPeopleInside(){
		return passengersInCar.size();
	}
	
	public int checkFloor()
	{
		return (int)(Math.floor(this.getCurrentVericalPosition() / Model.FLOOR_HEIGHT));
	}
	
	
	public void exit(Passenger passenger){
		passengersInCar.remove(passenger);
	}
	
	public void enter(Passenger passenger){
		new Exception().printStackTrace();
		passengersInCar.add(passenger);
	}

	public int getDestinationFloor() {
		return destinationFloor;
	}

	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}

	public float getCurrentDoorWidth() {
		return currentDoorWidth;
	}

	public float getCurrentVericalPosition() {
		return currentVerticalPosition;
	}
	
	public ArrayList<Integer> getPassangerDestinations(){
		Set<Integer> destinations = new HashSet<>();
		for (Passenger passenger : passengersInCar) {
			destinations.add(passenger.getDestination());
		}
		return new ArrayList<>(destinations);
	}

	public float getWidth() {
		return getCurrentDoorWidth();
	}

	public void setWidth(float elevatorWidth) {
		currentDoorWidth = elevatorWidth;
	}

	public float getY() {
		return getCurrentVericalPosition();
	}

	public void setY(float f) {
		currentVerticalPosition = f;
	}
	
	
	/**
	 * pakuje do windy pasazerow z podanej podlogi. robi to tak dlugo, az zapelni winde
	 * @param floor - pietro na ktorym sie znajduje wozek
	 */
	public void allowPassengersToEnter(int floor){
		while(getNumberOfPeopleInside() < MAX_PASSENGERS){

			Passenger p = Model.floors[floor].getInPassenger();
			
			if(p == null){
				return;
			}
			System.out.println("Do windy wchodzi: " + p.getID() +", Jedzie z" + p.getFloor() +  "na: " + p.getDestination());
			passengersInCar.add(p);
			sender.sendPacket(new PassengerEnterPacket(p.getID(), p.getFloor(), p.getDestination()));
			Model.incrementCounter();
		}
	}
	
	/**
	 * pozwala wysiasc pasazerom
	 * @param floor - pietro na ktorym sie znajduje wozek
	 */
	public void allowPassengersToLeave(int floor){
		for (Passenger passenger : passengersInCar) {
			if(passenger.getDestination() == floor){
				System.out.println("Z windy wychodzi: " + passenger.getID());
				passengersInCar.remove(passenger);
				Model.incrementCounter();
			}
		}
	}
}

