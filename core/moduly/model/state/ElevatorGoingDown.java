package model.state;


public class ElevatorGoingDown extends State
{
	@Override
	public
	State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
