package model.elevator;

import java.util.ArrayList;

import model.Passenger;
import model.elevator.state.DoorStill;
import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.ElevatorStill;
import model.elevator.state.State;
import model.elevator.state.StateMachine;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;

public class ElevatorCar extends Actor{
	
	final int floorHeight;
	final int numberOfFloors;
	
	public static final int ELEVATOR_HEIGHT =70;
	public static final int ELEVATOR_WIDTH =30;
	public final float DOOR_SPEED = 60;
	public final float ELEVATOR_SPEED = 90;
	

	private int destinationFloor = 0;
	private float currentHeight = 40;
	private float currentDoorWidth;
	
	
	State doorState;
	State elevatorState;
	
	final ArrayList<Passenger> passengersInCar;
	private StateMachine stateMachine;
	
	public ElevatorCar(int numberOfFloors, int floorHeight) {
		super();	
		this.numberOfFloors = numberOfFloors;
		this.stateMachine = new StateMachine(this);
		this.doorState = new DoorStill();
		this.elevatorState = new ElevatorGoingUp();
		this.passengersInCar = new ArrayList<>();
		this.floorHeight = floorHeight;

	}
	
	public void actElevator(float delta) throws ElevatorStateException{
		super.act(delta);	

		if(!elevatorState.getClass().equals(ElevatorStill.class) && !doorState.getClass().equals(DoorStill.class)) {
			throw new ElevatorStateException();
		}
		this.doorState = this.doorState.accept(this.stateMachine, delta);
		this.elevatorState = this.elevatorState.accept(this.stateMachine, delta);
	}

	
	public int getNumberOfPeopleInside(){
		return passengersInCar.size();
	}
	
	public int checkFloor()
	{
		return (int)(Math.floor(this.getY() / floorHeight));
	}
	
	public void dispatch(int callFloor, int destinationFloor)
	{
		this.setDestinationFloor(callFloor);
		if(callFloor > this.checkFloor()) {
			this.elevatorState = new ElevatorGoingUp();
		}
		else {
			this.elevatorState = new ElevatorGoingDown();
		}
	}

	
	public void exit(Passenger passenger){
		passengersInCar.remove(passenger);
	}
	
	public void enter(Passenger passenger){
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

	public float getCurrentHeight() {
		return currentHeight;
	}


}

