package com.sczr.symulator_windy.ui.elevator;

public class FloorButton 
{
	FloorButtonState state;
	
	FloorButton()
	{
		this.state = FloorButtonState.OFF;
	}
	
	public void pushButton()
	{
		this.state = FloorButtonState.ON;	
	}
	
	public void offButton()
	{
		this.state = FloorButtonState.OFF;
	}
	
	public FloorButtonState getState()
	{
		return this.state;
	}
}
