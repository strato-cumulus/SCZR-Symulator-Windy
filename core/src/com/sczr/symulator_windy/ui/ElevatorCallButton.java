package com.sczr.symulator_windy.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ElevatorCallButton extends TextButton{
	public final Direction direction;
	public final int storey;
	
	public static enum Direction{
		UP,
		DOWN;
	}
	
	public ElevatorCallButton(String text, Skin skin, Direction direction, int storey) {
		super(text, skin);
		this.storey = storey;
		this.direction = direction;
	}
	
}
