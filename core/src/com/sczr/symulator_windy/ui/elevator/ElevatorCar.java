package com.sczr.symulator_windy.ui.elevator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.ui.MainStage;

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
	
	StateMachine stateMachine;
	State doorState;
	State elevatorState;
	
	public ElevatorCar(int width, int height, int xPosition, int floorHeight, int numberOfFloors) {
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
}

