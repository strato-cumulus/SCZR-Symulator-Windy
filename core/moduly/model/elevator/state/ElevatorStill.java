package model.elevator.state;


public class ElevatorStill extends State
{
	public ElevatorStill() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	State accept(StateMachine sm, float delta) 
	{
		return sm.nextState(this, delta);
	}
}
