package model.elevator.state;



public class DoorOpening extends State
{
	@Override
	public State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
