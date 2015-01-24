package model.elevator.state;

import model.elevator.ElevatorCar;

import com.sczr.symulator_windy.ui.MainStage;

public class StateMachine 
{
	private ElevatorCar elevatorCar;
	
	public StateMachine(ElevatorCar elevatorCar)
	{
		this.elevatorCar = elevatorCar;
	}
	
	State nextState(DoorClosing state, float delta)
	{
		if(elevatorCar.getWidth() >= ElevatorCar.ELEVATOR_WIDTH) {
			elevatorCar.setWidth(ElevatorCar.ELEVATOR_WIDTH);
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
			elevatorCar.setY((elevatorCar.checkFloor()) * elevatorCar.getStage().getHeight() / MainStage.stories);
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
