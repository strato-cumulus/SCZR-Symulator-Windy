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
	
	
	/* WPIERDOL TUTAJ handleFloor() !!!*/
	
	/*TODO*/
	State nextState(ElevatorGoingDown state, float delta)
	{
		if(elevatorCar.checkFloor() < elevatorCar.getDestinationFloor()) {
			System.out.println("Arrived on floor " + elevatorCar.getDestinationFloor());
			//elevatorCar.setY((elevatorCar.checkFloor()) * elevatorCar.getStage().getHeight() / MainStage.STOREY_NUM);
			return new ElevatorStill();
		}
		elevatorCar.setY(elevatorCar.getY() - elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorGoingUp state, float delta)
	{
		if(elevatorCar.checkFloor() >= elevatorCar.getDestinationFloor()) {
			System.out.println("Arrived on floor " + elevatorCar.getDestinationFloor());
			elevatorCar.setY((elevatorCar.checkFloor()) * Model.FLOOR_HEIGHT);
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
