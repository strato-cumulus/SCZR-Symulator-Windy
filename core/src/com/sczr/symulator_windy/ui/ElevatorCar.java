package com.sczr.symulator_windy.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sczr.symulator_windy.exception.ElevatorStateException;

public class ElevatorCar extends Actor
{
	public final int ELEVATOR_WIDTH;
	public final int ELEVATOR_HEIGHT;
	
	private int peopleInside = 0;
	
	public ElevatorCar(UIModule module, int width, int height, int xPosition)
	{
		super();	
		this.ELEVATOR_HEIGHT = height;
		this.ELEVATOR_WIDTH = width;
		this.setHeight(this.ELEVATOR_HEIGHT);
		this.setWidth(this.ELEVATOR_WIDTH);
		this.setX(xPosition);
	}
	
	void actElevator(float delta) throws ElevatorStateException
	{
		super.act(delta);
	}
	
	void update(float verticalPosition, float doorWidth)
	{
		this.setX(verticalPosition);
		this.setWidth(doorWidth);
	}
	
	final int getNumberOfPeopleInside()
	{
		return this.peopleInside;
	}
}

