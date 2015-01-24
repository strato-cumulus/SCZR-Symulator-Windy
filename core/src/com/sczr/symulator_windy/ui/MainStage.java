package com.sczr.symulator_windy.ui;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.packets.ElevatorCallPacket_;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton.Direction;

public class MainStage extends Stage
{
	public static final int STOREY_NUM = 5;	//parter to pietro zero; liczba 5 oznacza ze jest parter i 4 pietra
	public static final int ELEVATOR_X = 200;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final ElevatorCar elevator;
	final UIModule uiModule;
	
	int peopleWaitingOnStorey[];
	
	Label storeyLabels[];
	Table mainTable;
	Label peopleInsideLabel;
	
	
	
	/*************************************************************************| 
	|
	|		                (_)      / ____| |                                |
	|	 _ __ ___   __ _ _ _ __ | (___ | |_ __ _  __ _  ___                   |	
	|	| '_ ` _ \ / _` | | '_ \ \___ \| __/ _` |/ _` |/ _ \                  |	
	|	| | | | | | (_| | | | | |____) | || (_| | (_| |  __/                  |	
	|	|_| |_| |_|\__,_|_|_| |_|_____/ \__\__,_|\__, |\___|                  |	
	|	                                          __/ |                       |	
	|	                                         |___/                        |	
	|	                                                                      |	
	| 		                                                                  |	
	|    This program is free software: you can redistribute it and/or modify | 	
	|	it under the terms of the GNU General Public License as published by  |  
	|   the Free Software Foundation, either version 3 of the License, or     |	
	|   (at your option) any later version.                                   |  
	|                                                                         |  
	|   This program is distributed in the hope that it will be useful,       |  
	|   but WITHOUT ANY WARRANTY; without even the implied warranty of        |  
	|    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        |  
	|    GNU General Public License for more details.                         |  
	|                                                                         |  
	|    You should have received a copy of the GNU General Public License    |  
	|   along with this program.  If not, see <http://www.gnu.org/licenses/>. |                                      
	 *************************************************************************/    
	//Author: Junior JavaScript Software Developer Szymon Mysiak
	//Date: 2014-12-21 00:16:32:12621
	//Date of Edition --
	//Power Supply: CODEGEN 800W
	
	public MainStage(Skin skin, UIModule uiModule){	
		storeyLabels = new Label[STOREY_NUM];	
		peopleWaitingOnStorey = new int[STOREY_NUM];
		this.uiModule = uiModule;
		
		this.elevator = new ElevatorCar(this.uiModule, 60, (int) (getHeight()/STOREY_NUM) - 30, ELEVATOR_X);
		addActor(elevator);
		
		for(int i=0; i<STOREY_NUM; i++){
			Label label = new Label("", skin);
			label.setY((getFloorLevel(i) ) + 5);
			addActor(label);
			storeyLabels[i] = label;
			
			TextButton addPersonButton = new TextButton("dodaj", skin);
			addPersonButton.setY(getFloorLevel(i));
			addPersonButton.setX(150);
			addPersonButton.setName(i+"");
			addActor(addPersonButton);
			addPersonButton.addListener(new ClickListener(){
				
				@Override
				public void clicked(InputEvent event, float x, float y){
					//TODO wysylac informacje o tym ze ktos kliknal \
					peopleWaitingOnStorey[Integer.parseInt(event.getListenerActor().getName())]+=1;
					//System.out.println("dodano osobe na pietrze: " + event.getListenerActor().getName());
				}
			});
		}
		
		ElevatorCallButton groundFloorButton = new ElevatorCallButton("^", skin, Direction.UP, 0);
		ElevatorCallButton topFloorButton = new ElevatorCallButton("v", skin, Direction.DOWN, STOREY_NUM-1);
		topFloorButton.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, getFloorLevel(STOREY_NUM-1)+60);
		groundFloorButton.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, getFloorLevel(0)+45);
		topFloorButton.addListener(new ElevatorButtonListener(4, Direction.DOWN));
		groundFloorButton.addListener(new ElevatorButtonListener(0, Direction.UP));
		
		ArrayList<ElevatorCallButton> callButtons = new ArrayList<ElevatorCallButton>();
		callButtons.add(groundFloorButton);
		callButtons.add(topFloorButton);
		//przyciski na pietrach nieskrajnych
		for(int i=1; i<STOREY_NUM - 1; i++){
			ElevatorCallButton up = new ElevatorCallButton("^", skin, Direction.UP, i);
			ElevatorCallButton down = new ElevatorCallButton("v", skin, Direction.DOWN, i);
			up.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, getFloorLevel(i)+60);
			down.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, getFloorLevel(i)+45);
			callButtons.add(up);
			callButtons.add(down);
			up.addListener(new ElevatorButtonListener(i, Direction.UP));
			down.addListener(new ElevatorButtonListener(i, Direction.DOWN));
		}
		for (ElevatorCallButton elevatorCallButton : callButtons) {
			addActor(elevatorCallButton);	
		}
		
		
		Table table = new Table();
		table.setFillParent(true);
		addActor(table); 
		Table leftCornerTable = new Table();
		peopleInsideLabel = new Label("", skin);
		leftCornerTable.add(peopleInsideLabel);
		table.add(leftCornerTable);
		table.left().top();
		
	}
	
	@Override
	public void draw()
	{	
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		
		//pietra
		for(int i=0; i<STOREY_NUM; i++) {
			shapeRenderer.line(0, getFloorLevel(i), getWidth(), getFloorLevel(i));
		}
		
		//szyb windy
		shapeRenderer.line(ELEVATOR_X, 0, ELEVATOR_X, getHeight());
		shapeRenderer.line(ELEVATOR_X + elevator.ELEVATOR_WIDTH, 0, ELEVATOR_X + elevator.ELEVATOR_HEIGHT, getHeight());
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
		
		peopleInsideLabel.setText("osoby w srodku windy: " + elevator.getNumberOfPeopleInside());
		for(int i=0; i<STOREY_NUM; i++){
			storeyLabels[i].setText("liczba oczekujacych: " + peopleWaitingOnStorey[i]);
		}
	}
	
	//podajesz pietro i zwraca ci na jakiej wysokosci w pixelach sie znajduje
	private int getFloorLevel(int floor){
		return (int) (floor*getHeight()/STOREY_NUM);
	}
	
	class ElevatorButtonListener extends ChangeListener 
	{
		private final int story;
		private final Direction direction;
		private final Random random;
		
		ElevatorButtonListener(int story, Direction direction)
		{
			this.story = story;
			this.direction = direction;
			this.random = new Random();
		}
		
		public void changed(ChangeEvent e, Actor a)
		{
			int randomValue;
			
			if(direction.equals(Direction.UP)) {
				randomValue = Math.abs(random.nextInt() % (STOREY_NUM - story - 1)) + story + 1;
			}
			else {
				randomValue = Math.abs(random.nextInt() % story);
			}
			System.out.println("Sending call from floor " + this.story + " to floor " + randomValue);
		}
	}	
}
