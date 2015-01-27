package com.sczr.symulator_windy.ui;

import java.util.ArrayList;
import java.util.Random;

import model.Model;
import model.elevator.ElevatorCarModel;

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
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.exception.ElevatorStateException;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton.Direction;

public class MainStage extends Stage
{
	private Client client;
	
	private static int lastClientID = 0;
	
	static int stories;//parter to pietro zero; liczba 5 oznacza ze jest parter i 4 pietra
	static int ELEVATOR_X = 200;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final ElevatorCar elevator;
	
	int peopleWaitingOnStorey[];
	
	Label storeyLabels[];
	Table mainTable;
	Label peopleInsideLabel;
	ModelUpdatesListener listener;

	/*************************************************************************| 
	|
	|		            (_)      / ____|                                      |
	|	 _ __ ___   __ _ _ _ __ | (___   __ _  __ _  ___                      |	
	|	| '_ ` _ \ / _` | | '_ \ \___ \ / _` |/ _` |/ _ \                     |	
	|	| | | | | | (_| | | | | |____) | (_| | (_| |  __/                     |	
	|	|_| |_| |_|\__,_|_|_| |_|_____/ \__,_|\__, |\___|                     |	
	|	                                       __/ |                          |	
	|	                                      |___/                           |	
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
	
	public MainStage(Skin skin, int storeyCount, int storeyHeight, int elevatorWidth, Client client)
	{	 
		this.client = client;
		stories = Model.NUMBER_OF_FLOORS;
		storeyLabels = new Label[stories];	
		peopleWaitingOnStorey = new int[stories];

		storeyHeight = (int) this.getHeight() / storeyCount;
		
		this.elevator = new ElevatorCar(ElevatorCarModel.ELEVATOR_WIDTH, Model.FLOOR_HEIGHT, ELEVATOR_X);
		addActor(elevator);
		this.listener = new ModelUpdatesListener(elevator,this);
		//client.addListener(listener);
		
		for(int i=0; i<stories; i++){
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
					Random random = new Random();
					//peopleWaitingOnStorey[Integer.parseInt(event.getListenerActor().getName())]+=1;
					client.sendTCP(new NewPassengerPacket(lastClientID++, Math.abs(random.nextInt() % stories), Integer.parseInt(event.getListenerActor().getName())));
				}
			});
		}
		
		client.addListener(new Listener() {
			@Override
			public void received(Connection c, Object o) {			
				if (o instanceof NewPassengerPacket) {
					System.out.println("gui pas");
					if(((NewPassengerPacket)o).floor > stories) {
						return;
					}
					peopleWaitingOnStorey[((NewPassengerPacket)o).floor] += 1;
				}
			}
		});
		
		ElevatorCallButton groundFloorButton = new ElevatorCallButton("^", skin, Direction.UP, 0);
		ElevatorCallButton topFloorButton = new ElevatorCallButton("v", skin, Direction.DOWN, stories-1);
		topFloorButton.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, (stories * storeyHeight) - (storeyHeight / 2));
		groundFloorButton.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, storeyHeight / 2);
		topFloorButton.addListener(new ElevatorButtonListener(4, Direction.DOWN));
		groundFloorButton.addListener(new ElevatorButtonListener(0, Direction.UP));
		
		ArrayList<ElevatorCallButton> callButtons = new ArrayList<ElevatorCallButton>();
		callButtons.add(groundFloorButton);
		callButtons.add(topFloorButton);
		//przyciski na pietrach nieskrajnych
		for(int i = storeyHeight, buttonCount = 1; i < (stories - 1) * storeyHeight; i += storeyHeight){
			ElevatorCallButton up = new ElevatorCallButton("^", skin, Direction.UP, buttonCount);
			ElevatorCallButton down = new ElevatorCallButton("v", skin, Direction.DOWN, buttonCount);
			up.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, i + storeyHeight / 2);
			down.setPosition(ELEVATOR_X+elevator.ELEVATOR_WIDTH, i + storeyHeight / 4);
			callButtons.add(up);
			callButtons.add(down);
			up.addListener(new ElevatorButtonListener(buttonCount, Direction.UP));
			down.addListener(new ElevatorButtonListener(buttonCount, Direction.DOWN));
			buttonCount++;
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
		for(int i=0; i<stories; i++) {
			shapeRenderer.line(0, getFloorLevel(i), getWidth(), getFloorLevel(i));
		}
		
		//szyb windy
		shapeRenderer.line(ELEVATOR_X, 0, ELEVATOR_X, getHeight());
		shapeRenderer.line(ELEVATOR_X + elevator.ELEVATOR_WIDTH, 0, ELEVATOR_X + elevator.ELEVATOR_WIDTH, getHeight());
		shapeRenderer.end();
		
		//winda
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(elevator.getX(), elevator.getY(), elevator.getWidth(), elevator.getHeight());
		shapeRenderer.end();
		
		super.draw();
	}
	
	public static int getStoreyCount()
	{
		return stories;
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
		
		updatePeopleInsideLabel();
		for(int i=0; i<stories; i++){
			storeyLabels[i].setText("liczba oczekujacych: " + peopleWaitingOnStorey[i]);
		}
	}
	
	//podajesz pietro i zwraca ci na jakiej wysokosci w pixelach sie znajduje
	private int getFloorLevel(int floor){
		return floor*Model.FLOOR_HEIGHT;
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
		
		@Override
		public void changed(ChangeEvent e, Actor a)
		{
			int randomValue;
			
			if(direction.equals(Direction.UP)) {
				randomValue = Math.abs(random.nextInt() % (stories - story - 1)) + story + 1;
			}
			else {
				randomValue = Math.abs(random.nextInt() % story);
			}
			System.out.println("Sending call from floor " + this.story + " to floor " + randomValue);
			//uiModule.client.sendTCP(new ElevatorCallPacket(story, random.nextInt()));
		}
	}
	public void updatePeopleInsideLabel(){
		peopleInsideLabel.setText("osoby w srodku windy: " + elevator.getNumberOfPeopleInside());
	}
}
