package com.sczr.symulator_windy;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.sczr.symulator_windy.controller.ElevatorController;
import com.sczr.symulator_windy.modules.RandomPassengersModule;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.state.Direction;
import com.sczr.symulator_windy.ui.UIModule;

public class SCZRApplication extends ApplicationAdapter
{
	public static int windowWidth, windowHeight;

	ElevatorController     controller;
	
	RandomPassengersModule passengersModule;
	UIModule               uiModule;
	
	public SCZRApplication(int windowWidth, int windowHeight)
	{
		SCZRApplication.windowWidth = windowWidth;
		SCZRApplication.windowHeight = windowHeight;
	}
	
	@Override
	public void create ()
	{	
		try {
			controller = new ElevatorController(49991);
			passengersModule = new RandomPassengersModule(50009);
			uiModule = new UIModule(50100, windowWidth, windowHeight);
			controller.register(passengersModule);
			controller.register(uiModule);
		} catch(IOException e) {
			e.printStackTrace();
		}
		controller.multicast(new ElevatorCallPacket(2, Direction.DOWN), RandomPassengersModule.class);
	}

	@Override
	public void render ()
	{
		this.uiModule.render();
	}
	
	@Override
	public void resize(int width, int height)
	{
		this.uiModule.resize(width, height);
	}
}
