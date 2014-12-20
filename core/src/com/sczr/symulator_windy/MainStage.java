package com.sczr.symulator_windy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sczr.symulator_windy.exception.ElevatorStateException;

public class MainStage extends Stage {
	public static final int STOREY_NUM = 5;
	public static final int ELEVATOR_X = 100;
	
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final ElevatorCar elevator = new ElevatorCar(60, (int) (getHeight()/STOREY_NUM) - 30);
	
	public MainStage(){	
		addActor(elevator);
	}
	
	@Override
	public void draw(){
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);	
		//pietra
		for(int i=0; i<STOREY_NUM; i++){
			shapeRenderer.line(0, i*getHeight()/5, getWidth(), i*getHeight()/5);
		}
		//szyb windy
		shapeRenderer.line(ELEVATOR_X, 0, ELEVATOR_X, getHeight());
		shapeRenderer.line(ELEVATOR_X+elevator.getElevatorWidth(), 0, ELEVATOR_X+elevator.getElevatorWidth(), getHeight());
		shapeRenderer.end();
		
		//winda
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(elevator.getX(), elevator.getY(), elevator.getWidth(), elevator.getHeight());
		shapeRenderer.end();
		
		super.draw();
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		try {
			elevator.actElevator(delta);
		} catch (ElevatorStateException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
}

