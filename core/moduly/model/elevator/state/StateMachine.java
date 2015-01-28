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
			return elevatorCar.previousElevatorState;
		}
		return state;
	}

	State nextState(ElevatorGoingDown state, float delta) throws InterruptedException{
		int destination = elevatorCar.getDestinationFloor();
		
		if(elevatorCar.checkFloor() < destination) {
			elevatorCar.setY((elevatorCar.checkFloor()+1) * Model.FLOOR_HEIGHT);
			elevatorCar.allowPassengersToLeave(elevatorCar.checkFloor());
			elevatorCar.allowPassengersToEnter(elevatorCar.checkFloor());
			elevatorCar.elevatorState = new ElevatorStill();
			return new ElevatorStill();
		}
		elevatorCar.previousElevatorState = state;
		elevatorCar.setY(elevatorCar.getY() - elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorGoingUp state, float delta) throws InterruptedException{
		int destination = elevatorCar.getDestinationFloor();
		if(elevatorCar.checkFloor() >= destination) {
			elevatorCar.setY((elevatorCar.checkFloor()) * Model.FLOOR_HEIGHT);
			elevatorCar.allowPassengersToLeave(elevatorCar.checkFloor());
			elevatorCar.allowPassengersToEnter(elevatorCar.checkFloor());
			elevatorCar.elevatorState = new ElevatorStill();
			return new ElevatorStill();
		}
		elevatorCar.previousElevatorState = state;
		elevatorCar.setY(elevatorCar.getY() + elevatorCar.ELEVATOR_SPEED * delta);
		return state;
	}
	
	State nextState(ElevatorStill state, float delta){
		if(Model.isControllerConnected == true){
			

			if(elevatorCar.checkFloor() >= elevatorCar.getDestinationFloor()){
				elevatorCar.elevatorState = new ElevatorGoingDown();
				return new ElevatorGoingDown();
			}
			else if(elevatorCar.checkFloor() < elevatorCar.getDestinationFloor()){
				elevatorCar.elevatorState = new ElevatorGoingUp();
				return new ElevatorGoingUp();
			}
			else{
				try {
					//throw new Exception("kod tu ma nie dojsc");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return state;
			}
		}
		
		//Ruch windy bez sterowania
		else{	
			if(elevatorCar.checkFloor() == Model.NUMBER_OF_FLOORS - 1){
				elevatorCar.setDestinationFloor(Model.NUMBER_OF_FLOORS - 2);
				elevatorCar.elevatorState = new ElevatorGoingDown();
				return new ElevatorGoingDown();
			}
			else if(elevatorCar.checkFloor() == 0){
				elevatorCar.setDestinationFloor(1);
				elevatorCar.elevatorState = new ElevatorGoingUp();
				return new ElevatorGoingUp();
			}
			else{
				if(elevatorCar.previousElevatorState instanceof ElevatorGoingUp){
					elevatorCar.setDestinationFloor(elevatorCar.getDestinationFloor()+1);
				}
				else if(elevatorCar.previousElevatorState instanceof ElevatorGoingDown){
					elevatorCar.setDestinationFloor(elevatorCar.getDestinationFloor()-1);
				}
				elevatorCar.elevatorState = elevatorCar.previousElevatorState;
				return elevatorCar.elevatorState;
			}
			
		}
	}
	
	
}
