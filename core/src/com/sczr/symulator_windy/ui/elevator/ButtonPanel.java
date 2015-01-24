package com.sczr.symulator_windy.ui.elevator;

public class ButtonPanel 
{
	final FloorButton[] floorButtons;
	
	ButtonPanel(final int floorsNumber)
	{
		floorButtons = new FloorButton[floorsNumber];
	}
	
	public void pushButton(final int buttonNumber)
	{

		floorButtons[buttonNumber].pushButton();
	}
	
	public void offButton(final int buttonNumber)
	{
		floorButtons[buttonNumber].offButton();
	}
	
	public FloorButtonState getButtonState(final int buttonNumber)
	{
		return floorButtons[buttonNumber].getState();
	}
}
