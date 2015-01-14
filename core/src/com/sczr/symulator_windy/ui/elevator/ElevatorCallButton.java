package com.sczr.symulator_windy.ui.elevator;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sczr.symulator_windy.state.Direction;

public class ElevatorCallButton extends TextButton
{
	public final Direction direction;
	public final int story;
	
	public ElevatorCallButton(String text, Skin skin, Direction direction, int story)
	{
		super(text, skin);
		this.story = story;
		this.direction = direction;
	}
}
