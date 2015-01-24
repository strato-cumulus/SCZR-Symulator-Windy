package com.sczr.symulator_windy.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.ElevatorStatePacket;
import com.sczr.symulator_windy.packets.FloorsStopElevatorPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton.Direction;

public class SerializationList 
{
	public static void register(Kryo kryo)
	{
		kryo.register(int.class);
		kryo.register(Direction.class);
		kryo.register(Packet.class);
		kryo.register(DispatchElevatorPacket.class);
		kryo.register(ElevatorCallPacket.class);
		kryo.register(ElevatorStateInfoPacket.class);
		kryo.register(ElevatorCallPacket.class);
		kryo.register(FloorsStopElevatorPacket.class);
		kryo.register(ElevatorStatePacket.class);
	}
}
