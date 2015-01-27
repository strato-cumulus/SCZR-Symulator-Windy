package com.sczr.symulator_windy.ui;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.GUIpackets.ModelStateInfoPacket;

class ModelUpdatesListener extends Listener
{
	ElevatorCar elevator;
	MainStage mainStage;
	
	ModelUpdatesListener(ElevatorCar e, MainStage s)
	{
		this.elevator = e;
		this.mainStage = s;
	}
	
	@Override
	public void received(Connection c, Object o)
	{
		if(o instanceof ModelStateInfoPacket){
			this.update((ModelStateInfoPacket) o);
		}
	}
	
	void update(ModelStateInfoPacket packet)
	{
		elevator.update(packet.verticalPosition, packet.doorWidth);
		for(int i=0; i<packet.peopleWaitingOnStoreys.length; i++){
			mainStage.peopleWaitingOnStorey[i] = packet.peopleWaitingOnStoreys[i]; 
		}
		elevator.setNumberOfPeopleInside(packet.peopleInsideCar);
	}
}
