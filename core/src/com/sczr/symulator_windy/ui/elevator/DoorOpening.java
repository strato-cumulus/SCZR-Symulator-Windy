package com.sczr.symulator_windy.ui.elevator;

public class DoorOpening extends State
{
	State accept(StateMachine sm, float delta)
	{
		return sm.nextState(this, delta);
	}
}
