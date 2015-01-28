package com.sczr.symulator_windy.ui;

import model.elevator.state.ElevatorStill;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.GUIpackets.ModelStateInfoPacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;

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
		if(o instanceof ModelStateInfoPacket)
		{
			ModelStateInfoPacket p = (ModelStateInfoPacket) o;
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
