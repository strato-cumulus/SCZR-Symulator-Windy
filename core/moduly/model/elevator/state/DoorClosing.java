package model.elevator.state;


public class DoorClosing extends State
{
	public DoorClosing(){}
	@Override
	public State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
