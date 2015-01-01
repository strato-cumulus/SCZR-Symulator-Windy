package com.sczr.symulator_windy.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.packets.SetStagePacket;
import com.sczr.symulator_windy.state.Direction;

public class SerializationList 
{
	public static void register(Kryo kryo)
	{
		kryo.register(int.class);
		kryo.register(Direction.class);
		kryo.register(Packet.class);
		kryo.register(ElevatorCallPacket.class);
		kryo.register(SetStagePacket.class);
	}
}
