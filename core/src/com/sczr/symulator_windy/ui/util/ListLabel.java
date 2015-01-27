package com.sczr.symulator_windy.ui.util;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ListLabel
{
	public final Label label;
	private CopyOnWriteArrayList<PassengerData> passengers;
	
	public ListLabel(CharSequence seq, Skin skin)
	{
		this.label = new Label(seq, skin);
		this.passengers = new CopyOnWriteArrayList<>();
	}
	
	public ListLabel(Skin skin)
	{
		this("", skin);
	}
	
	public void addPassenger(final int id, final int floor, final int destination)
	{
		this.passengers.add(new PassengerData(id, floor, destination));
		StringBuilder passengerList = new StringBuilder();
		passengerList.append(this.label.getText());
		passengerList.append(id + ": " + destination + " ");
		this.label.setText(passengerList.toString());
	}
	
	public void removePassenger(final int id, final int floor, final int destination)
	{
		this.removePassenger(new PassengerData(id, floor, destination));
	}
	
	public void removePassenger(PassengerData passenger)
	{
		for(PassengerData pd: this.passengers) {
			if(passenger.id == pd.id) {
				this.passengers.remove(pd);
			}
		}
		StringBuilder passengerList = new StringBuilder();
		for(PassengerData localPassenger: this.passengers) {
			passengerList.append(localPassenger.id + ": " + localPassenger.destination + " ");
		}
		this.label.setText(passengerList.toString());
	}
}