package com.sczr.symulator_windy.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;

public class ElevatorCar extends Actor {
	private final int ELEVATOR_WIDTH, ELEVATOR_HEIGHT;
	private final float DOOR_SPEED = 60;
	private final float ELEVATOR_SPEED = 90;
	
	DoorState doorState;
	ElevatorState elevatorState;
	int peopleInside;
	
	public static enum DoorState{
		OPENING,
		CLOSING,
		STILL;
	}
	
	public static enum ElevatorState{
		STILL,
		RIDING_UP,
		RIDING_DOWN;
	}
	
	public ElevatorCar(int width, int height) {
		super();	
		this.ELEVATOR_HEIGHT = height;
		this.ELEVATOR_WIDTH = width;
		setHeight(ELEVATOR_HEIGHT);
		setWidth(ELEVATOR_WIDTH);
		setX(MainStage.ELEVATOR_X);
		
		doorState = DoorState.STILL;
		elevatorState = ElevatorState.RIDING_UP;
	}
	
	public void actElevator(float delta) throws ElevatorStateException{
		super.act(delta);	
		
		
		if(elevatorState != ElevatorState.STILL && doorState != DoorState.STILL)
			throw new ElevatorStateException(doorState, elevatorState);
		switch(doorState){
		case OPENING:
			if(getWidth() <= 0){
				setWidth(0); 
				doorState = DoorState.STILL;
			}
			else{
				setWidth(getWidth() -DOOR_SPEED*delta); 
			}	
			break;
		case CLOSING:
			if(getWidth() >= ELEVATOR_WIDTH){
				setWidth(ELEVATOR_WIDTH);
				doorState = DoorState.STILL;
			}
			else{
				setWidth(getWidth() + DOOR_SPEED*delta); 
			}
			break;
		case STILL:
			break;			
		}
		
		switch(elevatorState){
		case RIDING_DOWN:
			if(getY() <= 0){
				setY(0);
				elevatorState = ElevatorState.STILL;
			}
			else{
				setY(getY() - ELEVATOR_SPEED*delta);
			}
			break;
		case RIDING_UP:
			if(getY() >= (MainStage.STOREY_NUM-1)*getStage().getHeight()/MainStage.STOREY_NUM){
				setY((MainStage.STOREY_NUM-1)*getStage().getHeight()/MainStage.STOREY_NUM);
				elevatorState = ElevatorState.STILL;
			}
			else{
				setY(getY() + ELEVATOR_SPEED*delta);
			}
			break;
		case STILL:
			break;
		}
		
	}
	
	public int getElevatorWidth(){
		return ELEVATOR_WIDTH;
	}
	
	public int getNumberOfPeopleInside(){
		return peopleInside;
	}
}

