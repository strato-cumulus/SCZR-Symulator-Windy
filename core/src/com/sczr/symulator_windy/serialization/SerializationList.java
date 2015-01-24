package com.sczr.symulator_windy.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket_;
import com.sczr.symulator_windy.packets.ElevatorCallPacket_;
import com.sczr.symulator_windy.packets.ElevatorStateInfoPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.ui.elevator.ElevatorCallButton.Direction;

public class SerializationList 
{
	public static void register(Kryo kryo)
	{
		kryo.register(int.class);
		kryo.register(Direction.class);
		kryo.register(Packet.class);
		kryo.register(DispatchElevatorPacket_.class);
		kryo.register(ElevatorCallPacket_.class);
		kryo.register(ElevatorStateInfoPacket.class);
		kryo.register(ElevatorCallPacket_.class);
		
	}
}
