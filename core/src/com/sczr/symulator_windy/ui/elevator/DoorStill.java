package com.sczr.symulator_windy.ui.elevator;

public class DoorStill extends State
{
	State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
