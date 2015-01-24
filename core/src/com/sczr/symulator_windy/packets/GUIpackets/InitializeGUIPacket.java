package com.sczr.symulator_windy.packets.GUIpackets;

public class InitializeGUIPacket {
	public Integer storeyNumber;
	public Integer storeyHeight;
	
	public InitializeGUIPacket() {}
	
	public InitializeGUIPacket(int storeyNumber, int storeyHeight){
		this.storeyHeight = storeyHeight;
		this.storeyNumber = storeyNumber;
	}
	
}
