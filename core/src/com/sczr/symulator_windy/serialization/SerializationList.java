package com.sczr.symulator_windy.serialization;

import java.util.ArrayList;

import model.elevator.state.DoorClosing;
import model.elevator.state.DoorOpening;
import model.elevator.state.DoorStill;
import model.elevator.state.ElevatorGoingDown;
import model.elevator.state.ElevatorGoingUp;
import model.elevator.state.ElevatorStill;

import com.esotericsoftware.kryo.Kryo;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.packets.GUIpackets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ChangeDestinationFloorPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.passengerpackets.NewPassengerPacket;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton.Direction;



public class SerializationList 
{
	public static void register(Kryo kryo)
	{
		kryo.register(int.class);
		kryo.register(Direction.class);
		kryo.register(Packet.class);

		kryo.register(ElevatorCallPacket.class);

		kryo.register(ElevatorStateInfoPacket.class);

		kryo.register(ElevatorCallPacket.class);

		kryo.register(ChangeDestinationFloorPacket.class);
		kryo.register(ElevatorStatePacket.class);
		kryo.register(GUIRegisterPacket.class);
		kryo.register(InitializeGUIPacket.class);
		kryo.register(NewPassengerPacket.class);

		kryo.register(ControllerRegisterPacket.class);
		kryo.register(ArrayList.class);
		kryo.register(ElevatorGoingUp.class);
		kryo.register(ElevatorGoingDown.class);
		kryo.register(ElevatorStill.class);
		kryo.register(DoorClosing.class);
		kryo.register(DoorOpening.class);
		kryo.register(DoorStill.class);
		kryo.register(Integer.class);
	}
}
