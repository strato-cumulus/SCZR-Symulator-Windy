package model.elevator;

import java.util.ArrayList;

import model.Passenger;
import model.state.DoorStill;
import model.state.ElevatorGoingDown;
import model.state.ElevatorGoingUp;
import model.state.ElevatorStill;
import model.state.State;
import model.state.StateMachine;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;

public class ElevatorCar extends Actor
{
	public static final float ELEVATOR_WIDTH = 30;
	final int floorHeight;
	final int numberOfFloors;
	final int ELEVATOR_HEIGHT =70;
	public final float DOOR_SPEED = 60;
	public final float ELEVATOR_SPEED = 90;
	int onFloor = 0;
	public int destinationFloor = 0;
	
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
	
	public int getFloor()
	{
		return this.onFloor;
	}
	
	public int checkFloor()
	{
		return (int)(Math.floor(this.getY() / floorHeight));
	}
	
	public void dispatch(int callFloor, int destinationFloor)
	{
		this.destinationFloor = callFloor;
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
}

