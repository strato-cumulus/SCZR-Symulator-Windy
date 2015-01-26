package model.elevator.state;


public class ElevatorGoingDown extends State
{
	public ElevatorGoingDown() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	State accept(StateMachine sm, float delta)
	{
		try {
			return sm.nextState(this, delta);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
