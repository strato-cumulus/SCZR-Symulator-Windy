package com.sczr.symulator_windy.ui.elevator;

import java.util.concurrent.ArrayBlockingQueue;

import com.sczr.symulator_windy.ui.MainStage;

class StateMachine 
{
	private ElevatorCar elevatorCar;
	
	StateMachine(ElevatorCar elevatorCar)
	{
		this.elevatorCar = elevatorCar;
	}
	
	State nextState(DoorClosing state, float delta)
	{
		if(elevatorCar.getWidth() >= elevatorCar.ELEVATOR_WIDTH) {
			elevatorCar.setWidth(elevatorCar.ELEVATOR_WIDTH);
			return new DoorStill();
		}
		elevatorCar.setWidth(elevatorCar.getWidth() + elevatorCar.DOOR_SPEED * delta);
		return state;
	}
	
	State nextState(DoorOpening state, float delta)
	{
		if(elevatorCar.getWidth() <= 0) {
			elevatorCar.setWidth(0); 
			return new DoorStill();
		}
		elevatorCar.setWidth(elevatorCar.getWidth() - elevatorCar.DOOR_SPEED * delta);
		return state;
	}
	
	State nextState(DoorStill state, float delta)
	{
		return state;
	}
	
	State nextState(ElevatorGoingDown state, float delta)
	{
		if(elevatorCar.checkFloor() < elevatorCar.destinationFloor) {
			//elevatorCar.setY((elevatorCar.checkFloor()) * elevatorCar.getStage().getHeight() / MainStage.STOREY_NUM);
			return new ElevatorStill();
		}
		elevatorCar.setY(elevatorCar.getY() - elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorGoingUp state, float delta)
	{
		if(elevatorCar.checkFloor() >= elevatorCar.destinationFloor) {
			System.out.println("Arrived on floor " + elevatorCar.destinationFloor);
			elevatorCar.setY((elevatorCar.checkFloor()) * elevatorCar.getStage().getHeight() / MainStage.STOREY_NUM);
			return new ElevatorStill();
		}
		elevatorCar.setY(elevatorCar.getY() + elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorStill state, float delta)
	{
		return state;
	}
}
