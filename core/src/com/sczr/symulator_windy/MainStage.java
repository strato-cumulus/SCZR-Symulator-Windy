package com.sczr.symulator_windy;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainStage extends Stage {
	private final int STOREY_NUM = 5;
	
	
	public MainStage(){
		addActor(new ElevatorCar());
	}
	
	
}

