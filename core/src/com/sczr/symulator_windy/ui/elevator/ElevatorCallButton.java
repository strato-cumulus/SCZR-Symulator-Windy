package com.sczr.symulator_windy.ui.elevator;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ElevatorCallButton extends TextButton
{
	public enum Direction 
	{
		UP, DOWN
	};

	public final Direction direction;
	public final int story;
	
	public ElevatorCallButton(String text, Skin skin, Direction direction, int story)
	{
		super(text, skin);
		this.story = story;
		this.direction = direction;
	}
}
