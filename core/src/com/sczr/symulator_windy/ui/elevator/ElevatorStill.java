package com.sczr.symulator_windy.ui.elevator;

public class ElevatorStill extends State
{
	@Override
	State accept(StateMachine sm, float delta) 
	{
		return sm.nextState(this, delta);
	}
}
