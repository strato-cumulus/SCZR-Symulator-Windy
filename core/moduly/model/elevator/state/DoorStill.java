package model.elevator.state;


public class DoorStill extends State
{
	public DoorStill() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
