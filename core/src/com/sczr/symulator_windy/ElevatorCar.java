package com.sczr.symulator_windy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ElevatorCar extends Actor {
	int height;
	
	@Override
	public void draw (Batch batch, float parentAlpha) {
		batch.draw(new Texture("badlogic.jpg"), 100, 1);
	}
}
