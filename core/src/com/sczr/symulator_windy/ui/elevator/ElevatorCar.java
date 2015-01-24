package com.sczr.symulator_windy.ui.elevator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.ui.UIModule;
import com.sczr.symulator_windy.ui.passengers.Passenger;
import com.sczr.symulator_windy.ui.passengers.PassengerState;

public class ElevatorCar extends Actor
{
	final int floorHeight;
	final int numberOfFloors;
	final int ELEVATOR_WIDTH, ELEVATOR_HEIGHT;
	final float DOOR_SPEED = 60;
	final float ELEVATOR_SPEED = 90;
	int onFloor = 0;
	int destinationFloor = 0;
	int peopleInside;
	final UIModule module;
	
	StateMachine stateMachine;
	State doorState;
	State elevatorState;
	
	final List<Passenger> passengersInCar;
	final ButtonPanel buttonPanel;
	
	public ElevatorCar(UIModule module, int width, int height, int xPosition, int floorHeight, int numberOfFloors) {
		super();	
		this.ELEVATOR_HEIGHT = height;
		this.ELEVATOR_WIDTH = width;
		this.setHeight(this.ELEVATOR_HEIGHT);
		this.setWidth(this.ELEVATOR_WIDTH);
		this.setX(xPosition);
		this.floorHeight = floorHeight;
		this.numberOfFloors = numberOfFloors;
		
		this.stateMachine = new StateMachine(this);
		this.doorState = new DoorStill();
		this.elevatorState = new ElevatorGoingUp();
		
		this.module = module;
		this.passengersInCar = new LinkedList<Passenger>();
		this.buttonPanel = new ButtonPanel(numberOfFloors);

	}
	
	public void actElevator(float delta) throws ElevatorStateException{
		super.act(delta);	

		if(!elevatorState.getClass().equals(ElevatorStill.class) && !doorState.getClass().equals(DoorStill.class)) {
			throw new ElevatorStateException();
		}
		this.doorState = this.doorState.accept(this.stateMachine, delta);
		this.elevatorState = this.elevatorState.accept(this.stateMachine, delta);
	}
	
	public int getElevatorWidth(){
		return ELEVATOR_WIDTH;
	}
	
	public int getNumberOfPeopleInside(){
		return peopleInside;
	}
	
	public int getFloor()
	{
		return this.onFloor;
	}
	
	int checkFloor()
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
	
	public void chooseFloor(final int number)
	{
		buttonPanel.pushButton(number);
	}
	
	public void offButton(final int number)
	{
		buttonPanel.offButton(number);
	}
	
	public List<Passenger> getPassengers()
	{
		return this.passengersInCar;
	}
	
	public void exit(Passenger passenger)
	{
		passengersInCar.remove(passenger);
		peopleInside--;
	}
	
	public void enter(Passenger passenger)
	{
		passengersInCar.add(passenger);
		peopleInside++;
	}
}

