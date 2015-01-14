package com.sczr.symulator_windy.ui.elevator;

abstract public class State
{
	abstract State accept(StateMachine sm, float delta);
}
