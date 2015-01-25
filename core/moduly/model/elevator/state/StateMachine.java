package model.elevator.state;

import model.Model;
import model.elevator.ElevatorCarModel;

public class StateMachine 
{
	private ElevatorCarModel elevatorCar;
	
	public StateMachine(ElevatorCarModel elevatorCar)
	{
		this.elevatorCar = elevatorCar;
	}
	
	State nextState(DoorClosing state, float delta)
	{
		if(elevatorCar.getWidth() >= ElevatorCarModel.ELEVATOR_WIDTH) {
			elevatorCar.setWidth(ElevatorCarModel.ELEVATOR_WIDTH);
			System.out.println("zakonczono zamykanie dzrwi");
			return new DoorStill();
		}
		elevatorCar.setWidth(elevatorCar.getWidth() + elevatorCar.DOOR_SPEED * delta);
		return state;
	}
	
	State nextState(DoorOpening state, float delta)
	{
		if(elevatorCar.getWidth() <= 0) {
			elevatorCar.setWidth(0); 
			System.out.println("zakonczono otwieranie dzrwi");
			return new DoorStill();
		}
		elevatorCar.setWidth(elevatorCar.getWidth() - elevatorCar.DOOR_SPEED * delta);
		return state;
	}
	
	State nextState(DoorStill state, float delta)
	{
		if(elevatorCar.checkFloor() <= Model.NUMBER_OF_FLOORS && elevatorCar.checkFloor() >= 0) {
			if(elevatorCar.previousElevatorState == null){
				return new ElevatorStill();
			}
			return elevatorCar.previousElevatorState;
		}
		return state;
	}

	State nextState(ElevatorGoingDown state, float delta)
	{
		if(elevatorCar.checkFloor() < elevatorCar.getDestinationFloor()) {
			System.out.println("Arrived on floor " + elevatorCar.getDestinationFloor());
			elevatorCar.setY((elevatorCar.checkFloor()) * Model.FLOOR_HEIGHT);
			while(elevatorCar.getNumberOfPeopleInside() < elevatorCar.MAX_PASSENGERS){
				elevatorCar.enter(Model.floors[elevatorCar.getDestinationFloor()].getInPassenger());
			}
			return new ElevatorStill();
		}
		elevatorCar.previousElevatorState = state;
		elevatorCar.setY(elevatorCar.getY() - elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorGoingUp state, float delta)
	{
		if(elevatorCar.checkFloor() >= elevatorCar.getDestinationFloor()) {
			System.out.println("Arrived on floor " + elevatorCar.getDestinationFloor());
			elevatorCar.setY((elevatorCar.checkFloor()) * Model.FLOOR_HEIGHT);
			while(elevatorCar.getNumberOfPeopleInside() < elevatorCar.MAX_PASSENGERS){
				elevatorCar.enter(Model.floors[elevatorCar.getDestinationFloor()].getInPassenger());
			}
			return new ElevatorStill();
		}
		elevatorCar.previousElevatorState = state;
		elevatorCar.setY(elevatorCar.getY() + elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorStill state, float delta)
	{
		if(elevatorCar.checkFloor() > elevatorCar.getDestinationFloor())
			return new ElevatorGoingDown();
		if(elevatorCar.checkFloor() < elevatorCar.getDestinationFloor())
			return new ElevatorGoingUp();
		return state;
	}
}
