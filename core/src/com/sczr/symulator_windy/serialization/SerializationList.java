package com.sczr.symulator_windy.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.packets.GUIpackets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ControllerRegisterPacket;
import com.sczr.symulator_windy.packets.controllerpackets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.controllerpackets.ChangeDestinationFloorPacket;
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
	
	}
}
