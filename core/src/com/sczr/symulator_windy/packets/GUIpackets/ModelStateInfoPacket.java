package com.sczr.symulator_windy.packets.GUIpackets;

import model.Model;

import com.sczr.symulator_windy.packets.Packet;


public class ModelStateInfoPacket extends Packet
{

	public final Float verticalPosition;
	public final Float doorWidth;
	public int[] peopleWaitingOnStoreys = new int[Model.NUMBER_OF_FLOORS];
	public int peopleInsideCar;
	
	
	public ModelStateInfoPacket() {
		verticalPosition = null;
		doorWidth = null;
	}
	
	public ModelStateInfoPacket(Float doorWidth, Float verticalPosition, int[] peopleWaiting, int peopleInsideCar)
	{
		this.verticalPosition = verticalPosition;
		this.doorWidth = doorWidth;
		this.peopleWaitingOnStoreys = peopleWaiting;
		this.peopleInsideCar = peopleInsideCar;
	}
}
