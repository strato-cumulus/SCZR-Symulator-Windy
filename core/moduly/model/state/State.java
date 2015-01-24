package model.state;


abstract public class State
{
	public abstract State accept(StateMachine sm, float delta);
}
