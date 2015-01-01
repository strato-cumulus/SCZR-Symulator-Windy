package com.sczr.symulator_windy.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sczr.symulator_windy.state.Direction;

public class ElevatorCallButton extends TextButton{
	public final Direction direction;
	public final int storey;
	
	ElevatorCallButton(String text, Skin skin, Direction direction, int storey) {
		super(text, skin);
		this.storey = storey;
		this.direction = direction;
	}
}
