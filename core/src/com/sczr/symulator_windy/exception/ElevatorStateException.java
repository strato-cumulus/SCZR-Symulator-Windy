package com.sczr.symulator_windy.exception;

import com.sczr.symulator_windy.ui.ElevatorCar.DoorState;
import com.sczr.symulator_windy.ui.ElevatorCar.ElevatorState;

public class ElevatorStateException extends Exception {
	private static final long serialVersionUID = 1L;

	final DoorState doorState;
	final ElevatorState elevatorState;
	
	public ElevatorStateException(DoorState doorState, ElevatorState elevatorState) {
		this.doorState = doorState;
		this.elevatorState = elevatorState;
	}
	
	@Override
	public String getMessage(){
		return "Unsupported comnbination of states: door state: "+doorState.toString()+" elevator state: " + elevatorState.toString();
	}
	
}
