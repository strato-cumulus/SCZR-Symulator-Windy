package com.sczr.symulator_windy;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.sczr.symulator_windy.controller.ElevatorController;
import com.sczr.symulator_windy.modules.model.Model;
import com.sczr.symulator_windy.modules.randompassengers.RandomPassengersModule;
import com.sczr.symulator_windy.ui.UIModule;

public class SCZRApplication extends ApplicationAdapter
{
	public static int windowWidth, windowHeight;

	ElevatorController     controller;
	
	Model controllerModule;
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
			controller = new ElevatorController(49980);
			controllerModule = new Model(50060);
			passengersModule = new RandomPassengersModule(50070);
			uiModule = new UIModule(50120, windowWidth, windowHeight);
			controller.register(controllerModule);
			controller.register(passengersModule);
			controller.register(uiModule);
		} catch(IOException e) {
			e.printStackTrace();
		}
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
