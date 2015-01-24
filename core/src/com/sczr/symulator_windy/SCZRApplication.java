package com.sczr.symulator_windy;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.sczr.symulator_windy.ui.UIModule;

public class SCZRApplication extends ApplicationAdapter
{
	public static int windowWidth, windowHeight;

	//Model controllerModule;
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
			//controllerModule = new Model(1234);
			uiModule = new UIModule(1234, windowWidth, windowHeight);
			//controller.register(controllerModule);
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
