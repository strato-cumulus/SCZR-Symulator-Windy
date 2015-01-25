package model.elevator.state;



public class DoorOpening extends State
{
	public DoorOpening() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
